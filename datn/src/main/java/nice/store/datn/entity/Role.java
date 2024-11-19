package nice.store.datn.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEN_ROLE")
    private String tenRole;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;
}