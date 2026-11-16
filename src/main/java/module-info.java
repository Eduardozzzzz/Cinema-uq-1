module co.edu.uniquindio.poo.cinemauq {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.cinemauq to javafx.fxml;
    exports co.edu.uniquindio.poo.cinemauq;
    exports co.edu.uniquindio.poo.cinemauq.model;
    exports co.edu.uniquindio.poo.cinemauq.controller;
    exports co.edu.uniquindio.poo.cinemauq.viewController;
    opens co.edu.uniquindio.poo.cinemauq.viewController to javafx.fxml;
}
