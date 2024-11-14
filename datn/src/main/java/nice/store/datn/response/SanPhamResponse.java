package nice.store.datn.response;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamResponse {
    private Integer id;
    private String maSanPham;
    private String tenSP;
    private Integer soLuong;
    private Integer trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
}
