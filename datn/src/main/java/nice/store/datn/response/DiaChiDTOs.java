package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaChiDTOs {
    private Integer id;
    private String diaChiCuThe;
    private String phuongXa;
    private String quanHuyen;
    private String tinhThanh;
    private Integer idKhachHang;

    private String ten;
    private Integer gioiTinh;
    private String sdt;
    private String email;
}
