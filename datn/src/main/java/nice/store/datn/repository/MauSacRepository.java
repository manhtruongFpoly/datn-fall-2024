package nice.store.datn.repository;

import nice.store.datn.entity.DeGiay;
import nice.store.datn.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    Optional<MauSac> findByTenMauSac(String tenMauSac);


    Optional<MauSac> findById(Integer id);


    boolean existsByTenMauSac(String tenMauSac);

    Optional<MauSac> findByMaMauSac(String maMauSac);
    List<MauSac> findAllByOrderByIdDesc();
}
