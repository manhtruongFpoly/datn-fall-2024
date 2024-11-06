package nice.store.datn.controller;


import nice.store.datn.entity.DiaChi;
import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.MauSac;
import nice.store.datn.service.DiaChiService;
import nice.store.datn.service.GioHangService;
import nice.store.datn.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mau-sac")
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;

    @GetMapping("/danh-sach")
    public List<MauSac> getAllMS() {
        return mauSacService.getAllMauSac();
    }

    @GetMapping("/{id}")
    public Optional<MauSac> getGioHangById(@PathVariable int id) {
        return mauSacService.getMauSacById(id);
    }

    @PostMapping("/add")
    public MauSac createMS(@RequestBody MauSac ms) {
        return  mauSacService.create(ms);
    }

    @PutMapping("/update/{id}")
    public MauSac updateMS(@PathVariable int id, @RequestBody MauSac ms) {
        return mauSacService.update(id, ms);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMS(@PathVariable int id) {
        mauSacService.deleteMauSacById(id);
        return "Mau Sac deleted";
    }
}
