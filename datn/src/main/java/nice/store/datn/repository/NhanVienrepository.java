package nice.store.datn.repository;



import nice.store.datn.entity.NhanVien;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienrepository extends JpaRepository<NhanVien, Integer> {
    @Query("SELECT nv FROM NhanVien nv WHERE nv.maNv = :maNV")
    Optional<NhanVien> findByMaNV(@Param("maNV") String maNV);

    Optional<NhanVien> findByMaNv(String maNv);
    NhanVien findByEmail(String email);
}
