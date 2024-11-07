package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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


    private LocalDate ngayTao;


    private LocalDate ngaySua;


    private int trangThai;
}
