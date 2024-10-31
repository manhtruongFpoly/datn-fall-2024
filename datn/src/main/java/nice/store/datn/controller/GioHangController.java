package nice.store.datn.controller;


import nice.store.datn.entity.GioHang;
import nice.store.datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gioHang")
public class GioHangController {
    @Autowired
    private GioHangService ghService;

    @GetMapping
    public List<GioHang> getAllKhachHang() {
        return ghService.getAllGH();
    }

    @GetMapping("/{id}")
    public Optional<GioHang> getGioHangById(@PathVariable int id) {
        return ghService.getGHById(id);
    }

    @PostMapping
    public GioHang createGioHang(@RequestBody GioHang gh) {
        return  ghService.create(gh);
    }

    @PutMapping("/{id}")
    public GioHang updateGh(@PathVariable int id, @RequestBody GioHang gh) {
        return ghService.update(id, gh);
    }

    @DeleteMapping("/{id}")
    public String deleteGH(@PathVariable int id) {
        ghService.deleteGhById(id);
        return "Gio hang deleted";
    }
}
