package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamCTResponse {
    private Integer id;
    private Integer idSanPham;
    private String maSPCT;
    private String tenSP;
    private String tenMauSac;
    private String tenTheLoai;
    private String tenKichCo;
    private String tenChatLieu;
    private String tenDeGiay;
    private String tenThuongHieu;

    private BigDecimal giaBan;
    private Integer soLuong;
    private String moTa;
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
    private Integer trangThai;
    private String url;
}
