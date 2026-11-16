package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.FuncionController;
import co.edu.uniquindio.poo.cinemauq.controller.ReporteController;
import co.edu.uniquindio.poo.cinemauq.model.Funcion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ReportesViewController implements VistaController {
    App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onVolver() {
        app.abrirMenu();
    }


    ReporteController reporteController;
    FuncionController funcionController;
    ObservableList<Funcion> listaFunciones = FXCollections.observableArrayList();

    @FXML
    private Label lblEntradas;

    @FXML
    private Label lblIngresos;

    @FXML
    private Label lblPelicula;

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private Label lblOcupacion;

    @FXML
    private DatePicker dtpFecha;

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
    private TableColumn<Funcion, String> tbcHora;

    @FXML
    private TableColumn<Funcion, String> tbcDisponibles;

    @FXML
    void initialize() {
        reporteController = new ReporteController(App.cine);
        funcionController = new FuncionController(App.cine);
        lblEntradas.setText("" + reporteController.totalEntradasVendidas());
        lblIngresos.setText(Formato.dinero(reporteController.totalIngresos()));
        lblPelicula.setText(reporteController.peliculaMasVendida());
        cmbFuncion.getItems().addAll(funcionController.obtenerListaFunciones());
        cmbFuncion.valueProperty().addListener((obs, viejo, nuevo) -> mostrarOcupacion(nuevo));
        if (!cmbFuncion.getItems().isEmpty()) {
            cmbFuncion.getSelectionModel().selectFirst();
        }
        initTabla();
        dtpFecha.setValue(LocalDate.now());
        buscarPorFecha();
    }

    private void mostrarOcupacion(Funcion funcion) {
        if (funcion == null) {
            lblOcupacion.setText("");
            return;
        }
        lblOcupacion.setText(reporteController.reporteOcupacion(funcion.getCodigo()));
    }

    private void initTabla() {
        tblFunciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tbcCodigo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCodigo()));
        tbcPelicula.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getPelicula().getNombre()));
        tbcSala.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getSala().getNombre()));
        tbcHora.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getHora()));
        tbcDisponibles.setCellValueFactory(celda -> new SimpleStringProperty("" + celda.getValue().getCantidadAsientosDisponibles()));
        tblFunciones.setItems(listaFunciones);
    }

    @FXML
    void onBuscar() {
        buscarPorFecha();
    }

    private void buscarPorFecha() {
        LocalDate fecha = dtpFecha.getValue();
        listaFunciones.clear();
        if (fecha == null) {
            lblResumen.setText("Selecciona una fecha");
            return;
        }
        listaFunciones.addAll(reporteController.funcionesPorFecha(fecha));
        lblResumen.setText(listaFunciones.size() + " funciones el " + fecha);
    }
}
