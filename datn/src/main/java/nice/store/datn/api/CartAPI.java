package nice.store.datn.api;


import jakarta.servlet.http.HttpSession;
import nice.store.datn.entity.*;
import nice.store.datn.response.SanPhamCTResponse;
import nice.store.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CartAPI {

    @Autowired
    private CartService cartService;

    @PostMapping("/api/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> requestData, HttpSession session) {
        try {
            Integer productId = (Integer) requestData.get("productId");
            Integer quantity = (Integer) requestData.get("quantity");
            cartService.addToCart(productId, quantity, session);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Sản phẩm đã được thêm vào giỏ hàng.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Autowired
    private SanPhamCTService sanPhamCTService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private KichCoService kichCoService;

    @GetMapping("/detai12/{productId}")
    public ResponseEntity<?> getProductDetail(
            @PathVariable("productId") Integer productId,
            @RequestParam(value = "colorId", required = false) Integer colorId,
            @RequestParam(value = "sizeId", required = false) Integer sizeId) {

        try {
            // Lấy chi tiết sản phẩm
            SanPhamCTResponse sanPhamCT = sanPhamCTService.getSanPhamCTById1(productId);

            // Danh sách màu sắc và kích cỡ
            List<MauSac> listMauSac = mauSacService.getMauSacByProductId(productId);
            List<KichCo> listKichCo = kichCoService.getKichCoByProductId(productId);

            // Tạo map trả về dữ liệu
            Map<String, Object> response = new HashMap<>();
            response.put("sanPhamCT", sanPhamCT);
            response.put("listMauSac", listMauSac);
            response.put("listKichCo", listKichCo);

            // Trả về response dạng JSON
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Xử lý lỗi và trả về thông báo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }


    @Autowired
    private PhieuGiamGiaService phieuGiamGiaService;

//    @GetMapping("/api/phieu-giam-gia-online")
//    public ResponseEntity<List<PhieuGiamGia>> hienThi(@RequestParam("totalAmount") BigDecimal totalAmount) {
//        List<PhieuGiamGia> danhSach = phieuGiamGiaService.findAll();
//        LocalDate now = LocalDate.now();
//
//        // Lọc phiếu giảm giá phù hợp
//        List<PhieuGiamGia> phieuGiamGiaPhuHop = danhSach.stream()
//                .filter(pgg -> {
//                    boolean isActive = pgg.getNgayBatDau() != null && pgg.getNgayKetThuc() != null
//                            && !now.isBefore(pgg.getNgayBatDau()) && !now.isAfter(pgg.getNgayKetThuc());
//                    boolean isTotalValid = totalAmount != null && totalAmount.compareTo(pgg.getDonToiThieu()) >= 0;
//                    return isActive && isTotalValid;
//                })
//                .collect(Collectors.toList());
//
//        phieuGiamGiaPhuHop.forEach(pgg -> {
//            if (pgg.getNgayBatDau() != null && pgg.getNgayKetThuc() != null) {
//                if (now.isBefore(pgg.getNgayBatDau())) {
//                    pgg.setTrangThai(2); // Chưa diễn ra
//                } else if (now.isAfter(pgg.getNgayKetThuc())) {
//                    pgg.setTrangThai(1); // Ngừng hoạt động
//                } else {
//                    pgg.setTrangThai(0); // Hoạt động
//                }
//            } else {
//                pgg.setTrangThai(1);
//            }
//        });
//
//        return ResponseEntity.ok(phieuGiamGiaPhuHop);
//    }


    @GetMapping("/api/phieu-giam-gia-online")
    public ResponseEntity<List<PhieuGiamGia>> hienThi(@RequestParam("totalAmount") BigDecimal totalAmount) {
        List<PhieuGiamGia> danhSach = phieuGiamGiaService.findAll();
        LocalDate now = LocalDate.now();

        // Lọc phiếu giảm giá phù hợp
        List<PhieuGiamGia> phieuGiamGiaPhuHop = danhSach.stream()
                .filter(pgg -> {
                    boolean isActive = pgg.getNgayBatDau() != null && pgg.getNgayKetThuc() != null
                            && !now.isBefore(pgg.getNgayBatDau()) && !now.isAfter(pgg.getNgayKetThuc());
                    boolean isTotalValid = totalAmount != null && totalAmount.compareTo(pgg.getDonToiThieu()) >= 0;
                    return isActive && isTotalValid;
                })
                .collect(Collectors.toList());

        phieuGiamGiaPhuHop.forEach(pgg -> {
            if (pgg.getNgayBatDau() != null && pgg.getNgayKetThuc() != null) {
                if (now.isBefore(pgg.getNgayBatDau())) {
                    pgg.setTrangThai(2); // Chưa diễn ra
                } else if (now.isAfter(pgg.getNgayKetThuc())) {
                    pgg.setTrangThai(1); // Ngừng hoạt động
                } else {
                    pgg.setTrangThai(0); // Hoạt động
                }
            } else {
                pgg.setTrangThai(1);
            }

            // Tính toán giá trị giảm nếu phiếu giảm giá là phần trăm hoặc số tiền cố định
            if (pgg.getLoaiVoucher() != null) {
                BigDecimal giaTriGiam = BigDecimal.ZERO;

                if ("Phần trăm".equals(pgg.getLoaiVoucher())) {
                    // Tính giảm giá theo phần trăm
                    giaTriGiam = totalAmount.multiply(pgg.getGiaTriGiam()).divide(new BigDecimal(100));
                } else if ("Tiền mặt".equals(pgg.getLoaiVoucher())) {
                    // Tính giảm giá theo số tiền cố định
                    giaTriGiam = pgg.getGiaTriGiam();
                }

                // Kiểm tra giá trị giảm không vượt quá giới hạn tối đa
                if (giaTriGiam.compareTo(pgg.getGiaTriMax()) > 0) {
                    giaTriGiam = pgg.getGiaTriMax();
                }

                // Gán giá trị giảm vào phiếu giảm giá
                pgg.setGiaTriMax(giaTriGiam);
            }
        });

        return ResponseEntity.ok(phieuGiamGiaPhuHop);
    }


    @Autowired
    HoaDonService hoaDonService;

    @PostMapping("/api/gio-hang/tao-hoa-don-dat-hang-khong-dang-nhap")
    public ResponseEntity<?> taoHoaDonDatHang2(@RequestBody HoaDon hoaDon) {

        hoaDon.setDiaChiNguoiNhan(hoaDon.getDiaChiNguoiNhan());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setLoaiHoaDon("Online");
        hoaDon.setTrangThai(1);
        hoaDon.setMaHd(hoaDon.getMaHd());
        hoaDon.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
        hoaDon.setKhachHang(hoaDon.getKhachHang());
        hoaDon.setTongTien(hoaDon.getTongTien());
        hoaDon.setTienGiam(hoaDon.getTienGiam());
        hoaDon.setPhiShip(hoaDon.getPhiShip());
        hoaDon.setSdt(hoaDon.getSdt());
//        if(hoaDon.getPhieuGiamGia().getId() == null){
//            hoaDon.setPhieuGiamGia(null);
//        }
        PhieuGiamGia pg = new PhieuGiamGia();
        pg.setGiaTriGiam(new BigDecimal(0));

        HoaDon savedHoaDon = hoaDonService.add(hoaDon);

        String maHoaDon = "HD" + String.format("%03d", savedHoaDon.getId());
        savedHoaDon.setMaHd(maHoaDon);
        hoaDonService.taoMaHoaDon(savedHoaDon.getId(), savedHoaDon);

        return ResponseEntity.ok(savedHoaDon);
    }

    @Autowired HoaDonChiTietService hoaDonChiTietService;
    @PostMapping("/api/gio-hang/them-san-pham-vao-hoa-don/{id}")
    public ResponseEntity<?> themSanPhamVaoHoaDon (@PathVariable("id") Integer id ,@RequestBody HoaDonChiTiet hoaDonChiTiet){
        hoaDonChiTiet.setSanPhamChiTiet(hoaDonChiTiet.getSanPhamChiTiet());
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(id);
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong());
        hoaDonChiTiet.setDonGia(hoaDonChiTiet.getDonGia());
        hoaDonChiTiet.setNgayTao(LocalDateTime.now());
        hoaDonChiTiet.setTrangThai(1);
        HoaDonChiTiet saveHDCT = hoaDonChiTietService.add(hoaDonChiTiet);

        return ResponseEntity.ok(saveHDCT);
    }


    @Autowired GioHangService gioHangCTService;
    @PostMapping("/api/gio-hang/delete-sp/{id}")
    public ResponseEntity<?> deleteSP(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(gioHangCTService.deleteById(id));
    }

}
