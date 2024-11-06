package nice.store.datn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "CHAT_LIEU")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChatLieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Ma_Chat_Lieu", length = 50)
    private String maChatLieu;

    @Column(name = "Ten_Chat_Lieu", length = 50)
    private String tenChatLieu;

    @Column(name = "NGAY_TAO")
    private LocalDate ngayTao;

    @Column(name = "NGAY_SUA")
    private LocalDate ngaySua;

    @Column(name = "TRANG_THAI")
    private int trangThai;
}
