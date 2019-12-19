package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.models.DanhMucHang;
import sample.models.ImageData;

import java.io.FileNotFoundException;
import java.io.IOException;

public class QuanLyDanhMucController {
    public ImageView imvbieutuong;
    public TextField tftendm;
    public Button btxoa;
    public Button btcapnhat;
    public Button bthuy;

    public void btthemmoidanhmucClick(ActionEvent actionEvent) {
        try {
            if (!Main.temp_data.isExistDanhMuc(tftendm.getText().trim())) {
                DanhMucHang dm = new DanhMucHang(Main.temp_data.getNextIDDM(), tftendm.getText(), new ImageData(imvbieutuong.getImage(), "png"));
                Main.temp_data.getDanhmuchang().add(dm);
                Controller.showAlert("Thêm Danh Mục", "Danh Mục đã được thêm vào", Alert.AlertType.INFORMATION);
                Controller.daluuchua = false;
            } else {
                Controller.showAlert("Thêm Danh Mục", "Lỗi! Tên danh mục này đã tồn tại HOẶC không được phép dùng, xin vui lòng nhập tên khác!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            Controller.showAlert("Thêm Danh Mục", "Lỗi! Xin vui lòng kiểm tra lại dữ liệu đã nhập!", Alert.AlertType.ERROR);
        }

    }

    public void imvbieutuongClick(MouseEvent mouseEvent) throws FileNotFoundException {
        Main.selectImage(imvbieutuong);
    }

    public void imvbieutuongMouseEntered(MouseEvent mouseEvent) {
        Tooltip.install((ImageView) mouseEvent.getTarget(), new Tooltip("Nhấp Chuột Để Chọn Ảnh"));
    }

    public void capNhatTuDong() {
        try {
            if (Main.obj == null) {
                btcapnhat.setDisable(true);
                btxoa.setDisable(true);
            }
            DanhMucHang dm = (DanhMucHang) Main.obj;
            tftendm.setText(dm.getTen_dm());
            imvbieutuong.setImage(dm.getHinhanh().readImageFromArray());
        } catch (Exception e) {
        }

    }

    public void bthuyClick(ActionEvent actionEvent) {
        ((Stage) bthuy.getScene().getWindow()).close();
    }

    public void btcapnhatClick(ActionEvent actionEvent) {
        try {
            DanhMucHang dm = (DanhMucHang) Main.obj;
            Main.temp_data.getDanhMucAt(dm.getId_dm()).setTen_dm(tftendm.getText());
            Main.temp_data.getDanhMucAt(dm.getId_dm()).setHinhanh(new ImageData(imvbieutuong.getImage(), "png"));
            Controller.updatedata = 1;
            Controller.showAlert("Cập Nhật Danh Mục", "Cập nhật thành công!", Alert.AlertType.INFORMATION);
            Controller.daluuchua = false;
        } catch (Exception e) {
            Controller.showAlert("Cập Nhật Danh Mục", "Lỗi! Xin vui lòng kiểm tra lại dữ liệu đã nhập!", Alert.AlertType.ERROR);
        }
    }

    public void btxoaClick(ActionEvent actionEvent) {
        ButtonType bttype = Controller.showAlert("Xoá Danh Mục", "Bạn có muốn xoá danh mục: " + tftendm.getText() + "?", Alert.AlertType.CONFIRMATION);
        if (bttype == ButtonType.OK) {
            Main.temp_data.getDanhmuchang().remove(Main.obj);
            Controller.showAlert("Xoá Danh Mục", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
            ((Stage) btxoa.getScene().getWindow()).close();
            Controller.daluuchua = false;
        }
    }
}