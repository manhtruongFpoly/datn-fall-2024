package nice.store.datn.repository;

import nice.store.datn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {

}
