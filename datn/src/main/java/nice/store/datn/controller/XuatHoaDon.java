package nice.store.datn.controller;

import nice.store.datn.entity.HoaDon;
import nice.store.datn.response.XuatHoaDonDTO;
import nice.store.datn.service.HoaDonService;
import nice.store.datn.service.XuatHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class XuatHoaDon {

    private final HoaDonService hoaDonService;
    private final XuatHoaDonService xuatHoaDonService;

    @Autowired
    public XuatHoaDon(HoaDonService hoaDonService, XuatHoaDonService xuatHoaDonService) {
        this.hoaDonService = hoaDonService;
        this.xuatHoaDonService = xuatHoaDonService;
    }


    @GetMapping("/admin/invoice/{idTabHD}")
    @ResponseBody
    public XuatHoaDonDTO exportInvoiceToJSON(@PathVariable Integer idTabHD) {
        HoaDon hoaDon = hoaDonService.findHoaDonById(idTabHD);
        XuatHoaDonDTO dto = hoaDonService.toXuatHoaDonDTO(hoaDon);
        return dto;
    }

    @GetMapping("/admin/invoice/export-pdf/{idTabHD}")
public ResponseEntity<Resource> exportInvoiceToPDF(@PathVariable Integer idTabHD) {
    try {
        HoaDon hoaDon = hoaDonService.findHoaDonById(idTabHD);

        XuatHoaDonDTO dto = hoaDonService.toXuatHoaDonDTO(hoaDon);

        String filePath = "C:\\Users\\LAPTOP24H\\Desktop\\fix\\datn-fall-2024\\HoaDonPDF\\invoice_" + dto.getMaHd() + ".pdf";

        xuatHoaDonService.exportInvoiceToPDF(dto, filePath);

        Resource resource = new FileSystemResource(new File(filePath));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new File(filePath).getName() + "\"")
                .body(resource);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


}
