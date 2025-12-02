package com.clinic.pharmacy.repository;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseReceiptItemRepository extends JpaRepository<WarehouseReceiptItem, Long> {

    List<WarehouseReceiptItem> findByReceipt(WarehouseReceipt receipt);

    List<WarehouseReceiptItem> findByReceiptId(Long receiptId);

    List<WarehouseReceiptItem> findByProductCode(String productCode);
}
