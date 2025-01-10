package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GioHangCTDTO {
    private Integer id;
    private Integer productId;
    private String tenSanPham;
    private String mauSac;
    private String kichCo;
    private Integer soLuong;
    private BigDecimal giaBan;
    private String hinhAnh;

    // Tính tổng tiền cho sản phẩm này
    public BigDecimal getThanhTien() {
        if (giaBan != null && soLuong != null) {
            return giaBan.multiply(BigDecimal.valueOf(soLuong));
        }
        return BigDecimal.ZERO;
    }
}
