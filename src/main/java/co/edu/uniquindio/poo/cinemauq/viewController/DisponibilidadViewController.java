package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import co.edu.uniquindio.poo.cinemauq.controller.FuncionController;
import co.edu.uniquindio.poo.cinemauq.model.Asiento;
import co.edu.uniquindio.poo.cinemauq.model.EstadoAsiento;
import co.edu.uniquindio.poo.cinemauq.model.Funcion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

public class DisponibilidadViewController implements VistaController {
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

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblDisponibles;

    @FXML
    private Label lblOcupados;

    @FXML
    private Label lblAsiento;

    @FXML
    private TilePane tileAsientos;

    @FXML
    void initialize() {
        funcionController = new FuncionController(App.cine);
        cmbFuncion.getItems().addAll(funcionController.obtenerFuncionesProgramadas());
        cmbFuncion.valueProperty().addListener((obs, viejo, nuevo) -> cargarAsientos(nuevo));
        if (!cmbFuncion.getItems().isEmpty()) {
            cmbFuncion.getSelectionModel().selectFirst();
        }
    }

    private void cargarAsientos(Funcion funcion) {
        tileAsientos.getChildren().clear();
        lblAsiento.setText("");
        if (funcion == null) {
            lblTotal.setText("0");
            lblDisponibles.setText("0");
            lblOcupados.setText("0");
            return;
        }
        lblTotal.setText("" + funcion.getCantidadAsientos());
        lblDisponibles.setText("" + funcion.getCantidadAsientosDisponibles());
        lblOcupados.setText("" + funcion.getCantidadAsientosOcupados());
        for (Asiento asiento : funcion.getSala().getAsientos()) {
            Button boton = new Button(asiento.getCodigo());
            boton.setMinWidth(50);
            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                boton.setDisable(true);
            } else {
                boton.setOnAction(evento -> lblAsiento.setText("Asiento " + asiento.getCodigo() + "   ·   " + asiento.getEstado()));
            }
            tileAsientos.getChildren().add(boton);
        }
    }
}
