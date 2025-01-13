package nice.store.datn.repository;

import nice.store.datn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import java.util.Optional;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {


    List<SanPhamChiTiet> findBySanPhamId(Integer sanPhamId);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.mauSac.id = :mauSacId AND s.kichCo.id = :kichCoId AND s.id = :idSp")
    Optional<SanPhamChiTiet> findByMauSacAndKichCo(@Param("mauSacId") Integer mauSacId,
                                                   @Param("kichCoId") Integer kichCoId,
                                                   @Param("idSp") Integer idSp);


    @Query("SELECT s FROM SanPhamChiTiet s WHERE " +
            "s.mauSac.id = :mauSacId AND " +
            "s.loaiGiay.id = :theLoaiId AND " +
            "s.kichCo.id = :kichCoId AND " +
            "s.chatLieu.id = :chatLieuId AND " +
            "s.deGiay.id = :deGiayId AND " +
            "s.thuongHieu.id = :thuongHieuId AND " +
            "s.sanPham.id = :idSp")
        // Thay maSpct bằng sanPham.id
    Optional<SanPhamChiTiet> findByMauSacIdAndTheLoaiIdAndKichCoIdAndChatLieuIdAndDeGiayIdAndThuongHieuIdAndSanPhamId(
            @Param("mauSacId") Integer mauSacId,
            @Param("theLoaiId") Integer theLoaiId,
            @Param("kichCoId") Integer kichCoId,
            @Param("chatLieuId") Integer chatLieuId,
            @Param("deGiayId") Integer deGiayId,
            @Param("thuongHieuId") Integer thuongHieuId,
            @Param("idSp") Integer idSp  // Tham số mới idSp
    );

    @Query("SELECT spct FROM SanPhamChiTiet spct WHERE spct.mauSac.id = :mauSacId AND spct.kichCo.id = :kichCoId")
    Optional<SanPhamChiTiet> findByMauSacAndKichCo(@Param("mauSacId") Integer mauSacId, @Param("kichCoId") Integer kichCoId);

    @Query("SELECT SUM(spct.soLuong) FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :productId")
    Integer findTotalQuantityByProductId(@Param("productId") Integer productId);


    //cilnt
    @Query(value = "SELECT TOP 1 spct.SO_LUONG " +
            "FROM SAN_PHAM_CT spct " +
            "WHERE spct.ID_SP = :productId " +
            "AND spct.ID_MAU_SAC = :colorId " +
            "AND spct.ID_KICH_CO = :sizeId " +
            "ORDER BY spct.SO_LUONG DESC",
            nativeQuery = true)
    Integer findQuantityByProductAndAttributes(@Param("productId") Integer productId,
                                               @Param("colorId") Integer colorId,
                                               @Param("sizeId") Integer sizeId
                                               );

    @Query(value = "SELECT TOP 1 * " +
            "FROM SAN_PHAM_CT spct " +
            "WHERE spct.ID_SP = :productId " +
            "AND spct.ID_MAU_SAC = :colorId " +
            "AND spct.ID_KICH_CO = :sizeId " +
            "ORDER BY spct.SO_LUONG DESC",
            nativeQuery = true)
    Optional<SanPhamChiTiet> findTopByProductAndAttributesNative(@Param("productId") Integer productId,
                                                                 @Param("colorId") Integer colorId,
                                                                 @Param("sizeId") Integer sizeId
                                                                );


//    @Query("SELECT spct FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :productId AND spct.mauSac.id = :colorId AND spct.kichCo.id = :sizeId")
//    Optional<SanPhamChiTiet> findAllByAttributes(@Param("productId") Integer productId, @Param("colorId") Integer colorId, @Param("sizeId") Integer sizeId);
   @Query("SELECT spct FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :productId AND spct.mauSac.id = :colorId AND spct.kichCo.id = :sizeId")
    Optional<SanPhamChiTiet> findAllByAttributes(@Param("productId") Integer productId, @Param("colorId") Integer colorId, @Param("sizeId") Integer sizeId);

}
