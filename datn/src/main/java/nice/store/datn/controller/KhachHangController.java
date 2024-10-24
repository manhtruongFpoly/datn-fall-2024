package nice.store.datn.controller;


import nice.store.datn.model.KhachHang;
import nice.store.datn.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService khService;


    @GetMapping
    public List<KhachHang> getAllKhachHang() {
        return khService.getAllKH();
    }

    @GetMapping("/{id}")
    public Optional<KhachHang> getKhachHangById(@PathVariable int id) {
        return khService.getKHById(id);
    }

    @PostMapping
    public KhachHang createKhachHang(@RequestBody KhachHang kh) {
        return  khService.createKH(kh);
    }

    @PutMapping("/{id}")
    public KhachHang updateKhachhang(@PathVariable int id, @RequestBody KhachHang kh) {
        return khService.updateKH(id, kh);
    }

    @DeleteMapping("/{id}")
    public String deleteKhachhang(@PathVariable int id) {
        khService.deleteKHById(id);
        return "Khach hang deleted";
    }
}
