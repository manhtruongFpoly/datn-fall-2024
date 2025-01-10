package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nice.store.datn.entity.SanPhamChiTiet;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Integer id; // ID của giỏ hàng chi tiết
    private Integer sanPhamId; // ID sản phẩm
    private String tenSanPham; // Tên sản phẩm
    private Integer soLuong; // Số lượng sản phẩm
    private BigDecimal donGia;
    private SanPhamChiTiet sanPhamChiTiet;
    public SanPhamChiTiet getSanPhamChiTiet() {
        return sanPhamChiTiet;
    }
}
