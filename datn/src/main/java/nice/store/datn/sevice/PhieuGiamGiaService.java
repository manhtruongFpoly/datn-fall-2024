package nice.store.datn.sevice;

import nice.store.datn.entity.PhieuGiamGia;
import nice.store.datn.repository.PhieuGiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuGiamGiaService {

    @Autowired
    PhieuGiamGiaRepository phieuGiamGiaRepository;

    public List<PhieuGiamGia> findAll() {
        return phieuGiamGiaRepository.findAll();
    }

    public PhieuGiamGia findById(Integer id){
        Optional<PhieuGiamGia>optional = phieuGiamGiaRepository.findById(id);
        return optional.map(o -> o).orElse(null);
    }

    public PhieuGiamGia save(PhieuGiamGia phieuGiamGia) {
        return phieuGiamGiaRepository.save(phieuGiamGia);
    }

    public PhieuGiamGia update(Integer id, PhieuGiamGia newpgg) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepository.findById(id);
        return optional.map(existingPgg -> {
            // Cập nhật các trường cần thiết từ newpgg
            existingPgg.setMa(newpgg.getMa());
            existingPgg.setTen(newpgg.getTen());
            existingPgg.setLoaiVoucher(newpgg.getLoaiVoucher());
            existingPgg.setNgayBatDau(newpgg.getNgayBatDau());
            existingPgg.setNgayKetThuc(newpgg.getNgayKetThuc());
            existingPgg.setGiaTriGiam(newpgg.getGiaTriGiam());
            existingPgg.setGiaTriMax(newpgg.getGiaTriMax());
            existingPgg.setSoLuong(newpgg.getSoLuong());
            existingPgg.setMoTa(newpgg.getMoTa());
            existingPgg.setTrangThai(newpgg.getTrangThai());
            // Cập nhật trường ngày sửa
            existingPgg.setNgaySua(LocalDateTime.now());
            // Lưu
            return phieuGiamGiaRepository.save(existingPgg);
        }).orElse(null);
    }

}
