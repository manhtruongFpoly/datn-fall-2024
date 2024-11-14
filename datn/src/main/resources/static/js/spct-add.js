let selectedKichCo = [];
let selectedMauSac = [];

// Xử lý việc chọn checkbox cho Kích Cỡ
document.querySelectorAll('.kichCoCheckbox').forEach(checkbox => {
    checkbox.addEventListener('change', (e) => {
        const value = e.target.value;
        const id = e.target.id; // Lưu thêm id của checkbox
        if (e.target.checked) {
            selectedKichCo.push({value, id});
        } else {
            selectedKichCo = selectedKichCo.filter(item => item.id !== id);
        }
    });
});

// Xử lý việc chọn checkbox cho Màu Sắc
document.querySelectorAll('.mauSacCheckbox').forEach(checkbox => {
    checkbox.addEventListener('change', (e) => {
        const value = e.target.value;
        const id = e.target.id; // Lưu thêm id của checkbox
        if (e.target.checked) {
            selectedMauSac.push({value, id});
        } else {
            selectedMauSac = selectedMauSac.filter(item => item.id !== id);
        }
    });
});

// Khi mở modal Kích Cỡ, tải các giá trị đã chọn
$('#kichCoModal').on('show.bs.modal', function () {
    document.querySelectorAll('.kichCoCheckbox').forEach(checkbox => {
        checkbox.checked = selectedKichCo.some(item => item.id === checkbox.id);
    });
});

// Khi mở modal Màu Sắc, tải các giá trị đã chọn
$('#mauSacModal').on('show.bs.modal', function () {
    document.querySelectorAll('.mauSacCheckbox').forEach(checkbox => {
        checkbox.checked = selectedMauSac.some(item => item.id === checkbox.id);
    });
});

// Xử lý nút lưu cho Kích Cỡ
document.getElementById('saveKichCoBtn').addEventListener('click', () => {
    // Cập nhật các thẻ đã chọn cho Kích Cỡ
    const selectedTags = selectedKichCo.map(size =>
        `<span class="badge bg-secondary me-2" data-value="${size.value}" data-id="${size.id}">${size.value} <button class="btn-close btn-sm ms-2" type="button"></button></span>`
    ).join('');
    document.getElementById('kichCoTags').innerHTML = selectedTags;
});

// Xử lý nút lưu cho Màu Sắc
document.getElementById('saveMauSacBtn').addEventListener('click', () => {
    // Cập nhật các thẻ đã chọn cho Màu Sắc
    const selectedTags = selectedMauSac.map(color =>
        `<span class="badge bg-secondary me-2" data-value="${color.value}" data-id="${color.id}">${color.value} <button class="btn-close btn-sm ms-2" type="button"></button></span>`
    ).join('');
    document.getElementById('mauSacTags').innerHTML = selectedTags;
});

// Xóa thẻ và bỏ chọn checkbox khi nhấn nút đóng thẻ
document.getElementById('kichCoTags').addEventListener('click', (e) => {
    if (e.target.classList.contains('btn-close')) {
        const tagValue = e.target.closest('span').getAttribute('data-value');
        selectedKichCo = selectedKichCo.filter(item => item.value !== tagValue);

        // Bỏ chọn checkbox tương ứng
        document.querySelectorAll('.kichCoCheckbox').forEach(checkbox => {
            if (checkbox.value === tagValue) {
                checkbox.checked = false;
            }
        });

        // Xóa thẻ trong UI
        e.target.closest('span').remove();
    }
});

document.getElementById('mauSacTags').addEventListener('click', (e) => {
    if (e.target.classList.contains('btn-close')) {
        const tagValue = e.target.closest('span').getAttribute('data-value');
        selectedMauSac = selectedMauSac.filter(item => item.value !== tagValue);

        // Bỏ chọn checkbox tương ứng
        document.querySelectorAll('.mauSacCheckbox').forEach(checkbox => {
            if (checkbox.value === tagValue) {
                checkbox.checked = false;
            }
        });

        // Xóa thẻ trong UI
        e.target.closest('span').remove();
    }
});


//fix

