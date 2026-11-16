package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.SalaController;
import co.edu.uniquindio.poo.cinemauq.model.Sala;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdministrarSalasViewController implements VistaController {
    App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onVolver() {
        app.abrirMenu();
    }


    SalaController salaController;
    ObservableList<Sala> listaSalas = FXCollections.observableArrayList();
    Sala seleccionada;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Sala> tblSalas;

    @FXML
    private TableColumn<Sala, String> tbcCodigo;

    @FXML
    private TableColumn<Sala, String> tbcNombre;

    @FXML
    private TableColumn<Sala, String> tbcCapacidad;

    @FXML
    private TableColumn<Sala, String> tbcAsientos;

    @FXML
    private TableColumn<Sala, String> tbcEstado;

    @FXML
    void initialize() {
        salaController = new SalaController(App.cine);
        initTabla();
        listaSalas.addAll(salaController.obtenerListaSalas());
        tblSalas.setItems(listaSalas);
        tblSalas.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            seleccionada = nuevo;
            mostrarSala(nuevo);
        });
    }

    private void initTabla() {
        tblSalas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcCodigo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getNombre()));
        tbcCapacidad.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getCapacidad()));
        tbcAsientos.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getAsientos().size()));
        tbcEstado.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getEstado()));
    }

    private void mostrarSala(Sala sala) {
        if (sala == null) {
            return;
        }
        txtCodigo.setText(sala.getCodigo());
        txtNombre.setText(sala.getNombre());
        txtCapacidad.setText("" + sala.getCapacidad());
    }

    @FXML
    void onAgregar() {
        try {
            Sala sala = new Sala(txtCodigo.getText(), txtNombre.getText(), Integer.parseInt(txtCapacidad.getText()));
            if (salaController.crearSala(sala)) {
                listaSalas.add(sala);
                lblMensaje.setText("Sala " + sala.getNombre() + " registrada con " + sala.getAsientos().size() + " asientos.");
                limpiarCampos();
            } else {
                lblMensaje.setText("Ya existe una sala con el código " + sala.getCodigo());
            }
        } catch (RuntimeException ex) {
            lblMensaje.setText("Revisa los datos: " + ex.getMessage());
        }
    }

    @FXML
    void onActualizar() {
        if (seleccionada == null) {
            lblMensaje.setText("Selecciona una sala de la tabla.");
            return;
        }
        try {
            seleccionada.setNombre(txtNombre.getText());
            seleccionada.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            seleccionada.generarAsientos();
            salaController.actualizarSala(seleccionada);
            tblSalas.refresh();
            lblMensaje.setText("Sala " + seleccionada.getCodigo() + " actualizada.");
        } catch (RuntimeException ex) {
            lblMensaje.setText("Revisa los datos: " + ex.getMessage());
        }
    }

    @FXML
    void onDeshabilitar() {
        if (seleccionada == null) {
            lblMensaje.setText("Selecciona una sala de la tabla.");
            return;
        }
        salaController.deshabilitarSala(seleccionada.getCodigo());
        tblSalas.refresh();
        lblMensaje.setText("Sala " + seleccionada.getCodigo() + " fuera de servicio.");
    }

    @FXML
    void onLimpiar() {
        tblSalas.getSelectionModel().clearSelection();
        seleccionada = null;
        limpiarCampos();
        lblMensaje.setText("");
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtCapacidad.clear();
    }
}
