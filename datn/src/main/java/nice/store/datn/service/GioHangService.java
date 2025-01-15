package nice.store.datn.service;


import jakarta.servlet.http.HttpSession;
import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.GioHangCT;
import nice.store.datn.entity.KhachHang;
import nice.store.datn.repository.GioHangChiTietRepository;
import nice.store.datn.repository.GioHangrepository;
import nice.store.datn.repository.KhachHangRepository;
import nice.store.datn.response.CartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {

    @Autowired
    private GioHangrepository gioHangrepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    public GioHang create(GioHang gioHang) {
        gioHang.setNgayTao(LocalDateTime.now());
        return gioHangrepository.save(gioHang);
    }

    //get all gio hang
    public List<GioHang> getAllGH() {
        return gioHangrepository.findAll();
    }

    //get gioHang by id
    public Optional<GioHang> getGHById(int id) {
        return gioHangrepository.findById(id);
    }

    //update gioHang
    public GioHang update(Integer id, GioHang gioHang) {
        if (gioHangrepository.existsById(id)) {
            gioHang.setId(id);
            return gioHangrepository.save(gioHang);
        }
        throw new RuntimeException("Gio Hang id" + id + " not found");
    }

    //delete GioHang
    public void deleteGhById(int id) {
        gioHangrepository.deleteById(id);
    }


    public GioHangCT deleteById(Integer id) {
        Optional<GioHangCT> optional = gioHangChiTietRepository.findById(id);
        return optional.map(o -> {
            gioHangChiTietRepository.delete(o);
            return o;
        }).orElse(null);
    }

    public String deleteById(Integer cartId, Integer productId) {
        try {
            gioHangChiTietRepository.deleteByCartIdAndProductId(cartId, productId);
            return "Sản phẩm đã được xóa khỏi giỏ hàng.";
        } catch (Exception e) {
            throw new RuntimeException("Có lỗi xảy ra khi xóa sản phẩm.");
        }
    }
}

