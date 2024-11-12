package nice.store.datn.service;

import nice.store.datn.entity.Nhanvien;

import java.util.List;

public interface NhanVienservice {
    List<Nhanvien> getAllEmployees();
    void saveEmployee(Nhanvien employee);
    Nhanvien getEmployeeById(int id);
    void deleteEmployeeById(int id);
}
