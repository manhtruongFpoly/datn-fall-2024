package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "PHUONG_THUC_THANH_TOAN")
public class PhuongThucThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_HOA_DON")
    private HoaDon idHoaDon;

    @Column(name = "TEN_THANH_TOAN", columnDefinition = "nvarchar(max)")
    private String tenThanhToan;

    @Column(name = "LOAI_THANH_TOAN")
    private Boolean loaiThanhToan;

    @Column(name = "TIEN_DA_THANH_TOAN", precision = 18, scale = 0)
    private BigDecimal tienDaThanhToan;

    @Column(name = "GHI_CHU", length = 50)
    private String ghiChu;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_CAP_NHAT")
    private LocalDateTime ngayCapNhat;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;
}