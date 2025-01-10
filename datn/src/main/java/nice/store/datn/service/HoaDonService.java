package nice.store.datn.service;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.HoaDonChiTiet;
import nice.store.datn.entity.PhuongThucThanhToan;
import nice.store.datn.repository.HoaDonChiTietRepository;
import nice.store.datn.repository.HoaDonRepository;
import nice.store.datn.repository.PhuongThucThanhToanRepository;
import nice.store.datn.response.HoaDonChiTietDTO;
import nice.store.datn.response.PhuongThucThanhToanDTO;
import nice.store.datn.response.XuatHoaDonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HoaDonService {

    private final HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    PhuongThucThanhToanRepository phuongThucThanhToanRepository;

    @Autowired
    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }


    public List<HoaDon> getAllHoaDon() {
        // Sắp xếp theo trường "ngayTao" giảm dần (DESC)
        return hoaDonRepository.findAll(Sort.by(Sort.Order.desc("id")));
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
            o.setThanhPho(hd.getThanhPho());
            o.setQuanHuyen(hd.getQuanHuyen());
            o.setPhuongXa(hd.getPhuongXa());
            o.setPhiShip(hd.getPhiShip());
            o.setGhiChu(hd.getGhiChu());
            return hoaDonRepository.save(o);
        }).orElse(null);
    }

    public HoaDon updateHoaDon(Integer id, HoaDon hd) {
        Optional<HoaDon> optional = hoaDonRepository.findById(id);
        return optional.map(o -> {
            o.setTenNguoiNhan(hd.getTenNguoiNhan());
            o.setSdt(hd.getSdt());
            o.setDiaChiNguoiNhan(hd.getDiaChiNguoiNhan());
            o.setThanhPho(hd.getThanhPho());
            o.setQuanHuyen(hd.getQuanHuyen());
            o.setPhuongXa(hd.getPhuongXa());
            o.setPhiShip(hd.getPhiShip());
            o.setGhiChu(hd.getGhiChu());
            o.setTrangThai(hd.getTrangThai());

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

    public HoaDon findHoaDonById(Integer id) {
        return hoaDonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
    }

    public XuatHoaDonDTO toXuatHoaDonDTO(HoaDon hoaDon) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        List<PhuongThucThanhToan> phuongThucThanhToanList = phuongThucThanhToanRepository.findByIdHoaDon1(hoaDon.getId());

        XuatHoaDonDTO dto = new XuatHoaDonDTO();
        dto.setId(hoaDon.getId());
        dto.setMaHd(hoaDon.getMaHd());
        dto.setDiaChiNguoiNhan(hoaDon.getDiaChiNguoiNhan());
        dto.setSdt(hoaDon.getSdt());
        dto.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
        dto.setGhiChu(hoaDon.getGhiChu());
        dto.setTongTien(hoaDon.getTongTien());
        dto.setNgayTao(hoaDon.getNgayTao());
        dto.setTienGiam(hoaDon.getTienGiam());
        dto.setTrangThai(hoaDon.getTrangThai());
        dto.setThanhPho(hoaDon.getThanhPho());
        dto.setQuanHuyen(hoaDon.getQuanHuyen());
        dto.setPhuongXa(hoaDon.getPhuongXa());
        dto.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
        dto.setPhiShip(hoaDon.getPhiShip());

        dto.setNhanVienTen(hoaDon.getNhanVien() != null ? hoaDon.getNhanVien().getTen() : null);
        dto.setKhachHangTen(hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getTen() : null);
        dto.setPhieuGiamGiaMa(hoaDon.getPhieuGiamGia() != null ? hoaDon.getPhieuGiamGia().getMa() : null);

        List<HoaDonChiTietDTO> chiTietDTOList = chiTietList.stream().map(chiTiet -> {
            HoaDonChiTietDTO chiTietDTO = new HoaDonChiTietDTO();
            chiTietDTO.setTenSanPham(chiTiet.getSanPhamChiTiet().getSanPham().getTenSP());
            chiTietDTO.setSoLuong(chiTiet.getSoLuong());
            chiTietDTO.setDonGia(chiTiet.getDonGia());

            return chiTietDTO;
        }).collect(Collectors.toList());

        dto.setHoaDonChiTietList(chiTietDTOList);

        List<PhuongThucThanhToanDTO> phuongThucThanhToanDTOList = phuongThucThanhToanList.stream().map(phuongThuc -> {
            PhuongThucThanhToanDTO phuongThucDTO = new PhuongThucThanhToanDTO();
            phuongThucDTO.setId(phuongThuc.getId());
            phuongThucDTO.setTenThanhToan(phuongThuc.getTenThanhToan());
            phuongThucDTO.setLoaiThanhToan(phuongThuc.getLoaiThanhToan());
            phuongThucDTO.setTienDaThanhToan(phuongThuc.getTienDaThanhToan());
            phuongThucDTO.setGhiChu(phuongThuc.getGhiChu());
            phuongThucDTO.setNgayTao(phuongThuc.getNgayTao());
            phuongThucDTO.setNgayCapNhat(phuongThuc.getNgayCapNhat());
            phuongThucDTO.setTrangThai(phuongThuc.getTrangThai());
            return phuongThucDTO;
        }).collect(Collectors.toList());
        dto.setPhuongThucThanhToanList(phuongThucThanhToanDTOList);


        return dto;
    }


}
