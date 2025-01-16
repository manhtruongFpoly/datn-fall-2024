package nice.store.datn.controller;

import nice.store.datn.entity.ChatLieu;
import nice.store.datn.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService;

    @GetMapping("/chat-lieu")
    public String listChatLieu(Model model) {
        model.addAttribute("listChatLieu", chatLieuService.getAllChatLieu());
        return "/admin/ChatLieu/ChatLieuIndex";
    }

    @GetMapping("/addChatLieu")
    public String showAddForm(Model model) {
        model.addAttribute("chatLieu", new ChatLieu());
        return "/admin/ChatLieu/ChatLieuAdd";
    }

    @PostMapping("/saveChatLieu")
    public String saveChatLieu(@ModelAttribute ChatLieu chatLieu) {
        // Kiểm tra nếu `ngayTao` chưa được gán giá trị
        if (chatLieu.getNgayTao() == null) {
            chatLieu.setNgayTao(LocalDate.now()); // Đặt ngày hiện tại
        }
        chatLieuService.addChatLieu(chatLieu);
        return "redirect:/chat-lieu";
    }

    @GetMapping("/editCL/{id}")
    public String showEditCL(@PathVariable("id") Integer id, Model model) {
        ChatLieu chatLieu = chatLieuService.findById(id);  // Kiểm tra xem chatLieu có null không
        if (chatLieu == null) {
            return "redirect:/chat-lieu"; // Trở về danh sách nếu không tìm thấy
        }
        model.addAttribute("chatLieu", chatLieu);
        return "/admin/ChatLieu/ChatLieuUpdate";
    }

    @PostMapping("/updateChatLieu/{id}")
    public String updateCL(@PathVariable("id") Integer id, @ModelAttribute ChatLieu chatLieu) {
        // Nếu `chatLieu` chưa có ngày tạo, gán ngày hiện tại
        if (chatLieu.getNgayTao() == null) {
            chatLieu.setNgayTao(LocalDate.now());
        }

        // Luôn cập nhật `ngaySua` là ngày hiện tại
        chatLieu.setNgaySua(LocalDate.now());
        chatLieuService.saveChatLieu(chatLieu);
        return "redirect:/chat-lieu";
    }



}
