package nice.store.datn.config;



import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.NhanVien;
import nice.store.datn.repository.KhachHangRepository;

import nice.store.datn.repository.NhanVienrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienrepository nhanVienRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tìm kiếm khách hàng
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        if (khachHang != null) {
            return new CustomUserDetails(khachHang);
        }

        // Tìm kiếm nhân viên
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        if (nhanVien != null) {
            return new CustomUserDetails(nhanVien);
        }

        throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
    }
}

