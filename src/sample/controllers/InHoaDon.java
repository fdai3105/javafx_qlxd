package sample.controllers;

import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sample.Main;
import sample.models.DonHang;

import java.text.DecimalFormat;

public class InHoaDon {
    public WebView wvhoadon;
    public WebEngine webengine;

    public void btcloseClick(ActionEvent actionEvent) {
        ((Stage) wvhoadon.getScene().getWindow()).close();
    }

    public void btinhoadonClick(ActionEvent actionEvent) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            webengine.print(job);
            job.endJob();
        }
    }

    public void xuatHoaDonDuocChon() {
        try {
            DonHang dh = (DonHang) Main.obj;
            webengine = wvhoadon.getEngine();
            webengine.loadContent(toHTMLContent(dh));
            wvhoadon.setContextMenuEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toHTMLContent(DonHang dh) {
        int iddh = dh.getId_mh();
        String tenkhach = Main.temp_data.getKhachHangAt(dh.getId_kh()).getTen_kh();
        String sdtkhach = Main.temp_data.getKhachHangAt(dh.getId_kh()).getSdt_kh();
        String tennhanvien;
        if(dh.getId_nv()==0)
            tennhanvien = "administrator";
        else
            tennhanvien = Main.temp_data.getNhanViengAt(dh.getId_nv()).getHoten_nv();//Controller.tenNhanVienDangNhap();

        String result = "";
        result += "<html style='background-color:#f7f7f7;'><body style='font-family:sans-serif;padding:10px;'>"
                + "<h1 style='text-align:center;font-size:15px;'>HÓA ĐƠN MUA HÀNG</h1>"
                + "<p style='padding-bottom:20px;'><b>Tên Khách Hàng: </b>" + tenkhach + "<br/>"
                + "<b>Số Điện Thoại: </b>" + sdtkhach + "<br/></p>"
                + "<b>Mã: </b>" + iddh + "<br/></p>";
        result += "<table border='1' width='100%'>" +
                "<tr style='font-weight:bold;'>" +
                "<td>STT</td>" +
                "<td>Tên Hàng</td>" +
                "<td>Đơn giá</td>" +
                "<td>Số lượng</td>" +
                "</tr>";
        int tongtien = 0;
        for (int i = 0; i < dh.getIds_hang().size(); i++) {
            result += "<tr>" +
                    "<td>" + (i + 1) + "</td>" +
                    "<td>" + Main.temp_data.getHangHoaAt(dh.getIds_hang().get(i)).getTen_hh() + "</td>" +
                    "<td>" + (new DecimalFormat("###,###")).format(dh.getDongias_hang().get(i)) + " VND</td>" +
                    "<td>" + dh.getSoluongs_hang().get(i) + "</td>" +
                    "</tr>";
            tongtien += dh.getDongias_hang().get(i)*dh.getSoluongs_hang().get(i);
        }
        result += "</table>";
        result += "<p style='padding-top:20px;'><b>Tổng tiền: </b>" + (new DecimalFormat("###,###")).format(tongtien) + " VND<br/>";
        result += "<b>Ngày mua: </b>" + dh.getThoigian_mh() + "<br/>";
        result += "<b>Nhân viên bán hàng: </b>" + tennhanvien + "</p>";
        result += "</body></html>";
        return result;
    }
}
