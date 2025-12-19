package com.clinic.pharmacy.service.impl;

import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.exception.BadRequestException;
import com.clinic.pharmacy.repository.WarehouseReceiptItemRepository;
import com.clinic.pharmacy.service.ReceiptItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
class ReceiptItemServiceImpl implements ReceiptItemService {

    private final WarehouseReceiptItemRepository repository;

    @Override
    public List<WarehouseReceiptItem> findByReceiptId(Long receiptId) {
        if (receiptId == null || receiptId <= 0) {
            throw new BadRequestException("receiptId must be positive");
        }
        return repository.findByReceiptId(receiptId);
    }

    @Override
    public List<WarehouseReceiptItem> findByProductCode(String productCode) {
        if (!StringUtils.hasText(productCode)) {
            throw new BadRequestException("productCode is required");
        }
        return repository.findByProductCode(productCode.trim());
    }
}
