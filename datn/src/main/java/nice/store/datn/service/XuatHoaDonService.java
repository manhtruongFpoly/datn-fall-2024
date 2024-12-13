//package nice.store.datn.service;
//
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.borders.SolidBorder;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.properties.TextAlignment;
//import nice.store.datn.entity.HoaDon;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class XuatHoaDonService {
//
//    public void exportInvoiceToPDF(HoaDon hoaDon, String filePath) {
//        try {
//            // Tạo PdfWriter và PdfDocument
//            PdfWriter writer = new PdfWriter(filePath);
//            PdfDocument pdf = new PdfDocument(writer);
//            Document document = new Document(pdf);
//
//            // Thêm tiêu đề hóa đơn
//            document.add(new Paragraph("HÓA ĐƠN")
//                    .setBold()  // In đậm
//                    .setFontSize(16)  // Đặt cỡ chữ
//                    .setTextAlignment(TextAlignment.CENTER));
//
//            // Tạo bảng 2 cột với đường viền xung quanh
//            Table table = new Table(2).useAllAvailableWidth(); // 2 cột cho thông tin hóa đơn
//
//            // Thêm thông tin vào bảng với các border
//            table.addCell(createCell("Hóa Đơn: " + hoaDon.getMaHd()));
//            table.addCell(createCell("Tên Người Nhận: " + hoaDon.getTenNguoiNhan()));
//
//            table.addCell(createCell("Địa chỉ người nhận: " + hoaDon.getDiaChiNguoiNhan()));
//            table.addCell(createCell("Số điện thoại: " + hoaDon.getSdt()));
//
//            table.addCell(createCell("Tổng Tiền: " + hoaDon.getTongTien()));
//            table.addCell(createCell("Ngày tạo: " + hoaDon.getNgayTao().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
//
//            table.addCell(createCell("Giảm giá: " + hoaDon.getTienGiam()));
//            table.addCell(createCell("Phi Ship: " + hoaDon.getPhiShip()));
//
//            table.addCell(createCell("Thành Phố: " + hoaDon.getThanhPho()));
//            table.addCell(createCell("Quận/Huyện: " + hoaDon.getQuanHuyen()));
//
//            table.addCell(createCell("Phường/Xã: " + hoaDon.getPhuongXa()));
//            table.addCell(createCell("Loại Hóa Đơn: " + hoaDon.getLoaiHoaDon()));
//
//            table.addCell(createCell("Ghi Chú: " + hoaDon.getGhiChu()));
//
//            // Thêm bảng vào tài liệu PDF
//            document.add(table);
//            // Đóng tài liệu PDF
//            document.close();
//            System.out.println("Hóa đơn đã được xuất thành công!");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi xuất hóa đơn: " + e.getMessage());
//        }
//    }
//
//    // Hàm tạo cell với border và căn chỉnh
//    private Cell createCell(String content) {
//        return new Cell().add(new Paragraph(content))
//                .setBorderTop(new SolidBorder(1)) // Đặt border trên
//                .setBorderLeft(new SolidBorder(1)) // Đặt border trái
//                .setBorderRight(new SolidBorder(1)) // Đặt border phải
//                .setBorderBottom(new SolidBorder(1)) // Đặt border dưới
//                .setPadding(5); // Đặt padding cho cell
//    }
//}
package nice.store.datn.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import nice.store.datn.response.HoaDonChiTietDTO;
import nice.store.datn.response.PhuongThucThanhToanDTO;
import nice.store.datn.response.XuatHoaDonDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class XuatHoaDonService {

    public void exportInvoiceToPDF(XuatHoaDonDTO hoaDonDTO, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Đảm bảo font tiếng Việt có sẵn
            PdfFont vietnameseFont = PdfFontFactory.createFont("c:/windows/fonts/times.ttf", PdfEncodings.IDENTITY_H);


            document.add(new Paragraph("NikeStore")
                    .setFont(vietnameseFont)
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph(".....................................................................................................................................................................")
                    .setFont(vietnameseFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));

            // Thêm tiêu đề hóa đơn
            document.add(new Paragraph("Mã HÓA ĐƠN: " + (hoaDonDTO.getMaHd()) + ("     " + (hoaDonDTO.getNgayTao())))
                    .setFont(vietnameseFont)
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Tên Người Nhận: " + hoaDonDTO.getTenNguoiNhan())
                    .setFont(vietnameseFont)
                    .setFontSize(12));

            document.add(new Paragraph("Số Điện Thoại: " + hoaDonDTO.getSdt())
                    .setFont(vietnameseFont)
                    .setFontSize(12));
            document.add(new Paragraph("Địa chỉ: " + hoaDonDTO.getDiaChiNguoiNhan())
                    .setFont(vietnameseFont)
                    .setFontSize(12));
            document.add(new Paragraph(".....................................................................................................................................................................")
                    .setFont(vietnameseFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));


            // Tạo bảng chi tiết sản phẩm
            Table detailTable = new Table(5).useAllAvailableWidth();
            detailTable.addCell(createCell("STT", vietnameseFont));
            detailTable.addCell(createCell("Tên Sản Phẩm", vietnameseFont));
            detailTable.addCell(createCell("Số Lượng", vietnameseFont));
            detailTable.addCell(createCell("Đơn Giá", vietnameseFont));
            detailTable.addCell(createCell("Thành Tiền", vietnameseFont));

            int index = 1;
            for (HoaDonChiTietDTO chiTiet : hoaDonDTO.getHoaDonChiTietList()) {
                detailTable.addCell(createCell(String.valueOf(index), vietnameseFont)); // Add index
                detailTable.addCell(createCell(chiTiet.getTenSanPham(), vietnameseFont)); // Product name
                detailTable.addCell(createCell(chiTiet.getSoLuong().toString(), vietnameseFont)); // Quantity
                detailTable.addCell(createCell(formatCurrency(chiTiet.getDonGia()), vietnameseFont)); // Unit price
                detailTable.addCell(createCell(formatCurrency(chiTiet.getDonGia().multiply(BigDecimal.valueOf(chiTiet.getSoLuong()))), vietnameseFont)); // Total price

                index++;
            }
            document.add(detailTable);
            document.add(new Paragraph(" ")
                    .setFont(vietnameseFont)
                    .setFontSize(12));
            document.add(new Paragraph("Tổng Tiền Hàng: " + formatCurrency(hoaDonDTO.getTongTien().add(hoaDonDTO.getTienGiam())))
                    .setFont(vietnameseFont)
                    .setFontSize(12));


            document.add(new Paragraph("Giảm Giá: " + formatCurrency(hoaDonDTO.getTienGiam()))
                    .setFont(vietnameseFont)
                    .setFontSize(12));

            document.add(new Paragraph("Tổng Hóa Đơn: " + formatCurrency(hoaDonDTO.getTongTien()))
                    .setFont(vietnameseFont)
                    .setFontSize(12));

            document.add(new Paragraph(".....................................................................................................................................................................")
                    .setFont(vietnameseFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Thanh Toán")
                    .setFont(vietnameseFont)
                    .setFontSize(12));

            StringBuilder phuongThucThanhToanText = new StringBuilder();
            int index1 = 1; // Bắt đầu từ 1 nếu cần đánh số thứ tự
            for (PhuongThucThanhToanDTO phuongThucThanhToanDTO : hoaDonDTO.getPhuongThucThanhToanList()) {
                phuongThucThanhToanText.append("Phương thức ")
                        .append(index1).append(": ")
                        .append("     -     ").append(phuongThucThanhToanDTO.getNgayTao()).append(" ")
                        .append("     -     ").append(phuongThucThanhToanDTO.getLoaiThanhToan() ? "Tiền mặt" : "Chuyển khoản").append(" ")
                        .append("     -     ").append(formatCurrency(phuongThucThanhToanDTO.getTienDaThanhToan())).append("\n");


                document.add(new Paragraph("Tổng Thanh Toán: " + formatCurrency(phuongThucThanhToanDTO.getTienDaThanhToan()))
                        .setFont(vietnameseFont)
                        .setFontSize(12));
                document.add(new Paragraph(".....................................................................................................................................................................")
                        .setFont(vietnameseFont)
                        .setFontSize(12)
                        .setTextAlignment(TextAlignment.CENTER));
                index1++;
            }
            document.add(new Paragraph(phuongThucThanhToanText.toString())
                    .setFont(vietnameseFont)
                    .setFontSize(12));

            document.close();
            System.out.println("Xuất hóa đơn thành công tại: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xuất hóa đơn: " + e.getMessage());
        }
    }


    // Phương thức định dạng tiền tệ
    private String formatCurrency(BigDecimal amount) {
        if (amount != null) {
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            return formatter.format(amount) + " VND";
        }
        return "0 VND";
    }

    private Cell createCell(String content, PdfFont font) {
        return new Cell().add(new Paragraph(content).setFont(font)) // Đặt font cho content
                .setBorderTop(new SolidBorder(1)) // Đặt border trên
                .setBorderLeft(new SolidBorder(1)) // Đặt border trái
                .setBorderRight(new SolidBorder(1)) // Đặt border phải
                .setBorderBottom(new SolidBorder(1)) // Đặt border dưới
                .setPadding(5); // Đặt padding cho cell
    }
}
