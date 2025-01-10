package nice.store.datn.repository;

import nice.store.datn.entity.KichCo;
import nice.store.datn.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {

    Optional<ThuongHieu> findByMaThuongHieu(String maTh);
    Optional<ThuongHieu> findByTenThuongHieu(String tenThuongHieu);

    @Query(value = "SELECT * FROM thuong_hieu t WHERE (:searchTerm IS NULL OR t.ma_thuong_hieu LIKE CONCAT('%', :searchTerm, '%') " +
            "OR t.ten_thuong_hieu LIKE CONCAT('%', :searchTerm, '%'))",
            nativeQuery = true)
    Page<ThuongHieu> timKiemTheoMaVaTen(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query(value = "SELECT * FROM thuong_hieu t WHERE (:trangThai IS NULL OR t.trang_thai = :trangThai)", nativeQuery = true)
    List<ThuongHieu> timKiemTheoTrangThai(@Param("trangThai") Integer trangThai);

    @Query("SELECT t FROM ThuongHieu t WHERE " +
            "(:ngayBatDau IS NULL OR t.ngayTao >= :ngayBatDau) AND " +
            "(:ngayKetThuc IS NULL OR t.ngayTao <= :ngayKetThuc)")
    List<ThuongHieu> findByNgayTaoBetween(LocalDate ngayBatDau, LocalDate ngayKetThuc);

    // Lọc theo ngày tạo mới nhất
    @Query(value = "SELECT * FROM thuong_hieu ORDER BY ngay_tao DESC", nativeQuery = true)
    List<ThuongHieu> findNewestThuongHieu();

    // Lọc theo ngày tạo cũ nhất
    @Query(value = "SELECT * FROM thuong_hieu ORDER BY ngay_tao ASC", nativeQuery = true)
    List<ThuongHieu> findOldestThuongHieu();

    @Query("SELECT th FROM ThuongHieu th " +
            "JOIN SanPhamChiTiet spct ON spct.thuongHieu.id = th.id " +
            "WHERE spct.sanPham.id = :productId")
    List<ThuongHieu> findByProductId(@Param("productId") Integer productId);


}
