package nice.store.datn.repository;

import jakarta.transaction.Transactional;
import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.response.SanPhamBanChayDTO;
import nice.store.datn.response.SanPhamSapHetHangDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    @Query(value = "SELECT * FROM HOA_DON WHERE TRANG_THAI = 0 ORDER BY id DESC", nativeQuery = true)
    List<HoaDon> findHoaDonCho();

    @Modifying
    @Query(value = "UPDATE HOA_DON_CT SET SO_LUONG = :soLuong, DON_GIA = :gia, TRANG_THAI = :trangThai WHERE ID_CTSP = :idChiTietSanPham AND ID_HOA_DON = :idHoaDon", nativeQuery = true)
    void updateByIdChiTietSanPhamAndIdHoaDon(@Param("soLuong") Integer soLuong,
                                             @Param("gia") BigDecimal gia,
                                             @Param("trangThai") Integer trangThai,
                                             @Param("idChiTietSanPham") Integer idChiTietSanPham,
                                             @Param("idHoaDon") Integer idHoaDon);

    // Thống kê sản phẩm bán chạy
//    @Query(value = "SELECT spct.ID AS SanPhamChiTietID, " +
//            "(SELECT TOP 1 URL FROM HINH_ANH WHERE HINH_ANH.ID_SPCT = spct.ID AND HINH_ANH.TRANG_THAI = 0 ORDER BY HINH_ANH.ID ASC) AS HinhAnh, " +
//            "sp.Ten_SP AS TenSanPham, " +
//            "spct.GIA_BAN AS GiaBan, " +
//            "SUM(hdct.SO_LUONG) AS TongSoLuongBan " +
//            "FROM HOA_DON hd " +
//            "INNER JOIN HOA_DON_CT hdct ON hd.ID = hdct.ID_HOA_DON " +
//            "INNER JOIN SAN_PHAM_CT spct ON hdct.ID_CTSP = spct.ID " +
//            "INNER JOIN SAN_PHAM sp ON sp.ID = spct.ID_SP " +
//            "WHERE hd.TRANG_THAI = 6 " +  // Hóa đơn đã hoàn thành
//            "AND sp.TRANG_THAI != 0 " +  // Sản phẩm không bị vô hiệu hóa
//            "GROUP BY spct.ID, sp.Ten_SP, spct.GIA_BAN " +
//            "ORDER BY SUM(hdct.SO_LUONG) DESC " +
//            "OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY", nativeQuery = true)
//    List<Object[]> getTopSanPhamBanChayWithPagination(@Param("offset") int offset, @Param("pageSize") int pageSize);
    @Query(value = "SELECT spct.ID AS SanPhamChiTietID, " +
            "(SELECT TOP 1 URL FROM HINH_ANH WHERE HINH_ANH.ID_SPCT = spct.ID AND HINH_ANH.TRANG_THAI = 0 ORDER BY HINH_ANH.ID ASC) AS HinhAnh, " +
            "sp.Ten_SP AS TenSanPham, " +
            "spct.GIA_BAN AS GiaBan, " +
            "SUM(hdct.SO_LUONG) AS TongSoLuongBan " +
            "FROM HOA_DON hd " +
            "INNER JOIN HOA_DON_CT hdct ON hd.ID = hdct.ID_HOA_DON " +
            "INNER JOIN SAN_PHAM_CT spct ON hdct.ID_CTSP = spct.ID " +
            "INNER JOIN SAN_PHAM sp ON sp.ID = spct.ID_SP " +
            "WHERE hd.TRANG_THAI = 6 " +  // Hóa đơn đã hoàn thành
            "AND sp.TRANG_THAI != 0 " +  // Sản phẩm không bị vô hiệu hóa
            "GROUP BY spct.ID, sp.Ten_SP, spct.GIA_BAN " +
            "ORDER BY SUM(hdct.SO_LUONG) DESC " +
            "OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY", nativeQuery = true)
    List<Object[]> getTopSanPhamBanChayWithPagination(@Param("offset") int offset, @Param("pageSize") int pageSize);


    @Query("SELECT DISTINCT spct FROM SanPhamChiTiet spct " +
            "LEFT JOIN FETCH spct.hinhAnhs ha " +
            "WHERE spct.soLuong < 10 AND (ha.trangThai = 0 OR ha IS NULL) " +
            "ORDER BY spct.soLuong desc")
    List<SanPhamChiTiet> getSanPhamSapHetHang();



//    //thongke doanh thu
//    @Query(value = "SELECT * FROM HOA_DON WHERE TRANG_THAI = :trangThai AND NGAY_TAO BETWEEN :startDate AND :endDate", nativeQuery = true)
//    List<HoaDon> findByTrangThaiAndNgayThanhToanBetween(@Param("trangThai") int trangThai,
//                                                        @Param("startDate") LocalDateTime startDate,
//                                                        @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM HOA_DON WHERE TRANG_THAI IN :trangThais AND NGAY_TAO BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<HoaDon> findByTrangThaiInAndNgayThanhToanBetween(@Param("trangThais") List<Integer> trangThais,
                                                          @Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate);

    List<HoaDon> findByNgayTaoBetween(LocalDateTime startDate, LocalDateTime endDate);

}
