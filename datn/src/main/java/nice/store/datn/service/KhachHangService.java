package nice.store.datn.service;

import nice.store.datn.model.KhachHang;
import nice.store.datn.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;


    public KhachHang createKH(KhachHang kh) {
        kh.setNgayTao(LocalDateTime.now());
        return khachHangRepository.save(kh);
    }

    public List<KhachHang> getAllKH() {
        return khachHangRepository.findAll();
    }

    public Optional<KhachHang> getKHById(int id) {
        return khachHangRepository.findById(id);
    }

    public KhachHang updateKH(Integer id, KhachHang kh ) {
        if (khachHangRepository.existsById(id)) {
            kh.setId(id);
            return khachHangRepository.save(kh);
        }
        throw new RuntimeException("KhachHang id " + id + " not found");
    }

    public void deleteKHById(int id) {
        khachHangRepository.deleteById(id);
    }

}
