package nice.store.datn.service;

import nice.store.datn.entity.LoaiGiay;
import nice.store.datn.repository.LoaiGiayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public LoaiGiay save(LoaiGiay loaiGiay) {
        return loaiGiayRepository.save(loaiGiay);
    }

    public void deleteById(Integer id) {
        loaiGiayRepository.deleteById(id);
    }
}
