package nice.store.datn.service;

import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.SanPham;
import nice.store.datn.repository.KhachHangRepository;
import nice.store.datn.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;


    public SanPham create(SanPham sp) {
        if (sanPhamRepository.findByProductId(sp.getProductId()).isPresent()) {
            throw new RuntimeException("Mã sản phẩm đã tồn tại: " + sp.getProductId());
        }
        sp.setDateCreate(LocalDateTime.now());
        sp.setDateFix(LocalDateTime.now());
        return sanPhamRepository.save(sp);
    }

    public List<SanPham> getAllSp() {
        return sanPhamRepository.findAll();
    }

    public Optional<SanPham> getIdSanPham(int id) {

        return sanPhamRepository.findById(id);
    }

    public SanPham updateSp(Integer id, SanPham sp ) {

        if (sanPhamRepository.existsById(id)) {
            SanPham getProduct = sanPhamRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Product id " + id + " not found"));
            sp.setId(id);
            sp.setDateCreate(getProduct.getDateCreate());
            sp.setDateFix(LocalDateTime.now());
            return sanPhamRepository.save(sp);
        }
        throw new RuntimeException("Product id " + id + " not found");
    }

    public void deleteSanPham(int id) {
        sanPhamRepository.deleteById(id);
    }

}
