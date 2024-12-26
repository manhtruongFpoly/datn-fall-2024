package nice.store.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nice.store.datn.entity.KhachHang;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HOA_DON")

public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NV", referencedColumnName = "ID")
    private Nhanvien nhanVien; // ánh xạ với entity NhanVien

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_KH", referencedColumnName = "ID")
    private KhachHang khachHang; // ánh xạ với entity KhachHang

    @Column(name = "MA_HD")
    private String maHd;

    @ManyToOne
    @JoinColumn(name = "ID_VOUCHER")
    private PhieuGiamGia phieuGiamGia; // ánh xạ với entity PhieuGiamGia

    @Column(name = "DIA_CHI_NGUOI_NHAN")
    private String diaChiNguoiNhan;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "TEN_NGUOI_NHAN")
    private String tenNguoiNhan;

    @Column(name = "GHI_CHU")
    private String ghiChu;

    @Column(name = "TONG_TIEN")
    private BigDecimal tongTien;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "TIEN_GIAM")
    private BigDecimal tienGiam;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;

    @Column(name = "THANH_PHO")
    private String thanhPho;

    @Column(name = "QUAN_HUYEN")
    private String quanHuyen;

    @Column(name = "PHUONG_XA")
    private String phuongXa;

    @Column(name = "LOAI_HOA_DON")
    private String loaiHoaDon;

    @Column(name = "PHI_SHIP")
    private Integer phiShip;


    public String getStringTrangThai() {
        if (this.trangThai == null) {
            return "";
        }
// 0 : Hóa Đơn chờ 4 hoàn thành 1: chờ xác nhận mặc định k được thay đổi 2 case này
        switch (this.trangThai) {
            case 0:
                return "Hóa Đơn Chờ";
            case 1:
                return "Chờ xác nhận";
            case 2:
                return "Đã xác nhận";
            case 3:
                return "Chờ vận chuyển";
            case 4:
                return "Vận chuyển";
            case 5:
                return "Thanh toán";
            case 6:
                return "Hoàn thành";
            case 7:
                return "Đã hủy";
            case 8:
                return "Không xác định";
            default:
                break;
        }
        return "";
    }

}
