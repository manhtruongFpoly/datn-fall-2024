package nice.store.datn.repository;

import nice.store.datn.entity.Nhanvien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienrepository extends JpaRepository<Nhanvien, Integer> {
}
