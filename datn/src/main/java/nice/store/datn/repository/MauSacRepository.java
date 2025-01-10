package nice.store.datn.repository;

import nice.store.datn.entity.DeGiay;
import nice.store.datn.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    Optional<MauSac> findByTenMauSac(String tenMauSac);

    @Query("SELECT ms FROM MauSac ms " +
            "JOIN SanPhamChiTiet spct ON spct.mauSac.id = ms.id " +
            "WHERE spct.sanPham.id = :productId")
    List<MauSac> findByProductId(@Param("productId") Integer productId);
    Optional<MauSac> findById(Integer id);


    boolean existsByTenMauSac(String tenMauSac);

    Optional<MauSac> findByMaMauSac(String maMauSac);
    List<MauSac> findAllByOrderByIdDesc();
}
