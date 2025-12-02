package com.clinic.pharmacy.controller;

import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.service.ReceiptItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipt-items")
@RequiredArgsConstructor
public class ReceiptItemController {

    private final ReceiptItemService receiptItemService;

    @GetMapping("/by-receipt/{receiptId}")
    public List<WarehouseReceiptItem> getByReceiptId(@PathVariable("receiptId") Long receiptId) {
        return receiptItemService.findByReceiptId(receiptId);
    }

    @GetMapping("/by-product/{productCode}")
    public List<WarehouseReceiptItem> getByProductCode(@PathVariable("productCode") String productCode) {
        return receiptItemService.findByProductCode(productCode);
    }
}
