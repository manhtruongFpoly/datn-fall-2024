package nice.store.datn.service;

import nice.store.datn.entity.*;
import nice.store.datn.repository.HoaDonChiTietRepository;
import nice.store.datn.repository.HoaDonRepository;
import nice.store.datn.repository.SanPhamChiTietRepository;
import nice.store.datn.response.DiaChiDTOs;
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


    public DiaChiDTOs mapToDTO(DiaChi diaChi) {
        DiaChiDTOs dto = new DiaChiDTOs();
        dto.setId(diaChi.getId());
        dto.setDiaChiCuThe(diaChi.getDiaChiCuThe());
        dto.setPhuongXa(diaChi.getPhuongXa());
        dto.setQuanHuyen(diaChi.getQuanHuyen());
        dto.setTinhThanh(diaChi.getTinhThanh());

        if (diaChi.getIdKH() != null) {
            KhachHang khachHang = diaChi.getIdKH();
            dto.setIdKhachHang(khachHang.getId());
            dto.setTen(khachHang.getTen());
            dto.setGioiTinh(khachHang.getGioiTinh());
            dto.setSdt(String.valueOf(khachHang.getSdt()));
            dto.setEmail(khachHang.getEmail());
        }

        return dto;
    }


}
