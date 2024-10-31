package nice.store.datn.repository;

import jakarta.transaction.Transactional;
import nice.store.datn.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonRepository  extends JpaRepository<HoaDon, Integer> {

}
