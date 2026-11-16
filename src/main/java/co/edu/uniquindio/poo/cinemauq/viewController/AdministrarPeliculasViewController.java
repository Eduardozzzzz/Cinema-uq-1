package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.PeliculaController;
import co.edu.uniquindio.poo.cinemauq.model.Clasificacion;
import co.edu.uniquindio.poo.cinemauq.model.EstadoPelicula;
import co.edu.uniquindio.poo.cinemauq.model.Pelicula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdministrarPeliculasViewController implements VistaController {
    App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onVolver() {
        app.abrirMenu();
    }


    PeliculaController peliculaController;
    ObservableList<Pelicula> listaPeliculas = FXCollections.observableArrayList();
    Pelicula seleccionada;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtDuracion;

    @FXML
    private ComboBox<Clasificacion> cmbClasificacion;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Pelicula> tblPeliculas;

    @FXML
    private TableColumn<Pelicula, String> tbcCodigo;

    @FXML
    private TableColumn<Pelicula, String> tbcNombre;

    @FXML
    private TableColumn<Pelicula, String> tbcGenero;

    @FXML
    private TableColumn<Pelicula, String> tbcDuracion;

    @FXML
    private TableColumn<Pelicula, String> tbcClasificacion;

    @FXML
    private TableColumn<Pelicula, String> tbcEstado;

    @FXML
    void initialize() {
        peliculaController = new PeliculaController(App.cine);
        cmbClasificacion.getItems().addAll(Clasificacion.values());
        initTabla();
        listaPeliculas.addAll(peliculaController.obtenerListaPeliculas());
        tblPeliculas.setItems(listaPeliculas);
        tblPeliculas.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            seleccionada = nuevo;
            mostrarPelicula(nuevo);
        });
    }

    private void initTabla() {
        tblPeliculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcCodigo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getNombre()));
        tbcGenero.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getGenero()));
        tbcDuracion.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getDuracion()));
        tbcClasificacion.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getClasificacion()));
        tbcEstado.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getEstado()));
    }

    private void mostrarPelicula(Pelicula pelicula) {
        if (pelicula == null) {
            return;
        }
        txtCodigo.setText(pelicula.getCodigo());
        txtNombre.setText(pelicula.getNombre());
        txtGenero.setText(pelicula.getGenero());
        txtDuracion.setText("" + pelicula.getDuracion());
        cmbClasificacion.setValue(pelicula.getClasificacion());
    }

    @FXML
    void onAgregar() {
        try {
            Pelicula pelicula = construirPelicula();
            if (peliculaController.crearPelicula(pelicula)) {
                listaPeliculas.add(pelicula);
                lblMensaje.setText("Película " + pelicula.getNombre() + " registrada.");
                limpiarCampos();
            } else {
                lblMensaje.setText("Ya existe una película con el código " + pelicula.getCodigo());
            }
        } catch (RuntimeException ex) {
            lblMensaje.setText("Revisa los datos: " + ex.getMessage());
        }
    }

    @FXML
    void onActualizar() {
        if (seleccionada == null) {
            lblMensaje.setText("Selecciona una película de la tabla.");
            return;
        }
        try {
            seleccionada.setNombre(txtNombre.getText());
            seleccionada.setGenero(txtGenero.getText());
            seleccionada.setDuracion(Integer.parseInt(txtDuracion.getText()));
            seleccionada.setClasificacion(cmbClasificacion.getValue());
            peliculaController.actualizarPelicula(seleccionada);
            tblPeliculas.refresh();
            lblMensaje.setText("Película " + seleccionada.getCodigo() + " actualizada.");
        } catch (RuntimeException ex) {
            lblMensaje.setText("Revisa los datos: " + ex.getMessage());
        }
    }

    @FXML
    void onDeshabilitar() {
        if (seleccionada == null) {
            lblMensaje.setText("Selecciona una película de la tabla.");
            return;
        }
        peliculaController.deshabilitarPelicula(seleccionada.getCodigo());
        tblPeliculas.refresh();
        lblMensaje.setText("Película " + seleccionada.getCodigo() + " deshabilitada.");
    }

    @FXML
    void onLimpiar() {
        tblPeliculas.getSelectionModel().clearSelection();
        seleccionada = null;
        limpiarCampos();
        lblMensaje.setText("");
    }

    private Pelicula construirPelicula() {
        int duracion = Integer.parseInt(txtDuracion.getText());
        Pelicula pelicula = new Pelicula(txtCodigo.getText(), txtNombre.getText(), txtGenero.getText(), duracion, cmbClasificacion.getValue());
        pelicula.setEstado(EstadoPelicula.DISPONIBLE);
        return pelicula;
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtGenero.clear();
        txtDuracion.clear();
        cmbClasificacion.setValue(null);
    }
}
