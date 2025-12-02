package com.clinic.pharmacy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.clinic.pharmacy.utils.DataConvention.*;

@Getter
@Setter
@Builder
public class WarehouseReceiptItemRequest {

    @Builder.Default
    @NonNull
    private String productCode = DEFAULT_STRING;

    @Builder.Default
    @NonNull
    private String productName = DEFAULT_STRING;

    @Builder.Default
    @NonNull
    private Integer importQuantity = DEFAULT_INTEGER;

    @Builder.Default
    @NonNull
    private String lotNumber = DEFAULT_STRING;

    @Builder.Default
    @NonNull
    private LocalDate expirationDate = DEFAULT_LOCALDATE;

    @Builder.Default
    @NonNull
    private String importUnit = DEFAULT_STRING;

    @Builder.Default
    @NonNull
    private BigDecimal conversionRate = DEFAULT_BIGDECIMAL;

    @Builder.Default
    @NonNull
    private BigDecimal importPrice = DEFAULT_BIGDECIMAL;

    @Builder.Default
    @NonNull
    private BigDecimal sellingPrice = DEFAULT_BIGDECIMAL;

}
