package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.VentaController;
import co.edu.uniquindio.poo.cinemauq.model.Venta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CancelarVentaViewController implements VistaController {
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
    ObservableList<Venta> listaVentas = FXCollections.observableArrayList();

    @FXML
    private TableView<Venta> tblVentas;

    @FXML
    private TableColumn<Venta, String> tbcCodigo;

    @FXML
    private TableColumn<Venta, String> tbcCliente;

    @FXML
    private TableColumn<Venta, String> tbcPelicula;

    @FXML
    private TableColumn<Venta, String> tbcFuncion;

    @FXML
    private TableColumn<Venta, String> tbcAsiento;

    @FXML
    private TableColumn<Venta, String> tbcValor;

    @FXML
    private TableColumn<Venta, String> tbcEstado;

    @FXML
    private Label lblMensaje;

    @FXML
    void initialize() {
        ventaController = new VentaController(App.cine);
        initTabla();
        listaVentas.addAll(ventaController.obtenerListaVentas());
        tblVentas.setItems(listaVentas);
    }

    private void initTabla() {
        tblVentas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcCodigo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCodigo()));
        tbcCliente.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCliente().getNombre()));
        tbcPelicula.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getFuncion().getPelicula().getNombre()));
        tbcFuncion.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getFuncion().getCodigo()));
        tbcAsiento.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getAsiento().getCodigo()));
        tbcValor.setCellValueFactory(celda -> new SimpleStringProperty(Formato.dinero(celda.getValue().getValorPagado())));
        tbcEstado.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getEstado()));
    }

    @FXML
    void onCancelarVenta() {
        Venta venta = tblVentas.getSelectionModel().getSelectedItem();
        if (venta == null) {
            lblMensaje.setText("Selecciona una venta de la tabla.");
            return;
        }
        try {
            ventaController.cancelarVenta(venta.getCodigo());
            tblVentas.refresh();
            lblMensaje.setText("Venta " + venta.getCodigo() + " cancelada. El asiento " + venta.getAsiento().getCodigo() + " quedó disponible.");
        } catch (RuntimeException ex) {
            lblMensaje.setText(ex.getMessage());
        }
    }
}
