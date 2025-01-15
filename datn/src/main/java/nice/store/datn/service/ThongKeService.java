package nice.store.datn.service;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.repository.HoaDonRepository;
import nice.store.datn.response.SanPhamBanChayDTO;
import nice.store.datn.response.SanPhamSapHetHangDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

            // Lấy hình ảnh đầu tiên từ danh sách hình ảnh
            if (sanPham.getHinhAnhs() != null && !sanPham.getHinhAnhs().isEmpty()) {
                // Lấy URL của hình ảnh đầu tiên
                dto.setHinhAnhs(sanPham.getHinhAnhs().get(0).getUrl()); // Giả sử bạn muốn lấy URL của hình ảnh
            }

            dtoList.add(dto);
        }

        return dtoList;
    }




    //    //danhthu
//    public List<HoaDon> getDoanhThuHomNay() {
//        LocalDate today = LocalDate.now();
//
//        LocalDateTime startOfDay = today.atStartOfDay();
//        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
//
//        return thongKeRepository.findByTrangThaiAndNgayThanhToanBetween(2, startOfDay, endOfDay);
//
//    }
    public List<HoaDon> getDoanhThuHomNay() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // Danh sách trạng thái cần lọc
        List<Integer> trangThais = Arrays.asList(6);

        // Gọi repository với danh sách trạng thái
        return thongKeRepository.findByTrangThaiInAndNgayTaoBetween(trangThais, startOfDay, endOfDay);
    }

    public List<HoaDon> getDoanhThuThangNay() {
        LocalDate today = LocalDate.now();

        // Xác định ngày bắt đầu và kết thúc của tháng này
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);  // Ngày đầu tiên của tháng
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());  // Ngày cuối cùng của tháng

        LocalDateTime startOfMonth = firstDayOfMonth.atStartOfDay();  // 00:00 ngày đầu tháng
        LocalDateTime endOfMonth = lastDayOfMonth.atTime(LocalTime.MAX);  // 23:59 ngày cuối tháng
        List<Integer> trangThais = Arrays.asList(6);
        return thongKeRepository.findByTrangThaiInAndNgayTaoBetween(trangThais, startOfMonth, endOfMonth);
    }

    public List<HoaDon> getDoanhThuNamNay() {
        // Lấy ngày bắt đầu và kết thúc của năm nay
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = today.withDayOfYear(1);  // Ngày đầu tiên trong năm
        LocalDate lastDayOfYear = today.withDayOfYear(today.lengthOfYear());  // Ngày cuối cùng trong năm

        // Chuyển đổi sang LocalDateTime
        LocalDateTime startOfYear = firstDayOfYear.atStartOfDay();
        LocalDateTime endOfYear = lastDayOfYear.atTime(LocalTime.MAX);
        List<Integer> trangThais = Arrays.asList(6);
        return thongKeRepository.findByTrangThaiInAndNgayTaoBetween(trangThais, startOfYear, endOfYear);
    }


//    public List<HoaDon> getThongKeHoaDon(LocalDateTime startDate, LocalDateTime endDate) {
//        // Lọc hóa đơn theo khoảng thời gian
//        return thongKeRepository.findByNgayTaoBetween(startDate, endDate);
//    }

public List<HoaDon> getThongKeHoaDon(LocalDateTime startDate, LocalDateTime endDate, List<Integer> trangThais) {
    // Lọc hóa đơn theo trạng thái và thời gian
    return thongKeRepository.findByTrangThaiInAndNgayTaoBetween(trangThais, startDate, endDate);
}


    public List<HoaDon> getAllHoaDons() {
        return thongKeRepository.findAll();
    }

}
