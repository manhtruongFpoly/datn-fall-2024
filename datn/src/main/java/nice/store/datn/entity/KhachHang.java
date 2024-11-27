package nice.store.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    private Integer gioiTinh;

    @Column(name = "NGAY_SINH")
    private Date ngaySinh;

    @Column(name = "SDT", unique = true)
    private Integer sdt;

    @Column(name = "EMAIL", length = 50, unique = true)
    private String email;

    @Column(name = "NGAY_TAO", length = 50)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_SUA")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngaySua;

    @Column(name = "TRANG_THAI")
    private Integer trangThai = 0;

    @NotNull(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    @Column(name = "MAT_KHAU", length = 50)
    private String matKhau;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ID_ROLE", referencedColumnName = "id")
   // @JsonManagedReference
    private Role idRole;

    @OneToMany(mappedBy = "idKH", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private List<GioHang> idGH;

    @OneToMany(mappedBy = "idKH", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   // @JsonManagedReference
    private List<DiaChi> diaChi = new ArrayList<>();


}
