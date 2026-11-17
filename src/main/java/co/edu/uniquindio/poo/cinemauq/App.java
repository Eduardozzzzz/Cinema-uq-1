package co.edu.uniquindio.poo.cinemauq;

import co.edu.uniquindio.poo.cinemauq.model.*;
import co.edu.uniquindio.poo.cinemauq.viewController.VistaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class App extends Application {

    private Stage primaryStage;
    public static Cine cine = new Cine("Cinemauq", "01", "Universidad del quindio");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //Pelicula pelicula = new Pelicula("P1", "Pelicula de prueba", "Accion", 30, Clasificacion.MAYORES_12);
        //cine.registrarPelicula(pelicula);
        //Sala sala = new Sala("S1", "Sala de prueba", 20);
        //cine.registrarSala(sala);
        //cine.registrarFuncion(new Funcion("F1", pelicula, sala, LocalDate.now(), LocalTime.of(14, 30), 15000));
        //Cliente cliente = new Cliente("1091", "Cristopher", TipoCliente.ESTUDIANTE);
        //cine.registrarCliente(cliente);
        //cine.venderEntrada(cliente, "F1", "A1");
        abrirMenu();
    }

    public void abrirMenu() {
        mostrarVista("principal.fxml", "Cinemauq");
    }

    public void abrirVista(String archivo, String titulo) {
        mostrarVista(archivo, "Cinemauq - " + titulo);
    }

    private void mostrarVista(String archivo, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(archivo));
            Parent raiz = loader.load();
            VistaController controlador = loader.getController();
            controlador.setApp(this);
            primaryStage.setTitle(titulo);
            primaryStage.setScene(new Scene(raiz, 1100, 700));
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo abrir la vista " + archivo, e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
