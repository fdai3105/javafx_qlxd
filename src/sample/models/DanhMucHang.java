package sample.models;

import java.io.Serializable;

public class DanhMucHang implements Serializable {
    private static final long serialVersionUID = 1196297211847218979L;

    private int id_dm;
    private String ten_dm;
    private ImageData hinhanh;

    public ImageData getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(ImageData hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getId_dm() {
        return id_dm;
    }

    public String getTen_dm() {
        return ten_dm;
    }

    public void setId_dm(int id_dm) {
        this.id_dm = id_dm;
    }

    public void setTen_dm(String ten_dm) {
        this.ten_dm = ten_dm;
    }

    public DanhMucHang(int id_dm, String ten_dm, ImageData hinhanh) {
        this.id_dm = id_dm;
        this.ten_dm = ten_dm;
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return ten_dm;
    }
}
