package nice.store.datn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "GIO_HANG")
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_KH", referencedColumnName = "ID",nullable = false)
    // @JsonBackReference
    private KhachHang khachHang;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_SUA")
    private LocalDateTime ngaySua;

    @Column(name = "TRANG_THAI")
    private int trangThai;
    @OneToMany(mappedBy = "gioHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GioHangCT> gioHangCTs = new ArrayList<>();

    public double getTongTien() {
        return gioHangCTs.stream()
                .filter(gioHangCT -> gioHangCT.getSanPhamChiTiet() != null && gioHangCT.getSanPhamChiTiet().getGiaBan() != null)
                .map(gioHangCT -> gioHangCT.getSanPhamChiTiet().getGiaBan()
                        .multiply(BigDecimal.valueOf(gioHangCT.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }


}