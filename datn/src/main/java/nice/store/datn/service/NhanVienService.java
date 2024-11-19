//package nice.store.datn.service;
//
//import nice.store.datn.entity.Nhanvien;
//
//import nice.store.datn.repository.NhanVienrepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class NhanVienService {
//
//    @Autowired
//    private NhanVienrepository nhanVienRepository;
//
//    public Nhanvien create(Nhanvien nv){
//        if (nhanVienRepository.findByIdNV(nv.getMaNV()).isPresent()){
//            throw new RuntimeException("Mã nhân viên đã tồn tại" +nv.getMaNV());
//        }
//        nv.setMaNV(String.valueOf(nv.getMaNV()));
//        nv.setHo(String.valueOf(nv.getHo()));
//        nv.setTen(String.valueOf(nv.getTen()));
//        nv.setEmail(String.valueOf(nv.getEmail()));
//        nv.setDateCreate(LocalDateTime.now());
//        nv.setDateFix(LocalDateTime.now());
//        return nhanVienRepository.save(nv);
//    }
//    public Optional<Nhanvien> getIdNhanVien(int id) {
//        return nhanVienRepository.findById(id);
//    }
//
//    public List<Nhanvien> getAllNv(){
//        return nhanVienRepository.findAll();
//    }
//    public Nhanvien update(Integer id, Nhanvien nv){
//        if(nhanVienRepository.existsById(id)){
//            Nhanvien getNV=nhanVienRepository.findById(id).orElseThrow();
//            new RuntimeException( "NhanVien id"+ id+ "notfoud");
//            nv.setID(id);
//            nv.setMaNV(String.valueOf(nv.getMaNV()));
//            nv.setHo(String.valueOf(nv.getHo()));
//            nv.setTen(String.valueOf(nv.getTen()));
//            nv.setEmail(String.valueOf(nv.getEmail()));
//            nv.setDateCreate(getNV.getDateCreate());
//            nv.setDateFix(LocalDateTime.now());
//            return nhanVienRepository.save(nv);
//        }
//        throw new RuntimeException("NhanVien id"+ id);
//    }
//    public void deleteNhanVien(int id){
//        nhanVienRepository.deleteById(id);
//    }
//}