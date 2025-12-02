package com.clinic.pharmacy.controller;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.model.Response;
import com.clinic.pharmacy.model.WarehouseReceiptItemRequest;
import com.clinic.pharmacy.service.ReceiptExportService;
import com.clinic.pharmacy.service.ReceiptService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
public class ReceiptExportController {

    private final ReceiptExportService exportService;
    private final ReceiptService receiptService;

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<@NonNull Response<String>> export(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(value = "encoding", required = false, defaultValue = "base64") String encoding) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        exportService.exportReceiptsToCsv(start, end, out);
        byte[] bytes = out.toByteArray();
        String payload;
        if ("base64".equalsIgnoreCase(encoding)) {
            payload = Base64.getEncoder().encodeToString(bytes);
        } else {
            payload = new String(bytes);
        }
        String filename = String.format("receipts_%s_%s.csv", start, end);
        Response<String> body = Response.<String>builder()
                .status(HttpStatus.OK)
                .message("Export generated: " + filename)
                .data(payload)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(body);
    }

    @GetMapping(value = "/{receiptId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<@NonNull Response<WarehouseReceipt>> getReceipt(
            @PathVariable("receiptId") Long receiptId) {
        WarehouseReceipt receipt = receiptService.getReceipt(receiptId);
        Response<WarehouseReceipt> ok = Response.<WarehouseReceipt>builder()
                .status(HttpStatus.OK)
                .message("Receipt fetched")
                .data(receipt)
                .build();
        return ResponseEntity.ok(ok);
    }

    @PostMapping(value = "/{receiptId}/items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<@NonNull Response<WarehouseReceiptItem>> addReceiptItem(
            @PathVariable("receiptId") Long receiptId,
            @RequestBody WarehouseReceiptItemRequest req) {
        WarehouseReceiptItem saved = receiptService.addReceiptItem(receiptId, req);
        Response<WarehouseReceiptItem> ok = Response.<WarehouseReceiptItem>builder()
                .status(HttpStatus.CREATED)
                .message("Item created")
                .data(saved)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(ok);
    }

    @PostMapping(value = "/{receiptId}/items/batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<@NonNull Response<List<WarehouseReceiptItem>>> addReceiptItems(
            @PathVariable("receiptId") Long receiptId,
            @RequestBody Set<WarehouseReceiptItemRequest> requests) {
        List<WarehouseReceiptItem> saved = receiptService.addReceiptItems(receiptId,
                requests);
        Response<List<WarehouseReceiptItem>> ok = Response.<List<WarehouseReceiptItem>>builder()
                .status(HttpStatus.CREATED)
                .message("Items created: " + saved.size())
                .data(saved)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(ok);
    }
}
