package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nice.store.datn.entity.HinhAnh;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamSapHetHangDTO {
    private Integer id;
    private String maSP;
    private String tenSP;
    private BigDecimal giaBan;
    private Integer soLuong;
    private String hinhAnhs;
}
