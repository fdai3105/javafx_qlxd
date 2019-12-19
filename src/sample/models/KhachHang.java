package sample.models;

import java.io.Serializable;
import java.time.LocalDate;

public class KhachHang implements Serializable {
    private static final long serialVersionUID = 6603746373628965943L;

    private int id_kh;
    private String ten_kh;
    private String sdt_kh;
    private String diachi_kh;
    private LocalDate ntns_kh;
    private String gt_kh;

    public int getId_kh() {
        return id_kh;
    }

    public void setId_kh(int id_kh) {
        this.id_kh = id_kh;
    }

    public String getTen_kh() {
        return ten_kh;
    }

    public void setTen_kh(String ten_kh) {
        this.ten_kh = ten_kh;
    }

    public String getSdt_kh() {
        return sdt_kh;
    }

    public void setSdt_kh(String sdt_kh) {
        this.sdt_kh = sdt_kh;
    }

    public String getDiachi_kh() {
        return diachi_kh;
    }

    public void setDiachi_kh(String diachi_kh) {
        this.diachi_kh = diachi_kh;
    }

    public LocalDate getNtns_kh() {
        return ntns_kh;
    }

    public void setNtns_kh(LocalDate ntns_kh) {
        this.ntns_kh = ntns_kh;
    }

    public String getGt_kh() {
        return gt_kh;
    }

    public void setGt_kh(String gt_kh) {
        this.gt_kh = gt_kh;
    }

    public KhachHang(int id_kh, String ten_kh, String sdt_kh, String diachi_kh, LocalDate ntns_kh, String gt_kh) {
        this.id_kh = id_kh;
        this.ten_kh = ten_kh;
        this.sdt_kh = sdt_kh;
        this.diachi_kh = diachi_kh;
        this.ntns_kh = ntns_kh;
        this.gt_kh = gt_kh;
    }

    @Override
    public String toString() {
        return ten_kh + " , " + sdt_kh;
    }
}
