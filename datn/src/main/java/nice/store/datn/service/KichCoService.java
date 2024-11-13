package nice.store.datn.service;

import nice.store.datn.entity.KichCo;
import nice.store.datn.entity.ThuongHieu;
import nice.store.datn.repository.KichCoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class KichCoService {
    @Autowired
    private KichCoRepository kichCoRepository;
    public List<KichCo> getAllKichCo() {
        return kichCoRepository.findAll();
    }
    public Optional<KichCo> getKichCoById(int id) {
        return kichCoRepository.findById(id);
    }

    public KichCo create(KichCo kc) {
        if (kichCoRepository.findByMaKichCo(kc.getMaKichCo()).isPresent()) {
            throw new RuntimeException("Đã tồn tại" + kc.getMaKichCo());
        }
        kc.setNgayTao(LocalDateTime.now());
        kc.setNgaySua(LocalDateTime.now());
        return kichCoRepository.save(kc);
    }
    public void deleteKichCo(int id) {
        kichCoRepository.deleteById(id);
    }
    public KichCo updateKC(Integer id, KichCo kc) {
        if (kichCoRepository.existsById(id)) {
            KichCo getProduct = kichCoRepository.findById(id).orElseThrow(() -> new RuntimeException("Kich Co id " + id + " not found"));
            kc.setId(id);
            kc.setNgayTao(getProduct.getNgayTao());
            kc.setNgaySua(getProduct.getNgaySua());
        }
        throw new RuntimeException("Product id" +id+ " not found");
    }
}
