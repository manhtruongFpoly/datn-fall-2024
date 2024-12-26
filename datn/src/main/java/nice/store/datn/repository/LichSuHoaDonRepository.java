package nice.store.datn.repository;

import nice.store.datn.entity.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, Integer> {

//    @Query("SELECT l FROM LichSuHoaDon l WHERE l.idHoaDon = :idHoaDon")
//    List<LichSuHoaDon> findByIdHoaDon(@Param("idHoaDon") Integer idHoaDon);
@Query("SELECT l FROM LichSuHoaDon l WHERE l.idHoaDon = :idHoaDon ORDER BY l.trangThai ASC")
List<LichSuHoaDon> findByIdHoaDon(@Param("idHoaDon") Integer idHoaDon);


}
