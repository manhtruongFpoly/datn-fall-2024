package nice.store.datn.api;


import jakarta.servlet.http.HttpSession;
import nice.store.datn.entity.KichCo;
import nice.store.datn.entity.MauSac;
import nice.store.datn.response.SanPhamCTResponse;
import nice.store.datn.service.CartService;
import nice.store.datn.service.KichCoService;
import nice.store.datn.service.MauSacService;
import nice.store.datn.service.SanPhamCTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CartAPI {

    @Autowired
    private CartService cartService;

    @PostMapping("/api/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> requestData, HttpSession session) {
        try {
            Integer productId = (Integer) requestData.get("productId");
            Integer quantity = (Integer) requestData.get("quantity");
            cartService.addToCart(productId, quantity, session);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Sản phẩm đã được thêm vào giỏ hàng.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @Autowired
    private SanPhamCTService sanPhamCTService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private KichCoService kichCoService;

    @GetMapping("/detai12/{productId}")
    public ResponseEntity<?> getProductDetail(
            @PathVariable("productId") Integer productId,
            @RequestParam(value = "colorId", required = false) Integer colorId,
            @RequestParam(value = "sizeId", required = false) Integer sizeId) {

        try {
            // Lấy chi tiết sản phẩm
            SanPhamCTResponse sanPhamCT = sanPhamCTService.getSanPhamCTById1(productId);

            // Danh sách màu sắc và kích cỡ
            List<MauSac> listMauSac = mauSacService.getMauSacByProductId(productId);
            List<KichCo> listKichCo = kichCoService.getKichCoByProductId(productId);

            // Tạo map trả về dữ liệu
            Map<String, Object> response = new HashMap<>();
            response.put("sanPhamCT", sanPhamCT);
            response.put("listMauSac", listMauSac);
            response.put("listKichCo", listKichCo);

            // Trả về response dạng JSON
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Xử lý lỗi và trả về thông báo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }





}
