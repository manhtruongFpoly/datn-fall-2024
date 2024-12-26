package nice.store.datn.controller;

import nice.store.datn.entity.LichSuHoaDon;
import nice.store.datn.service.LichSuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LichSuHoaDonController {

    @Autowired
    private LichSuHoaDonService service;

    @GetMapping("/api/lich-su-hoa-don")
    public List<LichSuHoaDon> getAll() {
        return service.getAll();
    }

    @GetMapping("/api/lich-su-hoa-don/{id}")
    public ResponseEntity<LichSuHoaDon> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/api/lich-su-hoa-don1/{id}")
    public ResponseEntity<List<LichSuHoaDon>> getByIdByIDHD(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getByIdByIDHoaDon(id));
    }




    @PutMapping("/api/lich-su-hoa-don/{id}")
    public ResponseEntity<LichSuHoaDon> update(@PathVariable Integer id, @RequestBody LichSuHoaDon lichSuHoaDon) {
        return ResponseEntity.ok(service.update(id, lichSuHoaDon));
    }


}

