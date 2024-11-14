package nice.store.datn.repository;

import nice.store.datn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet,Integer> {


    List<SanPhamChiTiet> findBySanPhamId(Integer sanPhamId);
    @Query("SELECT count(spct) FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :productId")
    Integer findTotalQuantityByProductId(@Param("productId") Integer productId);



}
