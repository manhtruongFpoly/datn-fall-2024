package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEN_ROLE")
    private String tenRole;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;

    @OneToMany(mappedBy = "idRole", cascade = CascadeType.ALL)
//    @JsonBackReference
    private List<KhachHang> idKH;
}