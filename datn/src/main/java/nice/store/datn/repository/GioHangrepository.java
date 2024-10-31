package nice.store.datn.repository;

import nice.store.datn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangrepository extends JpaRepository<GioHang, Integer> {
}
