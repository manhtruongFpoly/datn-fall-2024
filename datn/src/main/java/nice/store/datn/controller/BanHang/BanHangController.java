package nice.store.datn.controller.BanHang;


import nice.store.datn.entity.*;
import nice.store.datn.repository.*;
import nice.store.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BanHangController {
    @Autowired
    private SanPhamCTService sanPhamCTService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private ChatLieuService chatLieuService;
    @Autowired
    private DeGiayService deGiayService;
    @Autowired
    private LoaiGiayService loaiGiayService;

    @Autowired
    private KichCoService kichCoService;
    @Autowired
    private HinhAnhService hinhAnhService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private DeGiayRepository deGiayRepository;

    @Autowired
    private LoaiGiayRepository loaiGiayRepository;
    @Autowired
    private KichCoRepository kichCoRepository;
    @Autowired
    private MauSacRepository mauSacRepository;
    @GetMapping("/admin/ban-hang-tai-quay")
    public String hienThiHoaDon(Model model) {
        List<ThuongHieu> thuongHieu = thuongHieuService.getAllTh();
        List<ChatLieu> chatLieu = chatLieuService.getAllChatLieu();
        List<DeGiay> deGiay = deGiayService.getAllDeGiay();
        List<LoaiGiay> loaiGiay = loaiGiayService.findAll();
        List<KichCo> kichCo = kichCoService.getAllKichCo();
        List<MauSac> mauSac = mauSacService.getAllMauSac();

        model.addAttribute("listThuongHieu", thuongHieu);
        model.addAttribute("listChatLieu", chatLieu);
        model.addAttribute("listDeGiay", deGiay);
        model.addAttribute("listLoaiGiay", loaiGiay);
        model.addAttribute("listKichCo", kichCo);
        model.addAttribute("listMauSac", mauSac);
        return "admin/BanHangTaiQuay/BanHang";
    }

}
