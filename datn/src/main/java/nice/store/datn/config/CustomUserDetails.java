package nice.store.datn.config;


import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.NhanVien;
import nice.store.datn.entity.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;



    public CustomUserDetails(KhachHang khachHang) {
        this.email = khachHang.getEmail();
        this.password = khachHang.getMatKhau();
        this.authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + RoleEnum.fromValue(khachHang.getIdRole().getId()).name())
        );
    }

    public CustomUserDetails(NhanVien nhanVien) {
        this.email = nhanVien.getEmail();
        this.password = nhanVien.getMatKhau();
        this.authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + RoleEnum.fromValue(nhanVien.getIdRole()).name())
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}