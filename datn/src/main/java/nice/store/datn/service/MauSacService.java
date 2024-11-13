package nice.store.datn.service;

import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.MauSac;
import nice.store.datn.repository.GioHangrepository;
import nice.store.datn.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MauSacService {
    @Autowired
    private MauSacRepository mauSacRepository;

    public MauSac create(MauSac mauSac) {
        mauSac.setNgayTao(LocalDateTime.now());
        return mauSacRepository.save(mauSac);
    }


    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }



    public Optional<MauSac> getMauSacById(Integer id) {
        return mauSacRepository.findById(id);
    }

    public MauSac update(Integer id, MauSac mauSac) {
        if (mauSacRepository.existsById(id)) {
            MauSac existingMauSac = mauSacRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("MauSac id " + id + " not found"));


            existingMauSac.setTenMauSac(mauSac.getTenMauSac());
            existingMauSac.setMaMauSac(mauSac.getMaMauSac());
            existingMauSac.setTrangThai(mauSac.getTrangThai());


            return mauSacRepository.save(existingMauSac);
        }

        // Throw exception if MauSac not found
        throw new RuntimeException("MauSac id " + id + " not found");
    }


    public boolean deleteMauSacById(int id) {
        if (mauSacRepository.existsById(id)) {
            mauSacRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
