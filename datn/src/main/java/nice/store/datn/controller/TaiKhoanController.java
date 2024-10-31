package nice.store.datn.controller;


import nice.store.datn.entity.TaiKhoan;
import nice.store.datn.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanService taikhoanService;

    @PostMapping
    public TaiKhoan createTK(@RequestBody TaiKhoan tk) {
        return  taikhoanService.createTK(tk);
    }
}
