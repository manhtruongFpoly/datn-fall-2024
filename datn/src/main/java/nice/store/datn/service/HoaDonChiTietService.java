package nice.store.datn.service;

import nice.store.datn.entity.HoaDonChiTiet;
import nice.store.datn.repository.HoaDonChiTietRepository;
import nice.store.datn.response.HoaDonChiTietDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoaDonChiTietService {

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    public List<HoaDonChiTiet> findByHDId(Integer idHoaDon) {
        return hoaDonChiTietRepository.TimTongTienTheoIDHD(idHoaDon);
    }

    public List<HoaDonChiTietDTO> getHoaDonChiTietByHoaDonId(Integer hoaDonId) {
        return hoaDonChiTietRepository.TimTongTienTheoIDHD(hoaDonId).stream()
                .map(HoaDonChiTietDTO::new)
                .collect(Collectors.toList());
    }
    public HoaDonChiTiet add(HoaDonChiTiet hoaDonChiTiet) {
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }


}
