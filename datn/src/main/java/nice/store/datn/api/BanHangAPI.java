package nice.store.datn.api;

import nice.store.datn.entity.*;
import nice.store.datn.repository.KhachHangRepository;
import nice.store.datn.response.DiaChiDTOs;
import nice.store.datn.response.HoaDonChiTietDTO;
import nice.store.datn.response.KhachHangDTO;
import nice.store.datn.response.SanPhamChiTietDTO;
import nice.store.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    DiaChiService diaChiService;

    @Autowired
    KhachHangService khachHangService;

    @Autowired
    PhuongThucThanhToanService phuongThucThanhToanService;

    @Autowired
    SanPhamCTService sanPhamCTService;

    @GetMapping("/api/ban-hang")
    public List<HoaDon> hienThiHoaDonApi() {
        return banHangService.getHoaDonCho();
    }

    @GetMapping("/api/hoa-don/{hoaDonId}")
    public List<HoaDonChiTietDTO> getHoaDonChiTiet(@PathVariable Integer hoaDonId) {
        return hoaDonChiTietService.getHoaDonChiTietByHoaDonId(hoaDonId);
    }

    //lỗi vòng lặp
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

        // Lọc các sản phẩm có trạng thái khác 0
        List<SanPhamChiTietDTO> danhSachDTO = danhSach.stream()
                .filter(spct -> spct.getTrangThai() != 0)
                .map(spct -> {
                    String hinhAnh = spct.getHinhAnhs() != null && !spct.getHinhAnhs().isEmpty()
                            ? spct.getHinhAnhs().get(0).getUrl()
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

    @GetMapping("/api/san-pham-chi-tiet/{id}")
    public ResponseEntity<SanPhamChiTietDTO> getSanPhamChiTiet(@PathVariable Integer id) {
        SanPhamChiTietDTO sanPhamChiTiet = banHangService.getSanPhamChiTietById(id);
        if (sanPhamChiTiet != null) {
            return ResponseEntity.ok(sanPhamChiTiet);
        } else {
            return ResponseEntity.notFound().build();
        }
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
            return ResponseEntity.notFound().build();
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


    @PostMapping("/api/hoa-don-chi-tiet/danh-sach-san-pham/add")
    public ResponseEntity<?> themSanPham(@RequestBody HoaDonChiTiet hoaDonChiTiet) {
        if (hoaDonChiTiet.getHoaDon() == null) {
            return ResponseEntity.badRequest().body("HoaDon không được để trống.");
        }

        Integer idHD = hoaDonChiTiet.getHoaDon().getId();
        List<HoaDonChiTiet> hdctList = hoaDonChiTietService.findByHDId(idHD);

        BigDecimal tongTienHienTai = hdctList.stream()
                .map(hdct -> hdct.getSanPhamChiTiet().getGiaBan()
                        .multiply(BigDecimal.valueOf(hdct.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (hoaDonChiTiet.getSanPhamChiTiet() == null || hoaDonChiTiet.getSanPhamChiTiet().getGiaBan() == null) {
            return ResponseEntity.badRequest().body("Giá bán sản phẩm không hợp lệ.");
        }

        BigDecimal giaBanSanPhamMoi = hoaDonChiTiet.getSanPhamChiTiet().getGiaBan();
        BigDecimal soLuongSanPhamMoi = BigDecimal.valueOf(hoaDonChiTiet.getSoLuong());
        BigDecimal tongTienMoi = tongTienHienTai.add(giaBanSanPhamMoi.multiply(soLuongSanPhamMoi));
        BigDecimal tongTienSauKhiGiam = BigDecimal.ZERO;

        HoaDon hd = hoaDonService.detail(idHD);

        if (hd.getPhieuGiamGia() != null && hd.getPhieuGiamGia().getId() != null) {
            // Trường hợp có phiếu giảm giá
            BigDecimal giamToiDa = hd.getPhieuGiamGia().getGiaTriMax();
            System.out.println("Giảm giá tối đa: " + giamToiDa);
            tongTienSauKhiGiam = tongTienMoi.subtract(giamToiDa.min(tongTienMoi));
            hd.setTienGiam(giamToiDa.min(tongTienMoi));
            hd.setTongTien(tongTienSauKhiGiam);

        } else {
            hd.setTienGiam(BigDecimal.ZERO);
            hd.setTongTien(tongTienMoi);
        }

        hoaDonService.updateTongTien(idHD, hd);
        hoaDonChiTiet.setHoaDon(hoaDonChiTiet.getHoaDon());
        hoaDonChiTiet.setSanPhamChiTiet(hoaDonChiTiet.getSanPhamChiTiet());

        if (giaBanSanPhamMoi != null) {
            hoaDonChiTiet.setDonGia(BigDecimal.valueOf(giaBanSanPhamMoi.intValue()));
        } else {
            return ResponseEntity.badRequest().body("Giá bán không hợp lệ.");
        }
        hoaDonChiTiet.setNgayTao(LocalDateTime.now());

        return ResponseEntity.ok(banHangService.saveHoaDonChiTiet(hoaDonChiTiet));
    }


    @PutMapping("/api/hoa-don/update-tong-tien/{id}")
    public ResponseEntity<?> updateTongTien(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        HoaDon updateTongTien = hoaDonService.updateTongTien(id, hoaDon);
        if (updateTongTien != null) {
            return ResponseEntity.ok(updateTongTien);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HoaDon not found");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        ex.printStackTrace(); // In lỗi ra console
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi xảy ra: " + ex.getMessage());
    }

    @GetMapping("/api/ban-hang/dia-chi-mac-dinh-khach-hang/{id}")
    public DiaChi getDiaCHiMacDinh(@PathVariable("id") Integer id) {
        return diaChiService.getDiaChiMacDinh(id);
    }

    @GetMapping("/api/ban-hang/dia-chi-mac-dinh-khach-hang-dto/{id}")
    public ResponseEntity<DiaChiDTOs> getDiaChiMacDinh(@PathVariable("id") Integer id) {
        DiaChi diaChi = diaChiService.getDiaChiMacDinh(id);
        if (diaChi == null) {
            return ResponseEntity.ok(new DiaChiDTOs());
        }
        DiaChiDTOs diaChiDTOs = banHangService.mapToDTO(diaChi);
        return ResponseEntity.ok(diaChiDTOs);
    }


    @PutMapping("/api/ban-hang/update-khach-hang/{id}")
    public ResponseEntity<?> updateTrangThaiThanhToan(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        // Kiểm tra null cho đối tượng khachHang trong hoaDon
        if (hoaDon.getKhachHang() == null || hoaDon.getKhachHang().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin khách hàng không hợp lệ");
        }

        // Tìm thông tin khách hàng
        KhachHang khachHang = khachHangService.findById(hoaDon.getKhachHang().getId());
        if (khachHang == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khách hàng không tồn tại");
        }

        // Gán thông tin khách hàng vào hóa đơn
        hoaDon.setTenNguoiNhan(khachHang.getTen());
        hoaDon.setSdt(String.valueOf(khachHang.getSdt()));
        hoaDon.setKhachHang(khachHang);

        // Cập nhật hóa đơn
        HoaDon updatedHoaDon = banHangService.updateKhachHang(id, hoaDon);
        if (updatedHoaDon != null) {
            return ResponseEntity.ok(updatedHoaDon);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóa đơn không tồn tại hoặc không cập nhật được");
        }
    }


    @PostMapping("/api/ban-hang/them-phuong-thuc-thanh-toan/{id}")
    public ResponseEntity<?> themPhuongThucThanhToan(@PathVariable("id") Integer id, @RequestBody PhuongThucThanhToan phuongThucThanhToan) {
        phuongThucThanhToan.setTenThanhToan(phuongThucThanhToan.getTenThanhToan());
        HoaDon hd = new HoaDon();
        hd.setId(id);
        phuongThucThanhToan.setIdHoaDon(hd);
        phuongThucThanhToan.setLoaiThanhToan(phuongThucThanhToan.getLoaiThanhToan());
        phuongThucThanhToan.setTrangThai(phuongThucThanhToan.getTrangThai());
        phuongThucThanhToan.setNgayTao(LocalDateTime.now());
        return ResponseEntity.ok(phuongThucThanhToanService.add(phuongThucThanhToan));
    }


    @GetMapping("/api/ban-hang/phuong-thuc-thanh-toan/{id}")
    public ResponseEntity<List<PhuongThucThanhToan>> phuongThucThanhToan(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(phuongThucThanhToanService.detail(id));
    }

    @PostMapping("/api/ban-hang/xoa-phuong-thuc-thanh-toan/{id}")
    public ResponseEntity<PhuongThucThanhToan> xoaPTTT(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(phuongThucThanhToanService.deleteById(id));
    }

    @PostMapping("/api/ban-hang/delete-hdct/{id}")
    public ResponseEntity<HoaDonChiTietDTO> XoaSanPhamHDCT(@PathVariable("id") Integer id) {
        HoaDonChiTiet deletedItem = hoaDonChiTietService.deleteById(id);
        if (deletedItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new HoaDonChiTietDTO(deletedItem));
    }

    @PutMapping("/api/ban-hang/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        hoaDon.setTrangThai(hoaDon.getTrangThai());
        HoaDon updatedHoaDon = hoaDonService.updateTrangThai(id, hoaDon);
        if (updatedHoaDon != null) {
            return ResponseEntity.ok(updatedHoaDon);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HoaDon not found");
        }
    }


    @PutMapping("/api/san-pham-chi-tiet/cong-so-luong")
    public ResponseEntity<String> congSoLuongSanPham(@RequestParam Integer idHDCT) {
        // Lấy thông tin từ HoaDonChiTietDTO
        Optional<HoaDonChiTietDTO> optionalHDCTDTO = hoaDonChiTietService.findDTOById(idHDCT);
        if (optionalHDCTDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy chi tiết hóa đơn.");
        }

        HoaDonChiTietDTO hoaDonChiTietDTO = optionalHDCTDTO.get();
        Integer soLuongCong = hoaDonChiTietDTO.getSoLuong();
        Integer idSPCT = hoaDonChiTietDTO.getIdSPCT();

        // Lấy sản phẩm chi tiết và cộng lại số lượng
        Optional<SanPhamChiTiet> optionalSPCT = sanPhamCTService.getSanPhamChiTietById(idSPCT);
        if (optionalSPCT.isPresent()) {
            SanPhamChiTiet sanPhamChiTiet = optionalSPCT.get();
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + soLuongCong);
            sanPhamCTService.save(sanPhamChiTiet);
            return ResponseEntity.ok("Cộng số lượng sản phẩm thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm chi tiết.");
        }
    }


    @Autowired PhieuGiamGiaService phieuGiamGiaService;

    @PostMapping("/api/ban-hang/gsl-pgg/reduceQuantity")
    public ResponseEntity<Void> reduceVoucherQuantity(@RequestParam String maVoucher) {
        boolean success = phieuGiamGiaService.reduceVoucherQuantity(maVoucher);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }
    @PostMapping("/api/ban-hang/gsl-pgg-id/reduceQuantity")
    public ResponseEntity<Void> reduceVoucherQuantityID(@RequestBody Map<String, Integer> requestData) {
        Integer id = requestData.get("id");
        System.out.println("Received ID: " + id);  // In ra giá trị ID để kiểm tra
        boolean success = phieuGiamGiaService.reduceVoucherQuantityID(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(400).build();
    }


}
