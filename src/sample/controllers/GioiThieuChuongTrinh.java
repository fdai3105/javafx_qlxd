package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GioiThieuChuongTrinh {
    public Button btclose;

    public void btcloseClick(ActionEvent actionEvent) {
        ((Stage)btclose.getScene().getWindow()).close();
    }
}
