package nice.store.datn.service;

import jakarta.persistence.EntityNotFoundException;
import nice.store.datn.entity.MauSac;
import nice.store.datn.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MauSacService {
    @Autowired
    private MauSacRepository mauSacRepository;


    private String generateMaMauSac() {
        String prefix = "MS";
        int count = (int) mauSacRepository.count() + 1;
        return prefix + String.format("%05d", count);
    }


    public String addMauSac(MauSac mauSac) {
        Optional<MauSac> existingMauSac = mauSacRepository.findByTenMauSac(mauSac.getTenMauSac());
        if (existingMauSac.isPresent()) {
            return "Tên màu sắc đã tồn tại!";
        }

        String generatedMa = generateMaMauSac();
        mauSac.setMaMauSac(generatedMa);
        mauSac.setNgayTao(LocalDateTime.now());

        mauSacRepository.save(mauSac);
        return "Thêm màu sắc thành công với mã: " + generatedMa;
    }


    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAllByOrderByIdDesc();
    }

    public MauSac updateMauSac(String maMauSac, MauSac updatedMauSac) {



    }
}
