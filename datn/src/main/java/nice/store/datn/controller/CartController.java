package nice.store.datn.controller;

import jakarta.servlet.http.HttpSession;
import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.SanPhamChiTiet;
import nice.store.datn.response.GioHangDTO;
import nice.store.datn.response.SanPhamCTResponse;
import nice.store.datn.service.CartService;
import nice.store.datn.service.SanPhamCTService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    SanPhamCTService sanPhamCTService;

    ////hai Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Integer productId,
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            cartService.addToCart(productId, quantity, session);
            redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được thêm vào giỏ hàng.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng.");
        }
        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart1(Model model, HttpSession session) {
        GioHangDTO cartDTO = cartService.getCartFromSession1(session);
        if (cartDTO == null || cartDTO.getGioHangCTs().isEmpty()) {
            model.addAttribute("message", "Giỏ hàng của bạn hiện đang trống.");
        } else {
            double total = cartDTO.getTongTien();
            model.addAttribute("total", total);
            model.addAttribute("cart", cartDTO);
        }
        return "user/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model) {

        return "/user/ThanhToanChuaDangNhap"; // Trang thanh toán
    }





    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/remove")
    public String removeCartItem(@RequestParam("productId") Integer productId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        try {
            cartService.removeCartItem(productId, session);
            redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được xóa khỏi giỏ hàng.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa sản phẩm khỏi giỏ hàng.");
        }
        return "redirect:/cart/view";
    }

    // Xóa toàn bộ giỏ hàng
    @PostMapping("/clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            cartService.clearCart(auth);
            session.removeAttribute("cartId");
            redirectAttributes.addFlashAttribute("success", "Giỏ hàng đã được xóa toàn bộ.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa giỏ hàng.");
        }
        return "redirect:/cart/view";
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @PostMapping("/update")
    public String updateCartItem(@RequestParam("productId") Integer productId,
                                 @RequestParam("quantity") Integer quantity,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        try {
            cartService.updateCartItem(productId, quantity, session);
            redirectAttributes.addFlashAttribute("success", "Số lượng sản phẩm đã được cập nhật.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật số lượng sản phẩm.");
        }
        return "redirect:/cart/view";
    }
}