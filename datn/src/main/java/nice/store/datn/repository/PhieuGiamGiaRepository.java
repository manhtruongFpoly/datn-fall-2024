package nice.store.datn.repository;

import nice.store.datn.entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, Integer> {

    @Query("SELECT MAX(pgg.id) FROM PhieuGiamGia pgg")
    Integer findMaxId();

//    @Query(value = "SELECT * FROM [dbo].[VOUCHER] WHERE GETDATE() BETWEEN NGAY_BAT_DAU AND NGAY_KET_THUC AND DON_TOI_THIEU <= :maxValue ORDER BY GIA_TRI_MAX DESC", nativeQuery = true)
//    List<PhieuGiamGia> getVoucherPhuHop(@Param("maxValue") Long maxValue);

    //    @Query(value = "SELECT TOP 1 * FROM [dbo].[VOUCHER] " +
//            "WHERE DON_TOI_THIEU <= :maxValue " +
//            "AND TRANG_THAI = 0 " +
//            "AND GETDATE() BETWEEN NGAY_BAT_DAU AND NGAY_KET_THUC " +
//            "ORDER BY GIA_TRI_MAX DESC", nativeQuery = true)
//    List<PhieuGiamGia> getVoucherPhuHop(@Param("maxValue") Long maxValue);
    @Query(value = "SELECT TOP 1 * FROM [dbo].[VOUCHER] " +
            "WHERE DON_TOI_THIEU <= :maxValue " +
            "AND TRANG_THAI = 0 " +
            "AND GETDATE() BETWEEN NGAY_BAT_DAU AND DATEADD(SECOND, -1, DATEADD(DAY, 1, NGAY_KET_THUC)) " +
            "AND SO_LUONG > 0 " +
            "ORDER BY GIA_TRI_MAX DESC", nativeQuery = true)
    List<PhieuGiamGia> getVoucherPhuHop(@Param("maxValue") Long maxValue);


    PhieuGiamGia findByMa(String ma);
}
