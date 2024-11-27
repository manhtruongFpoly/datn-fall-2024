package nice.store.datn.repository;

import nice.store.datn.entity.PhuongThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhuongThucThanhToanRepository extends JpaRepository<PhuongThucThanhToan , Integer> {


    @Query(value = "SELECT * FROM PHUONG_THUC_THANH_TOAN WHERE ID_HOA_DON = :idHoaDon", nativeQuery = true)
    List<PhuongThucThanhToan> findByIdHoaDon(@Param("idHoaDon") Integer idHoaDon);

}
