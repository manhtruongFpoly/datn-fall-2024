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
@Table(name = "LICH_SU_HD")
public class LichSuHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ID_HOA_DON")
    private Integer idHoaDon;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "GHI_CHU")
    private String ghiChu;

    @Column(name = "TRANG-THAI")
    private String trangThai;
}
