package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.model.WarehouseReceiptItemRequest;
import com.clinic.pharmacy.repository.WarehouseReceiptItemRepository;
import com.clinic.pharmacy.repository.WarehouseReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
class ReceiptServiceImpl implements ReceiptService {

    private final WarehouseReceiptRepository receiptRepository;
    private final WarehouseReceiptItemRepository itemRepository;

    @Override
    public WarehouseReceipt getReceipt(Long receiptId) {
        validateReceiptId(receiptId);
        return receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Receipt not found: " + receiptId));
    }

    @Override
    public WarehouseReceiptItem addReceiptItem(Long receiptId,
                                               WarehouseReceiptItemRequest request) {
        validateReceiptId(receiptId);
        validateItemRequest(request);

        WarehouseReceipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Receipt not found: " + receiptId));

        WarehouseReceiptItem item = WarehouseReceiptItem.builder()
                .receipt(receipt)
                .productCode(request.getProductCode().trim())
                .productName(request.getProductName().trim())
                .importQuantity(request.getImportQuantity())
                .lotNumber(request.getLotNumber())
                .expirationDate(request.getExpirationDate())
                .importUnit(request.getImportUnit())
                .conversionRate(request.getConversionRate())
                .importPrice(request.getImportPrice())
                .sellingPrice(request.getSellingPrice())
                .build();

        return itemRepository.save(item);
    }

    @Override
    public List<WarehouseReceiptItem> addReceiptItems(Long receiptId, Set<WarehouseReceiptItemRequest> requests) {
        validateReceiptId(receiptId);
        Optional.ofNullable(requests)
                .filter(set -> !set.isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request set must not be empty"));

        WarehouseReceipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Receipt not found: " + receiptId));

        List<WarehouseReceiptItem> items = new ArrayList<>(requests.size());
        for (WarehouseReceiptItemRequest req : requests) {
            validateItemRequest(req);
            WarehouseReceiptItem item = WarehouseReceiptItem.builder()
                    .receipt(receipt)
                    .productCode(req.getProductCode().trim())
                    .productName(req.getProductName().trim())
                    .importQuantity(req.getImportQuantity())
                    .lotNumber(req.getLotNumber())
                    .expirationDate(req.getExpirationDate())
                    .importUnit(req.getImportUnit())
                    .conversionRate(req.getConversionRate())
                    .importPrice(req.getImportPrice())
                    .sellingPrice(req.getSellingPrice())
                    .build();
            items.add(item);
        }

        return itemRepository.saveAll(items);
    }

    private void validateReceiptId(Long receiptId) {
        Optional.ofNullable(receiptId)
                .filter(id -> id > 0)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "receiptId must be positive"));
    }

    private void validateItemRequest(WarehouseReceiptItemRequest request) {
        Optional.ofNullable(request)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Request body is required"));

        if (!StringUtils.hasText(request.getProductCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productCode is required");
        }
        if (!StringUtils.hasText(request.getProductName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productName is required");
        }
        if (Optional.ofNullable(request.getImportQuantity()).filter(q -> q > 0).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "importQuantity must be positive");
        }
    }
}
