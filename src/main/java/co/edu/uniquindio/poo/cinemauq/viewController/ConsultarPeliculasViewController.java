package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.PeliculaController;
import co.edu.uniquindio.poo.cinemauq.model.Pelicula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsultarPeliculasViewController implements VistaController {
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

    @FXML
    private Label lblResumen;

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
        initTabla();
        listaPeliculas.addAll(peliculaController.obtenerPeliculasDisponibles());
        tblPeliculas.setItems(listaPeliculas);
        lblResumen.setText(listaPeliculas.size() + " películas disponibles en cartelera");
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
}
