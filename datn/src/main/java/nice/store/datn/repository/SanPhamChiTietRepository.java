package nice.store.datn.repository;

import nice.store.datn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {


    List<SanPhamChiTiet> findBySanPhamId(Integer sanPhamId);

    //    @Query("SELECT count(spct) FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :productId")
    @Query("SELECT SUM(spct.soLuong) FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :productId")
    Integer findTotalQuantityByProductId(@Param("productId") Integer productId);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE " +
            "s.mauSac.id = :mauSacId AND " +
            "s.loaiGiay.id = :theLoaiId AND " +
            "s.kichCo.id = :kichCoId AND " +
            "s.chatLieu.id = :chatLieuId AND " +
            "s.deGiay.id = :deGiayId AND " +
            "s.thuongHieu.id = :thuongHieuId AND " +
            "s.sanPham.id = :idSp") // Thay maSpct bằng sanPham.id
    Optional<SanPhamChiTiet> findByMauSacIdAndTheLoaiIdAndKichCoIdAndChatLieuIdAndDeGiayIdAndThuongHieuIdAndSanPhamId(
            @Param("mauSacId") Integer mauSacId,
            @Param("theLoaiId") Integer theLoaiId,
            @Param("kichCoId") Integer kichCoId,
            @Param("chatLieuId") Integer chatLieuId,
            @Param("deGiayId") Integer deGiayId,
            @Param("thuongHieuId") Integer thuongHieuId,
            @Param("idSp") Integer idSp  // Tham số mới idSp
    );



}
