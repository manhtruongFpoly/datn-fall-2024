package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHangDTO {
    private String tenNguoiNhan;
    private String soDienThoai;
    private String diaChiNguoiNhan;
    private String thanhPho;
    private String quanHuyen;
    private String phuongXa;
    private String ghiChu;
    private Integer maVoucher;
    private List<CartItemDTO> danhSachSanPham;
    private String phuongThucThanhToan;
}
