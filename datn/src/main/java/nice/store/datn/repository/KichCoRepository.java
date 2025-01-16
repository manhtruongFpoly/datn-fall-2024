package nice.store.datn.repository;

import nice.store.datn.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Integer> {



    @Query("SELECT kc FROM KichCo kc " +
            "JOIN SanPhamChiTiet spct ON spct.kichCo.id = kc.id " +
            "WHERE spct.sanPham.id = :productId AND spct.trangThai = 1")
    List<KichCo> findByProductId(@Param("productId") Integer productId);


    Optional<KichCo> findKichCoBySize(String size);


    Optional<KichCo> findByMaKichCo(String maKichCo);


    boolean existsKichCoBySize(String size);

    List<KichCo> findAllByOrderByNgayTaoDesc();
    Optional<KichCo> findById(Integer id);
}
