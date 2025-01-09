package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamBanChayDTO {
    private Integer id;
    private String anh;
    private String ten;
    private BigDecimal giaBan;
    private Integer tongSoLuong;



}