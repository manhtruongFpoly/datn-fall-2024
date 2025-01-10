package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "GIO_HANG_CT")
public class GioHangCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_CTSP", referencedColumnName = "ID")
    private SanPhamChiTiet sanPhamChiTiet;
    @ManyToOne
    @JoinColumn(name = "ID_GIO_HANG", referencedColumnName = "ID")
    private GioHang gioHang;

    @Column(name = "SO_LUONG")
    private Integer soLuong;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;


}