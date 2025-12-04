package com.clinic.pharmacy.controller;

import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.model.ReceiptItemCreateDTO;
import com.clinic.pharmacy.model.Response;
import com.clinic.pharmacy.service.ReceiptItemService;
import com.clinic.pharmacy.service.ReceiptService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipt-items")
@RequiredArgsConstructor
public class ReceiptItemController {

    private final ReceiptItemService receiptItemService;
    private final ReceiptService receiptService;

    @GetMapping("/by-receipt/{receiptId}")
    public List<WarehouseReceiptItem> getByReceiptId(@PathVariable("receiptId") Long receiptId) {
        return receiptItemService.findByReceiptId(receiptId);
    }

    @GetMapping("/by-product/{productCode}")
    public List<WarehouseReceiptItem> getByProductCode(@PathVariable("productCode") String productCode) {
        return receiptItemService.findByProductCode(productCode);
    }

    @PostMapping(value = "/items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<@NonNull Response<WarehouseReceiptItem>> createWarehouseReceiptItem(
            @Valid @RequestBody ReceiptItemCreateDTO request) {
        WarehouseReceiptItem saved = receiptService.createWarehouseReceiptItem(request);
        Response<WarehouseReceiptItem> ok = Response.<WarehouseReceiptItem>builder()
                .status(HttpStatus.CREATED)
                .message("Item created")
                .data(saved)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(ok);
    }
}
