package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "HINH_ANH")
public class HinhAnh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "URL")
    private String url;

    @Column(name = "TRANG_THAI")
    private int trangThai;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SPCT", referencedColumnName = "ID")
    private SanPhamChiTiet sanPhamChiTiet;

}
