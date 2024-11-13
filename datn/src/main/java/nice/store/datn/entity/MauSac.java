package nice.store.datn.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "MAU_SAC")
public class MauSac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MA_MAU_SAC")
    private String maMauSac;

    @Column(name = "TEN_MAU_SAC")
    private String tenMauSac;

    @Column(name = "NGAY_TAO")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_SUA")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngaySua;

    @Column(name = "TRANG_THAI")
    private int trangThai;

    @OneToMany(mappedBy = "idMauSac", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPhamChiTiet> idSPCT;
}
