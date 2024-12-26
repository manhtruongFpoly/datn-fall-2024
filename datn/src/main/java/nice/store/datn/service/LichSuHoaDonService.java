package nice.store.datn.service;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.LichSuHoaDon;
import nice.store.datn.repository.HoaDonRepository;
import nice.store.datn.repository.LichSuHoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LichSuHoaDonService {

    @Autowired
    LichSuHoaDonRepository repository;
    @Autowired
    HoaDonRepository hoaDonRepository;


    public List<LichSuHoaDon> getAll() {
        return repository.findAll();
    }

    public LichSuHoaDon getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với ID: " + id));
    }
    public List<LichSuHoaDon> getByIdByIDHoaDon(Integer id) {
        List<LichSuHoaDon> results = repository.findByIdHoaDon(id);
        if (results.isEmpty()) {
            // Trả về danh sách rỗng thay vì ném lỗi
            return new ArrayList<>();  // Hoặc bạn có thể chỉ cần return results nếu không cần thay đổi gì thêm
        }
        return results;
    }




    public void addLichSuHoaDon(Integer idHD, LichSuHoaDon request) throws Exception {

        request.setIdHoaDon(idHD);
        request.setNgayTao(LocalDateTime.now());
        request.setGhiChu(request.getGhiChu());
        request.setTrangThai(request.getTrangThai());
        repository.save(request);
    }

    public LichSuHoaDon update(Integer id, LichSuHoaDon lichSuHoaDon) {
        LichSuHoaDon existing = getById(id);
        existing.setIdHoaDon(lichSuHoaDon.getIdHoaDon());
        existing.setNgayTao(lichSuHoaDon.getNgayTao());
        existing.setGhiChu(lichSuHoaDon.getGhiChu());
        existing.setTrangThai(lichSuHoaDon.getTrangThai());
        return repository.save(existing);
    }

}

