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
    private String generateMaMauSac() {
        String prefix = "MS";
        int count = (int) mauSacRepository.count() + 1;
        return prefix + String.format("%05d", count);
    }
    public Object create(MauSac mauSac) {
        Optional<MauSac> existsByTenMauSac = mauSacRepository.findByTenMauSac(mauSac.getTenMauSac());
        if (existsByTenMauSac.isPresent()) {
            return "Tên chất liệu đã tồn tại!";
        }


        String generatedMa = generateMaMauSac();
        mauSac.setMaMauSac(generatedMa);
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
