package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HOA_DON_CT")
public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_HOA_DON", referencedColumnName = "ID", nullable = false)
    private HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CTSP", referencedColumnName = "ID", nullable = false)
    private SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "SO_LUONG")
    private Integer soLuong;

    @Column(name = "DON_GIA")
    private Integer donGia;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;

    public Integer tongTien(){
        return this.donGia * this.soLuong;
    }

}
