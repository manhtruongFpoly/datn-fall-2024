package nice.store.datn.api;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.HoaDonChiTiet;
import nice.store.datn.entity.PhieuGiamGia;
import nice.store.datn.repository.PhieuGiamGiaRepository;
import nice.store.datn.service.HoaDonService;
import nice.store.datn.service.PhieuGiamGiaService;
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


}
