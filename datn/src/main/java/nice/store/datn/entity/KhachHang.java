package nice.store.datn.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "KHACH_HANG")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MA_KH", length = 50, unique = true)
    private String maKH;

    @Column(name = "HO", length = 50)
    private String ho;

    @Column(name = "TEN_DEM", length = 50)
    private String tenDem;

    @Column(name = "TEN", length = 50)
    private String ten;

    @Column(name = "GIOI_TINH")
    private int gioiTinh;

    @Column(name = "NGAY_SINH")
    private Date ngaySinh;

    @Column(name = "SDT", unique = true)
    private int sdt;

    @Column(name = "EMAIL", length = 50, unique = true)
    private String email;

    @Column(name = "NGAY_TAO", length = 50)
    private LocalDateTime ngayTao;

    @Column(name = "TRANG_THAI", length = 50)
    private int trangThai;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_TK", referencedColumnName = "id", unique = true)
    @JsonManagedReference
    private TaiKhoan idTK;

    @OneToMany(mappedBy = "idKH", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<GioHang> idGH;

    @OneToMany(mappedBy = "idKH",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DiaChi> diaChi = new ArrayList<>();

}
