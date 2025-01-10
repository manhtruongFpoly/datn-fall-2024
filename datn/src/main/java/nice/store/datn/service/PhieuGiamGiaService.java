package nice.store.datn.service;

import nice.store.datn.entity.PhieuGiamGia;
import nice.store.datn.repository.PhieuGiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            existingPgg.setDonToiThieu(newpgg.getDonToiThieu());
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

    public int getMaxId() {
        Integer maxId = phieuGiamGiaRepository.findMaxId();
        return (maxId == null) ? 0 : maxId;
    }

    public List<PhieuGiamGia> PhieuGiamGiaPhuHop(Long max){
        return phieuGiamGiaRepository.getVoucherPhuHop(max);
    }

    public BigDecimal calculateDiscount(PhieuGiamGia voucher, BigDecimal totalAmount) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        // Kiểm tra loại voucher và tính toán giảm giá
        if (voucher.getLoaiVoucher().equals("PERCENTAGE")) {
            // Giảm giá theo phần trăm
            discountAmount = totalAmount.multiply(voucher.getGiaTriGiam()).divide(new BigDecimal(100));
        } else if (voucher.getLoaiVoucher().equals("FIXED")) {
            // Giảm giá theo số tiền cố định
            discountAmount = voucher.getGiaTriGiam();
        }

        // Đảm bảo số tiền giảm không vượt quá tổng tiền
        if (discountAmount.compareTo(totalAmount) > 0) {
            discountAmount = totalAmount;
        }

        return discountAmount;
    }
}
