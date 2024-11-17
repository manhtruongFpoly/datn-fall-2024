package nice.store.datn.repository;

import nice.store.datn.entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia , Integer> {

    @Query("SELECT MAX(pgg.id) FROM PhieuGiamGia pgg")
    Integer findMaxId();



}
