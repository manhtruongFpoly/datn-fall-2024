package nice.store.datn.service;

import nice.store.datn.entity.*;
import nice.store.datn.repository.*;
import nice.store.datn.response.SanPhamCTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SanPhamCTService {
    @Autowired
    private SanPhamChiTietRepository sanPhamCTRepository;

    @Autowired
    private KichCoRepository kichCoRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private DeGiayRepository deGiayRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private HinhAnhRepository hinhAnhRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public SanPhamChiTiet findById(Integer spctId) {
        return sanPhamCTRepository.findById(spctId).orElse(null);  // Trả về null nếu không tìm thấy
    }
    public SanPhamChiTiet getSanPhamCTById(Integer id) {
        return sanPhamCTRepository.findById(id).orElse(null);
    }

    public List<KichCo> getAllKichCo() {
        return kichCoRepository.findAll();
    }

    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }

    public List<DeGiay> getAllDeGiay() {
        return deGiayRepository.findAll();
    }

    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    public SanPhamChiTiet updateSanPhamCT(SanPhamChiTiet sanPhamCT) {
        return sanPhamCTRepository.save(sanPhamCT);
    }

    public List<SanPhamChiTiet> getAll() {
        return sanPhamCTRepository.findAll();
    }

    ;

    public SanPhamCTResponse saveSanPhamChiTiet(SanPhamChiTiet sanPhamChiTiet, List<MultipartFile> files) {
        // Thiết lập ngày tạo và trạng thái
        sanPhamChiTiet.setNgayTao(LocalDateTime.now());
        sanPhamChiTiet.setNgaySua(LocalDateTime.now());
        sanPhamChiTiet.setTrangThai(1); // 1 có thể là trạng thái "hoạt động"

        // Lưu sản phẩm chi tiết vào database
        SanPhamChiTiet savedSanPham = sanPhamChiTietRepository.save(sanPhamChiTiet);

        // Lưu hình ảnh vào thư mục và database
        List<HinhAnh> hinhAnhs = saveImages(files, savedSanPham);

        // Trả về thông tin phản hồi
        return buildSanPhamCTResponse(savedSanPham, hinhAnhs);
    }

    private List<HinhAnh> saveImages(List<MultipartFile> files, SanPhamChiTiet sanPhamChiTiet) {
        List<HinhAnh> hinhAnhs = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    // Lưu tệp vào thư mục
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(UPLOAD_DIR + fileName);
                    Files.createDirectories(filePath.getParent());
                    file.transferTo(filePath);

                    // Tạo đối tượng HinhAnh và lưu vào database
                    HinhAnh hinhAnh = new HinhAnh();
                    hinhAnh.setSanPhamChiTiet(sanPhamChiTiet);
                    hinhAnh.setUrl("/images/" + fileName);
                    hinhAnhRepository.save(hinhAnh);

                    hinhAnhs.add(hinhAnh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hinhAnhs;
    }

    private SanPhamCTResponse buildSanPhamCTResponse(SanPhamChiTiet sanPhamChiTiet, List<HinhAnh> hinhAnhs) {
        String imageUrl = hinhAnhs.isEmpty() ? null : hinhAnhs.get(0).getUrl();

        return new SanPhamCTResponse(
                sanPhamChiTiet.getId(),
                sanPhamChiTiet.getMaSpct(),
                sanPhamChiTiet.getSanPham().getTenSP(),
                sanPhamChiTiet.getMauSac().getTenMauSac(),
                sanPhamChiTiet.getLoaiGiay().getTenLoaiGiay(),
                sanPhamChiTiet.getKichCo().getSize(),
                sanPhamChiTiet.getChatLieu().getTenChatLieu(),
                sanPhamChiTiet.getDeGiay().getTenDeGiay(),
                sanPhamChiTiet.getThuongHieu().getTenThuongHieu(),
                sanPhamChiTiet.getGiaBan(),
                sanPhamChiTiet.getSoLuong(),
                sanPhamChiTiet.getMoTa(),
                sanPhamChiTiet.getNgayTao(),
                sanPhamChiTiet.getNgaySua(),
                sanPhamChiTiet.getTrangThai(),
                imageUrl
        );
    }



    public List<SanPhamChiTiet> saveToDatabase(List<SanPhamChiTiet> sanPhamChiTiets) {
        // Lưu danh sách sản phẩm chi tiết vào database và trả về danh sách đã lưu (bao gồm ID)
        return sanPhamCTRepository.saveAll(sanPhamChiTiets);
    }


    public List<SanPhamChiTiet> findBySanPhamId(Integer productId) {
        return sanPhamChiTietRepository.findBySanPhamId(productId);
    }


    public SanPhamChiTiet save(SanPhamChiTiet spct) {
        return sanPhamChiTietRepository.save(spct);
    }


}




