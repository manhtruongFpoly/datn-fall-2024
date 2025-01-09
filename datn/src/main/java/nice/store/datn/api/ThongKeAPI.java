package nice.store.datn.api;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.response.SanPhamBanChayDTO;
import nice.store.datn.response.SanPhamSapHetHangDTO;
import nice.store.datn.service.ThongKeService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ThongKeAPI {
    @Autowired
    ThongKeService thongKeService;

    @GetMapping("/san-pham-ban-chay-nhat")
    public List<SanPhamBanChayDTO> getTop10SanPhamBanChay(Pageable pageable) {
        pageable = Pageable.ofSize(10);

        return thongKeService.getTop10Products(pageable);
    }

//    @GetMapping("/sap-het-hang1")
//    public List<SanPhamSapHetHangDTO> getSanPhamSapHetHang() {
//        return thongKeService.getSanPhamSapHetHangDTO();
//    }


    @GetMapping("/doanh-thu-hom-nay")
    public Map<String, Object> getDoanhThuHomNay() {
        List<HoaDon> hoaDons = thongKeService.getDoanhThuHomNay();
        long tongSoHoaDon = hoaDons.size();
        BigDecimal tongTien = hoaDons.stream()
                .map(HoaDon::getTongTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("tongSoHoaDon", tongSoHoaDon);
        result.put("tongTien", tongTien);

        return result;
    }

    @GetMapping("/doanh-thu-thang-nay")
    public Map<String, Object> getDoanhThuThangNay() {
        List<HoaDon> hoaDons = thongKeService.getDoanhThuThangNay();  // Lấy danh sách hóa đơn từ service

        // Tính tổng
        long tongSoHoaDon = hoaDons.size();
        BigDecimal tongTien = hoaDons.stream()
                .map(HoaDon::getTongTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo map để trả về cho frontend
        Map<String, Object> result = new HashMap<>();
        result.put("tongSoHoaDon", tongSoHoaDon);
        result.put("tongTien", tongTien);

        return result;
    }

    @GetMapping("/doanh-thu-nam-nay")
    public Map<String, Object> getDoanhThuNamNay() {
        List<HoaDon> hoaDons = thongKeService.getDoanhThuNamNay();

        // Tính tổng
        long tongSoHoaDon = hoaDons.size();
        BigDecimal tongTien = hoaDons.stream()
                .map(HoaDon::getTongTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("tongSoHoaDon", tongSoHoaDon);
        result.put("tongTien", tongTien);

        return result;
    }



    @GetMapping("/thong-ke-hoadon")
    public Map<String, Object> getThongKeHoadon(@RequestParam("startDate") String startDate,
                                                @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; // Định dạng ISO

        // Chuyển đổi chuỗi thành LocalDateTime
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        // Gọi service
        List<HoaDon> hoaDons = thongKeService.getThongKeHoaDon(start, end);

        Map<String, List<HoaDon>> groupedByDate = hoaDons.stream()
                .collect(Collectors.groupingBy(hd -> hd.getNgayTao().toLocalDate().toString()));

        List<String> labels = new ArrayList<>(groupedByDate.keySet());
        List<Integer> invoiceData = labels.stream()
                .map(label -> groupedByDate.get(label).size())
                .collect(Collectors.toList());
        List<BigDecimal> revenueData = labels.stream()
                .map(label -> groupedByDate.get(label).stream()
                        .map(HoaDon::getTongTien)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("invoiceData", invoiceData);
        result.put("revenueData", revenueData);

        return result;
    }

    @GetMapping("/thong-ke-trang-thai")
    public Map<String, Object> getThongKeTrangThai() {
        List<HoaDon> hoaDons = thongKeService.getAllHoaDons();
        // Đếm số lượng các trạng thái
        Map<String, Long> statusCounts = hoaDons.stream()
                .collect(Collectors.groupingBy(HoaDon::getStringTrangThai, Collectors.counting()));

        List<String> labels = new ArrayList<>(statusCounts.keySet());
        List<Long> data = new ArrayList<>(statusCounts.values());

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("data", data);

        return result;
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(@RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate) throws IOException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        // Lấy dữ liệu từ API thống kê
        List<HoaDon> hoaDons = thongKeService.getThongKeHoaDon(start, end);

        // Xử lý dữ liệu (giống như trong API cũ)
        Map<String, List<HoaDon>> groupedByDate = hoaDons.stream()
                .collect(Collectors.groupingBy(hd -> hd.getNgayTao().toLocalDate().toString()));

        List<String> labels = new ArrayList<>(groupedByDate.keySet());
        List<Integer> invoiceData = labels.stream()
                .map(label -> groupedByDate.get(label).size())
                .collect(Collectors.toList());
        List<BigDecimal> revenueData = labels.stream()
                .map(label -> groupedByDate.get(label).stream()
                        .map(HoaDon::getTongTien)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .collect(Collectors.toList());

        // Tạo file Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thong Ke Hoa Don");

        // Tạo tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("Số Lượng Hóa Đơn");
        headerRow.createCell(2).setCellValue("Doanh Thu");

        // Thêm dữ liệu vào bảng
        for (int i = 0; i < labels.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(labels.get(i));
            row.createCell(1).setCellValue(invoiceData.get(i));
            row.createCell(2).setCellValue(revenueData.get(i).doubleValue());
        }

        // Đóng gói dữ liệu thành byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        // Trả về file Excel
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ThongKeHoaDon.xlsx");

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
