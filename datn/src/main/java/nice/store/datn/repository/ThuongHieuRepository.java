package nice.store.datn.repository;

import nice.store.datn.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {

    Optional<ThuongHieu> findByMaThuongHieu(String maTh);
    Optional<ThuongHieu> findByTenThuongHieu(String tenThuongHieu);
}
