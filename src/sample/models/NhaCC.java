package sample.models;

import java.io.Serializable;

public class NhaCC  implements Serializable {
    private static final long serialVersionUID = 399273389957305470L;

    private int id_ncc;
    private String ten_ncc;
    private String quocgia_ncc;
    private ImageData hinhanh;

    public ImageData getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(ImageData hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getId_ncc() {
        return id_ncc;
    }

    public void setId_ncc(int id_ncc) {
        this.id_ncc = id_ncc;
    }

    public String getTen_ncc() {
        return ten_ncc;
    }

    public void setTen_ncc(String ten_ncc) {
        this.ten_ncc = ten_ncc;
    }

    public String getQuocgia_ncc() {
        return quocgia_ncc;
    }

    public void setQuocgia_ncc(String quocgia_ncc) {
        this.quocgia_ncc = quocgia_ncc;
    }

    public NhaCC(int id_ncc, String ten_ncc, String quocgia_ncc, ImageData hinhanh) {
        this.id_ncc = id_ncc;
        this.ten_ncc = ten_ncc;
        this.quocgia_ncc = quocgia_ncc;
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return ten_ncc;
    }
}
