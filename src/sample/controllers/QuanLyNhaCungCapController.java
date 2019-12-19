package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.models.DanhMucHang;
import sample.models.ImageData;
import sample.models.NhaCC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuanLyNhaCungCapController {
    public ComboBox cbquocgia;
    public ImageView imvbieutuong;
    public TextField tftenncc;
    public Button bthuy;
    public Button btcapnhat;
    public Button btxoa;

    public void btthemmoinccClick(ActionEvent actionEvent){
        try {
            if (!Main.temp_data.isExistNhaCC(tftenncc.getText().trim())) {
                NhaCC ncc = new NhaCC(Main.temp_data.getNextIDNCC(), tftenncc.getText(),
                        cbquocgia.getSelectionModel().getSelectedItem().toString(), new ImageData(imvbieutuong.getImage(), "png"));
                Main.temp_data.getNhacc().add(ncc);
                Controller.showAlert("Thêm Nhà Cung Cấp", "Nhà cung cấp đã được thêm thành công!", Alert.AlertType.INFORMATION);
                Controller.daluuchua = false;
            } else {
                Controller.showAlert("Thêm Nhà Cung Cấp", "Lỗi! Tên nhà cung cấp này đã tồn tại HOẶC không được phép dùng, xin vui lòng nhập tên khác!", Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            Controller.showAlert("Thêm Nhà Cung Cấp", "Lỗi! Xin vui lòng kiểm tra lại dữ liệu đã nhập!", Alert.AlertType.ERROR);
        }
    }

    public ArrayList<String> getCountries(){
        ArrayList<String> list = new ArrayList<>();
        String[] locale = Locale.getISOCountries();
        for(String country:locale)
            list.add((new Locale("vi", country)).getDisplayCountry(new Locale("vi", "vn")));
        Collections.sort(list);
        return list;
    }

    public void loadCountries(MouseEvent mouseEvent) {
        if(cbquocgia.getItems().isEmpty())
            cbquocgia.setItems(FXCollections.observableArrayList(getCountries()));
    }

    public void imvbieutuongClick(MouseEvent mouseEvent) throws FileNotFoundException {
        Main.selectImage(imvbieutuong);
    }

    public void imvbieutuongMouseEntered(MouseEvent mouseEvent) {
        Tooltip.install((ImageView)mouseEvent.getTarget(), new Tooltip("Nhấp Chuột Để Chọn Ảnh"));
    }

    public void capNhatTuDong() {
        try {
            if(Main.obj==null){
                btcapnhat.setDisable(true);
                btxoa.setDisable(true);
            }
            NhaCC cc = (NhaCC) Main.obj;
            tftenncc.setText(cc.getTen_ncc());
            NhaCC model = (NhaCC) Main.obj;
            imvbieutuong.setImage(cc.getHinhanh().readImageFromArray());
            cbquocgia.getSelectionModel().select(model.getQuocgia_ncc());
        } catch (Exception e) {}

    }

    public void bthuyClick(ActionEvent actionEvent) {
        ((Stage)bthuy.getScene().getWindow()).close();
    }

    public void btcapnhatClick(ActionEvent actionEvent) {
        try {
            NhaCC ncc = (NhaCC) Main.obj;
            Main.temp_data.getNhaCCAt(ncc.getId_ncc()).setTen_ncc(this.tftenncc.getText());
            Main.temp_data.getNhaCCAt(ncc.getId_ncc()).setQuocgia_ncc(this.cbquocgia.getSelectionModel().getSelectedItem().toString());
            Main.temp_data.getNhaCCAt(ncc.getId_ncc()).setHinhanh(new ImageData(this.imvbieutuong.getImage(),"png"));
            Controller.updatedata = 2;
            Controller.showAlert("Cập Nhật Nhà Cung Cấp", "Cập nhật thành công!", Alert.AlertType.INFORMATION);
            Controller.daluuchua = false;
        }catch (Exception e) {
            Controller.showAlert("Thêm Nhà Cung Cấp", "Lỗi! Xin vui lòng kiểm tra lại dữ liệu đã nhập!", Alert.AlertType.ERROR);
        }
    }

    public void btxoaClick(ActionEvent actionEvent) {
        ButtonType bttype = Controller.showAlert("Xoá Nhà Cung Cấp", "Bạn có muốn xoá nhà cung cấp: " +tftenncc.getText()+ "?", Alert.AlertType.CONFIRMATION);
        if (bttype == ButtonType.OK) {
            Main.temp_data.getNhacc().remove(Main.obj);
            Controller.showAlert("Xoá Nhà Cung Cấp", "Xoá Thành Công", Alert.AlertType.INFORMATION);
            ((Stage)btxoa.getScene().getWindow()).close();
            Controller.daluuchua = false;
        }
    }
}
