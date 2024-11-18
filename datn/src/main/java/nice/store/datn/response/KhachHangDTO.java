package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangDTO {
    private Integer id;
    private String maKh;
    private String ten;
    private Integer gioiTinh;
    private String email;
    private Integer sdt;
    private Integer trangThai;

}
