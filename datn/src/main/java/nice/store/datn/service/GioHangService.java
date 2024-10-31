package nice.store.datn.service;


import nice.store.datn.entity.GioHang;
import nice.store.datn.repository.GioHangrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {

    @Autowired
    private GioHangrepository gioHangrepository;

    public GioHang create(GioHang gioHang) {
        gioHang.setNgayTao(LocalDateTime.now());
        return gioHangrepository.save(gioHang);
    }

    //get all gio hang
    public List<GioHang> getAllGH() {
        return gioHangrepository.findAll();
    }

    //get gioHang by id
    public Optional<GioHang> getGHById(int id) {
        return gioHangrepository.findById(id);
    }

    //update gioHang
    public GioHang update(Integer id, GioHang gioHang) {
        if (gioHangrepository.existsById(id)) {
            gioHang.setId(id);
            return gioHangrepository.save(gioHang);
        }
        throw new RuntimeException("Gio Hang id" + id + " not found");
    }

    //delete GioHang
    public void deleteGhById(int id) {
         gioHangrepository.deleteById(id);
    }
}
