package nice.store.datn.service;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.HoaDonChiTiet;
import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.repository.HoaDonChiTietRepository;
import nice.store.datn.repository.HoaDonRepository;
import nice.store.datn.repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BanHangService {

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    //hd cho
    public List<HoaDon> getHoaDonCho() {
        return hoaDonRepository.findHoaDonCho();
    }
    public List<SanPhamChiTiet> getAllDSSanPham() {
        return sanPhamChiTietRepository.findAll();
    }

    public HoaDonChiTiet saveHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    public HoaDon updateKhachHang(Integer id, HoaDon hoaDon) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> {
            o.setKhachHang(hoaDon.getKhachHang());
            o.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
            o.setSdt(hoaDon.getSdt());
            return hoaDonRepository.save(o);
        }).orElse(null);
    }

}
