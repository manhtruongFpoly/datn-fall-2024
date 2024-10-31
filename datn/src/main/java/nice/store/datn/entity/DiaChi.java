package nice.store.datn.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "[DIA_CHI]")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_KH", referencedColumnName = "ID")
    @JsonBackReference
    private KhachHang idKH;


    @Column(name = "DIA_CHI_CU_THE", length = 255)
    private String diaChiCuThe;

    @Column(name = "PHUONG_XA", length = 50)
    private String phuongXa;

    @Column(name = "QUAN_HUYEN", length = 50)
    private String quanHuyen;

    @Column(name = "TINH_THANH", length = 50)
    private String tinhThanh;

    @Column(name = "NGAY_TAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAY_SUA")
    private LocalDateTime ngaySua;

    @Column(name = "TRANG_THAI")
    private int trangThai;

}
