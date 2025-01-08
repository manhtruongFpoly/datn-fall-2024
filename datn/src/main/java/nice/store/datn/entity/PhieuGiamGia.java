package nice.store.datn.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOUCHER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder


public class PhieuGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MA_VOUCHER", length = 50)
    private String ma;

    @Column(name = "TEN", length = 50)
    private String ten;

    @Column(name = "LOAI_VOUCHER", length = 50)
    private String loaiVoucher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NGAY_BAT_DAU")
    private LocalDate ngayBatDau;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NGAY_KET_THUC")
    private LocalDate ngayKetThuc;

    @Column(name = "DON_TOI_THIEU")
    private BigDecimal donToiThieu;

    @Column(name = "GIA_TRI_MIN")
    private BigDecimal giaTriGiam;

    @Column(name = "GIA_TRI_MAX")
    private BigDecimal giaTriMax;

    @Column(name = "SO_LUONG")
    private Integer soLuong;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_SUA")
    private LocalDateTime ngaySua;

    @Column(name = "MO_TA", columnDefinition = "nvarchar(max)")
    private String moTa;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;


    public String getFormattedDonToiThieu() {
        if (donToiThieu == null) {
            return "0 VND";  // Trả về giá trị mặc định khi donToiThieu là null
        }
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(donToiThieu) + " VND";
    }

    public String getFormattedGiaTriGiam() {
        if (giaTriGiam == null) {
            return "0 VND";  // Trả về giá trị mặc định khi giaTriGiam là null
        }
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(giaTriGiam) + " VND";
    }

    public String getFormattedGiaTriMax() {
        if (giaTriMax == null) {
            return "0 VND";  // Trả về giá trị mặc định khi giaTriMax là null
        }
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(giaTriMax) + " VND";
    }

}
