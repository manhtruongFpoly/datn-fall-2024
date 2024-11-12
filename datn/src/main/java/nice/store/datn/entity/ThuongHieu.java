package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ThuongHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String maThuongHieu;


    private String TenThuongHieu;


    private LocalDateTime ngayTao;


    private LocalDateTime ngaySua;


    private int trangThai;
}
