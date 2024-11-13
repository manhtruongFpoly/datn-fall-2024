package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "De_Giay")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DeGiay {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "MA_DE_GIAY", length = 50)
        private String maDeGiay;

        @Column(name = "TEN_DE_GIAY", length = 50)
        private String tenDeGiay;

        @Column(name = "NGAY_TAO")
        private LocalDate ngayTao;

        @Column(name = "NGAY_SUA")
        private LocalDateTime ngaySua;

        @Column(name = "TRANG_THAI")
        private int trangThai;
}
