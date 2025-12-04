package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.model.ReceiptItemCreateDTO;
import com.clinic.pharmacy.model.ReceiptItemRequest;
import java.util.List;
import java.util.Set;

public interface ReceiptService {

    WarehouseReceipt getReceipt(Long receiptId);

    WarehouseReceiptItem addReceiptItem(Long receiptId, ReceiptItemRequest request);

    List<WarehouseReceiptItem> addReceiptItems(Long receiptId,
        Set<ReceiptItemRequest> requests);

    WarehouseReceiptItem createWarehouseReceiptItem(ReceiptItemCreateDTO request);
}
