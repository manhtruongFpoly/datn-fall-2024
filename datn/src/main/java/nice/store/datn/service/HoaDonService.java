package nice.store.datn.service;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonService {

    private final HoaDonRepository hoaDonRepository;

    @Autowired
    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }



    public List<HoaDon> getAllHoaDon() {
        return hoaDonRepository.findAll();
    }

    public Optional<HoaDon> getHoaDonById(Integer id) {
        return hoaDonRepository.findById(id);
    }

    public void deleteById(Integer id) {
        hoaDonRepository.deleteById(id);
    }

    public HoaDon saveOrUpdate(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }




}
