package nice.store.datn.service;

import nice.store.datn.entity.ChatLieu;
import nice.store.datn.entity.ThuongHieu;
import nice.store.datn.repository.ChatLieuRepository;
import nice.store.datn.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuService {
    @Autowired
    private ThuongHieuRepository THrepository;

    public List<ThuongHieu> getAllChatLieu() {
        return THrepository.findAll();
    }

    public ThuongHieu findById(Integer id) {
        return THrepository.findById(id).orElse(null);
    }

    public ThuongHieu saveChatLieu(ThuongHieu thuongHieu) {
        return THrepository.save(thuongHieu);
    }

    public void deleteChatLieu(int id) {
        THrepository.deleteById(id);
    }
}
