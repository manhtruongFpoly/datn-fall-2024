package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GioHangDTO {
    private Integer id;
    private Integer khachHangId;
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
    private BigDecimal tongTien;
    private int trangThai;
    private List<GioHangCTDTO> gioHangCTs;

    public double getTongTien() {
        return gioHangCTs.stream()
                .filter(gioHangCT -> gioHangCT.getGiaBan() != null)
                .map(gioHangCT -> gioHangCT.getGiaBan().multiply(BigDecimal.valueOf(gioHangCT.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }
}
