package nice.store.datn.repository;

import nice.store.datn.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Integer> {
    Optional<KichCo> findByMaKichCo(String maKC);
}
