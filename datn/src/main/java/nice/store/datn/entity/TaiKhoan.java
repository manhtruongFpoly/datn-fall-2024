//package nice.store.datn.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Getter
//@Setter
//@Table(name = "TAI_KHOAN")
//public class TaiKhoan {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "USERNAME", length = 50, unique = true)
//    private String userName;
//
//    @Column(name = "PASSWORD", length = 50)
//    private String password;
//
//    @Column(name = "ROLE", length = 50)
//    private String role;
//
//    @OneToOne(mappedBy = "idTK", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private KhachHang idKH;
//
//}
