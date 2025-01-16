package nice.store.datn.service;

import nice.store.datn.entity.LoaiGiay;
import nice.store.datn.repository.LoaiGiayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiGiayService {
    @Autowired
    private LoaiGiayRepository loaiGiayRepository;
    public List<LoaiGiay> findAll() {

        return loaiGiayRepository.findAll();
    }

    public LoaiGiay findById(Integer id) {
        return loaiGiayRepository.findById(id).orElse(null);
    }
    private String generateMaLoaiGiay() {
        String prefix = "LG";
        int count = (int) loaiGiayRepository.count() + 1;
        return prefix + String.format("%05d", count);
    }
    public Object save(LoaiGiay loaiGiay) {
        Optional<LoaiGiay> existingDeGiay = loaiGiayRepository.findByTenLoaiGiay(loaiGiay.getTenLoaiGiay());
        if (existingDeGiay.isPresent()) {
            return "Tên đế giày đã tồn tại!";
        }

        String generatedMa = generateMaLoaiGiay();
        loaiGiay.setMaLoaiGiay(generatedMa);
        return loaiGiayRepository.save(loaiGiay);
    }

    public void deleteById(Integer id) {
        loaiGiayRepository.deleteById(id);
    }
}
