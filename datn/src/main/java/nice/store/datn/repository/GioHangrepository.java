package nice.store.datn.repository;

import nice.store.datn.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GioHangrepository extends JpaRepository<GioHang, Integer> {
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM GioHang g WHERE g.idKH.id = :khachHangId")
//    void deleteAllByKhachHangId(@Param("khachHangId") Integer khachHangId);
void deleteAllByKhachHangId(Integer khachHangId);
}
