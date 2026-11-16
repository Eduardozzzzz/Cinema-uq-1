package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.FuncionController;
import co.edu.uniquindio.poo.cinemauq.controller.PeliculaController;
import co.edu.uniquindio.poo.cinemauq.controller.SalaController;
import co.edu.uniquindio.poo.cinemauq.model.Funcion;
import co.edu.uniquindio.poo.cinemauq.model.Pelicula;
import co.edu.uniquindio.poo.cinemauq.model.Sala;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdministrarFuncionesViewController implements VistaController {
    App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onVolver() {
        app.abrirMenu();
    }


    FuncionController funcionController;
    PeliculaController peliculaController;
    SalaController salaController;
    ObservableList<Funcion> listaFunciones = FXCollections.observableArrayList();
    Funcion seleccionada;

    @FXML
    private TextField txtCodigo;

    @FXML
    private ComboBox<Pelicula> cmbPelicula;

    @FXML
    private ComboBox<Sala> cmbSala;

    @FXML
    private DatePicker dtpFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtValor;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Funcion> tblFunciones;

    @FXML
    private TableColumn<Funcion, String> tbcCodigo;

    @FXML
    private TableColumn<Funcion, String> tbcPelicula;

    @FXML
    private TableColumn<Funcion, String> tbcSala;

    @FXML
    private TableColumn<Funcion, String> tbcFecha;

    @FXML
    private TableColumn<Funcion, String> tbcHora;

    @FXML
    private TableColumn<Funcion, String> tbcValor;

    @FXML
    private TableColumn<Funcion, String> tbcEstado;

    @FXML
    void initialize() {
        funcionController = new FuncionController(App.cine);
        peliculaController = new PeliculaController(App.cine);
        salaController = new SalaController(App.cine);
        cmbPelicula.getItems().addAll(peliculaController.obtenerListaPeliculas());
        cmbSala.getItems().addAll(salaController.obtenerListaSalas());
        dtpFecha.setValue(LocalDate.now());
        initTabla();
        listaFunciones.addAll(funcionController.obtenerListaFunciones());
        tblFunciones.setItems(listaFunciones);
        tblFunciones.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            seleccionada = nuevo;
            mostrarFuncion(nuevo);
        });
    }

    private void initTabla() {
        tblFunciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcCodigo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCodigo()));
        tbcPelicula.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getPelicula().getNombre()));
        tbcSala.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getSala().getNombre()));
        tbcFecha.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getFecha()));
        tbcHora.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getHora()));
        tbcValor.setCellValueFactory(celda -> new SimpleStringProperty(Formato.dinero(celda.getValue().getValorBase())));
        tbcEstado.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getEstado()));
    }

    private void mostrarFuncion(Funcion funcion) {
        if (funcion == null) {
            return;
        }
        txtCodigo.setText(funcion.getCodigo());
        cmbPelicula.setValue(funcion.getPelicula());
        cmbSala.setValue(funcion.getSala());
        dtpFecha.setValue(funcion.getFecha());
        txtHora.setText("" + funcion.getHora());
        txtValor.setText("" + (int) funcion.getValorBase());
    }

    @FXML
    void onProgramar() {
        try {
            Funcion funcion = construirFuncion();
            if (funcionController.crearFuncion(funcion)) {
                listaFunciones.add(funcion);
                lblMensaje.setText("Función " + funcion.getCodigo() + " programada en " + funcion.getSala().getNombre());
                limpiarCampos();
            } else {
                lblMensaje.setText("Ya existe una función con el código " + funcion.getCodigo());
            }
        } catch (RuntimeException ex) {
            lblMensaje.setText(ex.getMessage());
        }
    }

    @FXML
    void onActualizar() {
        if (seleccionada == null) {
            lblMensaje.setText("Selecciona una función de la tabla.");
            return;
        }
        try {
            seleccionada.setPelicula(cmbPelicula.getValue());
            seleccionada.setSala(cmbSala.getValue());
            seleccionada.setFecha(dtpFecha.getValue());
            seleccionada.setHora(LocalTime.parse(txtHora.getText()));
            seleccionada.setValorBase(Double.parseDouble(txtValor.getText()));
            funcionController.actualizarFuncion(seleccionada);
            tblFunciones.refresh();
            lblMensaje.setText("Función " + seleccionada.getCodigo() + " actualizada.");
        } catch (RuntimeException ex) {
            lblMensaje.setText("Revisa los datos: " + ex.getMessage());
        }
    }

    @FXML
    void onEliminar() {
        if (seleccionada == null) {
            lblMensaje.setText("Selecciona una función de la tabla.");
            return;
        }
        funcionController.eliminarFuncion(seleccionada.getCodigo());
        listaFunciones.remove(seleccionada);
        lblMensaje.setText("Función " + seleccionada.getCodigo() + " eliminada.");
        onLimpiar();
    }

    @FXML
    void onLimpiar() {
        tblFunciones.getSelectionModel().clearSelection();
        seleccionada = null;
        limpiarCampos();
        lblMensaje.setText("");
    }

    private Funcion construirFuncion() {
        Pelicula pelicula = cmbPelicula.getValue();
        Sala sala = cmbSala.getValue();
        if (pelicula == null || sala == null) {
            throw new IllegalArgumentException("Selecciona la película y la sala");
        }
        LocalTime hora = LocalTime.parse(txtHora.getText());
        double valor = Double.parseDouble(txtValor.getText());
        return new Funcion(txtCodigo.getText(), pelicula, sala, dtpFecha.getValue(), hora, valor);
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtHora.clear();
        txtValor.clear();
        cmbPelicula.setValue(null);
        cmbSala.setValue(null);
    }
}
