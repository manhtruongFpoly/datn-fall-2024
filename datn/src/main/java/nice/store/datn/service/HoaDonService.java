package nice.store.datn.service;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public HoaDon detail1(Integer id) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);  // Tìm HoaDon từ ID
        return optional.orElse(null);  // Nếu không tìm thấy, trả về null
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

    public HoaDon updateTongTien(Integer id, HoaDon hd) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> {
            o.setTongTien(hd.getTongTien());
            o.setTienGiam(hd.getTienGiam());
            return hoaDonRepository.save(o);
        }).orElse(null);
    }

    public HoaDon updateThongTinNguoiNhan(Integer id, HoaDon hd) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> {
            o.setTenNguoiNhan(hd.getTenNguoiNhan());
            o.setSdt(hd.getSdt());
            o.setDiaChiNguoiNhan(hd.getDiaChiNguoiNhan());
            o.setPhiShip(hd.getPhiShip());
            o.setGhiChu(hd.getGhiChu());
            return hoaDonRepository.save(o);
        }).orElse(null);
    }

    public HoaDon updateTrangThai(Integer id, HoaDon hoaDon) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> {
            o.setTrangThai(hoaDon.getTrangThai());
            return hoaDonRepository.save(o);
        }).orElse(null);
    }

    @Transactional
    public void updateHoaDonChiTiet(Integer idChiTietSanPham, Integer idHoaDon, Integer soLuong, BigDecimal gia, Integer trangThai) {
        hoaDonRepository.updateByIdChiTietSanPhamAndIdHoaDon(soLuong, gia, trangThai, idChiTietSanPham, idHoaDon);

        System.out.println("Cập nhật dữ liệu thành công.");
    }





}
