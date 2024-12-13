package nice.store.datn.repository;

import nice.store.datn.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    @Query(value = "SELECT * FROM [dbo].[HOA_DON_CT] WHERE [ID_HOA_DON] = :idHoaDon ORDER BY [ID] DESC", nativeQuery = true)
    List<HoaDonChiTiet> TimTongTienTheoIDHD(@Param("idHoaDon") Integer idHoaDon);

    List<HoaDonChiTiet> findByHoaDonId(Integer hoaDonId);
}
