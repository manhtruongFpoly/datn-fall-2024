package nice.store.datn.repository;

import jakarta.transaction.Transactional;
import nice.store.datn.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonRepository  extends JpaRepository<HoaDon, Integer> {

    @Query(value = "SELECT * FROM HOA_DON WHERE TRANG_THAI = 0 ORDER BY id DESC", nativeQuery = true)
    List<HoaDon> findHoaDonCho();

    @Modifying
    @Query(value = "UPDATE HOA_DON_CT SET SO_LUONG = :soLuong, DON_GIA = :gia, TRANG_THAI = :trangThai WHERE ID_CTSP = :idChiTietSanPham AND ID_HOA_DON = :idHoaDon", nativeQuery = true)
    void updateByIdChiTietSanPhamAndIdHoaDon(@Param("soLuong") Integer soLuong,
                                             @Param("gia") BigDecimal gia,
                                             @Param("trangThai") Integer trangThai,
                                             @Param("idChiTietSanPham") Integer idChiTietSanPham,
                                             @Param("idHoaDon") Integer idHoaDon);

}
