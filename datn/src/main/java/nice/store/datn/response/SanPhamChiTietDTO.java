package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamChiTietDTO {
    private Integer id;
    private String maSpct;
    private BigDecimal giaBan;
    private Integer soLuong;
    private String moTa;
    private Integer trangThai;
    private String kichCo;  // Chỉ trả về tên kích cỡ
    private String mauSac;  // Chỉ trả về tên màu sắc
    private String loaiGiay;  // Chỉ trả về tên loại giày
    private String chatLieu;  // Chỉ trả về tên chất liệu
    private String deGiay;  // Chỉ trả về tên đế giày
    private String thuongHieu;  // Chỉ trả về tên thương hiệu
    private String tenSanPham;  // Tên sản phẩm (từ trường `SanPham`)
    private String hinhAnh;  // Chỉ lấy URL của ảnh đầu tiên
}

