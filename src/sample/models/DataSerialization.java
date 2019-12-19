package sample.models;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DataSerialization implements Serializable {
    private static final long serialVersionUID = 8050050860559378616L;

    private ArrayList<HangHoa> hanghoa;
    private ArrayList<DonHang> donhang;
    private ArrayList<KhachHang> khachang;
    private ArrayList<NhaCC> nhacc;
    private ArrayList<NhanVien> nhanvien;
    private ArrayList<DanhMucHang> danhmuchang;

    public ArrayList<HangHoa> getHanghoa() {
        return hanghoa;
    }

    public void setHanghoa(ArrayList<HangHoa> hanghoa) {
        this.hanghoa = hanghoa;
    }

    public ArrayList<DonHang> getDonhang() {
        return donhang;
    }

    public void setDonhang(ArrayList<DonHang> donhang) {
        this.donhang = donhang;
    }

    public ArrayList<KhachHang> getKhachang() {
        return khachang;
    }

    public void setKhachang(ArrayList<KhachHang> khachang) {
        this.khachang = khachang;
    }

    public ArrayList<NhaCC> getNhacc() {
        return nhacc;
    }

    public void setNhacc(ArrayList<NhaCC> nhacc) {
        this.nhacc = nhacc;
    }

    public ArrayList<NhanVien> getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(ArrayList<NhanVien> nhanvien) {
        this.nhanvien = nhanvien;
    }

    public DataSerialization() {
        hanghoa = new ArrayList<>();
        donhang = new ArrayList<>();
        khachang = new ArrayList<>();
        nhacc = new ArrayList<>();
        nhanvien = new ArrayList<>();
        danhmuchang = new ArrayList<>();
    }

    public static DataSerialization readData(File file) throws IOException, ClassNotFoundException {
        DataSerialization data;
        FileInputStream in = new FileInputStream(file);
        ObjectInputStream obj = new ObjectInputStream(in);
        data = (DataSerialization) obj.readObject();
        obj.close();
        in.close();
        return data;
    }

    public static void writeData(File file, DataSerialization data) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        ObjectOutputStream obj = new ObjectOutputStream(out);
        obj.writeObject(data);
        obj.close();
        out.close();
    }

    public int getNextIDNCC() {
        ArrayList<Integer> list = new ArrayList();
        list.add(0);
        for (NhaCC ncc : nhacc) {
            list.add(ncc.getId_ncc());
        }
        return Collections.max(list) + 1;
    }

    public int getNextIDDM() {
        ArrayList<Integer> list = new ArrayList();
        list.add(0);
        for (DanhMucHang ncc : getDanhmuchang()) {
            list.add(ncc.getId_dm());
        }
        return Collections.max(list) + 1;
    }

    public int getNextIDHangHoa() {
        ArrayList<Integer> list = new ArrayList();
        list.add(0);
        for (HangHoa ncc : hanghoa) {
            list.add(ncc.getId_hh());
        }
        return Collections.max(list) + 1;
    }

    public int getNextIDNhanVien() {
        ArrayList<Integer> list = new ArrayList();
        list.add(0);
        for (NhanVien nv : nhanvien) {
            list.add(nv.getId_nv());
        }
        return Collections.max(list) + 1;
    }

    public int getNextIDKhachHang() {
        ArrayList<Integer> list = new ArrayList();
        list.add(0);
        for (KhachHang kh : khachang) {
            list.add(kh.getId_kh());
        }
        return Collections.max(list) + 1;
    }

    public int getNextIDDonHang() {
        ArrayList<Integer> list = new ArrayList();
        list.add(0);
        for (DonHang dh : donhang) {
            list.add(dh.getId_mh());
        }
        return Collections.max(list) + 1;
    }

    public DanhMucHang getDanhMucAt(int iddm) {
        for (DanhMucHang dm : danhmuchang)
            if (dm.getId_dm() == iddm)
                return dm;
        return null;
    }

    public NhaCC getNhaCCAt(int idncc) {
        for (NhaCC ncc : nhacc)
            if (ncc.getId_ncc() == idncc)
                return ncc;
        return null;
    }

    public KhachHang getKhachHangAt(int idkh) {
        for (KhachHang kh : khachang)
            if (kh.getId_kh() == idkh)
                return kh;
        return null;
    }

    public NhanVien getNhanViengAt(int idnv) {
        for (NhanVien nv : nhanvien)
            if (nv.getId_nv() == idnv)
                return nv;
        return null;
    }

    public HangHoa getHangHoaAt(int idhh) {
        for (HangHoa hh : hanghoa)
            if (hh.getId_hh() == idhh)
                return hh;
        return null;
    }

    public boolean isExistDanhMuc(String tendm) {
        if (tendm.trim().compareToIgnoreCase("") == 0)
            return true;
        for (DanhMucHang dm : danhmuchang)
            if (dm.getTen_dm().compareToIgnoreCase(tendm) == 0)
                return true;
        return false;
    }

    public boolean isExistNhaCC(String tenncc) {
        if (tenncc.trim().compareToIgnoreCase("") == 0)
            return true;
        for (NhaCC ncc : nhacc)
            if (ncc.getTen_ncc().compareToIgnoreCase(tenncc) == 0)
                return true;
        return false;
    }

    public boolean isExistHangHoa(String tenhang) {
        if (tenhang.trim().compareToIgnoreCase("") == 0)
            return true;
        for (HangHoa hh : hanghoa)
            if (hh.getTen_hh().compareToIgnoreCase(tenhang) == 0)
                return true;
        return false;
    }

    public boolean isExistNhanVien(String sdt) {
        if (sdt.trim().compareToIgnoreCase("") == 0)
            return true;
        for (NhanVien nv : nhanvien)
            if (nv.getSdt_nv().compareToIgnoreCase(sdt) == 0)
                return true;
        return false;
    }

    public boolean isExistKhachHang(String sdt) {
        if (sdt.trim().compareToIgnoreCase("") == 0)
            return true;
        for (KhachHang kh : khachang)
            if (kh.getSdt_kh().compareToIgnoreCase(sdt) == 0)
                return true;
        return false;
    }

    public int coBaoNhieuDHCuaNV(int idnv) {
        if (idnv == 0)
            return donhang.size();
        ArrayList<DonHang> dh = new ArrayList<>();
        for (DonHang item : donhang)
            if (item.getId_nv() == idnv) {
                dh.add(item);
            }
        return dh.size();
    }

    public ArrayList<DanhMucHang> getDanhmuchang() {
        return danhmuchang;
    }

    public void setDanhmuchang(ArrayList<DanhMucHang> danhmuchang) {
        this.danhmuchang = danhmuchang;
    }

    public ArrayList<HangHoa> timKiemHangHoa(String tukhoa, String giatu, String giaden, DanhMucHang dm, NhaCC ncc) {
        ArrayList<HangHoa> hh = new ArrayList<>();
        int giatu_num = 0, giaden_num = 0;
        try {
            giatu_num = Integer.parseInt(giatu);
        } catch (Exception e) {
            giatu_num = 0;
        }
        try {
            giaden_num = Integer.parseInt(giaden);
        } catch (Exception e) {
            giaden_num = Integer.MAX_VALUE;
        }
        for (HangHoa h : hanghoa) {
            if (h.getTen_hh().toLowerCase().indexOf(tukhoa.toLowerCase()) >= 0)
                hh.add(h);
        }
        ArrayList<Integer> chisocanloaibo = new ArrayList<>();
        for (int i = 0; i < hh.size(); i++) {
            if (hh.get(i).getGiatien_hh() < giatu_num || hh.get(i).getGiatien_hh() > giaden_num)
                chisocanloaibo.add(i);
        }

        if (dm != null)
            for (int i = 0; i < hh.size(); i++) {
                if (hh.get(i).getId_dm() != dm.getId_dm())
                    chisocanloaibo.add(i);
            }
        if (ncc != null)
            for (int i = 0; i < hh.size(); i++) {
                if (hh.get(i).getId_ncc() != ncc.getId_ncc())
                    chisocanloaibo.add(i);
            }
        ArrayList<HangHoa> result = new ArrayList<>();
        if (chisocanloaibo.size() == 0)
            result = hh;
        else
            for (int i = 0; i < hh.size(); i++) {
                if (!chisocanloaibo.contains(i))
                    result.add(hh.get(i));
            }
        return result;
    }
}
