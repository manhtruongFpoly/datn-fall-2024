package nice.store.datn.repository;

import nice.store.datn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {
    @Query(value = "SELECT * FROM DIA_CHI WHERE ID_KH = :idKhachHang ", nativeQuery = true)
    DiaChi getDiaChiMacDinh(@Param("idKhachHang") Integer idKhachHang);


}
