package nice.store.datn.repository;

import nice.store.datn.entity.DeGiay;
import nice.store.datn.entity.LoaiGiay;
import nice.store.datn.service.LoaiGiayService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoaiGiayRepository extends JpaRepository<LoaiGiay, Integer> {
    Optional<LoaiGiay> findByTenLoaiGiay(String tenLoaiGiay);

    Optional<LoaiGiay> findById(Integer id);
    boolean existsByTenLoaiGiay(String tenLoaiGiay);
}
