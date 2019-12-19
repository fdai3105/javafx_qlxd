package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.controllers.*;
import sample.models.DataSerialization;
import sample.models.HangHoa;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Main extends Application {
    public static DataSerialization temp_data = new DataSerialization();
    public static File file;
    public static Object obj;//Xác định đối tượng được chọn để sửa...
    public static ArrayList<HangHoa> giohang = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/mainprogram.fxml"));
        root.getStylesheets().add("/sample/styles/main.css");
        primaryStage.setTitle("Quản Lý Bán Hàng Vật Liệu Xây Dựng");
        primaryStage.setScene(new Scene(root, 600, 675));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/medias/icon.png")));
        primaryStage.setMaximized(true);
        primaryStage.setOnShown(onshowevent -> {
            try {
                Stage mystage = Main.openOption("/sample/views/dangnhap.fxml", "/sample/styles/dangnhap.css",
                        "Đăng Nhập", 425, 210, null);
                mystage.initStyle(StageStyle.UTILITY);
                mystage.setOnCloseRequest(oncloseevent -> System.exit(1));
                mystage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        primaryStage.setOnCloseRequest(onclosevent -> {
            onclosevent.consume();
            if (onclosevent.isConsumed())
                if (!Controller.daluuchua) {
                    ButtonType bttype = Controller.showAlert("Thoát chương trình", "Bạn muốn lưu trước khi thoát hông?", Alert.AlertType.CONFIRMATION);
                    if (bttype == ButtonType.OK) {
                        try {
                            if (Main.file == null) {
                                FileChooser filechooser = new FileChooser();
                                filechooser.setTitle("Lưu Dữ Liệu");
                                filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Quản Lý Hàng Hóa *.qlh", "*.qlh"));
                                Main.file = filechooser.showSaveDialog(null);
                                if (Main.file != null)
                                    DataSerialization.writeData(Main.file, Main.temp_data);
                            } else {
                                DataSerialization.writeData(Main.file, Main.temp_data);
                                Controller.showAlert("Lưu Dữ Liệu", "Dữ liệu đã được lưu vào: " + Main.file.getAbsolutePath(), Alert.AlertType.INFORMATION);
                            }
                            FileOutputStream out = new FileOutputStream("path.properties");
                            Properties prop = new Properties();
                            prop.setProperty("path", Main.file.getAbsolutePath());
                            prop.store(out, null);
                            out.close();
                        } catch (Exception e) {
                        }
                        System.exit(0);
                    } else {
                        System.exit(0);
                    }
                } else
                    System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void selectImage(ImageView imv) throws FileNotFoundException {
        FileChooser fileopen = new FileChooser();
        fileopen.setTitle("Chọn tập tin ảnh");
        fileopen.getExtensionFilters().add(new FileChooser.ExtensionFilter("Các định dạng ảnh: *.jpg|*.jpeg|*.bmp|*.png|*.gif|*.jfif", "*.jpg", "*.png", "*.jpeg", "*.bmp", "*.gif", "*.jfif"));
        File file = fileopen.showOpenDialog(null);
        if (file != null) {
            imv.setImage(new Image(new FileInputStream(file)));
            imv.setFitHeight(100);
            imv.setPreserveRatio(true);
        }
    }

    public static Stage openOption(String fxml, String css, String title, int width, int height, Object ob) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource(fxml));
        Parent root = loader.load();
        root.getStylesheets().add(css);
        Stage mystage = new Stage();
        mystage.setTitle(title);
        mystage.setScene(new Scene(root, width, height));
        mystage.initModality(Modality.APPLICATION_MODAL);
        mystage.initStyle(StageStyle.UTILITY);
        mystage.setResizable(false);
        obj = ob;
        if (root.getId() != null) {
            if (root.getId().compareToIgnoreCase("QLHH") == 0) {//Hiển thị cửa sổ hàng hóa với ID là QLHH
                mystage.setOnShown(f -> {
                    QuanLyHangHoaController qlhh = loader.getController();
                    qlhh.capNhatTuDong();
                });
            }
            if (root.getId().compareToIgnoreCase("QLKH") == 0) {//Hiển thị cửa sổ khách hàng với ID là QLKH
                mystage.setOnShown(f -> {
                    QuanLyKhachHangController qlkh = loader.getController();
                    qlkh.capNhatTuDong();
                });
            }
            if (root.getId().compareToIgnoreCase("QLNV") == 0) {//Hiển thị cửa sổ nhân viên với ID là QLNV
                mystage.setOnShown(f -> {
                    QuanLyNhanVienController qlnv = loader.getController();
                    qlnv.capNhatTuDong();
                });
            }
            if (root.getId().compareToIgnoreCase("QLDM") == 0) {//Hiển thị cửa sổ danh mục với ID là QLDM
                mystage.setOnShown(f -> {
                    QuanLyDanhMucController qldm = loader.getController();
                    qldm.capNhatTuDong();
                });
            }
            if (root.getId().compareToIgnoreCase("QLNCC") == 0) {//Hiển thị cửa sổ nhà cung cấp với ID là QLNCC
                mystage.setOnShown(f -> {
                    QuanLyNhaCungCapController qlncc = loader.getController();
                    qlncc.capNhatTuDong();
                });
            }
            if (root.getId().compareToIgnoreCase("INHOADON") == 0) {//Hiển thị cửa sổ in hóa đơn
                mystage.setOnShown(f -> {
                    InHoaDon inhoadon = loader.getController();
                    inhoadon.xuatHoaDonDuocChon();
                });
            }
        }
        //mystage.show();
        return mystage;
    }
}
