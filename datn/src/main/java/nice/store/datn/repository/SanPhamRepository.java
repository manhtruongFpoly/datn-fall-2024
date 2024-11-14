package nice.store.datn.repository;

import nice.store.datn.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    Optional<SanPham> findByMaSP(String maSP);
    Optional<SanPham> findByTenSP(String tenSP);

}
