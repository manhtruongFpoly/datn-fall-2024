package nice.store.datn.repository;

import nice.store.datn.entity.Nhanvien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienrepository extends JpaRepository<Nhanvien, Integer> {
    Optional<Nhanvien> findByIdNV(String maNhanVien);
}
