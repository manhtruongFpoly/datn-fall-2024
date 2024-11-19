package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nice.store.datn.entity.HoaDonChiTiet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietDTO {
    private Integer id;
    private Integer soLuong;
    private Integer donGia;
    private String tenSanPham;
    private String mauSac;

    public HoaDonChiTietDTO(HoaDonChiTiet hoaDonChiTiet) {
        this.id = hoaDonChiTiet.getId();
        this.soLuong = hoaDonChiTiet.getSoLuong();
        this.donGia = hoaDonChiTiet.getDonGia();
        this.tenSanPham = hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSP();
        this.mauSac = hoaDonChiTiet.getSanPhamChiTiet().getMauSac().getTenMauSac();
    }


    // Getter, Setter
}
