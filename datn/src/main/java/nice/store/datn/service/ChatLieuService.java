package nice.store.datn.service;

import nice.store.datn.entity.ChatLieu;
import nice.store.datn.entity.DeGiay;
import nice.store.datn.entity.LoaiGiay;
import nice.store.datn.repository.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuService {
    @Autowired
    private ChatLieuRepository chatLieuRepository;
    private String generateMaChatLieu() {
        String prefix = "CL";
        int count = (int) chatLieuRepository.count() + 1;
        return prefix + String.format("%05d", count);
    }


    public String addChatLieu(ChatLieu chatLieu) {
        Optional<ChatLieu> existsByTenChatLieu = chatLieuRepository.findByTenChatLieu(chatLieu.getTenChatLieu());
        if (existsByTenChatLieu.isPresent()) {
            return "Tên chất liệu đã tồn tại!";
        }

        String generatedMa = generateMaChatLieu();
        chatLieu.setMaChatLieu(generatedMa);
        chatLieu.setNgayTao(LocalDate.now());
        chatLieu.setTrangThai(0);


        chatLieuRepository.save(chatLieu);
        return "Thêm đế giày thành công với mã: " + generatedMa;
    }

    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }

    public ChatLieu findById(Integer id) {
        return chatLieuRepository.findById(id).orElse(null);
    }

    public ChatLieu saveChatLieu(ChatLieu chatLieu) {
        return chatLieuRepository.save(chatLieu);
    }

    public void deleteChatLieu(int id) {
        chatLieuRepository.deleteById(id);
    }
}
