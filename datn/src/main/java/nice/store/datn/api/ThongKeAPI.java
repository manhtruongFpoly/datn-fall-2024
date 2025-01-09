package nice.store.datn.api;

import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.response.SanPhamBanChayDTO;
import nice.store.datn.response.SanPhamSapHetHangDTO;
import nice.store.datn.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThongKeAPI {
    @Autowired
    ThongKeService thongKeService;

    @GetMapping("/san-pham-ban-chay-nhat")
    public List<SanPhamBanChayDTO> getTop10SanPhamBanChay(Pageable pageable) {
        return thongKeService.getTop10Products(pageable);
    }

    @GetMapping("/sap-het-hang")
    public List<SanPhamSapHetHangDTO> getSanPhamSapHetHang() {
        return thongKeService.getSanPhamSapHetHangDTO();
    }
}
