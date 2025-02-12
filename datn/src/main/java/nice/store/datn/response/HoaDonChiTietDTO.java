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
    private BigDecimal tienGiam;
    private Integer phiShip;
    private String hinhAnh;
    private String loaiGiaoDich;
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
        this.tienGiam = hoaDonChiTiet.getHoaDon().getTienGiam();
        this.phiShip = hoaDonChiTiet.getHoaDon().getPhiShip();
        this.hinhAnh = hoaDonChiTiet.getSanPhamChiTiet().getHinhAnhs().isEmpty()
                ? null
                : String.valueOf(hoaDonChiTiet.getSanPhamChiTiet().getHinhAnhs().get(0));
        this.loaiGiaoDich= hoaDonChiTiet.getHoaDon().getLoaiHoaDon();
    }


    // Getter, Setter
}
