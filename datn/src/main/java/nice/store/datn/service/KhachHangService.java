package nice.store.datn.service;

import nice.store.datn.entity.KhachHang;
import nice.store.datn.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;


    public KhachHang createKH(KhachHang kh) {
        kh.setNgayTao(LocalDateTime.now());
        return khachHangRepository.save(kh);
    }

    public List<KhachHang> getAllKH() {
        return khachHangRepository.findAll();
    }

    public Optional<KhachHang> getKHById(int id) {
        return khachHangRepository.findById(id);
    }

    public KhachHang updateKH(Integer id, KhachHang kh) {
        if (khachHangRepository.existsById(id)) {

            // Lấy ra khách hàng hiện tại từ database
            KhachHang khachHang = khachHangRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("KhachHang id " + id + " not found"));

            // Cập nhật các thông tin cơ bản của khách hàng
            khachHang.setMaKH(kh.getMaKH());
            khachHang.setHo(kh.getHo());
            khachHang.setTenDem(kh.getTenDem());
            khachHang.setTen(kh.getTen());
            khachHang.setGioiTinh(kh.getGioiTinh());
            khachHang.setNgaySinh(kh.getNgaySinh());
            khachHang.setSdt(kh.getSdt());
            khachHang.setEmail(kh.getEmail());
            khachHang.setTrangThai(kh.getTrangThai());

            // Cập nhật danh sách địa chỉ (DiaChi)
            if (kh.getDiaChi() != null) {
                // Xóa các địa chỉ cũ nếu không còn tồn tại trong danh sách mới
                khachHang.getDiaChi().clear();
                khachHang.getDiaChi().addAll(kh.getDiaChi());
            }

            // Cập nhật thông tin vào cơ sở dữ liệu
            return khachHangRepository.save(khachHang);
        }

        throw new RuntimeException("KhachHang id " + id + " not found");
    }

    public boolean deleteKhachHang(Integer id) {
        if (khachHangRepository.existsById(id)) {
            khachHangRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
