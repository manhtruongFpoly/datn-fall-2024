package nice.store.datn.controller;

import nice.store.datn.entity.DiaChi;
import nice.store.datn.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diaChi")
public class DiaChiController {
    @Autowired
    private DiaChiService diaChiService;


    @GetMapping
    public List<DiaChi> getAllDiaChi() {
        return diaChiService.getAllDiaChi();
    }

    @GetMapping("/{id}")
    public Optional<DiaChi> getDiaChiById(@PathVariable int id) {
        return diaChiService.getDiaChiById(id);
    }

    @PostMapping
    public DiaChi createDiaChi(@RequestBody DiaChi diaChi) {
        return  diaChiService.createDiaChi(diaChi);
    }

    @PutMapping("/{id}")
    public DiaChi updateDiaChi(@PathVariable int id, @RequestBody DiaChi diaChi) {
        return diaChiService.updateDiaChi(id, diaChi);
    }

    @DeleteMapping("/{id}")
    public String deleteDiaChi(@PathVariable int id) {
        diaChiService.deleteDiaChiById(id);
        return "Khach hang deleted";
    }
}
