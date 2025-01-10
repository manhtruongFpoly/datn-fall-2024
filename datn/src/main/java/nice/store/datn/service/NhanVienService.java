package nice.store.datn.service;

import nice.store.datn.entity.NhanVien;
import nice.store.datn.repository.NhanVienrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NhanVienService {

    private final NhanVienrepository nhanvienRepository;

    @Autowired
    public NhanVienService(NhanVienrepository nhanvienRepository) {
        this.nhanvienRepository = nhanvienRepository;
    }


    public NhanVien create(NhanVien nv) {

        String maNV = generateMaNV();
        while (nhanvienRepository.findByMaNV(maNV).isPresent()) {

            maNV = generateMaNV();
        }

        nv.setMaNv(maNV);
        nv.setIdRole(2);
        nv.setNgayTao(LocalDateTime.now());
        nv.setNgaySua(LocalDateTime.now());

        return nhanvienRepository.save(nv);
    }
    private String generateMaNV() {

        return "NV" + String.format("%03d", (int) (Math.random() * 10000));
    }



    public List<NhanVien> getAllNV() {
        return nhanvienRepository.findAll();
    }


    public Optional<NhanVien> getIdNhanVien(Integer id) {
        return nhanvienRepository.findById(id);
    }
    public Optional<NhanVien> getNhanVienByMaNv(String maNv) {
        return nhanvienRepository.findByMaNv(maNv);
    }

    public NhanVien updateNhanVienByMaNv(String maNv, NhanVien updatedNv) {

        // Find the existing "Nhân Viên" or throw an exception if not found
        NhanVien existingNv = nhanvienRepository.findByMaNv(maNv)
                .orElseThrow(() -> new RuntimeException("Mã Nhân Viên " + maNv + " không tồn tại"));

        // Update the fields (excluding "maNv")
        existingNv.setHo(updatedNv.getHo());
        existingNv.setTenDem(updatedNv.getTenDem());
        existingNv.setTen(updatedNv.getTen());
        existingNv.setSdt(updatedNv.getSdt());
        existingNv.setEmail(updatedNv.getEmail());
        existingNv.setGioiTinh(updatedNv.getGioiTinh());
        existingNv.setDiaChi(updatedNv.getDiaChi());
        existingNv.setNgaySinh(updatedNv.getNgaySinh());
        existingNv.setTrangThai(updatedNv.getTrangThai());
        existingNv.setMatKhau(updatedNv.getMatKhau());

        // Update modification time
        existingNv.setNgaySua(LocalDateTime.now());

        // Save the updated "Nhân Viên"
        return nhanvienRepository.save(existingNv);
    }



    public void deleteNV(int id) {
        if (!nhanvienRepository.existsById(id)) {
            throw new RuntimeException("ID " + id + " không tồn tại");
        }
        nhanvienRepository.deleteById(id);
    }


    public Optional<NhanVien> findByMaNv(String maNv) {
        return nhanvienRepository.findByMaNV(maNv);
    }
}
