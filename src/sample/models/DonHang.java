package sample.models;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DonHang  implements Serializable {
    private static final long serialVersionUID = -1633967826670342483L;

    private int id_mh;
    private int id_kh;
    private ArrayList<Integer> ids_hang;
    private ArrayList<Integer> soluongs_hang;
    private ArrayList<Integer> dongias_hang;
    private LocalDateTime thoigian_mh;
    private int id_nv;

    public ArrayList<Integer> getDongias_hang() {
        return dongias_hang;
    }

    public void setDongias_hang(ArrayList<Integer> dongias_hang) {
        this.dongias_hang = dongias_hang;
    }

    public int getId_mh() {
        return id_mh;
    }

    public void setId_mh(int id_mh) {
        this.id_mh = id_mh;
    }

    public int getId_kh() {
        return id_kh;
    }

    public void setId_kh(int id_kh) {
        this.id_kh = id_kh;
    }

    public ArrayList<Integer> getIds_hang() {
        return ids_hang;
    }

    public void setIds_hang(ArrayList<Integer> ids_hang) {
        this.ids_hang = ids_hang;
    }

    public ArrayList<Integer> getSoluongs_hang() {
        return soluongs_hang;
    }

    public void setSoluongs_hang(ArrayList<Integer> soluongs_hang) {
        this.soluongs_hang = soluongs_hang;
    }

    public LocalDateTime getThoigian_mh() {
        return thoigian_mh;
    }

    public void setThoigian_mh(LocalDateTime thoigian_mh) {
        this.thoigian_mh = thoigian_mh;
    }

    public int getId_nv() {
        return id_nv;
    }

    public void setId_nv(int id_nv) {
        this.id_nv = id_nv;
    }

    public DonHang(int id_mh, int id_kh, ArrayList ids_hang, ArrayList soluongs_hang, ArrayList dongias_hang, LocalDateTime thoigian_mh, int id_nv) {
        this.id_mh = id_mh;
        this.id_kh = id_kh;
        this.ids_hang = ids_hang;
        this.soluongs_hang = soluongs_hang;
        this.dongias_hang = dongias_hang;
        this.thoigian_mh = thoigian_mh;
        this.id_nv = id_nv;
    }

    @Override
    public String toString() {
        return "DonHang{" +
                "id_mh=" + id_mh +
                ", id_kh=" + id_kh +
                ", ids_hang=" + ids_hang +
                ", soluongs_hang=" + soluongs_hang +
                ", thoigian_mh=" + thoigian_mh +
                ", id_nv=" + id_nv +
                '}';
    }
}
