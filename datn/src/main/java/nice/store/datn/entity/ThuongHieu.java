package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name="THUONG_HIEU")
public class ThuongHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MA_THUONG_HIEU", length = 50)
    private String maThuongHieu;

    @Column(name = "TEN_THUONG_HIEU", length = 50)
    private String tenThuongHieu;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_SUA")
    private LocalDateTime ngaySua;

    @Column(name = "TRANG_THAI")
    private int trangThai;
}
