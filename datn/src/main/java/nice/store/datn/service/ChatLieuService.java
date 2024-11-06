package nice.store.datn.service;

import nice.store.datn.entity.ChatLieu;
import nice.store.datn.entity.LoaiGiay;
import nice.store.datn.repository.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuService {
    @Autowired
    private ChatLieuRepository chatLieuRepository;

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
