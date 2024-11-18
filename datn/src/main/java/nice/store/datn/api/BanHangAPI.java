package nice.store.datn.api;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.HoaDonChiTiet;
import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.PhieuGiamGia;
import nice.store.datn.response.HoaDonChiTietDTO;
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
import java.util.stream.Collectors;

@RestController

public class BanHangAPI {

    @Autowired
    BanHangService banHangService;

    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HoaDonChiTietService hoaDonChiTietService;


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

        String maHoaDon = "HD0" + String.format("%01d", savedHoaDon.getId());
        savedHoaDon.setMaHd(maHoaDon);

        hoaDonService.taoMaHoaDon(savedHoaDon.getId(), savedHoaDon);

        return ResponseEntity.ok(savedHoaDon);
    }



}
