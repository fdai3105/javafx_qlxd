package sample.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import sample.Main;
import sample.models.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;


public class Controller {

    public Button btthemhang;
    public Button btmodl;
    public TableView<HangHoa> tbv_data;
    public TableView<DanhMucHang> tbv_dm;
    public TableView<NhaCC> tbv_ncc;
    public TableView<NhanVien> tbv_nhanvien;
    public MenuItem mnithemnv;
    public Button btthemnhanvien;
    public Tab tabnhanvien;
    public TableView<HangHoa> tbv_cart;
    public TableView<KhachHang> tbv_khachhang;
    public TableView<DonHang> tbv_hoadon;
    public Button btluudl;
    public Tab tabhoadon;
    public TabPane tabpane;
    public Tab tabgiohang;
    public ComboBox<DanhMucHang> cbdm;
    public ComboBox<NhaCC> cbncc;
    public Tab tabhanghoa;
    public MenuItem mnidangxuat;
    public TextField tfgiatu;
    public TextField tfgiaden;
    public TextField tftukhoa;
    public MenuItem mnithemhang;
    public static ArrayList<HangHoa> ketquatimkiem = new ArrayList<>();
    public static boolean daluuchua = true;
    public static int updatedata = -1;

    public Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(100), f -> {
                //Giới hạn chức năng của nhân viên
                if (DangNhapController.aidangdangnhap.compareTo("administrator") != 0) {
                    btmodl.setDisable(true);
                    mnithemnv.setDisable(true);
                    btthemhang.setDisable(true);
                    tabnhanvien.setDisable(true);
                    btthemnhanvien.setDisable(true);
                    mnithemhang.setDisable(true);
                }
                if (DangNhapController.aidangdangnhap == "") {
                    btluudl.setDisable(true);
                }

                //Chỉ cập nhật lại dữ liệu khi có sự thay đổi
                if (ketquatimkiem.size() != tbv_data.getItems().size())
                    taiLaiDuLieu(0);
                if (Main.temp_data.getDanhmuchang().size() != tbv_dm.getItems().size())
                    taiLaiDuLieu(1);
                if (Main.temp_data.getNhacc().size() != tbv_ncc.getItems().size())
                    taiLaiDuLieu(2);
                if (Main.temp_data.getNhanvien().size() != tbv_nhanvien.getItems().size())
                    taiLaiDuLieu(3);
                if (Main.temp_data.getKhachang().size() != tbv_khachhang.getItems().size())
                    taiLaiDuLieu(4);
                int idnv = 0;
                try {
                    idnv = Integer.parseInt(DangNhapController.aidangdangnhap);
                } catch (Exception e) {
                }
                if (Main.temp_data.coBaoNhieuDHCuaNV(idnv) != tbv_hoadon.getItems().size())
                    taiLaiDuLieu(5);
                if (Main.giohang.size() != tbv_cart.getItems().size())
                    taiLaiDuLieu(6);

                if (updatedata >= 0) {
                    taiLaiDuLieu(updatedata);
                    updatedata = -1;
                }

                if (cbdm.getItems().size() != Main.temp_data.getDanhmuchang().size())
                    cbdm.setItems(FXCollections.observableArrayList(Main.temp_data.getDanhmuchang()));
                if (cbncc.getItems().size() != Main.temp_data.getDanhmuchang().size())
                    cbncc.setItems(FXCollections.observableArrayList(Main.temp_data.getNhacc()));
            })
    );

    public static String tenNhanVienDangNhap() {
        if (DangNhapController.aidangdangnhap.compareTo("administrator") == 0) {
            return "administrator";
        } else if (DangNhapController.aidangdangnhap.trim().compareTo("") != 0) {
            int idnv = Integer.parseInt(DangNhapController.aidangdangnhap);
            for (NhanVien nv : Main.temp_data.getNhanvien())
                if (nv.getId_nv() == idnv)
                    return nv.getHoten_nv();
        }
        return "";
    }

    public void btmodlClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser filechooser = new FileChooser();

        filechooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        filechooser.setTitle("Mở Dữ Liệu");
        filechooser.setTitle("Vật Liệu Xây Dựng");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Quản Lý Bán Hàng Vật Liệu Xây Dựng *.hpd", "*.hpd"));
        File file = filechooser.showOpenDialog(null);

        if (file != null) {
            Main.file = file;
            Main.temp_data = DataSerialization.readData(Main.file);
            ketquatimkiem = Main.temp_data.getHanghoa();
            taiLaiDuLieu(0);//Tải dữ liệu cho bảng hàng hóa
            taiLaiDuLieu(1);//...cho bảng danh mục
            taiLaiDuLieu(2);//...cho bảng nhà cung cấp
            taiLaiDuLieu(3);//...cho bảng nhân viên
            taiLaiDuLieu(4);//...cho bảng khách hàng
            taiLaiDuLieu(5);//...cho bảng hóa đơn
        }
    }

    public void btluudlClick(ActionEvent actionEvent) throws IOException {
        if (Main.file == null) {
            FileChooser filechooser = new FileChooser();
            filechooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
            filechooser.setTitle("Lưu Dữ Liệu");
            filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Quản Lý Hàng Hóa *.hpd", "*.hpd"));
            Main.file = filechooser.showSaveDialog(null);
            DataSerialization.writeData(Main.file, Main.temp_data);
        } else {
            DataSerialization.writeData(Main.file, Main.temp_data);
            ButtonType bttype = showAlert("Lưu Dữ Liệu", "Bạn có muốn lưu?", Alert.AlertType.CONFIRMATION);
            if (bttype == ButtonType.OK) {
                showAlert("Lưu Dữ Liệu", "Dữ liệu đã được lưu vào: " + Main.file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            }
        }
        FileOutputStream out = new FileOutputStream("path.properties");
        Properties prop = new Properties();
        prop.setProperty("path", Main.file.getAbsolutePath());
        prop.store(out, null);
        out.close();
        daluuchua = true;
    }

    public void mnithemhangClick(ActionEvent actionEvent) throws IOException {
        hienThiThemMoiHang();
    }

    public void btthemhangClick(ActionEvent actionEvent) throws IOException {
        hienThiThemMoiHang();
    }

    private void hienThiThemMoiHang() throws IOException {
        Main.openOption("/sample/views/quanlyhanghoa.fxml", "/sample/styles/quanlyhanghoa.css",
                "Quản Lý Hàng Hóa", 455, 599, null).show();
    }

    public void btthemdmClick(ActionEvent actionEvent) throws IOException {
        Main.openOption("/sample/views/quanlydanhmuc.fxml", "/sample/styles/quanlydanhmuc.css",
                "Quản Lý Danh Mục", 455, 270, null).show();
    }

    public void btthemnhaccClick(ActionEvent actionEvent) throws IOException {
        Main.openOption("/sample/views/quanlynhacungcap.fxml", "/sample/styles/quanlynhacungcap.css",
                "Quản Lý Nhà Cung Cấp", 455, 312, null).show();
    }

    public void taiLaiDuLieu(int tbl_index) {
        switch (tbl_index) {
            case 0:
                //Tải dữ liệu cho bảng Hàng Hóa
                taiDuLieuChoBangHangHoa(ketquatimkiem);
                break;
            case 1:
                //Tải dữ liệu cho bảng danh mục
                taiDuLieuChoBangDanhMuc();
                break;
            case 2:
                //Tải dữ liệu cho bảng nhà cung cấp
                taiDuLieuChoBangNhaCC();
                break;
            case 3:
                //Tải dữ liệu cho bảng nhân viên
                taiDuLieuChoBangNhanVien();
                break;
            case 4:
                //Tải dữ liệu cho bảng khách hàng
                taiDuLieuChoBangKhachHang();
                break;
            case 5:
                //Tải dữ liệu cho bảng đơn hàng
                taiDuLieuChoBangDonHang();
                break;
            case 6:
                //Tải dữ liệu cho giỏ hàng
                taiDuLieuChoBangGioHang();
                break;
        }
    }

    private void taiDuLieuChoBangDonHang() {
        tbv_hoadon.getItems().clear();
        tbv_hoadon.getColumns().clear();
        ObservableList<DonHang> obldh;
        //Nếu admin đăng nhập, thì liệt kê toàn bộ hóa đơn. Nếu là nhân viên, thì chỉ liệt kê hóa đơn do họ tạo ra
        if (DangNhapController.aidangdangnhap.toLowerCase().compareToIgnoreCase("administrator") == 0)
            obldh = FXCollections.observableArrayList(Main.temp_data.getDonhang());
        else {
            ArrayList<DonHang> listdh = new ArrayList<>();
            for (DonHang dh : Main.temp_data.getDonhang())
                if (dh.getId_nv() == Integer.parseInt(DangNhapController.aidangdangnhap))
                    listdh.add(dh);
            obldh = FXCollections.observableArrayList(listdh);
        }
        TableColumn tbcid_dh = new TableColumn("ID Đơn Hàng");
        tbcid_dh.setCellValueFactory(new PropertyValueFactory<DonHang, Integer>("id_mh"));
        tbcid_dh.setMinWidth(220);
        tbcid_dh.setMaxWidth(230);

        TableColumn tbcthoigian_mh = new TableColumn("Thời Gian Mua");
        tbcthoigian_mh.setCellValueFactory(new PropertyValueFactory<DonHang, LocalDateTime>("thoigian_mh"));
        tbcthoigian_mh.setMinWidth(300);
        tbcthoigian_mh.setMaxWidth(310);

        TableColumn tbcten_kh = new TableColumn("Tên Khách Hàng");
        tbcten_kh.setCellValueFactory(new PropertyValueFactory<DonHang, Integer>("id_kh"));
        tbcten_kh.setMinWidth(220);
        tbcten_kh.setMaxWidth(230);
        tbcten_kh.setCellFactory(param -> new TableCell<DonHang, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    KhachHang kh = Main.temp_data.getKhachHangAt(item);
                    setText(kh.getTen_kh());
                }
            }
        });
        tbv_hoadon.setItems(obldh);
        tbv_hoadon.getColumns().addAll(tbcid_dh, tbcthoigian_mh, tbcten_kh);
    }

    private void taiDuLieuChoBangKhachHang() {
        tbv_khachhang.getItems().clear();
        tbv_khachhang.getColumns().clear();
        ObservableList<KhachHang> oblkh = FXCollections.observableArrayList(Main.temp_data.getKhachang());
        TableColumn tbcid_kh = new TableColumn("ID Khách Hàng");
        tbcid_kh.setCellValueFactory(new PropertyValueFactory<KhachHang, Integer>("id_kh"));
        tbcid_kh.setMinWidth(170);
        tbcid_kh.setMaxWidth(180);

        TableColumn tbcten_kh = new TableColumn("Họ Tên Khách Hàng");
        tbcten_kh.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("ten_kh"));
        tbcten_kh.setMinWidth(200);
        tbcten_kh.setMaxWidth(210);

        TableColumn tbcsdt_kh = new TableColumn("Số Điện Thoại");
        tbcsdt_kh.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("sdt_kh"));
        tbcsdt_kh.setMinWidth(160);
        tbcsdt_kh.setMaxWidth(170);

        TableColumn tbcdiachi_kh = new TableColumn("Địa Chỉ");
        tbcdiachi_kh.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("diachi_kh"));
        tbcdiachi_kh.setMinWidth(200);
        tbcdiachi_kh.setMaxWidth(300);
        tbcdiachi_kh.setStyle("-fx-wrap-text: true");

        TableColumn tbcntns_kh = new TableColumn("Năm Sinh");
        tbcntns_kh.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("ntns_kh"));
        tbcntns_kh.setMinWidth(160);
        tbcntns_kh.setMaxWidth(170);

        TableColumn tbcgt_kh = new TableColumn("Giới Tính");
        tbcgt_kh.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("gt_kh"));
        tbcgt_kh.setMinWidth(150);
        tbcgt_kh.setMaxWidth(160);

        tbv_khachhang.setItems(oblkh);
        tbv_khachhang.getColumns().addAll(tbcid_kh, tbcten_kh, tbcsdt_kh, tbcdiachi_kh, tbcntns_kh, tbcgt_kh);
    }

    boolean start_schedule = false;

    public void capNhatLaiDuLieu(MouseEvent mouseEvent) {
        if (start_schedule == false) {
            start_schedule = true;
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
        ((Stage) ((BorderPane) mouseEvent.getSource()).getScene().getWindow()).setTitle("Quản Lý Bán Hàng Vật Liệu Xây Dựng [" + tenNhanVienDangNhap() + "]");
    }

    //    ------định dạng tiền------
    public class DecimalColumnFactory<S, T extends Number> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        private DecimalFormat format;

        public DecimalColumnFactory(DecimalFormat format) {
            super();
            this.format = format;
        }

        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new TableCell<S, T>() {

                @Override
                protected void updateItem(T item, boolean empty) {
                    if (!empty && item != null) {
                        setText(format.format(item.doubleValue()));
                    } else {
                        setText("");
                    }
                }
            };
        }
    }

    //    ---------------
    public void taiDuLieuChoBangHangHoa(ArrayList<HangHoa> ketqua) {
        tbv_data.getItems().clear();
        tbv_data.getColumns().clear();
        ObservableList<HangHoa> oblhanghoa = FXCollections.observableArrayList(ketqua);
        TableColumn tbcid_hh = new TableColumn("ID Hàng Hóa");
        tbcid_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, Integer>("id_hh"));
        tbcid_hh.setMinWidth(140);
        tbcid_hh.setMaxWidth(145);

        TableColumn tbcten_hh = new TableColumn("Tên Hàng Hóa");
        tbcten_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, String>("ten_hh"));
        tbcten_hh.setMinWidth(200);
        tbcten_hh.setMaxWidth(500);

        TableColumn tbcsoluong_hh = new TableColumn("Số Lượng");
        tbcsoluong_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, Integer>("soluong_hh"));
        tbcsoluong_hh.setMinWidth(110);
        tbcsoluong_hh.setMaxWidth(120);

        TableColumn tbcgiatien_hh = new TableColumn("Đơn Giá (VNĐ)");
        tbcgiatien_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, Integer>("giatien_hh"));
        tbcgiatien_hh.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("###,###" + " VNĐ")));

        tbcgiatien_hh.setMinWidth(220);
        tbcgiatien_hh.setMaxWidth(230);

        TableColumn tbcmota_hh = new TableColumn("Mô Tả Hàng Hóa");
        tbcmota_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, String>("mota_hh"));
        tbcmota_hh.setMinWidth(300);
        tbcmota_hh.setMaxWidth(600);
        tbcmota_hh.setStyle("-fx-wrap-text: true");

        TableColumn tbchinhanh_hh = new TableColumn("Hình Ảnh Hàng Hóa");
        tbchinhanh_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, ImageData>("hinhanh_hh2"));
        tbchinhanh_hh.setStyle("-fx-alignment: CENTER");
        tbchinhanh_hh.setMinWidth(220);
        tbchinhanh_hh.setMaxWidth(230);


        tbchinhanh_hh.setCellFactory(p -> new TableCell<HangHoa, ImageData>() {
            @Override
            protected void updateItem(ImageData item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ImageView imv = new ImageView(item.readImageFromArray());

                        setGraphic(imv);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        TableColumn tbcdanhgia_hh = new TableColumn("Đánh Giá");
        tbcdanhgia_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, Float>("danhgia_hh"));
        tbcdanhgia_hh.setMinWidth(120);
        tbcdanhgia_hh.setMaxWidth(130);
        tbv_data.setItems(oblhanghoa);
        tbv_data.getColumns().addAll(tbcid_hh, tbcten_hh, tbchinhanh_hh, tbcsoluong_hh, tbcgiatien_hh, tbcdanhgia_hh, tbcmota_hh);
    }

    public void taiDuLieuChoBangGioHang() {
        tbv_cart.getItems().clear();
        tbv_cart.getColumns().clear();
        ObservableList<HangHoa> oblgiohang = FXCollections.observableArrayList(Main.giohang);
        TableColumn tbcid_hh = new TableColumn("ID Hàng Hóa");
        tbcid_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, Integer>("id_hh"));
        tbcid_hh.setMinWidth(140);
        tbcid_hh.setMaxWidth(145);

        TableColumn tbcten_hh = new TableColumn("Tên Hàng Hóa");
        tbcten_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, String>("ten_hh"));
        tbcten_hh.setMinWidth(200);
        tbcten_hh.setMaxWidth(500);

        TableColumn tbcsoluong_hh = new TableColumn("Số Lượng Hàng Hóa");
        tbcsoluong_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, Integer>("soluong_hh"));
        tbcsoluong_hh.setMinWidth(220);
        tbcsoluong_hh.setMaxWidth(230);

        TableColumn tbcgiatien_hh = new TableColumn("Đơn Giá");
        tbcgiatien_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, Integer>("giatien_hh"));
        tbcgiatien_hh.setMinWidth(110);
        tbcgiatien_hh.setMaxWidth(120);
        tbcgiatien_hh.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("###,###" + " VNĐ")));

        TableColumn tbcmota_hh = new TableColumn("Mô Tả Hàng Hóa");
        tbcmota_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, String>("mota_hh"));
        tbcmota_hh.setMinWidth(300);
        tbcmota_hh.setMaxWidth(600);
        tbcmota_hh.setStyle("-fx-wrap-text: true");

        TableColumn tbchinhanh_hh = new TableColumn("Hình Ảnh Hàng Hóa");
        tbchinhanh_hh.setCellValueFactory(new PropertyValueFactory<HangHoa, ImageData>("hinhanh_hh2"));
        tbchinhanh_hh.setStyle("-fx-alignment: CENTER");
        tbchinhanh_hh.setMinWidth(220);
        tbchinhanh_hh.setMaxWidth(230);
        tbchinhanh_hh.setCellFactory(p -> new TableCell<HangHoa, ImageData>() {
            @Override
            protected void updateItem(ImageData item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ImageView imv = new ImageView(item.readImageFromArray());
                        setGraphic(imv);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tbv_cart.setItems(oblgiohang);
        tbv_cart.getColumns().addAll(tbcid_hh, tbcten_hh, tbchinhanh_hh, tbcmota_hh, tbcgiatien_hh, tbcsoluong_hh);
    }

    public void taiDuLieuChoBangDanhMuc() {
        cbdm.getItems().clear();

        tbv_dm.getItems().clear();
        tbv_dm.getColumns().clear();
        ObservableList<DanhMucHang> obldm = FXCollections.observableArrayList(Main.temp_data.getDanhmuchang());
        TableColumn tbcid_dm = new TableColumn("ID Danh Mục");
        tbcid_dm.setCellValueFactory(new PropertyValueFactory<DanhMucHang, Integer>("id_dm"));
        tbcid_dm.setMinWidth(150);
        tbcid_dm.setMaxWidth(160);

        TableColumn tbcten_dm = new TableColumn("Tên Danh Mục");
        tbcten_dm.setCellValueFactory(new PropertyValueFactory<DanhMucHang, String>("ten_dm"));
        tbcten_dm.setMinWidth(220);
        tbcten_dm.setMaxWidth(230);
        tbcten_dm.setStyle("-fx-wrap-text: true");

        TableColumn tbcbieutuong_dm = new TableColumn("Biểu Tượng Danh Mục");
        tbcbieutuong_dm.setStyle("-fx-alignment: CENTER");
        tbcbieutuong_dm.setCellValueFactory(new PropertyValueFactory<DanhMucHang, ImageData>("hinhanh"));
        tbcbieutuong_dm.setMinWidth(220);
        tbcbieutuong_dm.setMaxWidth(230);

        tbcbieutuong_dm.setCellFactory(p -> new TableCell<DanhMucHang, ImageData>() {
            @Override
            protected void updateItem(ImageData item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ImageView imv = new ImageView(item.readImageFromArray());
                        setGraphic(imv);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tbv_dm.setItems(obldm);
        tbv_dm.getColumns().addAll(tbcid_dm, tbcten_dm, tbcbieutuong_dm);
    }

    public void taiDuLieuChoBangNhaCC() {
        cbncc.getItems().clear();

        tbv_ncc.getItems().clear();
        tbv_ncc.getColumns().clear();
        ObservableList<NhaCC> oblncc = FXCollections.observableArrayList(Main.temp_data.getNhacc());
        TableColumn tbcid_ncc = new TableColumn("ID Nhà Cung Cấp");
        tbcid_ncc.setCellValueFactory(new PropertyValueFactory<NhaCC, Integer>("id_ncc"));
        tbcid_ncc.setMinWidth(180);
        tbcid_ncc.setMaxWidth(170);

        TableColumn tbcten_ncc = new TableColumn("Tên Nhà Cung Cấp");
        tbcten_ncc.setCellValueFactory(new PropertyValueFactory<NhaCC, String>("ten_ncc"));
        tbcten_ncc.setMinWidth(340);
        tbcten_ncc.setMaxWidth(800);

        TableColumn tbcquocgia_ncc = new TableColumn("Quốc Gia Nhà Cung Cấp");
        tbcquocgia_ncc.setStyle("-fx-alignment: CENTER");
        tbcquocgia_ncc.setMinWidth(300);
        tbcquocgia_ncc.setMaxWidth(350);

        tbcquocgia_ncc.setCellValueFactory(new PropertyValueFactory<NhaCC, String>("quocgia_ncc"));
        TableColumn tbcbieutuong_ncc = new TableColumn("Biểu Tượng Nhà Cung Cấp");
        tbcbieutuong_ncc.setStyle("-fx-alignment: CENTER");
        tbcbieutuong_ncc.setCellValueFactory(new PropertyValueFactory<NhaCC, ImageView>("hinhanh"));
        tbcbieutuong_ncc.setMinWidth(300);
        tbcbieutuong_ncc.setMaxWidth(310);
        tbcbieutuong_ncc.setCellFactory(p -> new TableCell<NhaCC, ImageData>() {
            @Override
            protected void updateItem(ImageData item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ImageView imv = new ImageView(item.readImageFromArray());
                        setGraphic(imv);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tbv_ncc.setItems(oblncc);
        tbv_ncc.getColumns().addAll(tbcid_ncc, tbcten_ncc, tbcquocgia_ncc, tbcbieutuong_ncc);
    }

    public void taiDuLieuChoBangNhanVien() {
        tbv_nhanvien.getItems().clear();
        tbv_nhanvien.getColumns().clear();
        ObservableList<NhanVien> oblnv = FXCollections.observableArrayList(Main.temp_data.getNhanvien());
        TableColumn tbcid_nv = new TableColumn("ID Nhân Viên");
        tbcid_nv.setCellValueFactory(new PropertyValueFactory<NhaCC, Integer>("id_nv"));
        tbcid_nv.setMinWidth(160);
        tbcid_nv.setMaxWidth(170);

        TableColumn tbchoten_nv = new TableColumn("Họ Tên Nhân Viên");
        tbchoten_nv.setCellValueFactory(new PropertyValueFactory<NhaCC, String>("hoten_nv"));
        tbchoten_nv.setMinWidth(200);
        tbchoten_nv.setMaxWidth(210);

        TableColumn tbcsdt_nv = new TableColumn("Số Điện Thoại");
        tbcsdt_nv.setCellValueFactory(new PropertyValueFactory<NhaCC, String>("sdt_nv"));
        tbcsdt_nv.setMinWidth(150);
        tbcsdt_nv.setMaxWidth(160);

        /*TableColumn tbcmk_nv = new TableColumn("Mật Khẩu");
        tbcmk_nv.setCellValueFactory(new PropertyValueFactory<NhaCC, String>("mk_nv"));*/

        TableColumn tbcgt_nv = new TableColumn("Giới Tính");
        tbcgt_nv.setCellValueFactory(new PropertyValueFactory<NhaCC, ImageView>("gt_nv"));
        tbcgt_nv.setMinWidth(150);
        tbcgt_nv.setMaxWidth(160);

        tbv_nhanvien.setItems(oblnv);
        tbv_nhanvien.getColumns().addAll(tbcid_nv, tbchoten_nv, tbcsdt_nv, tbcgt_nv);
    }

    public void tbv_dataDblClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() >= 2) {
            tbv_dataSua(null);
        }
    }

    public void tbv_dataSua(ActionEvent actionEvent) throws IOException {
        if (tbv_data.getSelectionModel().getSelectedIndex() >= 0)
            Main.openOption("/sample/views/quanlyhanghoa.fxml", "/sample/styles/quanlyhanghoa.css",
                    "Quản Lý Hàng Hóa", 455, 599, tbv_data.getSelectionModel().getSelectedItem()).show();
        else {
            showAlert("Lỗi thực hiện sửa dữ liệu",
                    "Chưa chọn dữ liệu: nhấp chuột phải lên dòng dữ liệu cần sửa > chọn 'Sửa Dòng Được Chọn'.\nHOẶC\nDữ liệu trống: mở file dữ liệu hoặc tạo mới dữ liệu",
                    Alert.AlertType.ERROR);
        }
    }

    public static ButtonType showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.getDialogPane().getStylesheets().add("sample/styles/dialog.css");
        alert.getDialogPane().getStyleClass().add("myDialog");

        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().getChildren().get(2);
//        ImageView icon = new ImageView("/sample/medias/htkh.png"); // sửa logo thông báo
//        alert.getDialogPane().setGraphic(icon);
        Button btok = (Button) buttonBar.getButtons().get(0);
        btok.setText("Đồng Ý");
        if (buttonBar.getButtons().size() == 2) {
            Button btcancel = (Button) buttonBar.getButtons().get(1);
            btcancel.setText("Huỷ Bỏ");
            btcancel.setStyle("-fx-background-color: #ff0000");
        }

        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Controller.class.getResourceAsStream("/sample/medias/icon.png")));
        alert.showAndWait();
        return alert.getResult();
    }

    public void btthemnhanvienClick(ActionEvent actionEvent) throws IOException {
        Main.openOption("/sample/views/quanlynhanvien.fxml", "/sample/styles/quanlynhanvien.css",
                "Quản Lý Nhân Viên", 455, 285, null).show();
    }

    public void mnithemvaogiohangClick(ActionEvent actionEvent) throws IOException, CloneNotSupportedException {
        if (tbv_data.getSelectionModel().getSelectedItem() == null) {
            showAlert("Thêm Hàng", "Bạn cần chọn hàng để thêm vào giỏ!", Alert.AlertType.ERROR);
        } else {
            HangHoa hh = new HangHoa(tbv_data.getSelectionModel().getSelectedItem());
            hh.setSoluong_hh(1);
            Main.giohang.add(hh);
            Controller.showAlert("Thêm Hàng", "Hàng bạn chọn đã được thêm vào giỏ hàng!", Alert.AlertType.INFORMATION);
            daluuchua = false;
        }
    }

    public void mnithaydoisoluonghangClick(ActionEvent actionEvent) {
        HangHoa hh = tbv_cart.getSelectionModel().getSelectedItem();
        if (hh == null) {
            showAlert("Thay Đổi Số Lượng", "Bạn cần chọn hàng để thay đổi số lượng!", Alert.AlertType.ERROR);
        } else {
            try {
                TextInputDialog dialog = new TextInputDialog(tbv_cart.getSelectionModel().getSelectedItem().getSoluong_hh() + "");
                dialog.setTitle("Thay Đổi Số Lượng Hàng");
                dialog.setContentText("Nhập số lượng hàng cần mua: ");
                dialog.setHeaderText(null);
                dialog.getDialogPane().getStylesheets().add("sample/styles/dialog.css");
                dialog.getDialogPane().getStyleClass().add("myDialogg");
                dialog.getDefaultValue().indexOf(1);
                ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Controller.class.getResourceAsStream("/sample/medias/icon.png")));
                dialog.showAndWait();

                int soluongmoi = Integer.parseInt(dialog.getResult());
                tbv_cart.getSelectionModel().getSelectedItem().setSoluong_hh(soluongmoi);
                taiLaiDuLieu(6);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            daluuchua = false;
        }
    }

    public void btthemkhachhangClick(ActionEvent actionEvent) throws IOException {
        Main.openOption("/sample/views/quanlykhachhang.fxml", "/sample/styles/quanlykhachhang.css",
                "Quản Lý Khách Hàng", 455, 380, null).show();
    }

    //    ----------------------------Đúp bồ kíck----------------------------------------------------------
    public void tbv_cartDblClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2)
            mnithaydoisoluonghangClick(null);
    }

    public void tbv_khachhangDblClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            if (tbv_khachhang.getSelectionModel().getSelectedIndex() >= 0)
                Main.openOption("/sample/views/quanlykhachhang.fxml", "/sample/styles/quanlykhachhang.css",
                        "Quản Lý Khách Hàng", 455, 380, tbv_khachhang.getSelectionModel().getSelectedItem()).show();
            else {
                showAlert("Lỗi thực hiện sửa dữ liệu",
                        "Chưa chọn dữ liệu: nhấp đôi chuột lên dòng dữ liệu cần sửa.\nHOẶC\nDữ liệu trống: mở file dữ liệu hoặc tạo mới dữ liệu",
                        Alert.AlertType.ERROR);
            }
        }
    }

    public void tbv_nhanvienDblClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            if (tbv_nhanvien.getSelectionModel().getSelectedIndex() >= 0)
                Main.openOption("/sample/views/quanlynhanvien.fxml", "/sample/styles/quanlynhanvien.css",
                        "Quản Lý Nhân Viên", 455, 285, tbv_nhanvien.getSelectionModel().getSelectedItem()).show();
            else {
                showAlert("Lỗi thực hiện sửa dữ liệu",
                        "Chưa chọn dữ liệu: nhấp đôi chuột lên dòng dữ liệu cần sửa.\nHOẶC\nDữ liệu trống: mở file dữ liệu hoặc tạo mới dữ liệu",
                        Alert.AlertType.ERROR);
            }
        }
    }

    public void tbv_nccDblClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            if (tbv_ncc.getSelectionModel().getSelectedIndex() >= 0) {
                Main.openOption("/sample/views/quanlynhacungcap.fxml", "/sample/styles/quanlynhacungcap.css",
                        "Quản Lý Nhà Cung Cấp", 455, 312, tbv_ncc.getSelectionModel().getSelectedItem()).show();
                taiLaiDuLieu(1);
            } else {
                showAlert("Lỗi thực hiện sửa dữ liệu",
                        "Chưa chọn dữ liệu: nhấp đôi chuột lên dòng dữ liệu cần sửa.\nHOẶC\nDữ liệu trống: mở file dữ liệu hoặc tạo mới dữ liệu",
                        Alert.AlertType.ERROR);
            }
        }
    }

    public void tbv_danhmucDblClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            if (tbv_dm.getSelectionModel().getSelectedIndex() >= 0) {
                Main.openOption("/sample/views/quanlydanhmuc.fxml", "/sample/styles/quanlydanhmuc.css",
                        "Quản Lý Danh Mục", 455, 270, tbv_dm.getSelectionModel().getSelectedItem()).show();
                taiLaiDuLieu(1);
            } else {
                showAlert("Lỗi thực hiện sửa dữ liệu",
                        "Chưa chọn dữ liệu: nhấp đôi chuột lên dòng dữ liệu cần sửa.\nHOẶC\nDữ liệu trống: mở file dữ liệu hoặc tạo mới dữ liệu",
                        Alert.AlertType.ERROR);
            }
        }
    }

    //    -----------------------------------------------------------------------------------------------
    String msg = "";

    public void btthemmoidonhangClick(ActionEvent actionEvent) {
        try {
            if (tbv_cart.getItems().size() > 0) {
                tabpane.getSelectionModel().select(tabgiohang);
                ArrayList<Integer> ids_hang = new ArrayList();
                ArrayList<Integer> soluongs_hang = new ArrayList();
                ArrayList<Integer> dongias_hang = new ArrayList();
                int id_nv = 0;
                if (DangNhapController.aidangdangnhap.compareToIgnoreCase("administrator") != 0)
                    id_nv = Integer.parseInt(DangNhapController.aidangdangnhap);
                Main.openOption("/sample/views/chonkhachhang.fxml", "/sample/styles/chonkhachhang.css",
                        "Khách Hàng Đang Mua Hàng", 475, 380, null).showAndWait();
                int id_kh = ChonKhachHang.khachhanghientai.getId_kh();

                //Chuyển các mặt hàng trong giỏ hàng vào đơn hàng
                for (HangHoa hh : Main.giohang) {
                    ids_hang.add(hh.getId_hh());
                    soluongs_hang.add(hh.getSoluong_hh());
                    dongias_hang.add(hh.getGiatien_hh());
                }

                if (kiemTraHangCon(ids_hang, soluongs_hang)) {
                    //Tạo mới đơn hàng từ dữ liệu lấy được ở trên
                    DonHang dh = new DonHang(Main.temp_data.getNextIDDonHang(), id_kh, ids_hang, soluongs_hang, dongias_hang, LocalDateTime.now(), id_nv);
                    Main.temp_data.getDonhang().add(dh);
                    //System.out.println(dh.toString());
                    //Làm sạch dữ liệu liên quan đến đơn hàng
                    ChonKhachHang.khachhanghientai = null;
                    tbv_cart.getItems().clear();
                    Main.giohang.clear();
                    tabpane.getSelectionModel().select(tabhoadon);
                    showAlert("Tạo Đơn Hàng", "Đơn hàng đã được tạo thành công và lưu vào trong thẻ Hóa đơn!\nĐể xuất hóa đơn, xin vui lòng 'Nhấp chuột phải lên hóa đơn > chọn In hóa đơn'.", Alert.AlertType.INFORMATION);
                    capNhatSoLuong(ids_hang, soluongs_hang);
                    taiLaiDuLieu(5);
                    taiLaiDuLieu(0);
                } else {
                    showAlert("Mua Hàng", "Các Mặt Hàng Sau Không Đủ Số Lượng Để Bán!", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Xuất Hóa Đơn", "Giỏ hàng trống! Vui lòng thêm hàng vào giỏ trước!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
        }
        daluuchua = false;
    }

    private void capNhatSoLuong(ArrayList<Integer> ids_hang, ArrayList<Integer> soluongs_hang) {

    }

    private boolean kiemTraHangCon(ArrayList<Integer> ids_hang, ArrayList<Integer> soluongs_hang) {
        msg = "";
        boolean result = true;
        for (int i = 0; i < ids_hang.size(); i++) {
            if (Main.temp_data.getHangHoaAt(ids_hang.get(i)).getSoluong_hh() < soluongs_hang.get(i)) {
                result = false;
            }
        }
        return result;
    }

    public void mniinClick(ActionEvent actionEvent) throws IOException {
        if (tbv_hoadon.getSelectionModel().getSelectedIndex() >= 0)
            Main.openOption("/sample/views/inhoadon.fxml", "/sample/styles/inhoadon.css",
                    "In Hóa Đơn", 825, 680, tbv_hoadon.getSelectionModel().getSelectedItem()).show();
        else {
            showAlert("Lỗi in hóa đơn", "Bạn cần chọn hóa đơn cần in: Nhấp đôi chuột lên hóa đơn cần in!", Alert.AlertType.ERROR);
        }
    }

    public void tbv_hoadonDblClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() >= 2) {
            mniinClick(null);
        }
    }

    public void mnithemhdClick(ActionEvent actionEvent) {
        btthemmoidonhangClick(null);
    }

    public void mnithongtinClick(ActionEvent actionEvent) throws IOException {
        Main.openOption("/sample/views/gioithieuchuongtrinh.fxml", "/sample/styles/gioithieuchuongtrinh.css", "Chương Trình Quản Lý Bán Hàng", 450, 250, null).show();
    }

    public void btthoat(ActionEvent actionEvent) {
        System.exit(0);
    }

    //xoá------------------------------------------------------------------------------
    public void mnixoahanghoaClick(ActionEvent actionEvent) {
        HangHoa hh = tbv_data.getSelectionModel().getSelectedItem();
        if (hh == null) {
            showAlert("Xoá Danh Mục", "Bạn cần chọn hàng để in!", Alert.AlertType.ERROR);
        } else {
            ButtonType bttype = showAlert("Xoá Danh mục", "Bạn có muốn xoá: " + hh.getTen_hh() + "?", Alert.AlertType.CONFIRMATION);
            if (bttype == ButtonType.OK) {
                Main.temp_data.getHanghoa().remove(hh);
                taiLaiDuLieu(0);
                showAlert("Xoá Danh Mục", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
                daluuchua = false;
            }
        }
    }

    public void mnixoadanhmucClick(ActionEvent actionEvent) {
        DanhMucHang dm = tbv_dm.getSelectionModel().getSelectedItem();
        if (dm == null) {
            showAlert("Xoá Danh Mục", "Bạn cần chọn danh mục để xoá!", Alert.AlertType.ERROR);
        } else {
            ButtonType bttype = showAlert("Xoá Danh mục", "Bạn có muốn xoá danh mục: " + dm.getTen_dm() + "?", Alert.AlertType.CONFIRMATION);
            if (bttype == ButtonType.OK) {
                Main.temp_data.getDanhmuchang().remove(dm);
                taiLaiDuLieu(1);
                showAlert("Xoá Danh Mục", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
                daluuchua = false;
            }
        }

    }

    public void mnixoanccClick(ActionEvent actionEvent) {
        NhaCC ncc = tbv_ncc.getSelectionModel().getSelectedItem();
        if (ncc == null) {
            showAlert("Xoá Nhà Cung Cấp", "Bạn cần chọn nhà cung cấp để xoá!", Alert.AlertType.ERROR);
        } else {
            ButtonType bttype = showAlert("Xoá Nhà Cung Cấp", "Bạn có muốn xoá nhà cung cấp: " + ncc.getTen_ncc() + "?", Alert.AlertType.CONFIRMATION);
            if (bttype == ButtonType.OK) {
                Main.temp_data.getNhacc().remove(ncc);
                taiLaiDuLieu(2);
                showAlert("Xoá Nhà Cung Cấp", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
                daluuchua = false;
            }
        }
    }

    public void mnixoanv(ActionEvent actionEvent) {
        NhanVien nv = tbv_nhanvien.getSelectionModel().getSelectedItem();
        if (nv == null) {
            showAlert("Xoá Nhân Viên", "Bạn cần chọn nhân viên để xoá!", Alert.AlertType.ERROR);
        } else {
            ButtonType bttype = showAlert("Xoá Nhân Viên", "Bạn có muốn xoá nhân viên: " + nv.getHoten_nv() + "?", Alert.AlertType.CONFIRMATION);
            if (bttype == ButtonType.OK) {
                Main.temp_data.getNhanvien().remove(nv);
                taiLaiDuLieu(3);
                showAlert("Xoá Nhân Viên", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
                daluuchua = false;
            }
        }
    }

    public void mnixoakh(ActionEvent actionEvent) {
        KhachHang kh = tbv_khachhang.getSelectionModel().getSelectedItem();
        if (kh == null) {
            showAlert("Xoá Khách Hàng", "Bạn cần chọn khách hàng để xoá!", Alert.AlertType.ERROR);
        } else {
            ButtonType bttype = showAlert("Xoá Khách Hàng", "Bạn có muốn xoá khách: " + kh.getTen_kh() + "?", Alert.AlertType.CONFIRMATION);
            if (bttype == ButtonType.OK) {
                Main.temp_data.getKhachang().remove(kh);
                taiLaiDuLieu(4);
                showAlert("Xoá Khách Hàng", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
                daluuchua = false;
            }
        }
    }

    public void mnixoahoadonClick(ActionEvent actionEvent) {
        if (tbv_hoadon.getSelectionModel().getSelectedIndex() >= 0) {
            ButtonType muonxoa = showAlert("Xóa Hóa Đơn", "Bạn muốn xóa hóa đơn này? Lưu ý rằng, hóa đơn bị xóa sẽ không thể khôi phục lại!", Alert.AlertType.CONFIRMATION);
            if (muonxoa == ButtonType.OK) {
                Main.temp_data.getDonhang().remove(tbv_hoadon.getSelectionModel().getSelectedItem());
                tbv_hoadon.getItems().remove(tbv_hoadon.getSelectionModel().getSelectedItem());
                taiLaiDuLieu(5);
                showAlert("Xoá Hóa Đơn", "Xoá Thành Công!", Alert.AlertType.INFORMATION);
                daluuchua = false;
            }
        } else {
            showAlert("Lỗi xóa hóa đơn", "Bạn cần chọn hóa đơn cần xóa!", Alert.AlertType.ERROR);
        }
    }

    public void mnixoagiohangClick(ActionEvent actionEvent) throws IOException {
        HangHoa hh = tbv_cart.getSelectionModel().getSelectedItem();
        if (hh == null) {
            showAlert("Xoá Hàng", "Bạn cần chọn giỏ hàng để xoá!", Alert.AlertType.ERROR);
        } else {
            ButtonType bttype = showAlert("Xoá Hàng", "Bạn có muốn xoá >" + hh.getTen_hh() + "< khỏi giỏ hàng?", Alert.AlertType.CONFIRMATION);
            if (bttype == ButtonType.OK) {
                Main.giohang.remove(hh);
                taiLaiDuLieu(6);
                showAlert("Xoá Hàng", "Xoá Thành Công!", Alert.AlertType.ERROR);
                daluuchua = false;
            }
        }
    }

    //sửa-------------------------------------------------------------------------------
    public void mnisuadanhmucCLick(ActionEvent actionEvent) throws IOException {
        DanhMucHang dm = tbv_dm.getSelectionModel().getSelectedItem();
        if (dm == null) {
            showAlert("Cập Nhập Danh Mục", "Bạn Cần Chọn Danh Mục Để Sửa!", Alert.AlertType.ERROR);
        } else {
            Main.openOption("/sample/views/quanlydanhmuc.fxml", "/sample/styles/quanlydanhmuc.css",
                    "Quản lí danh mục", 455, 270, dm).show();
        }
    }

    public void mnisuanhaccClick(ActionEvent actionEvent) throws IOException {
        NhaCC ncc = tbv_ncc.getSelectionModel().getSelectedItem();
        if (ncc == null) {
            showAlert("Cập Nhập Nhà Cung Cấp", "Bạn Cần Chọn Nhà Cung Cấp Để Sửa!", Alert.AlertType.ERROR);
        } else {
            Main.openOption("/sample/views/quanlynhacungcap.fxml", "/sample/styles/quanlynhacungcap.css",
                    "Quản lí nhà cung cấp", 455, 312, ncc).show();
        }
    }

    public void mnisuanv(ActionEvent actionEvent) throws IOException {
        NhanVien nv = tbv_nhanvien.getSelectionModel().getSelectedItem();
        if (nv == null) {
            showAlert("Cập Nhập Nhân Viên", "Bạn Cần Chọn Nhân Viên Để Sửa!", Alert.AlertType.ERROR);
        } else {
            Main.openOption("/sample/views/quanlynhanvien.fxml", "/sample/styles/quanlynhanvien.css", "Quản lí nhân viên", 455, 285, nv).show();
        }
    }

    public void mnisuakh(ActionEvent actionEvent) throws IOException {
        KhachHang kh = tbv_khachhang.getSelectionModel().getSelectedItem();
        if (kh == null) {
            showAlert("Cập Nhật Khách Hàng", "Bạn Cần Chọn Khách Hàng Để Sửa!", Alert.AlertType.ERROR);
        } else {
            Main.openOption("/sample/views/quanlykhachhang.fxml", "/sample/styles/quanlykhachhang.css", "Quản lí khách hàng", 455, 380, kh).show();
        }
    }

    //--------------------------------tìm kiếm----------------------------
    public void bttimkiemClick(ActionEvent actionEvent) {
        tabpane.getSelectionModel().select(tabhanghoa);
        String tukhoa = tftukhoa.getText();
        String giatu = tfgiatu.getText();
        String giaden = tfgiaden.getText();
        DanhMucHang dm = cbdm.getSelectionModel().getSelectedItem();
        NhaCC ncc = cbncc.getSelectionModel().getSelectedItem();
        ketquatimkiem = Main.temp_data.timKiemHangHoa(tukhoa, giatu, giaden, dm, ncc);
        taiLaiDuLieu(0);
    }

    public void btlamlaiClick(ActionEvent actionEvent) {
        tftukhoa.clear();
        tfgiaden.clear();
        tfgiatu.clear();
        cbdm.getSelectionModel().select(null);
        cbncc.getSelectionModel().select(null);
        ketquatimkiem = Main.temp_data.getHanghoa();
        taiLaiDuLieu(0);
    }

}
