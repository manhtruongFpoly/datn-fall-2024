package nice.store.datn.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.GioHangCT;
import nice.store.datn.entity.KhachHang;

import nice.store.datn.repository.GioHangChiTietRepository;

import nice.store.datn.repository.GioHangrepository;
import nice.store.datn.repository.KhachHangRepository;
import nice.store.datn.repository.SanPhamChiTietRepository;
import nice.store.datn.response.GioHangCTDTO;
import nice.store.datn.response.GioHangDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    @Autowired
    private GioHangrepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangCTRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamCTRepository;

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(Integer productId, Integer quantity, HttpSession session) {
        if (productId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("ID sản phẩm hoặc số lượng không hợp lệ.");
        }
        GioHang cart = getCartFromSession(session);

        // Liên kết giỏ hàng với khách hàng (nếu đã đăng nhập)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String username = auth.getName();
            KhachHang customer = khachHangRepository.findByEmail(username);
            cart.setKhachHang(customer);
        } else {
            // Gán khách hàng mặc định nếu không đăng nhập
            KhachHang defaultCustomer = khachHangRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Khách hàng mặc định không tồn tại"));
            cart.setKhachHang(defaultCustomer);
        }

        // Thêm hoặc cập nhật sản phẩm trong giỏ hàng
        GioHangCT cartItem = gioHangCTRepository.findByGioHangAndSanPhamChiTiet_Id(cart, productId);
        if (cartItem == null) {
            cartItem = new GioHangCT();
            cartItem.setGioHang(cart);
            cartItem.setSanPhamChiTiet(sanPhamCTRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại")));
            cartItem.setSoLuong(quantity);

            // Cập nhật danh sách sản phẩm trong giỏ hàng
            cart.getGioHangCTs().add(cartItem);  // Thêm chi tiết giỏ hàng vào giỏ hàng
        } else {
            cartItem.setSoLuong(cartItem.getSoLuong() + quantity);
        }

        gioHangCTRepository.save(cartItem);
        gioHangRepository.save(cart); // Cập nhật giỏ hàng sau khi thay đổi chi tiết giỏ hàng

        session.setAttribute("cartId", cart.getId());
    }

    @Transactional
     public GioHang getCartFromSession(HttpSession session) {
         Integer cartId = (Integer) session.getAttribute("cartId");
         GioHang cart;

         if (cartId != null) {
             // Lấy giỏ hàng từ database dựa trên cartId trong session
             cart = gioHangRepository.findById(cartId).orElse(null);
             if (cart != null) {
                 return cart; // Nếu giỏ hàng tồn tại, trả về giỏ hàng
             }
         }

         // Nếu không tìm thấy giỏ hàng, tạo giỏ hàng mới
         cart = new GioHang();
         cart.setNgayTao(LocalDateTime.now());
         cart.setTrangThai(1);
         cart = gioHangRepository.save(cart); // Lưu giỏ hàng vào database

         // Lưu cartId của giỏ hàng mới vào session
         session.setAttribute("cartId", cart.getId());

         return cart; // Trả về giỏ hàng mới tạo
     }


     //hàm convert sang dtm giohang
    public GioHangDTO convertToDTO(GioHang cart) {
        GioHangDTO cartDTO = new GioHangDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setKhachHangId(cart.getKhachHang() != null ? cart.getKhachHang().getId() : null); // Chỉ lấy ID của khách hàng
        cartDTO.setNgayTao(cart.getNgayTao());
        cartDTO.setTrangThai(cart.getTrangThai());

        List<GioHangCTDTO> gioHangCTDTOS = cart.getGioHangCTs().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        cartDTO.setGioHangCTs(gioHangCTDTOS);

        cartDTO.setTongTien(BigDecimal.valueOf(cart.getTongTien()));

        return cartDTO;
    }

    //hàm convert sang dtm giohang ghct
    public GioHangCTDTO convertToDTO(GioHangCT gioHangCT) {
        GioHangCTDTO itemDTO = new GioHangCTDTO();

        itemDTO.setProductId(gioHangCT.getSanPhamChiTiet() != null ? gioHangCT.getSanPhamChiTiet().getId() : null);
        itemDTO.setTenSanPham(gioHangCT.getSanPhamChiTiet().getSanPham().getTenSP());
        itemDTO.setMauSac(gioHangCT.getSanPhamChiTiet().getMauSac().getTenMauSac());
        itemDTO.setKichCo(gioHangCT.getSanPhamChiTiet().getKichCo().getSize());
        itemDTO.setSoLuong(gioHangCT.getSoLuong());
        itemDTO.setGiaBan(gioHangCT.getSanPhamChiTiet() != null ? gioHangCT.getSanPhamChiTiet().getGiaBan() : null);

        // Lấy ảnh đầu tiên từ danh sách hinhAnhs (giả sử hinhAnhs là danh sách ảnh trong SanPhamChiTiet)
        if (gioHangCT.getSanPhamChiTiet() != null && gioHangCT.getSanPhamChiTiet().getHinhAnhs() != null && !gioHangCT.getSanPhamChiTiet().getHinhAnhs().isEmpty()) {
            itemDTO.setHinhAnh(gioHangCT.getSanPhamChiTiet().getHinhAnhs().get(0).getUrl());  // Lấy URL của ảnh đầu tiên
        }

        return itemDTO;
    }



    //gio hang dto
     @Transactional
     public GioHangDTO getCartFromSession1(HttpSession session) {
         // Lấy cartId từ session
         Integer cartId = (Integer) session.getAttribute("cartId");
         GioHang cart = null;

         if (cartId != null) {
             // Kiểm tra nếu cartId tồn tại trong session
             cart = gioHangRepository.findById(cartId).orElse(null);
             if (cart != null) {
                 // Nếu giỏ hàng tồn tại trong cơ sở dữ liệu, trả về giỏ hàng đó
                 return convertToDTO(cart); // Chuyển từ GioHang sang GioHangDTO
             }
         }

         // Nếu không tìm thấy giỏ hàng, tạo giỏ hàng mới
         cart = new GioHang();
         cart.setNgayTao(LocalDateTime.now());
         cart.setTrangThai(1); // Trạng thái giỏ hàng có thể được tùy chỉnh
         cart = gioHangRepository.save(cart); // Lưu giỏ hàng vào database

         // Lưu cartId của giỏ hàng mới vào session
         session.setAttribute("cartId", cart.getId());

         return convertToDTO(cart); // Trả về giỏ hàng mới dưới dạng GioHangDTO
     }





    //Xóa sản phẩm khỏi giỏ hàng
    public void removeCartItem(Integer productId, HttpSession session) {
        GioHang cart = getCartFromSession(session);
        GioHangCT cartItem = gioHangCTRepository.findByGioHangAndSanPhamChiTiet_Id(cart, productId);
        if (cartItem != null) {
            gioHangCTRepository.delete(cartItem);
            session.setAttribute("cartId", cart.getId());
        } else {
            throw new RuntimeException("Sản phẩm không có trong giỏ hàng!");
        }
    }


    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public void updateCartItem(Integer productId, Integer quantity, HttpSession session) {
        GioHang cart = getCartFromSession(session);
        GioHangCT cartItem = gioHangCTRepository.findByGioHangAndSanPhamChiTiet_Id(cart, productId);
        if (cartItem == null) {
            throw new RuntimeException("Sản phẩm không tồn tại trong giỏ hàng.");
        }
        cartItem.setSoLuong(quantity);
        gioHangCTRepository.save(cartItem);
        session.setAttribute("cartId", cart.getId());
    }

    // Xóa toàn bộ giỏ hàng
    public void clearCart(Authentication auth) {
        if (auth != null) {
            String username = auth.getName();
            KhachHang customer = khachHangRepository.findByEmail(username);
            if (customer != null) {
                gioHangRepository.deleteAllByKhachHangId(customer.getId());
            } else {
                throw new RuntimeException("Không tìm thấy khách hàng!");
            }
        } else {
            throw new RuntimeException("Người dùng không xác thực!");
        }
    }
}