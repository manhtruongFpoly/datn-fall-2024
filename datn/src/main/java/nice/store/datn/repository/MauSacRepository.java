package nice.store.datn.repository;

import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
}