function generateProductDetails() {
    const productName = document.getElementById('sanPhamSelect').options[document.getElementById('sanPhamSelect').selectedIndex].text;
    const productDescription = document.getElementById('moTa').value;
    const brand = document.getElementById('thuongHieuSelect').value;
    const material = document.getElementById('chatLieuSelect').value;
    const sole = document.getElementById('deGiaySelect').value;
    const category = document.getElementById('theLoaiSelect').value;

    // Lấy thông tin kích cỡ và màu sắc từ các tags
    const sizes = $('#kichCoTags .badge').map(function () {
        return $(this).attr('data-value');
    }).get();
    const colors = $('#mauSacTags .badge').map(function () {
        return $(this).attr('data-value');
    }).get();

    let productDetailsHtml = '';
    let index = $('#productDetailsTable tbody tr').length + 1;

    // Duyệt qua màu sắc và kích cỡ để tạo các sản phẩm chi tiết
    colors.forEach(function (color) {
        sizes.forEach(function (size) {
            // Kiểm tra xem cặp (size, color) đã tồn tại trong bảng chưa
            let exists = false;
            $('#productDetailsTable tbody tr').each(function () {
                const existingSize = $(this).find('td:eq(3)').text(); // Lấy kích cỡ trong bảng
                const existingColor = $(this).find('td:eq(4)').text(); // Lấy màu sắc trong bảng
                const isChecked = $(this).find('input[type="checkbox"]').prop('checked'); // Kiểm tra checkbox

                // Nếu cặp trùng và checkbox chưa được chọn, đánh dấu đã tồn tại
                if (existingSize === size && existingColor === color && !isChecked) {
                    exists = true;
                    return false; // Nếu tìm thấy cặp trùng, thoát khỏi vòng lặp
                }
            });

            // Nếu chưa tồn tại, tạo mới sản phẩm chi tiết
            if (!exists) {
                //test fullProductName
                const fullProductName = `${brand} - ${material} - ${sole} - ${category} - ${productName} [${size} - ${color}]`;
                productDetailsHtml += `
                <tr>
                    <td><input type="checkbox" value="new-product-${index}" class="checkbox-product"></td>
                    <td>${index}</td>
                    <td>${productName}</td> <!-- Hiển thị tên sản phẩm đơn giản -->
                            <td>${size}</td> <!-- Hiển thị kích cỡ -->
                            <td>${color}</td> <!-- Hiển thị màu sắc -->
                            <td><input type="number" class="form-control" name="productList[${index}].soLuong" value="1" required></td>
                            <td><input type="text" class="form-control" name="productList[${index}].giaBan" value="100000" required></td>
                         <td>
                         <input type="file" id="imageFile-${index}" name="productList[${index}].url" accept="image/*" multiple 
                         onchange="checkFileLimit(this, ${index})">
                         <div id="imagePreviewContainer-${index}" style="display: flex; gap: 5px; align-items: center; justify-content: center;">
                            <!-- Các ảnh sẽ hiển thị ở đây -->
                         </div>  
                         </td>

                             <td class="text-center">
                            <button type="button" class="btn btn-danger btn-sm" onclick="deleteRow(this)">Xóa</button>
                            </td>
                            </tr>
                            `;
                index++;
            }
        });
    });

    // Thêm các sản phẩm chi tiết mới vào bảng
    $('#productDetailsTable tbody').append(productDetailsHtml);
}

//hien anh vua chọn

function checkFileLimit(input, index) {
    const maxFiles = 4; // Số lượng file tối đa
    const files = input.files;

    if (files.length > maxFiles) {
        alert(`Bạn chỉ có thể chọn tối đa ${maxFiles} ảnh.`);
        input.value = ''; // Dọn sạch input
        document.getElementById(`imagePreviewContainer-${index}`).innerHTML = ''; // Xóa preview nếu có
    } else {
        previewImage(input, index); // Gọi hàm preview nếu số lượng hợp lệ
    }
}

function previewImage(input, index) {
    const files = input.files;
    const previewContainer = document.getElementById(`imagePreviewContainer-${index}`);
    previewContainer.innerHTML = ''; // Xóa ảnh cũ trước khi thêm ảnh mới

    if (files) {
        const fileLimit = Math.min(files.length, 4); // Giới hạn số lượng hiển thị tối đa

        for (let i = 0; i < fileLimit; i++) {
            const reader = new FileReader();

            reader.onload = function (e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.style.width = '70px';  // Điều chỉnh kích thước ảnh
                img.style.height = '70px';
                img.style.objectFit = 'contain';
                img.style.margin = '5px';

                previewContainer.appendChild(img); // Thêm ảnh vào container
            };

            reader.readAsDataURL(files[i]); // Đọc file ảnh
        }
    }
}


// Xóa dòng sản phẩm
function deleteRow(button) {
    $(button).closest('tr').remove();
}

// function checkFileLimit(input) {
//     const maxFiles = 4;  // Số lượng file tối đa
//     const files = input.files;
//
//     if (files.length > maxFiles) {
//         alert(`Bạn chỉ có thể chọn tối đa ${maxFiles} ảnh.`);
//         // Nếu chọn quá nhiều ảnh, loại bỏ các file thừa
//         input.value = '';  // Dọn sạch input, cho phép người dùng chọn lại ảnh
//     }
// }


