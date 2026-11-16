package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.ClienteController;
import co.edu.uniquindio.poo.cinemauq.model.Cliente;
import co.edu.uniquindio.poo.cinemauq.model.TipoCliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdministrarClientesViewController implements VistaController {
    App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onVolver() {
        app.abrirMenu();
    }

    ClienteController clienteController;
    ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    Cliente seleccionado;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<TipoCliente> cmbTipoCliente;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, String> tbcIdentificacion;

    @FXML
    private TableColumn<Cliente, String> tbcNombre;

    @FXML
    private TableColumn<Cliente, String> tbcTipoCliente;

    @FXML
    private TableColumn<Cliente, String> tbcValor;

    @FXML
    void initialize() {
        clienteController = new ClienteController(App.cine);
        cmbTipoCliente.getItems().addAll(TipoCliente.values());
        initTabla();
        listaClientes.addAll(clienteController.obtenerListaClientes());
        tblClientes.setItems(listaClientes);
        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            seleccionado = nuevo;
            mostrarCliente(nuevo);
        });
    }

    private void initTabla() {
        tblClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcIdentificacion.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getIdentificacion()));
        tbcNombre.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getNombre()));
        tbcTipoCliente.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getTipoCliente()));
        tbcValor.setCellValueFactory(celda -> new SimpleStringProperty(Formato.dinero(celda.getValue().getTipoCliente().getPrecio())));
    }

    private void mostrarCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }
        txtIdentificacion.setText(cliente.getIdentificacion());
        txtNombre.setText(cliente.getNombre());
        cmbTipoCliente.setValue(cliente.getTipoCliente());
    }

    @FXML
    void onAgregar() {
        try {
            Cliente cliente = construirCliente();
            if (clienteController.crearCliente(cliente)) {
                listaClientes.add(cliente);
                lblMensaje.setText("Cliente " + cliente.getNombre() + " registrado.");
                limpiarCampos();
            } else {
                lblMensaje.setText("Ya existe un cliente con la identificación " + cliente.getIdentificacion());
            }
        } catch (RuntimeException ex) {
            lblMensaje.setText("Revisa los datos: " + ex.getMessage());
        }
    }

    @FXML
    void onActualizar() {
        if (seleccionado == null) {
            lblMensaje.setText("Selecciona un cliente de la tabla.");
            return;
        }
        try {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setTipoCliente(cmbTipoCliente.getValue());
            clienteController.actualizarCliente(seleccionado);
            tblClientes.refresh();
            lblMensaje.setText("Cliente " + seleccionado.getIdentificacion() + " actualizado.");
        } catch (RuntimeException ex) {
            lblMensaje.setText("Revisa los datos: " + ex.getMessage());
        }
    }

    @FXML
    void onLimpiar() {
        tblClientes.getSelectionModel().clearSelection();
        seleccionado = null;
        limpiarCampos();
        lblMensaje.setText("");
    }

    private Cliente construirCliente() {
        String identificacion = txtIdentificacion.getText().trim();
        String nombre = txtNombre.getText().trim();
        TipoCliente tipoCliente = cmbTipoCliente.getValue();
        if (identificacion.isEmpty() || nombre.isEmpty() || tipoCliente == null) {
            throw new IllegalArgumentException("Completa identificación, nombre y tipo de cliente");
        }
        return new Cliente(identificacion, nombre, tipoCliente);
    }

    private void limpiarCampos() {
        txtIdentificacion.clear();
        txtNombre.clear();
        cmbTipoCliente.setValue(null);
    }
}
