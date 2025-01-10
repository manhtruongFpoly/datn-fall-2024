package nice.store.datn.service.imp;

import jakarta.transaction.Transactional;
import nice.store.datn.entity.*;
import nice.store.datn.repository.HoaDonRepository;
import nice.store.datn.repository.LichSuHoaDonRepository;
import nice.store.datn.response.CartItemDTO;
import nice.store.datn.response.DonHangDTO;
import nice.store.datn.service.CartService;

import nice.store.datn.service.OrderService;
import nice.store.datn.service.PhieuGiamGiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public abstract class OrderServiceImpl implements OrderService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private PhieuGiamGiaService voucherService;
    @Autowired
    private LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    private CartService cartService;



    public HoaDon taoDonHang(DonHangDTO donHangDTO, Authentication auth) {
        HoaDon hoaDon = new HoaDon();

        hoaDon.setMaHd(generateOrderCode());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTrangThai(TrangThaiDonHang.PROCESSING.getValue());

        hoaDon.setTenNguoiNhan(donHangDTO.getTenNguoiNhan());
        hoaDon.setSdt(donHangDTO.getSoDienThoai());
        hoaDon.setDiaChiNguoiNhan(donHangDTO.getDiaChiNguoiNhan());
        hoaDon.setThanhPho(donHangDTO.getThanhPho());
        hoaDon.setQuanHuyen(donHangDTO.getQuanHuyen());
        hoaDon.setPhuongXa(donHangDTO.getPhuongXa());
        hoaDon.setGhiChu(donHangDTO.getGhiChu());

        if (auth != null) {
            KhachHang khachHang = (KhachHang) auth.getPrincipal();
            hoaDon.setKhachHang(khachHang);
        }

        if (donHangDTO.getMaVoucher() != null) {
            applyVoucher(hoaDon, donHangDTO.getMaVoucher());
        }

        calculateShippingFee(hoaDon);

        hoaDon = hoaDonRepository.save(hoaDon);

        createOrderDetails(hoaDon, donHangDTO.getDanhSachSanPham());

        calculateTotalAmount(hoaDon);

        hoaDon = hoaDonRepository.save(hoaDon);

        cartService.clearCart(auth);



        return hoaDon;
    }
    private String generateOrderCode() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int randomNumber = new Random().nextInt(1000); // Số ngẫu nhiên từ 0 đến 999
        return "HD-" + timestamp + "-" + String.format("%03d", randomNumber);
    }
    private void applyVoucher(HoaDon hoaDon, Integer maVoucher) {
        PhieuGiamGia voucher = voucherService.findAll().get(maVoucher);
        if (voucher != null) {
            hoaDon.setPhieuGiamGia(voucher);
            hoaDon.setTienGiam(voucherService.calculateDiscount(voucher, hoaDon.getTongTien()));
        }
    }

    private void calculateShippingFee(HoaDon hoaDon) {
        int baseFee = 30000;

        switch (hoaDon.getThanhPho().toLowerCase()) {
            case "hà nội":
            case "hồ chí minh":
                hoaDon.setPhiShip(baseFee);
                break;
            default:
                hoaDon.setPhiShip(baseFee + 20000);
        }


    }

    private void createOrderDetails(HoaDon hoaDon, List<CartItemDTO> danhSachSanPham) {
        List<HoaDonChiTiet> orderDetails = new ArrayList<>();

        for (CartItemDTO item : danhSachSanPham) {
            HoaDonChiTiet detail = new HoaDonChiTiet();
            detail.setHoaDon(hoaDon);
            detail.setSanPhamChiTiet(item.getSanPhamChiTiet());
            detail.setSoLuong(item.getSoLuong());
            detail.setDonGia(item.getDonGia());
            detail.setNgayTao(LocalDateTime.now());
            detail.setTrangThai(1);

            orderDetails.add(detail);
        }

        hoaDon.setHoaDonChiTiets(orderDetails);
    }

    private void calculateTotalAmount(HoaDon hoaDon) {
        BigDecimal total = hoaDon.getHoaDonChiTiets().stream()
                .map(detail -> detail.getDonGia().multiply(new BigDecimal(detail.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        total = total.add(new BigDecimal(hoaDon.getPhiShip()));

        if (hoaDon.getTienGiam() != null) {
            total = total.subtract(hoaDon.getTienGiam());
        }

        hoaDon.setTongTien(total);
    }


    public void capNhatTrangThaiDonHang(Integer idDonHang, TrangThaiDonHang trangThaiMoi, Authentication auth) {
        HoaDon hoaDon = hoaDonRepository.findById(idDonHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        if (!canUpdateOrderStatus(auth, hoaDon)) {
            throw new RuntimeException("Không có quyền cập nhật đơn hàng");
        }

        hoaDon.setTrangThai(trangThaiMoi.getValue());
        hoaDonRepository.save(hoaDon);

        saveLichSuHoaDon(hoaDon);


    }

    private boolean canUpdateOrderStatus(Authentication auth, HoaDon hoaDon) {
        if (auth == null) return false;

        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {
            return hoaDon.getTrangThai() != TrangThaiDonHang.COMPLETED.getValue();
        }

        KhachHang khachHang = (KhachHang) auth.getPrincipal();
        return hoaDon.getKhachHang().getId().equals(khachHang.getId()) &&
                hoaDon.getTrangThai() == TrangThaiDonHang.PROCESSING.getValue();
    }

    private void saveLichSuHoaDon(HoaDon hoaDon) {
        LichSuHoaDon lichSu = new LichSuHoaDon();
        lichSu.setIdHoaDon(hoaDon.getId());
        lichSu.setNgayTao(LocalDateTime.now());
        lichSu.setTrangThai(hoaDon.getStringTrangThai());

        if (hoaDon.getTrangThai() == TrangThaiDonHang.COMPLETED.getValue()) {
            lichSu.setNgayTao(LocalDateTime.now());
        }

        lichSuHoaDonRepository.save(lichSu);
    }
}