function saveProductDetails() {
    let productDetails = [];
    console.log('Dữ liệu gửi đi:', productDetails);

    // Lấy thông tin chung của sản phẩm
    const productName = document.getElementById('sanPhamSelect').value;
    const category = document.getElementById('theLoaiSelect').value;
    const material = document.getElementById('chatLieuSelect').value;
    const sole = document.getElementById('deGiaySelect').value;
    const brand = document.getElementById('thuongHieuSelect').value;
    const productDescription = document.getElementById('moTa').value;

    // Duyệt qua từng dòng trong bảng sản phẩm chi tiết
    $('#productDetailsTable tbody tr').each(function () {
        const size = $(this).find('td:eq(3)').text();  // Kích cỡ
        const sizeId = selectedKichCo.find(item => item.value === size)?.id;

        const color = $(this).find('td:eq(4)').text();  // Màu sắc
        const colorId = selectedMauSac.find(item => item.value === color)?.id;

        const soluong = $(this).find('input[name*="soLuong"]').val();
        const giaBan = $(this).find('input[name*="giaBan"]').val();

        function generateMaSpct() {
            const now = new Date();
            return `SPCT-${now.getFullYear()}${String(now.getMonth() + 1).padStart(2, '0')}${String(now.getDate()).padStart(2, '0')}-${String(now.getHours()).padStart(2, '0')}${String(now.getMinutes()).padStart(2, '0')}${String(now.getSeconds()).padStart(2, '0')}`;
        }

        const productDetail = {
            sanPham: {id: productName},
            mauSac: {id: colorId},
            loaiGiay: {id: category},
            kichCo: {id: sizeId},
            chatLieu: {id: material},
            deGiay: {id: sole},
            thuongHieu: {id: brand},
            maSpct: generateMaSpct(),
            giaBan: giaBan,
            soLuong: soluong,
            moTa: productDescription,
            ngayTao: new Date().toISOString(),
            trangThai: 1
        };

        productDetails.push(productDetail);
    });

    // Bước 1: Lưu SPCT

    fetch('/api/san-pham-chi-tiet/saveSPCT', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productDetails)
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // Trả về danh sách SPCT bao gồm ID
            } else {
                throw new Error('Lỗi khi lưu sản phẩm chi tiết');
            }
        })
        .then(spctList => {
            console.log('Danh sách SPCT đã lưu:', spctList);
            // Thêm thông báo thành công
            alert('Lưu sản phẩm chi tiết thành công!');

            // Bước 2: Upload ảnh cho từng SPCT
            spctList.forEach((spct, index) => {
                const imageInput = document.querySelector(`#productDetailsTable tbody tr:nth-child(${index + 1}) input[type="file"]`);

                if (imageInput.files.length > 0) {
                    // Bước 2: Upload ảnh cho từng SPCT
                    const formData = new FormData();
                    formData.append('spctId', spct.id); // ID SPCT vừa lưu
                    Array.from(imageInput.files).forEach(file => {
                        formData.append('files', file); // Gửi nhiều file
                    });

                    fetch('/api/san-pham-chi-tiet/uploadImage', {
                        method: 'POST',
                        body: formData
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(`Lỗi khi upload ảnh cho SPCT ID ${spct.id}`);
                            }
                            return response.text();  // Đảm bảo phản hồi được phân tích thành JSON
                        })
                        .then(data => {
                            console.log(`Upload ảnh thành công cho SPCT ID ${spct.id}:`, data);
                        })
                        .catch(error => {
                            console.error(`Lỗi khi upload ảnh cho SPCT ID ${spct.id}:`, error);
                        });

                } else {
                    console.warn(`Không có ảnh nào được chọn cho SPCT ID ${spct.id}`);
                }
            });


            //chuyen
            // window.location.href = 'http://localhost:8080/products';

        })
        .catch(error => {
            console.error('Lỗi khi lưu sản phẩm chi tiết hoặc upload ảnh!', error);
        });
}


document.getElementById('hoanTatButton').addEventListener('click', function () {
    saveProductDetails();
});

// Chỉnh sửa số lượng và giá chung cho các sản phẩm
function openBatchEditModal() {
    const selectedProducts = $('input.checkbox-product:checked');
    if (selectedProducts.length > 0) {
        $('#batchEditModal').modal('show');
    } else {
        alert('Vui lòng chọn sản phẩm để chỉnh sửa!');
    }
}

// Áp dụng thay đổi số lượng và giá
function applyBatchEdit() {
    const quantity = $('#modalQuantity').val();
    const price = $('#modalPrice').val();

    $('input.checkbox-product:checked').each(function () {
        const row = $(this).closest('tr');
        row.find('input[name$=".soLuong"]').val(quantity);
        row.find('input[name$=".giaBan"]').val(price);
    });

    $('#batchEditModal').modal('hide');
}


