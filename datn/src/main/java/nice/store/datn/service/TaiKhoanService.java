package nice.store.datn.service;


import nice.store.datn.entity.TaiKhoan;
import nice.store.datn.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public TaiKhoan createTK(TaiKhoan tk) {
        return taiKhoanRepository.save(tk);
    }




}
