package nice.store.datn.service;


import nice.store.datn.entity.SanPham;

import nice.store.datn.response.SanPhamResponse;

import nice.store.datn.repository.SanPhamChiTietRepository;
import nice.store.datn.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamCTRepository;
    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }
    public List<SanPhamResponse> getAllProducts() {
        List<SanPham> products = sanPhamRepository.findAll();
        return products.stream().map(sp -> {
            Integer totalQuantity = sanPhamCTRepository.findTotalQuantityByProductId(sp.getId());
            return new SanPhamResponse(sp.getId(), sp.getMaSP(), sp.getTenSP(), totalQuantity != null ? totalQuantity : 0, sp.getTrangThai(), sp.getNgayTao(),
                    sp.getNgaySua() );
        }).collect(Collectors.toList());
    }

    public void createProduct(SanPham sanPham) {
        // Kiểm tra mã sản phẩm đã tồn tại
        Optional<SanPham> existingProductByCode = sanPhamRepository.findByMaSP(sanPham.getMaSP());
        if (existingProductByCode.isPresent()) {
            throw new IllegalArgumentException("Mã sản phẩm đã tồn tại!");
        }


        Optional<SanPham> existingProductByName = sanPhamRepository.findByTenSP(sanPham.getTenSP());
        if (existingProductByName.isPresent()) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại!");
        }

        // Kiểm tra và tạo mã sản phẩm nếu không có
        if (sanPham.getMaSP() == null || sanPham.getMaSP().isEmpty()) {
            sanPham.setMaSP(generateMaSanPham());
        }

        // Kiểm tra tên sản phẩm không để trống
        if (sanPham.getTenSP() == null || sanPham.getTenSP().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống!");
        }

        // Thiết lập ngày tạo và trạng thái mặc định
        sanPham.setNgayTao(LocalDateTime.now());
        sanPham.setTrangThai(1); // Trạng thái mặc định là 1 (hoạt động)

        // Lưu sản phẩm vào cơ sở dữ liệu
        sanPhamRepository.save(sanPham);
    }

    private String generateMaSanPham() {
        String prefix = "SP";
        int count = (int) sanPhamRepository.count() + 1;
        return prefix + String.format("%05d", count);
    }


    public SanPham updateSp(Integer id, SanPhamResponse sanPhamResponse) {
        if (sanPhamRepository.existsById(id)) {
            SanPham existingProduct = sanPhamRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm với ID " + id + " không tồn tại"));

            existingProduct.setMaSP(sanPhamResponse.getMaSanPham());
            existingProduct.setTenSP(sanPhamResponse.getTenSP());
            existingProduct.setTrangThai(sanPhamResponse.getTrangThai());
            existingProduct.setNgaySua(LocalDateTime.now());

            return sanPhamRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm với mã: " + sanPhamResponse.getMaSanPham());
        }
    }

    public Optional<SanPham> getIdSanPham(int id) {
        return sanPhamRepository.findById(id);
    }

    public SanPham finbyid(Integer id) {
        return sanPhamRepository.findById(id).orElse(null);
    }


    public SanPham updateSp(Integer id, SanPham sp ) {

        if (sanPhamRepository.existsById(id)) {
            SanPham getProduct = sanPhamRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Product id " + id + " not found"));
            sp.setId(id);
            sp.setNgayTao(getProduct.getNgayTao());
            sp.setNgaySua(LocalDateTime.now());
            return sanPhamRepository.save(sp);
        }
        throw new RuntimeException("Product id " + id + " not found");
    }

}








