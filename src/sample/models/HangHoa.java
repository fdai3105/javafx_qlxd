package sample.models;


import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;

public class HangHoa  implements Serializable , Cloneable{
    private static final long serialVersionUID = 4464937400557521395L;

    private int id_hh;
    private String ten_hh;
    private int soluong_hh;
    private int giatien_hh;
    private String mota_hh;
    private ImageData hinhanh_hh2;
    private float danhgia_hh;
    private int id_dm;
    private int id_ncc;

    public ImageData getHinhanh_hh2() {
        return hinhanh_hh2;
    }

    public void setHinhanh_hh2(ImageData hinhanh_hh2) {
        this.hinhanh_hh2 = hinhanh_hh2;
    }

    public void setId_dm(int id_dm) {
        this.id_dm = id_dm;
    }

    public void setId_ncc(int id_ncc) {
        this.id_ncc = id_ncc;
    }

    public int getId_hh() {
        return id_hh;
    }

    public void setId_hh(int id_hh) {
        this.id_hh = id_hh;
    }

    public String getTen_hh() {
        return ten_hh;
    }

    public void setTen_hh(String ten_hh) {
        this.ten_hh = ten_hh;
    }

    public int getSoluong_hh() {
        return soluong_hh;
    }

    public void setSoluong_hh(int soluong_hh) {
        this.soluong_hh = soluong_hh;
    }

    public int getGiatien_hh() {
        return giatien_hh;
    }

    public void setGiatien_hh(int giatien_hh) {
        this.giatien_hh = giatien_hh;
    }

    public String getMota_hh() {
        return mota_hh;
    }

    public void setMota_hh(String mota_hh) {
        this.mota_hh = mota_hh;
    }

    /*public ImageView getHinhanh_hh() {
        return hinhanh_hh;
    }*/

   /* public void setHinhanh_hh(ImageView hinhanh_hh) {
        this.hinhanh_hh = hinhanh_hh;
    }*/

    public float getDanhgia_hh() {
        return danhgia_hh;
    }

    public void setDanhgia_hh(float danhgia_hh) {
        this.danhgia_hh = danhgia_hh;
    }

    public int getId_dm() {
        return id_dm;
    }

    public int getId_ncc() {
        return id_ncc;
    }


    public HangHoa(int id_hh, String ten_hh, int soluong_hh, int giatien_hh, String mota_hh, ImageData hinhanh_hh2, float danhgia_hh, int id_dm, int id_ncc) throws IOException {
        this.id_hh = id_hh;
        this.ten_hh = ten_hh;
        this.soluong_hh = soluong_hh;
        this.giatien_hh = giatien_hh;
        this.mota_hh = mota_hh;
        this.hinhanh_hh2 = hinhanh_hh2;
        //updateImage();
        this.danhgia_hh = danhgia_hh;
        this.id_dm = id_dm;
        this.id_ncc = id_ncc;
    }

    public HangHoa(HangHoa h) throws IOException, CloneNotSupportedException {
        this.id_hh = h.id_hh;
        this.ten_hh = h.ten_hh;
        this.soluong_hh = h.soluong_hh;
        this.giatien_hh = h.giatien_hh;
        this.mota_hh = h.mota_hh;
        this.hinhanh_hh2 = h.hinhanh_hh2.clone();
        //updateImage();
        this.danhgia_hh = h.danhgia_hh;
        this.id_dm = h.id_dm;
        this.id_ncc = h.id_ncc;
    }

    /*public void updateImage() throws IOException {
        this.hinhanh_hh = new ImageView(hinhanh_hh2.readImageFromArray());
        this.hinhanh_hh.setFitHeight(64);
        this.hinhanh_hh.setPreserveRatio(true);
    }*/


    @Override
    public String toString() {
        return "HangHoa{" +
                "id_hh=" + id_hh +
                ", ten_hh='" + ten_hh + '\'' +
                ", soluong_hh=" + soluong_hh +
                ", giatien_hh=" + giatien_hh +
                ", mota_hh='" + mota_hh + '\'' +
                //", hinhanh_hh='" + hinhanh_hh + '\'' +
                ", danhgia_hh='" + danhgia_hh + '\'' +
                ", id_dm=" + id_dm +
                ", id_ncc=" + id_ncc +
                '}';
    }
}
