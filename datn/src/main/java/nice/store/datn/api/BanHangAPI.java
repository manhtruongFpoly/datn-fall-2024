package nice.store.datn.api;

import nice.store.datn.entity.*;
import nice.store.datn.repository.KhachHangRepository;
import nice.store.datn.response.HoaDonChiTietDTO;
import nice.store.datn.response.KhachHangDTO;
import nice.store.datn.response.SanPhamChiTietDTO;
import nice.store.datn.service.BanHangService;
import nice.store.datn.service.HoaDonChiTietService;
import nice.store.datn.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

public class BanHangAPI {

    @Autowired
    BanHangService banHangService;

    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HoaDonChiTietService hoaDonChiTietService;

    @Autowired KhachHangRepository khachHangRepository;

    @GetMapping("/api/ban-hang")
    public List<HoaDon> hienThiHoaDonApi() {
        return banHangService.getHoaDonCho();
    }

    //Get ra dữ liệu sản phẩm có trong Hóa đơn chi tiết
    @GetMapping("/api/hoa-don/{hoaDonId}")
    public List<HoaDonChiTietDTO> getHoaDonChiTiet(@PathVariable Integer hoaDonId) {

        return hoaDonChiTietService.getHoaDonChiTietByHoaDonId(hoaDonId);
    }


    @GetMapping("/api/ban-hang/{id}")
    public ResponseEntity<Map<String, Object>> detail(@PathVariable("id") Integer id) {
        HoaDon hd = hoaDonService.detail(id);
        List<HoaDonChiTiet> hdctList = hoaDonChiTietService.findByHDId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("hd", hd);
        response.put("hdctList", hdctList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/ban-hang/tao-hoa-don")
    public ResponseEntity<?> taoHoaDon(@RequestBody HoaDon hoaDon) {
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setLoaiHoaDon("Tại Quầy");
        hoaDon.setTrangThai(0);

        KhachHang kh = new KhachHang();
        kh.setId(1);
        hoaDon.setKhachHang(kh);
        // Thiết lập các giá trị tiền
        hoaDon.setTongTien(new BigDecimal(0));
        hoaDon.setTienGiam(new BigDecimal(0));
        hoaDon.setPhiShip(0);
        PhieuGiamGia pg = new PhieuGiamGia();
        pg.setGiaTriGiam(new BigDecimal(0));


        HoaDon savedHoaDon = hoaDonService.add(hoaDon);

        String maHoaDon = "HD" + String.format("%03d", savedHoaDon.getId());
        savedHoaDon.setMaHd(maHoaDon);

        hoaDonService.taoMaHoaDon(savedHoaDon.getId(), savedHoaDon);

        return ResponseEntity.ok(savedHoaDon);
    }


    @GetMapping("/api/ban-hang/danh-sach-san-pham-chi-tiet")
    public ResponseEntity<List<SanPhamChiTietDTO>> danhSachSanPham() {
        List<SanPhamChiTiet> danhSach = banHangService.getAllDSSanPham();

        List<SanPhamChiTietDTO> danhSachDTO = danhSach.stream()
                .map(spct -> {
                    String hinhAnh = spct.getHinhAnhs() != null && !spct.getHinhAnhs().isEmpty()
                            ? spct.getHinhAnhs().get(0).getUrl()  // Lấy URL của ảnh đầu tiên
                            : null;

                    return new SanPhamChiTietDTO(
                            spct.getId(),
                            spct.getMaSpct(),
                            spct.getGiaBan(),
                            spct.getSoLuong(),
                            spct.getMoTa(),
                            spct.getTrangThai(),
                            spct.getKichCo() != null ? spct.getKichCo().getSize() : null,
                            spct.getMauSac() != null ? spct.getMauSac().getTenMauSac() : null,
                            spct.getLoaiGiay() != null ? spct.getLoaiGiay().getTenLoaiGiay() : null,
                            spct.getChatLieu() != null ? spct.getChatLieu().getTenChatLieu() : null,
                            spct.getDeGiay() != null ? spct.getDeGiay().getTenDeGiay() : null,
                            spct.getThuongHieu() != null ? spct.getThuongHieu().getTenThuongHieu() : null,
                            spct.getSanPham() != null ? spct.getSanPham().getTenSP() : null,
                            hinhAnh
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(danhSachDTO);
    }

    @GetMapping("/api/ban-hang/danh-sach-khach-hang")
    public ResponseEntity<List<KhachHangDTO>> danhSachKhachHang() {
        // Lấy danh sách khách hàng từ service (giả sử bạn có hàm này trong Service)

        List<KhachHang> danhSach = khachHangRepository.findAll();

        // Chuyển đổi từ KhachHang sang KhachHangDTO
        List<KhachHangDTO> danhSachDTO = danhSach.stream()
                .map(kh -> new KhachHangDTO(
                        kh.getId(),
                        kh.getMaKH(),
                        kh.getTen(),
                        kh.getGioiTinh(),
                        kh.getEmail(),
                        kh.getSdt(),
                        kh.getTrangThai()
                ))
                .collect(Collectors.toList());

        // Trả về danh sách DTO
        return ResponseEntity.ok(danhSachDTO);
    }

        @GetMapping("/api/ban-hang/khach-hang/{id}")
        public ResponseEntity<KhachHangDTO> getKhachHangById(@PathVariable Integer id) {
            Optional<KhachHang> khachHangOpt = khachHangRepository.findById(id);

            if (khachHangOpt.isEmpty()) {
                return ResponseEntity.notFound().build();  // Trả về Not Found nếu không tìm thấy khách hàng
            }

            KhachHang khachHang = khachHangOpt.get();

            KhachHangDTO khachHangDTO = new KhachHangDTO(
                    khachHang.getId(),
                    khachHang.getMaKH(),
                    khachHang.getTen(),
                    khachHang.getGioiTinh(),
                    khachHang.getEmail(),
                    khachHang.getSdt(),
                    khachHang.getTrangThai()
            );
            return ResponseEntity.ok(khachHangDTO);
        }




}
