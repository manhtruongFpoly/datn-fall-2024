package nice.store.datn.service;

import jakarta.persistence.EntityNotFoundException;
import nice.store.datn.entity.*;
import nice.store.datn.repository.*;
import nice.store.datn.response.SanPhamCTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
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
        sanPhamChiTiet.setNgayTao(LocalDateTime.now());
        sanPhamChiTiet.setNgaySua(LocalDateTime.now());
        sanPhamChiTiet.setTrangThai(1);

        SanPhamChiTiet savedSanPham = sanPhamChiTietRepository.save(sanPhamChiTiet);

        List<HinhAnh> hinhAnhs = saveImages(files, savedSanPham);

        return buildSanPhamCTResponse(savedSanPham, hinhAnhs);
    }

    //Trưởng k dùng hải coment
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
                sanPhamChiTiet.getSanPham().getId(),
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

    //hai luu k ok
    public List<SanPhamChiTiet> saveToDatabase(List<SanPhamChiTiet> sanPhamChiTiets) {
        List<SanPhamChiTiet> savedSanPhamChiTiets = new ArrayList<>();

        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTiets) {
            // Kiểm tra xem sản phẩm chi tiết đã tồn tại hay không
            Optional<SanPhamChiTiet> existingSanPhamChiTiet = sanPhamCTRepository
                    .findByMauSacIdAndTheLoaiIdAndKichCoIdAndChatLieuIdAndDeGiayIdAndThuongHieuIdAndSanPhamId(
                            sanPhamChiTiet.getMauSac().getId(),
                            sanPhamChiTiet.getLoaiGiay().getId(),
                            sanPhamChiTiet.getKichCo().getId(),
                            sanPhamChiTiet.getChatLieu().getId(),
                            sanPhamChiTiet.getDeGiay().getId(),
                            sanPhamChiTiet.getThuongHieu().getId(),
                            sanPhamChiTiet.getSanPham().getId()
                    );

            if (existingSanPhamChiTiet.isPresent()) {
                // Sản phẩm chi tiết đã tồn tại, cập nhật số lượng
                SanPhamChiTiet existing = existingSanPhamChiTiet.get();
                existing.setSoLuong(existing.getSoLuong() + sanPhamChiTiet.getSoLuong()); // Cập nhật số lượng

                // Cập nhật danh sách hình ảnh (nếu có thay đổi)
                if (sanPhamChiTiet.getHinhAnhs() != null && !sanPhamChiTiet.getHinhAnhs().isEmpty()) {
                    for (HinhAnh hinhAnh : sanPhamChiTiet.getHinhAnhs()) {
                        hinhAnh.setSanPhamChiTiet(existing); // Liên kết với sản phẩm chi tiết hiện tại
                    }
                    existing.setHinhAnhs(sanPhamChiTiet.getHinhAnhs()); // Cập nhật danh sách hình ảnh
                }

                savedSanPhamChiTiets.add(sanPhamCTRepository.save(existing)); // Lưu lại sản phẩm chi tiết đã cập nhật
            } else {
                // Sản phẩm chi tiết chưa tồn tại, lưu mới
                if (sanPhamChiTiet.getHinhAnhs() != null && !sanPhamChiTiet.getHinhAnhs().isEmpty()) {
                    for (HinhAnh hinhAnh : sanPhamChiTiet.getHinhAnhs()) {
                        hinhAnh.setSanPhamChiTiet(sanPhamChiTiet); // Liên kết với sản phẩm chi tiết mới
                    }
                }

                savedSanPhamChiTiets.add(sanPhamCTRepository.save(sanPhamChiTiet)); // Lưu sản phẩm chi tiết mới
            }
        }

        return savedSanPhamChiTiets;  // Trả về danh sách các sản phẩm chi tiết đã lưu (bao gồm ID)
    }

    public List<SanPhamChiTiet> findBySanPhamId(Integer productId) {
        return sanPhamChiTietRepository.findBySanPhamId(productId);
    }


    public SanPhamChiTiet save(SanPhamChiTiet spct) {
        return sanPhamChiTietRepository.save(spct);
    }

    public Optional<SanPhamChiTiet> getSanPhamChiTietById(Integer id) {
        return sanPhamChiTietRepository.findById(id);
    }


    private SanPhamCTResponse convertToResponse(SanPhamChiTiet sanPhamChiTiet) {
        String imageUrl = (sanPhamChiTiet.getHinhAnhs() != null && !sanPhamChiTiet.getHinhAnhs().isEmpty())
                ? sanPhamChiTiet.getHinhAnhs().get(0).getUrl()
                : null;

        return new SanPhamCTResponse(
                sanPhamChiTiet.getId(),
                sanPhamChiTiet.getSanPham().getId(),
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

    // Lấy tất cả SanPhamCTResponse từ danh sách SanPhamChiTiet
    public List<SanPhamCTResponse> getAllSanPhamCTResponses() {
        List<SanPhamChiTiet> sanPhamChiTietList = sanPhamCTRepository.findAll(); // Lấy danh sách dữ liệu từ repository
        List<SanPhamCTResponse> responseList = new ArrayList<>();

        for (SanPhamChiTiet sanPhamChiTiet : sanPhamChiTietList) {
            responseList.add(convertToResponse(sanPhamChiTiet)); // Chuyển từng SanPhamChiTiet sang SanPhamCTResponse
        }

        return responseList;
    }
    public SanPhamCTResponse getSanPhamCTById1(Integer productId) {
        // Lấy chi tiết sản phẩm từ cơ sở dữ liệu
        Optional<SanPhamChiTiet> product = sanPhamCTRepository.findById(productId);

        if (product.isPresent()) {
            // Chuyển đổi từ SanPhamChiTiet sang SanPhamCTResponse
            return convertToResponse(product.get());
        }

        // Nếu không tìm thấy sản phẩm, trả về null hoặc xử lý tùy chỉnh
        return null;
    }

    public SanPhamCTResponse getSanPhamCTById2(Integer productId, Integer colorId, Integer sizeId) {
        Optional<SanPhamChiTiet> product = sanPhamCTRepository.findAllByAttributes(productId, colorId, sizeId);

        if (product.isPresent()) {
            // Chuyển đổi từ SanPhamChiTiet sang SanPhamCTResponse
            return convertToResponse(product.get());
        }

        return null;
    }

    public Optional<SanPhamChiTiet> findByMauSacAndKichCo(Integer mauSacId, Integer kichCoId, Integer idSp) {
        return sanPhamChiTietRepository.findByMauSacAndKichCo(mauSacId, kichCoId, idSp);
    }


    public Integer getQuantityByAttributes(Integer productId, Integer colorId, Integer sizeId) {
        return sanPhamCTRepository.findQuantityByProductAndAttributes(productId, colorId, sizeId);
    }

    public Integer getSanPhamCTIdByAttributes(Integer productId, Integer colorId, Integer sizeId) {
        // Lấy sản phẩm chi tiết dựa trên các thuộc tính
        Optional<SanPhamChiTiet> sanPhamChiTietOptional = sanPhamCTRepository.findTopByProductAndAttributesNative(productId, colorId, sizeId);

        if (sanPhamChiTietOptional.isPresent()) {
            // Trả về ID của sản phẩm chi tiết nếu tìm thấy
            return sanPhamChiTietOptional.get().getId();
        } else {
            // Trả về null nếu không tìm thấy sản phẩm chi tiết
            return null;
        }
    }



}




