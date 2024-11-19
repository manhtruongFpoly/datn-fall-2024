package nice.store.datn.controller;

import jakarta.validation.Valid;
import nice.store.datn.entity.SanPham;
import nice.store.datn.entity.ThuongHieu;
import nice.store.datn.repository.ThuongHieuRepository;
import nice.store.datn.service.SanPhamService;
import nice.store.datn.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Controller
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService service;
    @Autowired
    private ThuongHieuRepository repo;
    @GetMapping("/danh-sach-thuong-hieu")
    public String getAll(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size,
                         Model model){
        Pageable pageable = PageRequest.of(page, size);
        Page<ThuongHieu> thuongHieuPage = repo.findAll(pageable);
        model.addAttribute("listThuongHieu", thuongHieuPage);  // Chỉ lấy danh sách phần tử
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", thuongHieuPage.getTotalPages());
        model.addAttribute("totalItems", thuongHieuPage.getTotalElements());
        model.addAttribute("size", size);
        return "/admin/ThuongHieu/ThuongHieuIndex";
    }
    @GetMapping("/add-view/th")
    public String ThuongHieuViewTable(Model model) {
        model.addAttribute("thuongHieu", new ThuongHieu());
        return "/admin/ThuongHieu/ThuongHieuAdd";
    }

@PostMapping("/add")
public String add(@Valid @ModelAttribute("themSanPham") ThuongHieu sp,
                  BindingResult result,
                  Model model) {
    // Kiểm tra lỗi từ BindingResult
    if (result.hasErrors()) {
        return "admin/ThuongHieu/ThuongHieuAdd"; // Quay lại trang tạo sản phẩm với thông báo lỗi
    }

    try {
        service.create(sp);
        model.addAttribute("successMessage", "Thêm sản phẩm thành công!");
        return "redirect:/danh-sach-thuong-hieu";// Chuyển hướng đến trang thêm sản phẩm
    } catch (RuntimeException e) {
        model.addAttribute("errorMessage","Trùng mã");
        return "/admin/ThuongHieu/ThuongHieuIndex"; // Quay lại trang thêm sản phẩm
    }
}

    @GetMapping("/update-view-th/{id}")
    public String ViewUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<ThuongHieu> th = service.getIdTh(id);
        if (th.isPresent()) {
            model.addAttribute("sp", th.get());
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
//            model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
            System.out.println("Loi");
            return "error"; // Chuyển hướng đến trang lỗi hoặc trang khác
        }
        return "admin/ThuongHieu/ThuongHieuUpdate";
    }


    @PostMapping("/update/thuong-hieu/{id}")
    public String updateTH(@PathVariable("id") Integer id, @Valid
    @ModelAttribute("sp") ThuongHieu sp, BindingResult result) {
        LocalDate now = LocalDate.now();
        if (result.hasErrors()) {
            return "admin/ThuongHieu/ThuongHieuIndex";
        }
        ThuongHieu updateSp = service.updateSp(id, sp);
        if (updateSp != null) {
            return "redirect:/danh-sach-thuong-hieu";
        }
        return "admin/ThuongHieu/ThuongHieuUpdate";
    }

    @GetMapping("/delete-thuong-hieu/{id}")
    public String deleteTh(@PathVariable("id") Integer id) {
        Optional<ThuongHieu> thuongHieuOptional = service.getIdTh(id);

        if (thuongHieuOptional.isPresent()) {
            service.deleteThuongHieu(id);
            return "redirect:/danh-sach-thuong-hieu";
        } else {
            return "redirect:/danh-sach-thuong-hieu";
        }
    }

    @GetMapping("/tim-kiem-thuong-hieu")
    public String timKiemThuongHieu(@RequestParam(required = false) String searchTerm,
                                    Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ThuongHieu> danhSachThuongHieu = service.timKiemTheoMaVaTen(searchTerm, pageable);
        model.addAttribute("listThuongHieu", danhSachThuongHieu);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", danhSachThuongHieu.getTotalPages());
        model.addAttribute("totalItems", danhSachThuongHieu.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("searchTerm", searchTerm);
        return "admin/ThuongHieu/ThuongHieuIndex";
    }


    @GetMapping("/loc-trang-thai")
    public String loctrangThai(@RequestParam(value = "trangThai", required = false) Integer trangThai, Model model) {
        // Gọi service để lấy dữ liệu theo trạng thái nếu có
        List<ThuongHieu> listThuongHieu = service.timKiemTheoTrangThai(trangThai);

        // Thêm dữ liệu vào model
        model.addAttribute("listThuongHieu", listThuongHieu);
        model.addAttribute("trangThai", trangThai);  // Giữ trạng thái đã chọn trong dropdown

        return "admin/ThuongHieu/ThuongHieuIndex";
    }

    @GetMapping("/tim-kiem-theo-khoang-ngay")
    public String timKiemTheoKhoangNgay(@RequestParam(value = "ngayBatDau", required = false) String ngayBatDauStr,
                                        @RequestParam(value = "ngayKetThuc", required = false) String ngayKetThucStr,
                                        Model model) {
        LocalDate ngayBatDau = null;
        LocalDate ngayKetThuc = null;

        // Kiểm tra và chuyển đổi ngày tháng
        try {
            if (ngayBatDauStr != null && !ngayBatDauStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                ngayBatDau = LocalDate.parse(ngayBatDauStr, formatter);
            }
            if (ngayKetThucStr != null && !ngayKetThucStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                ngayKetThuc = LocalDate.parse(ngayKetThucStr, formatter);
            }
        } catch (DateTimeParseException e) {
            model.addAttribute("errorMessage", "Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng DD-MM-YYYY.");
            return  "admin/ThuongHieu/ThuongHieuIndex"; // Quay lại trang với thông báo lỗi
        }

        // Tìm kiếm theo khoảng ngày
        List<ThuongHieu> thuongHieuList = service.timKiemTheoKhoangNgay(ngayBatDau, ngayKetThuc);
        model.addAttribute("listThuongHieu", thuongHieuList);
        return  "admin/ThuongHieu/ThuongHieuIndex";
    }

}
