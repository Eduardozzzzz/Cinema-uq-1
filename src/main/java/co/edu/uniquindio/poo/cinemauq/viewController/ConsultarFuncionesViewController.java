package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.FuncionController;
import co.edu.uniquindio.poo.cinemauq.model.Funcion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsultarFuncionesViewController implements VistaController {
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
    ObservableList<Funcion> listaFunciones = FXCollections.observableArrayList();

    @FXML
    private Label lblResumen;

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
    private TableColumn<Funcion, String> tbcDisponibles;

    @FXML
    void initialize() {
        funcionController = new FuncionController(App.cine);
        initTabla();
        listaFunciones.addAll(funcionController.obtenerFuncionesProgramadas());
        tblFunciones.setItems(listaFunciones);
        lblResumen.setText(listaFunciones.size() + " funciones programadas");
    }

    private void initTabla() {
        tblFunciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcCodigo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCodigo()));
        tbcPelicula.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getPelicula().getNombre()));
        tbcSala.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getSala().getNombre()));
        tbcFecha.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getFecha()));
        tbcHora.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getHora()));
        tbcValor.setCellValueFactory(celda -> new SimpleStringProperty(Formato.dinero(celda.getValue().getValorBase())));
        tbcDisponibles.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getCantidadAsientosDisponibles()));
    }
}
