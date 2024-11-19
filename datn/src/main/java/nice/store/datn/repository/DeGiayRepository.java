package nice.store.datn.repository;

import nice.store.datn.entity.DeGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeGiayRepository extends JpaRepository<DeGiay,Integer> {
    Optional<DeGiay> findByTenDeGiay(String tenDeGiay);

    Optional<DeGiay> findById(Integer id);
     boolean existsByTenDeGiay(String tenDeGiay);
    Optional<DeGiay> findByMaDeGiay(String maDeGiay);
    List<DeGiay> findAllByOrderByIdDesc();



}
