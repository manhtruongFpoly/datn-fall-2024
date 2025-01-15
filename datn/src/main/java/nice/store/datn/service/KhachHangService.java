package nice.store.datn.service;

import nice.store.datn.config.SHA256PasswordEncoder;
import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.Role;
import nice.store.datn.repository.KhachHangRepository;
import nice.store.datn.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;
    private SHA256PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public KhachHangService(KhachHangRepository khachHangRepository, RoleRepository roleRepository) {
        this.khachHangRepository = khachHangRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new SHA256PasswordEncoder(); // Initialize here
    }
    public KhachHang createKH(KhachHang kh) {
        String maKH = generateMaKH();
        while (khachHangRepository.findByMaKH(maKH).isPresent()) {

            maKH = generateMaKH();
        }
        kh.setMaKH(maKH);
        Role roleKhachHang = roleRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Role với id = 2 không tồn tại"));

        // Gán Role vào khách hàng
        kh.setIdRole(roleKhachHang);
        String encodedPassword = passwordEncoder.encode(kh.getMatKhau());
        kh.setMatKhau(encodedPassword);
        kh.setNgayTao(LocalDateTime.now());

        return khachHangRepository.save(kh);
    }

    private String generateMaKH() {

        return "KH" + String.format("%03d", (int) (Math.random() * 10000));
    }

    public List<KhachHang> findAllOrderedByDate() {
        return khachHangRepository.findAll(Sort.by(Sort.Order.desc("ngayTao"))); // Sắp xếp theo ngày tạo giảm dần
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
            if (kh.getMatKhau() != null && !kh.getMatKhau().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(kh.getMatKhau());
                khachHang.setMatKhau(encodedPassword);
            }

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

    public Optional<KhachHang> findByMaKhOrEmail(String maKh, String email) {
        return khachHangRepository.findByMaKhOrEmail(maKh, email);
    }

    //bán hàng
    public KhachHang findById(Integer id) {
        Optional<KhachHang> optional = khachHangRepository.findById(id);
        return optional.map(o -> o).orElse(null);
    }

}
