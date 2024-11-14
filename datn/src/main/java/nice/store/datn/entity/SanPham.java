package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="SAN_PHAM")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="Ma_San_Pham")
    private String maSP;
    @Column(name="Ten_SP")
    private String tenSP;
    @Column(name="Ngay_Tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime ngayTao;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="Ngay_Sua")
    private LocalDateTime ngaySua;
    @Column(name="Trang_Thai")
    private Integer trangThai;
    @OneToMany(mappedBy = "sanPham")
    private List<SanPhamChiTiet> sanPhamChiTietList;
}
