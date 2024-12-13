package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nice.store.datn.entity.PhuongThucThanhToan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XuatHoaDonDTO {
    private Integer id;
    private String maHd;
    private String diaChiNguoiNhan;
    private String sdt;
    private String tenNguoiNhan;
    private String ghiChu;
    private BigDecimal tongTien;
    private LocalDateTime ngayTao;
    private BigDecimal tienGiam;
    private Integer trangThai;
    private String thanhPho;
    private String quanHuyen;
    private String phuongXa;
    private String loaiHoaDon;
    private Integer phiShip;

    private String nhanVienTen;
    private String khachHangTen;
    private String phieuGiamGiaMa;

    private List<HoaDonChiTietDTO> hoaDonChiTietList;
    private List<PhuongThucThanhToanDTO> phuongThucThanhToanList;

}
