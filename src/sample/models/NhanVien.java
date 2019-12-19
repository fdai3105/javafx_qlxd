package sample.models;

import java.io.Serializable;

public class NhanVien  implements Serializable {
    private static final long serialVersionUID = -7045271661162023433L;

    private int id_nv;
    private String hoten_nv;
    private String sdt_nv;
    private String mk_nv;
    private String gt_nv;

    public int getId_nv() {
        return id_nv;
    }

    public void setId_nv(int id_nv) {
        this.id_nv = id_nv;
    }

    public String getHoten_nv() {
        return hoten_nv;
    }

    public void setHoten_nv(String hoten_nv) {
        this.hoten_nv = hoten_nv;
    }

    public String getSdt_nv() {
        return sdt_nv;
    }

    public void setSdt_nv(String sdt_nv) {
        this.sdt_nv = sdt_nv;
    }

    public String getMk_nv() {
        return mk_nv;
    }

    public void setMk_nv(String mk_nv) {
        this.mk_nv = mk_nv;
    }

    public String isGt_nv() {
        return gt_nv;
    }

    public void setGt_nv(String gt_nv) {
        this.gt_nv = gt_nv;
    }

    public NhanVien(int id_nv, String hoten_nv, String sdt_nv, String mk_nv, String gt_nv) {
        this.id_nv = id_nv;
        this.hoten_nv = hoten_nv;
        this.sdt_nv = sdt_nv;
        this.mk_nv = mk_nv;
        this.gt_nv = gt_nv;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "id_nv=" + id_nv +
                ", hoten_nv='" + hoten_nv + '\'' +
                ", sdt_nv='" + sdt_nv + '\'' +
                ", mk_nv='" + mk_nv + '\'' +
                ", gt_nv=" + gt_nv +
                '}';
    }
}
