package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Main;
import sample.models.DataSerialization;
import sample.models.NhanVien;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DangNhapController {
    public static String aidangdangnhap = "";

    public TextField tfsdt;
    public TextField tfmk;

    public void bthuyClick(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void btdangnhapClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String sdt = tfsdt.getText();
        String mk = tfmk.getText();
        if (loginAdmin(sdt, mk) || loginUser(sdt, mk)) {
            tfsdt.getScene().getWindow().hide();
        }
    }

    public boolean loginAdmin(String sdt, String mk) throws IOException {
        InputStream in = getClass().getResourceAsStream("/sample/config.properties");
        Properties prop = new Properties();
        prop.load(in);
        if (prop.getProperty("username").compareTo(sdt) == 0 && prop.getProperty("password").compareTo(mk) == 0) {
            aidangdangnhap = sdt;
            return true;
        }
        return false;
    }

    public boolean loginUser(String sdt, String mk) throws IOException, ClassNotFoundException {
        FileInputStream in = new FileInputStream("path.properties");
        Properties prop = new Properties();
        prop.load(in);
        Main.file = new File(prop.getProperty("path"));
        Main.temp_data = DataSerialization.readData(Main.file);
        for (NhanVien nv : Main.temp_data.getNhanvien()) {
            if (nv.getSdt_nv().compareTo(sdt) == 0 && nv.getMk_nv().compareTo(mk) == 0) {
                aidangdangnhap = nv.getId_nv() + "";
                Controller.ketquatimkiem = Main.temp_data.getHanghoa();
                return true;
            }
        }
        return false;
    }

    public void onEnterPressed(KeyEvent keyEvent) throws IOException, ClassNotFoundException {
        if (keyEvent.getCode() == KeyCode.ENTER)
            btdangnhapClick(null);
    }
}
