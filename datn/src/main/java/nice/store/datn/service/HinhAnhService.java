package nice.store.datn.service;

import nice.store.datn.entity.HinhAnh;
import nice.store.datn.repository.HinhAnhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HinhAnhService {

    @Autowired
    private HinhAnhRepository hinhAnhRepository;


    @Transactional
    public HinhAnh addHinhAnh(HinhAnh hinhAnh) {
        return hinhAnhRepository.save(hinhAnh);
    }

    @Transactional
    public HinhAnh updateHinhAnh(Integer id, HinhAnh hinhAnh) {
        Optional<HinhAnh> existingHinhAnh = hinhAnhRepository.findById(id);
        if (existingHinhAnh.isPresent()) {
            HinhAnh updatedHinhAnh = existingHinhAnh.get();
            updatedHinhAnh.setUrl(hinhAnh.getUrl());
            updatedHinhAnh.setTrangThai(hinhAnh.getTrangThai());
            updatedHinhAnh.setSanPhamChiTiet(hinhAnh.getSanPhamChiTiet());
            return hinhAnhRepository.save(updatedHinhAnh);
        }
        return null;
    }


    @Transactional
    public void deleteHinhAnh(Integer id) {
        hinhAnhRepository.deleteById(id);
    }


    public Optional<HinhAnh> getHinhAnhById(Integer id) {
        return hinhAnhRepository.findById(id);
    }
}
