package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.model.WarehouseReceiptItemRequest;
import java.util.List;
import java.util.Set;

public interface ReceiptService {

    WarehouseReceipt getReceipt(Long receiptId);

    WarehouseReceiptItem addReceiptItem(Long receiptId, WarehouseReceiptItemRequest request);

    List<WarehouseReceiptItem> addReceiptItems(Long receiptId,
        Set<WarehouseReceiptItemRequest> requests);
}
