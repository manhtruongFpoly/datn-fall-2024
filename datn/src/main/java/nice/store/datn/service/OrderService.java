package nice.store.datn.service;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.TrangThaiDonHang;
import nice.store.datn.response.DonHangDTO;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.List;

public interface OrderService {
    HoaDon taoDonHang(DonHangDTO donHangDTO, Authentication auth);
    void capNhatTrangThaiDonHang(Integer idDonHang, TrangThaiDonHang trangThaiMoi, Authentication auth);
    HoaDon layDonHangTheoId(Integer id);
    List<HoaDon> layDanhSachDonHangTheoKhachHang(Integer idKhachHang);
    void huyDonHang(Integer idDonHang, Authentication auth);
}
