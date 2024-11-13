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

        Optional<MauSac> existingMauSacOpt = mauSacRepository.findByMaMauSac(maMauSac);

        if (existingMauSacOpt.isPresent()) {

            MauSac existingMauSac = existingMauSacOpt.get();


            existingMauSac.setTenMauSac(updatedMauSac.getTenMauSac());
            existingMauSac.setTrangThai(updatedMauSac.getTrangThai());



            existingMauSac.setNgaySua(LocalDateTime.now());

            // Save the updated MauSac
            return mauSacRepository.save(existingMauSac);
        } else {
            throw new EntityNotFoundException("MauSac with maMauSac " + maMauSac + " not found");
        }
    }



    public String deleteMauSac(Integer id) {
        if (mauSacRepository.existsById(id)) {
            mauSacRepository.deleteById(id);
            return "Xóa màu sắc thành công!";
        }
        return "Không tìm thấy màu sắc!";
    }


    public Optional<MauSac> getMauSacById(Integer id) {
        return mauSacRepository.findById(id);
    }


    public boolean existsByTenMauSac(String tenMauSac) {
        return mauSacRepository.existsByTenMauSac(tenMauSac);
    }
}
