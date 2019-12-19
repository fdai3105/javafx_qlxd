package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.models.KhachHang;

import java.io.IOException;

public class ChonKhachHang {
    public static KhachHang khachhanghientai;

    public ListView<KhachHang> lvketqua;
    public TextField tftukhoa;

    public void btthemmoiClick(ActionEvent actionEvent) throws IOException {
        Main.openOption("../views/quanlykhachhang.fxml", "sample/styles/quanlykhachhang.css",
                "Quản Lý Khách Hàng", 425, 380, null).show();
    }

    public void tftukhoaPressed(KeyEvent keyEvent) {
        lvketqua.getItems().clear();
        String kw = tftukhoa.getText();
        for(KhachHang kh:Main.temp_data.getKhachang()){
            if(kh.getTen_kh().toLowerCase().indexOf(kw.toLowerCase()) >= 0 || kh.getSdt_kh().toLowerCase().indexOf(kw.trim().toLowerCase()) >= 0){
                lvketqua.getItems().add(kh);
            }
        }
    }

    public void lvketquaDblClick(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount()>=2) {
            khachhanghientai = lvketqua.getSelectionModel().getSelectedItem();
            ((Stage) lvketqua.getScene().getWindow()).close();
        }
    }

    public void bthoantatClick(MouseEvent mouseEvent) {
        khachhanghientai = lvketqua.getSelectionModel().getSelectedItem();
        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
