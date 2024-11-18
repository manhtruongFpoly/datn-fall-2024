package nice.store.datn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SAN_PHAM_CT")
public class SanPhamChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_SP", referencedColumnName = "ID")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "ID_MAU_SAC", referencedColumnName = "ID")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "ID_THE_LOAI", referencedColumnName = "ID")
    private LoaiGiay loaiGiay;

    @ManyToOne
    @JoinColumn(name = "ID_KICH_CO", referencedColumnName = "ID")
    private KichCo kichCo;

    @ManyToOne
    @JoinColumn(name = "ID_CHAT_LIEU", referencedColumnName = "ID")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "ID_DE_GIAY", referencedColumnName = "ID")
    private DeGiay deGiay;

    @ManyToOne
    @JoinColumn(name = "ID_THUONG_HIEU", referencedColumnName = "ID")
    private ThuongHieu thuongHieu;

    @Column(name = "MA_SPCT")
    private String maSpct;

    @Column(name = "GIA_BAN")
    private Double giaBan;

    @Column(name = "SO_LUONG")
    private Integer soLuong;

    @Column(name = "MO_TA")
    private String moTa;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_SUA")
    private LocalDateTime ngaySua;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;

    @OneToMany(mappedBy = "sanPhamChiTiet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HinhAnh> hinhAnhs;
}
