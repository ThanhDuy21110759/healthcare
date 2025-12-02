package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.repository.WarehouseReceiptItemRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
class ReceiptItemServiceImpl implements ReceiptItemService {

    private final WarehouseReceiptItemRepository repository;

    @Override
    public List<WarehouseReceiptItem> findByReceiptId(Long receiptId) {
        if (receiptId == null || receiptId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "receiptId must be positive");
        }
        return repository.findByReceiptId(receiptId);
    }

    @Override
    public List<WarehouseReceiptItem> findByProductCode(String productCode) {
        if (!StringUtils.isEmpty(productCode) || productCode.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productCode is required");
        }
        return repository.findByProductCode(productCode.trim());
    }
}
