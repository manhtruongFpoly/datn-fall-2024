package nice.store.datn.repository;

import nice.store.datn.entity.ChatLieu;
import nice.store.datn.entity.DeGiay;
import nice.store.datn.entity.LoaiGiay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {
    Optional<ChatLieu> findByTenChatLieu(String tenChatLieu);
    boolean existsByTenChatLieu(String tenChatLieu);
}
