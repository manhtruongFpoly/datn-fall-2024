package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "NHAN_VIEN")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ID_ROLE")
    private Integer idRole;

    @Column(name = "MA_NV", length = 50)
    private String maNv;

    @Column(name = "HO", length = 50)
    private String ho;

    @Column(name = "TEN_DEM", length = 50)
    private String tenDem;

    @Column(name = "TEN", length = 50)
    private String ten;
    @Column(name = "MAT_KHAU", length = 50)
    private String matKhau;
    @Column(name = "SDT", length = 15)
    private String sdt;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "GIOI_TINH")
    private int gioiTinh;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NGAY_SINH")
    private LocalDate ngaySinh;

    @Column(name = "DIA_CHI", length = 100)
    private String diaChi;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "NGAY_SUA")
    private LocalDateTime ngaySua;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;
}
