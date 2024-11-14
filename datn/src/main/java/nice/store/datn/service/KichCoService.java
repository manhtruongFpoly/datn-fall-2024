package nice.store.datn.service;

import jakarta.persistence.EntityNotFoundException;
import nice.store.datn.entity.KichCo;
import nice.store.datn.repository.KichCoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KichCoService {

    @Autowired
    private KichCoRepository kichCoRepository;

    // Method to generate a new MaKichCo (size code)
    private String generateMaKichCo() {
        String prefix = "KC";
        int count = (int) kichCoRepository.count() + 1;  // Count the existing records to generate the next ID
        return prefix + String.format("%05d", count);
    }


    public String addKichCo(KichCo kichCo) {

        Optional<KichCo> existingKichCo = kichCoRepository.findKichCoBySize(kichCo.getSize());
        if (existingKichCo.isPresent()) {
            return "Size already exists!";
        }

        String generatedMa = generateMaKichCo();
        kichCo.setMaKichCo(generatedMa);
        kichCo.setNgayTao(LocalDateTime.now());

        kichCoRepository.save(kichCo);
        return "Successfully added size with code: " + generatedMa;
    }

    public List<KichCo> getAllKichCo() {
        return kichCoRepository.findAllByOrderByNgayTaoDesc();
    }

    public KichCo updateKichCo(String maKichCo, KichCo updatedKichCo) {
        Optional<KichCo> existingKichCo = kichCoRepository.findByMaKichCo(maKichCo);
        if (existingKichCo.isPresent()) {
            KichCo kichCo = existingKichCo.get();


            kichCo.setSize(updatedKichCo.getSize());
            kichCo.setNgaySua(LocalDateTime.now());


            return kichCoRepository.save(kichCo);
        } else {
            throw new EntityNotFoundException("KichCo with MaKichCo " + maKichCo + " not found");
        }
    }

    // Method to delete a KichCo by its ID
    public String deleteKichCo(Integer id) {
        if (kichCoRepository.existsById(id)) {
            kichCoRepository.deleteById(id);
            return "Successfully deleted size!";
        }
        return "KichCo not found!";
    }


    public Optional<KichCo> getKichCoById(Integer id) {
        return kichCoRepository.findById(id);
    }

    public boolean existsBySize(String size) {
        return kichCoRepository.existsKichCoBySize(size);
    }
}
