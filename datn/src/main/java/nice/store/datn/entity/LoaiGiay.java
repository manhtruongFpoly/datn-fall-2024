package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Loai_Giay")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LoaiGiay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Ma_Loai_Giay", length = 50)
    private String maLoaiGiay;

    @Column(name = "Ten_Loai_Giay", length = 50)
    private String tenLoaiGiay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Ngay_Tao")
    private LocalDate ngayTao;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Ngay_Sua")
    private LocalDate ngaySua;

    @Column(name = "Trang_Thai")
    private Integer trangThai;
}
