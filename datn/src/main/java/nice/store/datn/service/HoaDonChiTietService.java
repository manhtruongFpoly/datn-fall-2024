package nice.store.datn.service;

import nice.store.datn.entity.HoaDonChiTiet;
import nice.store.datn.entity.PhuongThucThanhToan;
import nice.store.datn.repository.HoaDonChiTietRepository;
import nice.store.datn.response.HoaDonChiTietDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public HoaDonChiTiet deleteById(Integer id) {
        Optional<HoaDonChiTiet> optional = hoaDonChiTietRepository.findById(id);
        return optional.map(o -> {
            hoaDonChiTietRepository.delete(o);
            return o;
        }).orElse(null);
    }

    public Optional<HoaDonChiTietDTO> findDTOById(Integer id) {
        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDonChiTietRepository.findById(id);
        if (hoaDonChiTietOptional.isPresent()) {
            HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietOptional.get();
            return Optional.of(new HoaDonChiTietDTO(hoaDonChiTiet));
        } else {
            return Optional.empty();
        }
    }



}
