package nice.store.datn.entity;

public enum TrangThaiDonHang {
    PENDING(0, "HOA_DON_CHO"),
    PROCESSING(1, "CHO_XAC_NHAN"),
    CONFIRMED(2, "DA_XAC_NHAN"),
    WAITING_FOR_SHIPPING(3, "CHO_VAN_CHUYEN"),
    IN_TRANSIT(4, "VAN_CHUYEN"),
    PAID(5, "THANH_TOAN"),
    COMPLETED(6, "HOAN_THANH"),
    CANCELLED(7, "DA_HUY"),
    UNDETERMINED(8, "KHONG_XAC_DINH");


    private final int value;
    private final String description;

    TrangThaiDonHang(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

