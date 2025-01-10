package nice.store.datn.service;

import nice.store.datn.entity.ChatLieu;
import nice.store.datn.entity.KichCo;
import nice.store.datn.entity.SanPham;
import nice.store.datn.entity.ThuongHieu;
import nice.store.datn.repository.ChatLieuRepository;
import nice.store.datn.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ThuongHieuService {
    @Autowired
    private ThuongHieuRepository THrepository;

    public List<ThuongHieu> getAllTh() {

        return THrepository.findAll();
    }

    public Optional<ThuongHieu> getIdTh(int id) {
        return THrepository.findById(id);
    }

    public ThuongHieu findById(Integer id) {
        return THrepository.findById(id).orElse(null);
    }

    public ThuongHieu create(ThuongHieu sp) {
        if (THrepository.findByMaThuongHieu(sp.getMaThuongHieu()).isPresent()) {
            throw new RuntimeException("Mã sản phẩm đã tồn tại: " + sp.getMaThuongHieu());
        }
        sp.setNgayTao(LocalDate.now());
        sp.setNgaySua(LocalDateTime.now());
        return THrepository.save(sp);
    }

    public void deleteThuongHieu(int id) {
        THrepository.deleteById(id);
    }

    public ThuongHieu updateSp(Integer id, ThuongHieu sp ) {

        if (THrepository.existsById(id)) {
            ThuongHieu getProduct = THrepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Thuonghieu id " + id + " not found"));
            sp.setId(id);
            sp.setNgayTao(getProduct.getNgayTao());
            sp.setNgaySua(LocalDateTime.now());
            return THrepository.save(sp);
        }
        throw new RuntimeException("Product id " + id + " not found");
    }

    public Page<ThuongHieu> timKiemTheoMaVaTen(String searchTerm, Pageable pageable) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return THrepository.timKiemTheoMaVaTen(searchTerm, pageable);
        }
        return THrepository.findAll(pageable);  // Trả về tất cả nếu không có searchTerm
    }
    public List<ThuongHieu> timKiemTheoTrangThai(Integer trangThai) {
        if (trangThai == null) {
            return THrepository.findAll();  // Trả về tất cả nếu không lọc theo trạng thái
        }
        return THrepository.timKiemTheoTrangThai(trangThai);
    }

    public List<ThuongHieu> timKiemTheoKhoangNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        return THrepository.findByNgayTaoBetween(ngayBatDau, ngayKetThuc);
    }

    public List<ThuongHieu> getNewestThuongHieu() {
        return THrepository.findNewestThuongHieu();
    }

    public List<ThuongHieu> getOldestThuongHieu() {
        return THrepository.findOldestThuongHieu();
    }


    public ThuongHieu getThuongHieuByProductId(Integer id) {
        List<ThuongHieu> thuongHieuList = THrepository.findByProductId(id);
        if (!thuongHieuList.isEmpty()) {
            return thuongHieuList.get(0);
        } else {
            return null;
        }
    }




}
