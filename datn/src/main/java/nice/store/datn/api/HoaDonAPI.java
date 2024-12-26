package nice.store.datn.api;

import nice.store.datn.entity.*;
import nice.store.datn.repository.PhieuGiamGiaRepository;
import nice.store.datn.response.HoaDonChiTietDTO;
import nice.store.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class HoaDonAPI {

    @Autowired
    PhieuGiamGiaService phieuGiamGiaService;
    @Autowired
    HoaDonService hoaDonService;
    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    PhuongThucThanhToanService phuongThucThanhToanService;
    @Autowired
    LichSuHoaDonService lichSuHoaDonService;

    @GetMapping("/api/hoa-don/phieu-giam-gia-phu-hop/{max}")
    public List<PhieuGiamGia> getPhieuGiamGiaPhuHop(@PathVariable("max") Long max) {
        return phieuGiamGiaService.PhieuGiamGiaPhuHop(max);
    }

    @PutMapping("/api/hoa-don/update-phieu-giam-gia/{id}")
    public ResponseEntity<?> updatePhieuGiamGia(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        HoaDon hd = hoaDonService.detail(id);

        if (hoaDon.getPhieuGiamGia() != null) {
            PhieuGiamGia pgg = phieuGiamGiaRepository.findById(hoaDon.getPhieuGiamGia().getId())
                    .orElseThrow(() -> new RuntimeException("PhieuGiamGia not found"));
            hd.setPhieuGiamGia(pgg);
        } else {
            hd.setPhieuGiamGia(null);
        }

        return ResponseEntity.ok(hoaDonService.updatePGG(id, hd));
    }

    @PutMapping("/api/hoa-don/update-thong-tin-nguoi-nhan/{id}")
    public ResponseEntity<?> updateThongTinNguoiNhan(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        HoaDon updatedHoaDon = hoaDonService.updateThongTinNguoiNhan(id, hoaDon);
        if (updatedHoaDon != null) {
            return ResponseEntity.ok(updatedHoaDon);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HoaDon not found");
        }
    }

    @PutMapping("/api/hoa-don/update-thong-tin-nguoi-nhan-hoa-don/{id}")
    public ResponseEntity<?> update2(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        HoaDon updatedHoaDon = hoaDonService.updateHoaDon(id, hoaDon);
        if (updatedHoaDon != null) {
            return ResponseEntity.ok(updatedHoaDon);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HoaDon not found");
        }
    }

    @PutMapping("/api/hoa-don-chi-tiet/update-so-luong-sp")
    public ResponseEntity<?> updateHoaDonChiTiet(@RequestBody HoaDonChiTiet hoaDonChiTiet) {
        hoaDonService.updateHoaDonChiTiet(
                hoaDonChiTiet.getSanPhamChiTiet().getId(),
                hoaDonChiTiet.getHoaDon().getId(),
                hoaDonChiTiet.getSoLuong(),
                hoaDonChiTiet.getDonGia(),
                hoaDonChiTiet.getTrangThai()
        );
        return ResponseEntity.ok("Cập nhật thành công");
    }

    //api phan hoa don detail
    @GetMapping("/api/san-pham-ct-co-trong-hoa-don/{hoaDonId}")
    public List<HoaDonChiTietDTO> getHoaDonChiTiet(@PathVariable Integer hoaDonId) {
        return hoaDonChiTietService.getHoaDonChiTietByHoaDonId(hoaDonId);
    }
    //api phan hoa don detail
    @PutMapping("/api/hoa-don/update-trang-thai-thanh-toan/{id}")
    public ResponseEntity<?> updateTrangThaiThanhToan(
            @PathVariable("id") Integer id,
            @RequestBody PhuongThucThanhToan phuongThucThanhToan) {

        PhuongThucThanhToan updated = phuongThucThanhToanService.updateTrangThaiThanhToan(id, phuongThucThanhToan);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PhuongThucThanhToan not found");
        }
    }


    @PutMapping("/api/hoa-don/update-trang-thai/{idHD}")
    public ResponseEntity<String> updateTrangThai(@PathVariable Integer idHD, @RequestBody HoaDon hoaDon) {
        try {
            hoaDonService.updateTrangThai(idHD, hoaDon);
            return ResponseEntity.ok("Cập nhật trạng thái hóa đơn thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật trạng thái thất bại: " + e.getMessage());
        }
    }


    @PostMapping("/api/hoa-don/lich-su-hoa-don/add/{idHD}")
    public ResponseEntity<String> addLichSuHoaDon(@PathVariable Integer idHD, @RequestBody LichSuHoaDon lichSuHoaDon) {
        try {
            lichSuHoaDonService.addLichSuHoaDon(idHD, lichSuHoaDon);
            return ResponseEntity.ok("Thêm lịch sử hóa đơn thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thêm lịch sử hóa đơn thất bại: " + e.getMessage());
        }
    }

}
