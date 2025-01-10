package nice.store.datn.controller;

import jakarta.validation.constraints.Size;
import nice.store.datn.entity.*;
import nice.store.datn.repository.*;
import nice.store.datn.response.SanPhamCTResponse;
import nice.store.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class SanPhamCTController {

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



    @GetMapping("/san-phamct")
    public String hienThiDanhSachSanPhamCT(Model model) {
        // Fetch products and attributes (brands, materials, sizes, colors)
        List<SanPham> sanPham = sanPhamService.getAllSanPham();
        List<ThuongHieu> thuongHieu = thuongHieuService.getAllTh();
        List<ChatLieu> chatLieu = chatLieuService.getAllChatLieu();
        List<DeGiay> deGiay = deGiayService.getAllDeGiay();
        List<LoaiGiay> loaiGiay = loaiGiayService.findAll();
        List<KichCo> kichCo = kichCoService.getAllKichCo();
        List<MauSac> mauSac = mauSacService.getAllMauSac();

        // Add all data to the model to display in the view
        model.addAttribute("listSanPham", sanPham);
       model.addAttribute("listThuongHieu", thuongHieu);
        model.addAttribute("listChatLieu", chatLieu);
        model.addAttribute("listDeGiay", deGiay);
        model.addAttribute("listLoaiGiay", loaiGiay);
        model.addAttribute("listKichCo", kichCo);
        model.addAttribute("listMauSac", mauSac);

        return "admin/sanPham/sanPhamCT";
    }


    @PostMapping("/tao-moi")
    public String addNewProduct(@RequestParam String tenSP, Model model) {
        try {
            // Tạo đối tượng sanPhamAdd và set giá trị
            SanPham sanPhamAdd = new SanPham();
            sanPhamAdd.setTenSP(tenSP);


            Optional<SanPham> existingProduct = sanPhamRepository.findByTenSP(tenSP);
            if (existingProduct.isPresent()) {
                // Nếu tên sản phẩm trùng, ném lỗi
                throw new IllegalArgumentException("Tên sản phẩm đã tồn tại!");
            }

            // Thêm vào cơ sở dữ liệu
            sanPhamService.createProduct(sanPhamAdd);

            // Nếu không có lỗi, chuyển hướng đến trang danh sách sản phẩm
            return "redirect:/san-phamct";  // Chuyển hướng sau khi thêm sản phẩm
        } catch (IllegalArgumentException e) {
            // Nếu có lỗi trùng tên sản phẩm, giữ lại giá trị đã nhập vào và thông báo lỗi
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("tenSP", tenSP); // Đưa tên sản phẩm đã nhập vào model
            return "admin/sanPham/sanPhamCT";  // Quay lại trang thêm sản phẩm
        }
    }

    @PostMapping("/kiem-tra-thuong-hieu")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkBrandExistence(@RequestParam String thuongHieu) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<ThuongHieu> existingBrand = thuongHieuRepository.findByTenThuongHieu(thuongHieu);
            if (existingBrand.isPresent()) {
                response.put("trungLap", true);
                response.put("message", "Thương hiệu đã tồn tại!");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            response.put("trungLap", false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("trungLap", true);
            response.put("message", "Đã xảy ra lỗi, vui lòng thử lại.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/tao-moi-thuong-hieu")
    public String addNewBrand(@RequestParam String thuongHieu) {
        try {
            // Tạo mới thương hiệu
            ThuongHieu thuongHieuAdd = new ThuongHieu();
            thuongHieuAdd.setTenThuongHieu(thuongHieu);
            thuongHieuRepository.save(thuongHieuAdd);

            // Chuyển hướng đến trang sanPhamCT
            return "redirect:/admin/sanPham/sanPhamCT";  // Chuyển hướng đến URL tương ứng
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return "error";  // Trả về trang lỗi nếu có sự cố
        }
    }
    @PostMapping("/tao-chat-lieu")
    public String addNewMaterial(@RequestParam String tenChatLieu) {
        try {
            // Thêm chất liệu mới
            ChatLieu chatLieu = new ChatLieu();
            chatLieu.setTenChatLieu(tenChatLieu);
            chatLieuRepository.save(chatLieu);

            // Chuyển hướng về trang sản phẩm
            return "redirect:/san-phamct";
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return "redirect:/error"; // Ví dụ chuyển hướng về trang lỗi
        }
    }

    @PostMapping("/tao-de-giay")
    public String addNewSole(@RequestParam String tenDeGiay) {
        try {
            // Thêm đế giày mới
            DeGiay deGiay = new DeGiay();
            deGiay.setTenDeGiay(tenDeGiay);
            deGiayRepository.save(deGiay);

            // Chuyển hướng về trang sản phẩm
            return "redirect:/san-phamct";
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return "redirect:/error"; // Ví dụ chuyển hướng về trang lỗi
        }
    }

    @PostMapping("/tao-loai-giay")
    public String addNewCategory(@RequestParam String tenLoaiGiay) {
        try {
            // Thêm thể loại giày mới
            LoaiGiay loaiGiay = new LoaiGiay();
            loaiGiay.setTenLoaiGiay(tenLoaiGiay);
            loaiGiayRepository.save(loaiGiay);

            // Chuyển hướng về trang sản phẩm
            return "redirect:/san-phamct";
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return "redirect:/error"; // Ví dụ chuyển hướng về trang lỗi
        }
    }


    @PostMapping("/save")
    public SanPhamCTResponse saveSanPhamChiTiet(
            @ModelAttribute SanPhamChiTiet sanPhamChiTiet,
            @RequestParam("files") List<MultipartFile> files) {
        return sanPhamCTService.saveSanPhamChiTiet(sanPhamChiTiet, files);
    }

    @GetMapping("/products/view/{productId}")
    public String viewSanPhamChiTiet(@PathVariable("productId") Integer productId, Model model) {

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
        // Lấy sản phẩm theo id
        SanPham sanPham = sanPhamService.finbyid(productId);
        // Lấy danh sách SPCT của sản phẩm
        List<SanPhamChiTiet> sanPhamChiTiets = sanPhamCTService.findBySanPhamId(productId);

        for (SanPhamChiTiet spct : sanPhamChiTiets) {
            List<HinhAnh> hinhAnhs = spct.getHinhAnhs();  // Truy vấn hình ảnh
            spct.setHinhAnhs(hinhAnhs);
        }

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("sanPhamChiTiets", sanPhamChiTiets);
        model.addAttribute("selectedSanPhamId", sanPham);

        return "admin/sanPham/SanPhamCTPage";
    }

    @GetMapping("/products/{productId}/updateSPCT/{spctId}")
    public String showUpdateForm(
            @PathVariable("productId") Integer productId,
            @PathVariable("spctId") Integer spctId,
            Model model,
            RedirectAttributes redirectAttributes) {


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


        // Lấy thông tin sản phẩm chi tiết theo spctId
        SanPhamChiTiet spct = sanPhamCTService.findById(spctId);
        if (spct == null || !spct.getSanPham().getId().equals(productId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm hoặc chi tiết sản phẩm không tồn tại!");
            return "redirect:/products";
        }

        model.addAttribute("spct", spct);
        return "admin/sanPham/SanPhamCTPageUpdate";
    }



    @PostMapping("/products/{productId}/updateSPCT/{spctId}")
    public String updateSanPhamChiTiet(
            @PathVariable("productId") Integer productId,
            @PathVariable("spctId") Integer spctId,
            @ModelAttribute("spct") SanPhamChiTiet updatedSPCT,
            RedirectAttributes redirectAttributes, Model model) {

        // Tìm sản phẩm chi tiết cần cập nhật
        SanPhamChiTiet existingSPCT = sanPhamCTService.findById(spctId);
        if (existingSPCT == null || !existingSPCT.getSanPham().getId().equals(productId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm chi tiết!");
            return "redirect:/products";
        }
        // Cập nhật các thuộc tính
        existingSPCT.setMauSac(updatedSPCT.getMauSac());
        existingSPCT.setLoaiGiay(updatedSPCT.getLoaiGiay());
        existingSPCT.setKichCo(updatedSPCT.getKichCo());
        existingSPCT.setChatLieu(updatedSPCT.getChatLieu());
        existingSPCT.setDeGiay(updatedSPCT.getDeGiay());
        existingSPCT.setThuongHieu(updatedSPCT.getThuongHieu());
//        existingSPCT.setMaSpct(updatedSPCT.getMaSpct());
        existingSPCT.setGiaBan(updatedSPCT.getGiaBan());
        existingSPCT.setSoLuong(updatedSPCT.getSoLuong());
        existingSPCT.setMoTa(updatedSPCT.getMoTa());
        existingSPCT.setTrangThai(updatedSPCT.getTrangThai());
        existingSPCT.setNgaySua(LocalDateTime.now());

        // Lưu vào database
        sanPhamCTService.save(existingSPCT);


        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm chi tiết thành công!");
        return "redirect:/products/view/" + productId;
    }


//    @GetMapping("/product-detail/{productId}")
//    public String viewProductDetail(@PathVariable("productId") Integer productId,
//                                    Model model,
//                                    @RequestParam(value = "colorId", required = false) Integer colorId,
//                                    @RequestParam(value = "sizeId", required = false) Integer sizeId) {
//        // Lấy chi tiết sản phẩm theo ID
//        SanPhamCTResponse sanPhamCT = sanPhamCTService.getSanPhamCTById1(productId);
//
//        // Nếu không tìm thấy sản phẩm, chuyển hướng đến trang lỗi
//        if (sanPhamCT == null) {
//            System.out.println("Không tìm thấy sản phẩm với ID: " + productId);
//            return "redirect:/error";
//        }
//
//        // Thêm chi tiết sản phẩm vào Model để hiển thị trên view
//        model.addAttribute("sanPhamCT", sanPhamCT);
//
//        // Fetch các danh sách phụ theo productId
//        List<MauSac> listMauSac = mauSacService.getMauSacByProductId(productId);
//        List<KichCo> listKichCo = kichCoService.getKichCoByProductId(productId);
//
//        // Kiểm tra xem danh sách màu sắc và kích cỡ có dữ liệu không
//        if (listMauSac == null || listMauSac.isEmpty()) {
//            System.out.println("Không tìm thấy màu sắc cho sản phẩm ID: " + productId);
//        }
//        if (listKichCo == null || listKichCo.isEmpty()) {
//            System.out.println("Không tìm thấy kích cỡ cho sản phẩm ID: " + productId);
//        }
//
//        // Thêm danh sách màu sắc và kích cỡ vào model
//        model.addAttribute("listMauSac", listMauSac);
//        model.addAttribute("listKichCo", listKichCo);
//
//        // Lưu trữ thông tin về màu sắc và kích cỡ được chọn
//        model.addAttribute("selectedColor", colorId);
//        model.addAttribute("selectedSize", sizeId);
//
//        // Thêm các danh sách khác vào model nếu cần
//        model.addAttribute("listThuongHieu", thuongHieuService.getAllTh());
//        model.addAttribute("listChatLieu", chatLieuService.getAllChatLieu());
//        model.addAttribute("listDeGiay", deGiayService.getAllDeGiay());
//        model.addAttribute("listLoaiGiay", loaiGiayService.findAll());
//
//        // Trả về view chi tiết sản phẩm
//        return "user/ProductDetail";
//    }


    @GetMapping("/product-detail/{productId}")
    public String viewProductDetail(@PathVariable("productId") Integer productId,
                                    @RequestParam(value = "colorId", required = false) Integer colorId,
                                    @RequestParam(value = "sizeId", required = false) Integer sizeId,
                                    Model model) {

        // Lấy chi tiết sản phẩm
        SanPhamCTResponse sanPhamCT = sanPhamCTService.getSanPhamCTById1(productId);
        model.addAttribute("sanPhamCT", sanPhamCT);

        // Danh sách màu sắc và kích cỡ
        List<MauSac> listMauSac = mauSacService.getMauSacByProductId(productId);
        List<KichCo> listKichCo = kichCoService.getKichCoByProductId(productId);
//        ThuongHieu listThuongHieu = thuongHieuService.getThuongHieuByProductId(productId);
        model.addAttribute("listMauSac", listMauSac);
        model.addAttribute("listKichCo", listKichCo);
//        model.addAttribute("listThuongHieu", listThuongHieu);
//        // Số lượng sản phẩm chi tiết
//        Integer quantity = null;
//        if (colorId != null && sizeId != null) {
//            quantity = sanPhamCTService.getQuantityByAttributes(productId, colorId, sizeId);
//        }
//        model.addAttribute("quantity", quantity);

        return "user/ProductDetail";
    }

    @GetMapping("/product-detail-tra-sl-sp-va-id-spct/{productId}")
    @ResponseBody
    public Map<String, Object> getProductDetail(@PathVariable("productId") Integer productId,
                                                @RequestParam(value = "colorId", required = false) Integer colorId,
                                                @RequestParam(value = "sizeId", required = false) Integer sizeId,
                                                @RequestParam(value = "giaBan", required = false) BigDecimal giaBan
    ) {
        Map<String, Object> response = new HashMap<>();

        // Lấy chi tiết sản phẩm (vẫn dùng productId để lấy thông tin cơ bản của sản phẩm)
        SanPhamCTResponse sanPhamCT = sanPhamCTService.getSanPhamCTById1(productId);
        response.put("sanPhamCT", sanPhamCT);

        // Danh sách màu sắc và kích cỡ
        List<MauSac> listMauSac = mauSacService.getMauSacByProductId(productId);
        List<KichCo> listKichCo = kichCoService.getKichCoByProductId(productId);
        response.put("listMauSac", listMauSac);
        response.put("listKichCo", listKichCo);

        // Lấy idSanPhamCT từ các thuộc tính màu sắc, kích cỡ
        Integer sanPhamCTId = null;
        if (colorId != null && sizeId != null) {
            sanPhamCTId = sanPhamCTService.getSanPhamCTIdByAttributes(productId, colorId, sizeId,giaBan);
        }

        // Trả về id của SanPhamCT nếu tìm thấy
        response.put("sanPhamCTId", sanPhamCTId);

        // Lấy số lượng sản phẩm chi tiết dựa trên thuộc tính colorId và sizeId
        Integer quantity = null;
        if (colorId != null && sizeId != null) {
            quantity = sanPhamCTService.getQuantityByAttributes(productId, colorId, sizeId, giaBan);
        }
        response.put("quantity", quantity);


        return response;
    }


}
