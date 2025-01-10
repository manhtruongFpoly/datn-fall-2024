package nice.store.datn.service;


import nice.store.datn.entity.MauSac;

import nice.store.datn.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MauSacService {
    @Autowired
    private MauSacRepository mauSacRepository;
    public List<MauSac> getMauSacByProductId(Integer productId) {
        return mauSacRepository.findByProductId(productId);
    }
    public MauSac create(MauSac mauSac) {
        mauSac.setNgayTao(LocalDateTime.now());
        return mauSacRepository.save(mauSac);
    }


    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    public List<MauSac> findAllOrderedByDate() {
        return mauSacRepository.findAll(Sort.by(Sort.Order.desc("ngayTao"))); // Sắp xếp theo ngày tạo giảm dần
    }


    public Optional<MauSac> getMauSacById(Integer id) {
        return mauSacRepository.findById(id);
    }

    public MauSac update(Integer id, MauSac mauSac) {
        MauSac existingMauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc với ID: " + id));

        existingMauSac.setTenMauSac(mauSac.getTenMauSac());
        existingMauSac.setMaMauSac(mauSac.getMaMauSac());
        existingMauSac.setTrangThai(mauSac.getTrangThai());

        return mauSacRepository.save(existingMauSac);
    }


    public boolean deleteMauSacById(int id) {
        if (mauSacRepository.existsById(id)) {
            mauSacRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
