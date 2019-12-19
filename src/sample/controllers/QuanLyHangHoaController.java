package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.models.DanhMucHang;
import sample.models.HangHoa;
import sample.models.ImageData;
import sample.models.NhaCC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class QuanLyHangHoaController {
    public ImageView imvhanghoa;
    public TextField tften_hh;
    public TextField tfsoluong_hh;
    public TextField tfgiatien_hh;
    public TextArea txtmota_hh;
    public ComboBox<DanhMucHang> cbdanhmuc_hh;
    public ComboBox<NhaCC> cbnhacc_hh;
    public TextField tfdanhgia_hh;
    public Button btxoa;
    public Button btcapnhat;
    public Button bthuy;

    public void imvhanghoaClick(MouseEvent mouseEvent) throws FileNotFoundException {
        Main.selectImage(imvhanghoa);
    }

    public void imvhanghoaMouseEntered(MouseEvent mouseEvent) {
        Tooltip.install((ImageView) mouseEvent.getTarget(), new Tooltip("Nhấp Chuột Để Chọn Ảnh"));
    }

    public void btthemmoihangClick(ActionEvent actionEvent) {
        try {
            if (!Main.temp_data.isExistHangHoa(tften_hh.getText().trim()) && Float.parseFloat(tfdanhgia_hh.getText()) <= 5) {
                int id_hh = Main.temp_data.getNextIDHangHoa();
                String ten_hh = tften_hh.getText();
                int soluong_hh = Integer.parseInt(tfsoluong_hh.getText());
                int giatien_hh = Integer.parseInt(tfgiatien_hh.getText());
                String mota_hh = txtmota_hh.getText();
                ImageData hinhanh_hh = new ImageData(imvhanghoa.getImage(), "png");
                float danhgia_hh = Float.parseFloat(tfdanhgia_hh.getText());
                int id_dm = ((DanhMucHang) (cbdanhmuc_hh.getSelectionModel().getSelectedItem())).getId_dm();
                int id_ncc = ((NhaCC) (cbnhacc_hh.getSelectionModel().getSelectedItem())).getId_ncc();
                HangHoa h = new HangHoa(id_hh, ten_hh, soluong_hh, giatien_hh,
                        mota_hh, hinhanh_hh, danhgia_hh, id_dm, id_ncc);
                Main.temp_data.getHanghoa().add(h);
                Controller.showAlert("Thêm Hàng Hóa", "Hàng hóa đã được thêm thành công!", Alert.AlertType.INFORMATION);
                Controller.daluuchua = false;
            } else {
                Controller.showAlert("Thêm Hàng Hóa", "Lỗi! Tên hàng hóa này đã tồn tại HOẶC không được phép dùng! Xin vui lòng nhập tên khác!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            Controller.showAlert("Thêm Hàng Hóa", "Lỗi! Xin vui lòng kiểm tra lại dữ liệu đã nhập!", Alert.AlertType.ERROR);
        }
    }

    public void btthemnhacc(ActionEvent actionEvent) throws IOException {
        Main.openOption("/sample/views/quanlynhacungcap.fxml", "/sample/styles/quanlynhacungcap.css",
                "Quản Lý Nhà Cung Cấp", 455, 312, null).show();
    }

    public void btthemdanhmuc(ActionEvent actionEvent) throws IOException {
        Main.openOption("/sample/views/quanlydanhmuc.fxml", "/sample/styles/quanlydanhmuc.css",
                "Quản Lý Danh Mục", 455, 270, null).show();
    }

    public void lietKeDM(MouseEvent mouseEvent) {
        if (cbdanhmuc_hh.getItems().size() != Main.temp_data.getDanhmuchang().size()) {
            cbdanhmuc_hh.setItems(FXCollections.observableArrayList(Main.temp_data.getDanhmuchang()));
        }
    }

    public void lietKeNCC(MouseEvent mouseEvent) {
        if (cbnhacc_hh.getItems().size() != Main.temp_data.getNhacc().size())
            cbnhacc_hh.setItems(FXCollections.observableArrayList(Main.temp_data.getNhacc()));
    }

    //Hiển thị dữ liệu từ hàng hóa được chọn lên hộp thoại sửa dữ liệu
    public void capNhatTuDong() {
        try {
            if (Main.obj == null) {
                btcapnhat.setDisable(true);
                btxoa.setDisable(true);
            }
            HangHoa model = (HangHoa) Main.obj;
            tften_hh.setText(model.getTen_hh());
            tfsoluong_hh.setText(model.getSoluong_hh() + "");
//            tfgiatien_hh.setText((new DecimalFormat("###,###").format(model.getGiatien_hh()))+ " VNĐ");
            tfgiatien_hh.setText(model.getGiatien_hh() + "");
            txtmota_hh.setText(model.getMota_hh());
            imvhanghoa.setImage(model.getHinhanh_hh2().readImageFromArray());
            tfdanhgia_hh.setText(model.getDanhgia_hh() + "");

            DanhMucHang dm = Main.temp_data.getDanhMucAt(model.getId_dm());
            cbdanhmuc_hh.getSelectionModel().select(dm);
            NhaCC ncc = Main.temp_data.getNhaCCAt(model.getId_ncc());
            cbnhacc_hh.getSelectionModel().select(ncc);
        } catch (Exception e) {
        }
    }

    public void btcapnhathangClick(ActionEvent actionEvent) {
        try {
            HangHoa model = (HangHoa) Main.obj;
            model.setTen_hh(tften_hh.getText());
            model.setSoluong_hh(Integer.parseInt(tfsoluong_hh.getText()));
            model.setGiatien_hh(Integer.parseInt(tfgiatien_hh.getText()));
            model.setMota_hh(txtmota_hh.getText());
            ImageData hinhanh_hh = new ImageData(imvhanghoa.getImage(), "png");
            model.setHinhanh_hh2(hinhanh_hh);
            if (Float.parseFloat(tfdanhgia_hh.getText()) <= 5) {
                model.setDanhgia_hh(Float.parseFloat(tfdanhgia_hh.getText()));
                int id_dm = ((DanhMucHang) (cbdanhmuc_hh.getSelectionModel().getSelectedItem())).getId_dm();
                model.setId_dm(id_dm);
                int id_ncc = ((NhaCC) (cbnhacc_hh.getSelectionModel().getSelectedItem())).getId_ncc();
                model.setId_ncc(id_ncc);
                //model.updateImage();
                Controller.updatedata = 0;//Đã có sự thay đổi dữ liệu của bảng Hàng Hóa

                Controller.showAlert("Cập Nhật Hàng Hoá", "Cập nhật thành công!", Alert.AlertType.INFORMATION);
                Controller.daluuchua = false;
            } else {
                Controller.showAlert("Cập Nhật Hàng Hoá", "Điểm đánh giá phải từ 1" +
                        " đến 5!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            Controller.showAlert("Cập Nhật Hàng Hoá", "Lỗi! Xin vui lòng kiểm tra lại dữ liệu đã nhập!", Alert.AlertType.ERROR);
        }
    }

    public void bthuyClick(ActionEvent actionEvent) {
        ((Stage) bthuy.getScene().getWindow()).close();
    }

    public void btxoaClick(ActionEvent actionEvent) {
        ButtonType bttype = Controller.showAlert("Xoá?", "Bạn có muốn xoá: " + tften_hh.getText() + "?", Alert.AlertType.CONFIRMATION);
        if (bttype == ButtonType.OK) {
            Main.temp_data.getHanghoa().remove(Main.obj);
            Controller.showAlert("Xoá Nhà Cung Cấp", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
            ((Stage) btxoa.getScene().getWindow()).close();
            Controller.daluuchua = false;
        }
    }
}
