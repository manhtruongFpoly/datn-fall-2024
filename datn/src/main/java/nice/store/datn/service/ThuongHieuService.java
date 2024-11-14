package nice.store.datn.service;

import nice.store.datn.entity.ChatLieu;
import nice.store.datn.entity.SanPham;
import nice.store.datn.entity.ThuongHieu;
import nice.store.datn.repository.ChatLieuRepository;
import nice.store.datn.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        sp.setNgayTao(LocalDateTime.now());
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


}
