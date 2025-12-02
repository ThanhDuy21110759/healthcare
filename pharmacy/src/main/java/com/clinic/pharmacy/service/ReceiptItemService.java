package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import java.util.List;

public interface ReceiptItemService {

    List<WarehouseReceiptItem> findByReceiptId(Long receiptId);

    List<WarehouseReceiptItem> findByProductCode(String productCode);
}
