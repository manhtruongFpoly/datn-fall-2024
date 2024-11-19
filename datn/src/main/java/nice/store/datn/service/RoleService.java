package nice.store.datn.service;


import nice.store.datn.entity.PhieuGiamGia;
import nice.store.datn.entity.Role;
import nice.store.datn.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role createRole(Role role) {
        if (role.getId() == null) {
            // Nếu id là null thì persist, vì đó là thực thể mới
            return roleRepository.save(role);
        } else {
            // Nếu id đã có thì merge vào entity đang quản lý
            return roleRepository.save(role);
        }

    }

    public Role findById(Integer id){
        Optional<Role>role = roleRepository.findById(id);
        return role.map(o -> o).orElse(null);
    }

    // Lấy tất cả các vai trò
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Lấy vai trò theo ID
    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }




    // Cập nhật vai trò
    public Role updateRole(Integer id, Role role) {
        if (roleRepository.existsById(id)) {
            role.setId(id); // Thiết lập ID từ tham số
            return roleRepository.save(role);
        }
        return null;
    }

    // Xóa vai trò
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }


}
