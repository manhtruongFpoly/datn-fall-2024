package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "Nhan_vien")
public class Nhanvien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID", length = 50)
    private int ID;
    @Column(name = "MA_NV", length = 50)
    private String MANV;

    @Column(name = "HO", length = 50)
    private String HO;

    @Column(name = "TEN", length = 50)
    private String TEN;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFix;

    @Column(name = "TRANG_THAI", length = 50)
    private Integer status;
}
