package co.edu.uniquindio.poo.cinemauq.viewController;

import co.edu.uniquindio.poo.cinemauq.App;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class PrimaryController implements VistaController {

    App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onVenta() {
        app.abrirVista("venta.fxml", "Venta de entrada");
    }

    @FXML
    void onCancelarVenta() {
        app.abrirVista("cancelarVenta.fxml", "Cancelar venta");
    }

    @FXML
    void onConsultarPeliculas() {
        app.abrirVista("consultarPeliculas.fxml", "Consultar películas");
    }

    @FXML
    void onConsultarFunciones() {
        app.abrirVista("consultarFunciones.fxml", "Consultar funciones");
    }

    @FXML
    void onDisponibilidad() {
        app.abrirVista("disponibilidad.fxml", "Disponibilidad");
    }

    @FXML
    void onAdministrarPeliculas() {
        app.abrirVista("administrarPeliculas.fxml", "Administrar películas");
    }

    @FXML
    void onAdministrarFunciones() {
        app.abrirVista("administrarFunciones.fxml", "Administrar funciones");
    }

    @FXML
    void onAdministrarSalas() {
        app.abrirVista("administrarSalas.fxml", "Administrar salas");
    }

    @FXML
    void onAdministrarClientes() {
        app.abrirVista("clientes.fxml", "Administrar clientes");
    }

    @FXML
    void onReportes() {
        app.abrirVista("reportes.fxml", "Reportes");
    }

    @FXML
    void onSalir() {
        Platform.exit();
    }
}
