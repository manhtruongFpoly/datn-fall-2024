package nice.store.datn.controller;

import nice.store.datn.entity.LichSuHoaDon;
import nice.store.datn.entity.PhuongThucThanhToan;
import nice.store.datn.service.PhuongThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhuongThucThanhToanController {
    @Autowired
    PhuongThucThanhToanService phuongThucThanhToanService;


    @GetMapping("/api/phuong-thuc-thanh-toan-all/{id}")
    public ResponseEntity<List<PhuongThucThanhToan>> getByIdByIDHD(@PathVariable Integer id) {
        return ResponseEntity.ok(phuongThucThanhToanService.getByIdByIDHoaDon(id));
    }
}
