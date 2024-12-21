package nice.store.datn.service;

import nice.store.datn.entity.PhuongThucThanhToan;
import nice.store.datn.repository.PhuongThucThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhuongThucThanhToanService {

    @Autowired
    PhuongThucThanhToanRepository phuongThucThanhToanRepository;
    public PhuongThucThanhToan add(PhuongThucThanhToan phuongThucThanhToan) {
        return phuongThucThanhToanRepository.save(phuongThucThanhToan);
    }

    public List<PhuongThucThanhToan> detail(Integer id) {
        return  phuongThucThanhToanRepository.findByIdHoaDon(id);
    }

    public PhuongThucThanhToan deleteById(Integer id) {
        Optional<PhuongThucThanhToan> optional = phuongThucThanhToanRepository.findById(id);
        return optional.map(o -> {
            phuongThucThanhToanRepository.delete(o);
            return o;
        }).orElse(null);
    }
}
