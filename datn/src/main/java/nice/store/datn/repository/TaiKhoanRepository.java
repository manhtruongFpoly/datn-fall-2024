package nice.store.datn.repository;

import nice.store.datn.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {

}
