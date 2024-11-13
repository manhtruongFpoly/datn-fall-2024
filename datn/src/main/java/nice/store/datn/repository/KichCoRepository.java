package nice.store.datn.repository;

import nice.store.datn.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Integer> {


    Optional<KichCo> findKichCoBySize(int size);


    Optional<KichCo> findByMaKichCo(String maKichCo);


    boolean existsKichCoBySize(int size);

    List<KichCo> findAllByOrderByNgayTaoDesc();
    Optional<KichCo> findById(Integer id);
}
