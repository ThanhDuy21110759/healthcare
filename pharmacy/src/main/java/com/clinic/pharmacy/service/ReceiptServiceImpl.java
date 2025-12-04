package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.exception.BadRequestException;
import com.clinic.pharmacy.exception.NotFoundException;
import com.clinic.pharmacy.model.ReceiptItemCreateDTO;
import com.clinic.pharmacy.model.ReceiptItemRequest;
import com.clinic.pharmacy.repository.WarehouseReceiptItemRepository;
import com.clinic.pharmacy.repository.WarehouseReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<WarehouseReceiptItem> addReceiptItems(Long receiptId, Set<ReceiptItemRequest> requests) {
        validateReceiptId(receiptId);
        Optional.ofNullable(requests)
                .filter(set -> !set.isEmpty())
                .orElseThrow(() -> new BadRequestException(REQUEST_SET_EMPTY));

        WarehouseReceipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new NotFoundException(String.format(RECEIPT_NOT_FOUND, receiptId)));

        List<WarehouseReceiptItem> items = new ArrayList<>(requests.size());
        for (ReceiptItemRequest req : requests) {
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
                .orElseThrow(() -> new BadRequestException(RECEIPT_ID_POSITIVE));
    }
}
