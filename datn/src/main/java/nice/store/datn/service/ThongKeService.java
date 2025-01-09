package nice.store.datn.service;

import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.repository.HoaDonRepository;
import nice.store.datn.response.SanPhamBanChayDTO;
import nice.store.datn.response.SanPhamSapHetHangDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThongKeService {

    @Autowired
    HoaDonRepository thongKeRepository;

    public List<SanPhamBanChayDTO> getTop10Products(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int pageSize = pageable.getPageSize();

        List<Object[]> results = thongKeRepository.getTopSanPhamBanChayWithPagination(offset, pageSize);

        List<SanPhamBanChayDTO> sanPhamBanChayDTOList = new ArrayList<>();

        for (Object[] result : results) {
            // Lấy giá trị từ Object[] và ép kiểu đúng
            Integer sanPhamChiTietID = (Integer) result[0];
            String hinhAnh = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            Integer tongSoLuongBan = ((Number) result[4]).intValue();

            SanPhamBanChayDTO dto = new SanPhamBanChayDTO(sanPhamChiTietID, hinhAnh, tenSanPham, giaBan, tongSoLuongBan);
            sanPhamBanChayDTOList.add(dto);
        }
        return sanPhamBanChayDTOList;
    }


    public List<SanPhamChiTiet> getSanPhamSapHetHang() {
        return thongKeRepository.getSanPhamSapHetHang();
    }


    public List<SanPhamSapHetHangDTO> getSanPhamSapHetHangDTO() {
        List<SanPhamChiTiet> sanPhamChiTietList = thongKeRepository.getSanPhamSapHetHang();
        List<SanPhamSapHetHangDTO> dtoList = new ArrayList<>();

        // Chuyển đổi thủ công từ SanPhamChiTiet sang SanPhamSapHetHangDTO
        for (SanPhamChiTiet sanPham : sanPhamChiTietList) {
            SanPhamSapHetHangDTO dto = new SanPhamSapHetHangDTO();
            dto.setId(sanPham.getId());
            dto.setMaSP(sanPham.getMaSpct());
            dto.setTenSP(sanPham.getSanPham().getTenSP());
            dto.setGiaBan(sanPham.getGiaBan());
            dto.setSoLuong(sanPham.getSoLuong());
            //dto.setHinhAnhs(sanPham.getHinhAnhs());
            dtoList.add(dto);
        }

        return dtoList;
    }
}
