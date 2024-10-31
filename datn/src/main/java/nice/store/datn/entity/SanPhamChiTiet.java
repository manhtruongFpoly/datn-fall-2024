package nice.store.datn.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "ID_SP")
    private Integer idSp;

    @Column(name = "ID_MAU_SAC")
    private Integer idMauSac;

    @Column(name = "ID_THE_LOAI")
    private Integer idTheLoai;

    @Column(name = "ID_KICH_CO")
    private Integer idKichCo;

    @Column(name = "ID_CHAT_LIEU")
    private Integer idChatLieu;

    @Column(name = "ID_DE_GIAY")
    private Integer idDeGiay;

    @Column(name = "ID_THUONG_HIEU")
    private Integer idThuongHieu;

    @Column(name = "MA_SPCT")
    private String maSpct;

    @Column(name = "GIA_BAN")
    private BigDecimal giaBan;

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
}
