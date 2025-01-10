package nice.store.datn.repository;

import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.GioHangCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GioHangChiTietRepository extends JpaRepository<GioHangCT, Integer> {


        @Query("SELECT ghct FROM GioHangCT ghct WHERE ghct.gioHang = :gioHang AND ghct.sanPhamChiTiet.id = :sanPhamChiTietId")
        GioHangCT findByGioHangAndSanPhamChiTiet_Id(@Param("gioHang") GioHang gioHang, @Param("sanPhamChiTietId") Integer sanPhamChiTietId);



}
