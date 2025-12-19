package com.clinic.pharmacy.service.impl;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.exception.BadRequestException;
import com.clinic.pharmacy.exception.NotFoundException;
import com.clinic.pharmacy.model.ReceiptItemCreateDTO;
import com.clinic.pharmacy.model.ReceiptItemRequest;
import com.clinic.pharmacy.repository.WarehouseReceiptItemRepository;
import com.clinic.pharmacy.repository.WarehouseReceiptRepository;
import com.clinic.pharmacy.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.clinic.pharmacy.utils.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
class ReceiptServiceImpl implements ReceiptService {

    private final WarehouseReceiptRepository receiptRepository;
    private final WarehouseReceiptItemRepository itemRepository;

    @Override
    public WarehouseReceipt getReceipt(Long receiptId) {
        validateReceiptId(receiptId);
        return receiptRepository.findById(receiptId)
                .orElseThrow(() -> new NotFoundException(String.format(RECEIPT_NOT_FOUND, receiptId)));
    }

    @Override
    public WarehouseReceiptItem createWarehouseReceiptItem(ReceiptItemCreateDTO request) {
        Optional.ofNullable(request)
                .orElseThrow(() -> new BadRequestException(REQUEST_BODY_REQUIRED));

        WarehouseReceiptItem item = WarehouseReceiptItem.builder()
                .productCode(request.getProductCode())
                .importUnit(request.getImportUnit())
                .productName(request.getProductName())
                .productGroup(request.getProductGroup())
                .importQuantity(request.getImportQuantity())
                .importPrice(request.getImportPrice())
                .sellingPrice(request.getSellingPrice())
                .itemVatRate(request.getItemVatRate())
                .expirationDate(request.getExpirationDate())
                .keywords(request.getKeywords())
                .build();

        return itemRepository.save(item);
    }

    @Override
    public WarehouseReceiptItem addReceiptItem(Long receiptId,
                                               ReceiptItemRequest request) {
        validateReceiptId(receiptId);
        Optional.ofNullable(request)
                .orElseThrow(() -> new BadRequestException(REQUEST_BODY_REQUIRED));

        WarehouseReceipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new NotFoundException(String.format(RECEIPT_NOT_FOUND, receiptId)));

        String code = Optional.ofNullable(request.getProductCode())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new BadRequestException(PRODUCT_CODE_REQUIRED));

        checkUniqueProductCode(receipt, code);
        WarehouseReceiptItem item = WarehouseReceiptItem.builder()
                .receipt(receipt)
                .productCode(code)
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
    public List<WarehouseReceiptItem> addReceiptItems(Long receiptId, Set<ReceiptItemRequest> requests) {
        validateReceiptId(receiptId);
        Optional.ofNullable(requests)
                .filter(set -> !set.isEmpty())
                .orElseThrow(() -> new BadRequestException(REQUEST_SET_EMPTY));

        WarehouseReceipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new NotFoundException(String.format(RECEIPT_NOT_FOUND, receiptId)));

        List<WarehouseReceiptItem> items = new ArrayList<>(requests.size());
        HashSet<String> seenCodes = new HashSet<>();
        for (ReceiptItemRequest req : requests) {
            String code = Optional.ofNullable(req.getProductCode())
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .orElseThrow(() -> new BadRequestException(PRODUCT_CODE_REQUIRED));

            String normalized = code.toLowerCase();
            if (!seenCodes.add(normalized)) {
                throw new BadRequestException(String.format(DUPLICATE_PRODUCT_CODE_IN_REQUEST_SET, code));
            }

            checkUniqueProductCode(receipt, code);
            WarehouseReceiptItem item = WarehouseReceiptItem.builder()
                    .receipt(receipt)
                    .productCode(code)
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

    private void checkUniqueProductCode(WarehouseReceipt receipt, String code) {
        if (!isProductCodeUnique(receipt.getId(), code)) {
            throw new BadRequestException(String.format(PRODUCT_CODE_EXISTS_IN_RECEIPT, code));
        }
    }

    @Override
    public boolean isProductCodeUnique(Long receiptId, String productCode) {
        validateReceiptId(receiptId);
        String code = Optional.ofNullable(productCode)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new BadRequestException(PRODUCT_CODE_REQUIRED));
        return !itemRepository.existsByReceiptIdAndProductCodeIgnoreCase(receiptId, code);
    }

    private void validateReceiptId(Long receiptId) {
        Optional.ofNullable(receiptId)
                .filter(id -> id > 0)
                .orElseThrow(() -> new BadRequestException(RECEIPT_ID_POSITIVE));
    }
}
