package nice.store.datn.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HOA_DON_CT")
@Builder

public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_HOA_DON")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "ID_CTSP")
    private SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "SO_LUONG")
    private Integer soLuong;

    @Column(name = "DON_GIA")
    private Integer donGia;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;


    @Column(name = "TRANG_THAI", nullable = false)
    private Integer trangThai ;


}
