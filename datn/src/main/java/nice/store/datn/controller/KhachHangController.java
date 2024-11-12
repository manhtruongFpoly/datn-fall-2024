package nice.store.datn.controller;


import jakarta.validation.Valid;
import nice.store.datn.entity.DiaChi;
import nice.store.datn.entity.KhachHang;
import nice.store.datn.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khService;


    @GetMapping("/danh-sach")
    public String getAllKhachHang(Model model) {
        List<KhachHang> khachHangList = khService.getAllKH();
        model.addAttribute("khachHangList", khachHangList);
        return "admin/khach-hang/danh-sach";
    }
//    @GetMapping("/danh-sach")
//    @ResponseBody
//    public List<KhachHang> getAllKhachHangJson() {
//        return khService.getAllKH();
//    }


    @GetMapping("/chi-tiet-khach-hang/{id}")
    public String getKhachHangById(@PathVariable int id, Model model) {
        Optional<KhachHang> khachHang = khService.getKHById(id);
        if (khachHang.isPresent()) {
            model.addAttribute("khachHang", khachHang.get());
            return "update";
        } else {
            return "redirect:/khach-hang/danh-sach";
        }
    }

    @GetMapping("/them")
    public String showAddKHForm(KhachHang khachHang, Model model) {

        model.addAttribute("khachHang", new KhachHang());
        return "admin/khach-hang/add-kh";
    }

    @PostMapping("/them")
    public String createKhachHang(@ModelAttribute @Valid KhachHang khachHang, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/khach-hang/add-kh"; // Trả về view có form
        }

        LocalDateTime now = LocalDateTime.now();

        // Thiết lập thông tin cho từng địa chỉ
        if (khachHang.getDiaChi() == null) {
            khachHang.setDiaChi(new ArrayList<>());
        }
        for (DiaChi diaChi : khachHang.getDiaChi()) {
            diaChi.setNgayTao(now);
            diaChi.setIdKH(khachHang);
        }
        khService.createKH(khachHang);
        return "redirect:/khach-hang/danh-sach";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<KhachHang> khachHang = khService.getKHById(id);
        model.addAttribute("khachHang", khachHang);
        return "admin/khach-hang/update"; // Tên trang HTML
    }

    @PostMapping("/update/{id}")
    public String updateKhachHang(
            @PathVariable Integer id,
            @ModelAttribute KhachHang khachHang,  // KhachHang nhận các thông tin chung
            @RequestParam("gioiTinh") Integer gioiTinh,
            @RequestParam("ngaySinh") String ngaySinh,
            @RequestParam("email") String email,
            @RequestParam("sdt") String sdt,  // Nhận sdt là String, sau đó chuyển sang Integer nếu cần
            @RequestParam("trangThai") Integer trangThai,
            @RequestParam Map<String, String> diaChiParams) {

        // Cập nhật các thông tin cơ bản
        khachHang.setGioiTinh(gioiTinh);
        khachHang.setNgaySinh(Date.valueOf(ngaySinh));  // Chuyển đổi từ String sang Date nếu cần
        khachHang.setEmail(email);

        // Chuyển đổi sdt từ String sang Integer nếu cần thiết
        try {
            khachHang.setSdt(Integer.parseInt(sdt));
        } catch (NumberFormatException e) {
            // Nếu có lỗi khi chuyển đổi số điện thoại
            e.printStackTrace();
        }

        khachHang.setTrangThai(trangThai);

        // Kiểm tra và xử lý các địa chỉ nếu có
        if (diaChiParams != null && !diaChiParams.isEmpty()) {
            List<DiaChi> diaChiEntities = new ArrayList<>();

            int index = 0;
            while (diaChiParams.containsKey("diaChi[" + index + "].diaChiCuThe")) {
                String diaChiCuThe = diaChiParams.get("diaChi[" + index + "].diaChiCuThe");
                String phuongXa = diaChiParams.get("diaChi[" + index + "].phuongXa");
                String quanHuyen = diaChiParams.get("diaChi[" + index + "].quanHuyen");
                String tinhThanh = diaChiParams.get("diaChi[" + index + "].tinhThanh");

                DiaChi diaChi = new DiaChi();
                diaChi.setDiaChiCuThe(diaChiCuThe);
                diaChi.setPhuongXa(phuongXa);
                diaChi.setQuanHuyen(quanHuyen);
                diaChi.setTinhThanh(tinhThanh);
                diaChi.setIdKH(khachHang);

                diaChiEntities.add(diaChi);
                index++;
            }

            khachHang.setDiaChi(diaChiEntities);
        }

        // Gọi Service để cập nhật khách hàng
        khService.updateKH(id, khachHang);

        // Sau khi cập nhật, điều hướng đến danh sách khách hàng
        return "redirect:/khach-hang/danh-sach";
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteKhachHang(@PathVariable Integer id) {
        boolean isDeleted = khService.deleteKhachHang(id);
        if (isDeleted) {
            return ResponseEntity.ok("Khách hàng đã được xóa!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khách hàng không tồn tại!");
        }
    }

}
