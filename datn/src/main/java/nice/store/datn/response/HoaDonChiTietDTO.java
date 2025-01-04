package nice.store.datn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nice.store.datn.entity.HoaDonChiTiet;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietDTO {
    private Integer id;
    private Integer soLuong;
    private BigDecimal donGia;
    private String tenSanPham;
    private String kichCo;
    private String mauSac;
    private Integer idSPCT;
    private String maHD;
    private BigDecimal giaTriMax;
    public HoaDonChiTietDTO(HoaDonChiTiet hoaDonChiTiet) {
        this.id = hoaDonChiTiet.getId();
        this.soLuong = hoaDonChiTiet.getSoLuong();
        this.donGia = hoaDonChiTiet.getDonGia();
        this.tenSanPham = hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSP();
        this.kichCo = hoaDonChiTiet.getSanPhamChiTiet().getKichCo().getSize();
        this.mauSac = hoaDonChiTiet.getSanPhamChiTiet().getMauSac().getTenMauSac();
        this.idSPCT = hoaDonChiTiet.getSanPhamChiTiet().getId();
        this.maHD = hoaDonChiTiet.getHoaDon().getMaHd();
        this.giaTriMax = hoaDonChiTiet.getHoaDon().getPhieuGiamGia() != null ? hoaDonChiTiet.getHoaDon().getPhieuGiamGia().getGiaTriMax() : BigDecimal.valueOf(0);

    }


    // Getter, Setter
}