document.getElementById('btnLuuThuongHieu').addEventListener('click', function () {
    const thuongHieu = document.getElementById('thuongHieuInput').value.trim();
    const errorMessageDiv = document.getElementById('error-message1');

    // Kiểm tra tên thương hiệu có trống hay không
    if (thuongHieu === "") {
        showError("Vui lòng nhập tên thương hiệu.");
        return;
    }

    // Gửi yêu cầu kiểm tra tên thương hiệu có trùng hay không
    fetch('/kiem-tra-thuong-hieu', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({thuongHieu})
    })
        .then(response => response.json())
        .then(data => {
            if (data.trungLap) {
                showError(data.message);
            } else {
                // Nếu không trùng, gửi yêu cầu để thêm thương hiệu
                addThuongHieu(thuongHieu);
            }
        })
        .catch(error => {
            showError("Đã xảy ra lỗi khi kiểm tra tên thương hiệu.");
            console.error(error);
        });
});

function addThuongHieu(thuongHieu) {
    // Gửi yêu cầu để thêm thương hiệu
    fetch('/tao-moi-thuong-hieu', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({thuongHieu})
    })
        .then(response => response.json())
        .then(data => {
            if (data.trungLap) {
                showError(data.message);
            } else {
                // Nếu thêm thành công, cập nhật lại dropdown và đóng modal
                updateThuongHieuSelect(data);
                alert("Thêm thương hiệu thành công!");
                $('#themThuongHieuModal').modal('hide');
                document.getElementById('formThemThuongHieu').reset();

                // Điều hướng về trang khác
                window.location.href = "http://localhost:8080/san-phamct";
            }
        })
        .catch(error => {
            showError("Đã xảy ra lỗi, vui lòng thử lại.");
            console.error(error);
        });
}


function showError(message) {
    const errorMessageDiv = document.getElementById('error-message1');
    errorMessageDiv.textContent = message;
    errorMessageDiv.classList.remove('d-none');
}

function updateThuongHieuSelect(data) {
    // Cập nhật dropdown với thương hiệu mới
    const selectElement = document.getElementById('thuongHieuSelect');
    const newOption = document.createElement('option');
    newOption.value = data.id;
    newOption.textContent = data.tenThuongHieu;
    selectElement.appendChild(newOption);

    // Tự động chọn thương hiệu mới vừa thêm
    selectElement.value = data.id;
}

const modalElement = document.getElementById('themThuongHieuModal');
modalElement.addEventListener('hidden.bs.modal', function () {
    document.getElementById('formThemThuongHieu').reset();
    document.getElementById('error-message1').classList.add('d-none');
});


// Hàm gửi yêu cầu AJAX cho Thêm Chất Liệu
function addMaterial() {
    $.ajax({
        url: '/tao-chat-lieu',
        type: 'POST',
        data: {tenChatLieu: $('#chatLieuInput').val()}, // Lấy giá trị từ input
        success: function (response) {
            if (response.success) {
                window.location.href = "http://localhost:8080/san-phamct";
            } else {
                alert('Có lỗi xảy ra khi thêm chất liệu.');
            }
        },
        error: function () {
            alert('Có lỗi xảy ra khi thêm chất liệu.');
        }
    });
}

// Hàm gửi yêu cầu AJAX cho Thêm Đế Giày
function addSole() {
    $.ajax({
        url: '/tao-de-giay',
        type: 'POST',
        data: {tenDeGiay: $('#deGiayInput').val()}, // Lấy giá trị từ input
        success: function (response) {
            if (response.success) {
                window.location.href = "/san-phamct";
            } else {
                alert('Có lỗi xảy ra khi thêm đế giày.');
            }
        },
        error: function () {
            alert('Có lỗi xảy ra khi thêm đế giày.');
        }
    });
}

// Hàm gửi yêu cầu AJAX cho Thêm Thể Loại Giày
function addCategory() {
    $.ajax({
        url: '/tao-loai-giay',
        type: 'POST',
        data: {tenLoaiGiay: $('#loaiGiayInput').val()}, // Lấy giá trị từ input
        success: function (response) {
            if (response.success) {
                window.location.href = "/san-phamct";
            } else {
                alert('Có lỗi xảy ra khi thêm thể loại giày.');
            }
        },
        error: function () {
            alert('Có lỗi xảy ra khi thêm thể loại giày.');
        }
    });
}

// Gọi các hàm thêm mới khi người dùng nhấn vào nút Lưu
$('#btnAddMaterial').click(function () {
    addMaterial();
});

$('#btnAddSole').click(function () {
    addSole();
});

$('#btnAddCategory').click(function () {
    addCategory();
});

