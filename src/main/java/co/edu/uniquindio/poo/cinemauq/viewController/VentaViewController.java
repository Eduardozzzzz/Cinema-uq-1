package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.ClienteController;
import co.edu.uniquindio.poo.cinemauq.controller.FuncionController;
import co.edu.uniquindio.poo.cinemauq.controller.VentaController;
import co.edu.uniquindio.poo.cinemauq.model.Asiento;
import co.edu.uniquindio.poo.cinemauq.model.Cliente;
import co.edu.uniquindio.poo.cinemauq.model.Funcion;
import co.edu.uniquindio.poo.cinemauq.model.Venta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VentaViewController implements VistaController {
    App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onVolver() {
        app.abrirMenu();
    }


    VentaController ventaController;
    ClienteController clienteController;
    FuncionController funcionController;
    ObservableList<Venta> listaVentas = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private ComboBox<Asiento> cmbAsiento;

    @FXML
    private Label lblValor;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Venta> tblVentas;

    @FXML
    private TableColumn<Venta, String> tbcCodigo;

    @FXML
    private TableColumn<Venta, String> tbcCliente;

    @FXML
    private TableColumn<Venta, String> tbcPelicula;

    @FXML
    private TableColumn<Venta, String> tbcAsiento;

    @FXML
    private TableColumn<Venta, String> tbcValor;

    @FXML
    private TableColumn<Venta, String> tbcEstado;

    @FXML
    void initialize() {
        ventaController = new VentaController(App.cine);
        clienteController = new ClienteController(App.cine);
        funcionController = new FuncionController(App.cine);
        cmbCliente.getItems().addAll(clienteController.obtenerListaClientes());
        cmbFuncion.getItems().addAll(funcionController.obtenerFuncionesProgramadas());
        initTabla();
        listaVentas.addAll(ventaController.obtenerListaVentas());
        tblVentas.setItems(listaVentas);
        lblValor.setText(Formato.dinero(0));
        cmbCliente.valueProperty().addListener((obs, viejo, nuevo) -> actualizarValor(nuevo));
        cmbFuncion.valueProperty().addListener((obs, viejo, nuevo) -> cargarAsientos(nuevo));
    }

    private void initTabla() {
        tblVentas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcCodigo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCodigo()));
        tbcCliente.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCliente().getNombre()));
        tbcPelicula.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getFuncion().getPelicula().getNombre()));
        tbcAsiento.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getAsiento().getCodigo()));
        tbcValor.setCellValueFactory(celda -> new SimpleStringProperty(Formato.dinero(celda.getValue().getValorPagado())));
        tbcEstado.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getEstado()));
    }

    private void cargarAsientos(Funcion funcion) {
        cmbAsiento.getItems().clear();
        if (funcion != null) {
            cmbAsiento.getItems().addAll(funcion.getAsientosDisponibles());
        }
    }

    private void actualizarValor(Cliente cliente) {
        if (cliente == null) {
            lblValor.setText("$0");
            return;
        }
        lblValor.setText(Formato.dinero(cliente.getTipoCliente().getPrecio()));
    }

    @FXML
    void onVender() {
        Cliente cliente = cmbCliente.getValue();
        Funcion funcion = cmbFuncion.getValue();
        Asiento asiento = cmbAsiento.getValue();
        if (cliente == null || funcion == null || asiento == null) {
            lblMensaje.setText("Selecciona el cliente, la función y el asiento.");
            return;
        }
        try {
            Venta venta = ventaController.venderEntrada(cliente, funcion.getCodigo(), asiento.getCodigo());
            listaVentas.add(venta);
            cargarAsientos(funcion);
            cmbAsiento.getSelectionModel().clearSelection();
            lblMensaje.setText("Venta " + venta.getCodigo() + " registrada por " + Formato.dinero(venta.getValorPagado()));
        } catch (RuntimeException ex) {
            lblMensaje.setText(ex.getMessage());
        }
    }
}
