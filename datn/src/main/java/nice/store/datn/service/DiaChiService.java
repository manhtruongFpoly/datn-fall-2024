package nice.store.datn.service;

import nice.store.datn.entity.DiaChi;
import nice.store.datn.repository.DiaChiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepository;

    //create diachi
    public DiaChi createDiaChi(DiaChi diaChi) {
        diaChi.setNgayTao(LocalDateTime.now());
        return diaChiRepository.save(diaChi);
    }

    //get all
    public List<DiaChi> getAllDiaChi() {
        return diaChiRepository.findAll();
    }


    //find by id
    public Optional<DiaChi> getDiaChiById(int id) {
        return diaChiRepository.findById(id);
    }

    //update diachi
    public DiaChi updateDiaChi(Integer id, DiaChi diaChi) {
        if (diaChiRepository.existsById(id)) {
            diaChi.setId(id);
            return diaChiRepository.save(diaChi);
        }
        throw new  RuntimeException("Dia Chi id " + id + "not found!!");
    }

    //delete dia chi
    public void deleteDiaChiById(int id) {
        diaChiRepository.deleteById(id);
    }


    //dia chi ban hang
    public DiaChi getDiaChiMacDinh(Integer idKh) {
        return diaChiRepository.getDiaChiMacDinh(idKh);
    }
}
