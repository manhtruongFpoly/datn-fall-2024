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




    //Hải Làm Bán Hàng
    public HoaDon detail(Integer id) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> o).orElse(null);
    }

    public HoaDon add(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    public HoaDon taoMaHoaDon(Integer id, HoaDon hoaDon) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> {
            o.setMaHd(hoaDon.getMaHd());
            return hoaDonRepository.save(o);
        }).orElse(null);
    }

    public HoaDon updatePGG(Integer id, HoaDon hd) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> {
            o.setPhieuGiamGia(hd.getPhieuGiamGia());
            return hoaDonRepository.save(o);
        }).orElse(null);
    }

}
