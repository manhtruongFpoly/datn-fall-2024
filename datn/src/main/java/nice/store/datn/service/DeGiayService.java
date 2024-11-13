package nice.store.datn.service;

import jakarta.persistence.EntityNotFoundException;
import nice.store.datn.entity.DeGiay;
import nice.store.datn.entity.SanPham;
import nice.store.datn.repository.DeGiayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeGiayService {
    @Autowired
    private DeGiayRepository deGiayRepository;


    private String generateMaDeGiay() {
        String prefix = "DG";
        int count = (int) deGiayRepository.count() + 1;
        return prefix + String.format("%05d", count);
    }


    public String addDeGiay(DeGiay deGiay) {
        Optional<DeGiay> existingDeGiay = deGiayRepository.findByTenDeGiay(deGiay.getTenDeGiay());
        if (existingDeGiay.isPresent()) {
            return "Tên đế giày đã tồn tại!";
        }

        String generatedMa = generateMaDeGiay();
        deGiay.setMaDeGiay(generatedMa);
        deGiay.setNgayTao(LocalDate.now());


        deGiayRepository.save(deGiay);
        return "Thêm đế giày thành công với mã: " + generatedMa;
    }


    public List<DeGiay> getAllDeGiay() {

        return deGiayRepository.findAllByOrderByIdDesc();
    }

    public DeGiay updateDG(String maDeGiay, DeGiay dg) {

        Optional<DeGiay> existingDeGiay = deGiayRepository.findByMaDeGiay(maDeGiay);

        if (existingDeGiay.isPresent()) {
            DeGiay getProduct = existingDeGiay.get();


            dg.setId(getProduct.getId());
            dg.setMaDeGiay(maDeGiay);
            dg.setNgayTao(getProduct.getNgayTao());
            dg.setNgaySua(LocalDateTime.now());


            return deGiayRepository.save(dg);
        } else {

            throw new EntityNotFoundException("DeGiay with maDeGiay " + maDeGiay + " not found");
        }
    }


    public String deleteDeGiay(Integer id) {
        if (deGiayRepository.existsById(id)) {
            deGiayRepository.deleteById(id);
            return "Xóa đế giày thành công!";
        }
        return "Không tìm thấy đế giày!";
    }
    public Optional<DeGiay> getDeGiayById(Integer id) {
        return deGiayRepository.findById(id);
    }
    public boolean existsByTenDeGiay(String tenDeGiay) {
        return deGiayRepository.existsByTenDeGiay(tenDeGiay);
    }


}
