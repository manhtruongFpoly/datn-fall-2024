package nice.store.datn.repository;

import nice.store.datn.entity.LoaiGiay;
import nice.store.datn.service.LoaiGiayService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaiGiayRepository extends JpaRepository<LoaiGiay, Integer> {
}
