package nice.store.datn.repository;


import nice.store.datn.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

    KhachHang findByTen(String username);
    @Query("SELECT kh FROM KhachHang kh WHERE kh.maKH = :maKh OR kh.email = :email")
    Optional<KhachHang> findByMaKhOrEmail(@Param("maKh") String maKh, @Param("email") String email);
    KhachHang findByEmail(String email);
}
