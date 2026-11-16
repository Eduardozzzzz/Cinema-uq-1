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
    public static Cine cine = new Cine("CINEMAUQ", "890.123.456-7", "Universidad del Quindío");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        inicializarData();
        abrirMenu();
    }

    public void abrirMenu() {
        mostrarVista("principal.fxml", "CINEMAUQ");
    }

    public void abrirVista(String archivo, String titulo) {
        mostrarVista(archivo, "CINEMAUQ - " + titulo);
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

    private void inicializarData() {
        Pelicula algoritmo = new Pelicula("P1", "El Último Algoritmo", "Ciencia ficción", 118, Clasificacion.MAYORES_12);
        Pelicula sombras = new Pelicula("P2", "Sombras del Código", "Suspenso", 104, Clasificacion.MAYORES_15);
        Pelicula bits = new Pelicula("P3", "Bits y Bytes", "Animación", 89, Clasificacion.TODOS);
        cine.registrarPelicula(algoritmo);
        cine.registrarPelicula(sombras);
        cine.registrarPelicula(bits);

        Sala sala1 = new Sala("S1", "Sala 1", 20);
        Sala sala2 = new Sala("S2", "Sala 2", 30);
        Sala sala3 = new Sala("S3", "Sala 3", 40);
        cine.registrarSala(sala1);
        cine.registrarSala(sala2);
        cine.registrarSala(sala3);

        cine.registrarFuncion(new Funcion("F1", algoritmo, sala1, LocalDate.now(), LocalTime.of(14, 30), 15000));
        cine.registrarFuncion(new Funcion("F2", sombras, sala2, LocalDate.now(), LocalTime.of(17, 0), 15000));
        cine.registrarFuncion(new Funcion("F3", bits, sala3, LocalDate.now(), LocalTime.of(16, 0), 15000));
        cine.registrarFuncion(new Funcion("F4", algoritmo, sala2, LocalDate.now().plusDays(1), LocalTime.of(19, 0), 15000));

        Cliente cristopher = new Cliente("1094000001", "Cristopher Tapasco", TipoCliente.ESTUDIANTE);
        Cliente eduardo = new Cliente("1094000002", "Eduardo Prada", TipoCliente.GENERAL);
        Cliente maria = new Cliente("1094000003", "María Fernanda Gómez", TipoCliente.ADULTO_MAYOR);
        Cliente samuel = new Cliente("1094000004", "Samuel Niño", TipoCliente.NIÑO);
        cine.registrarCliente(cristopher);
        cine.registrarCliente(eduardo);
        cine.registrarCliente(maria);
        cine.registrarCliente(samuel);

        cine.venderEntrada(cristopher, "F1", "A1");
        cine.venderEntrada(cristopher, "F1", "A2");
        cine.venderEntrada(eduardo, "F1", "A3");
        cine.venderEntrada(maria, "F2", "B1");
        cine.venderEntrada(samuel, "F3", "A5");
    }

    public static void main(String[] args) {
        launch();
    }
}
