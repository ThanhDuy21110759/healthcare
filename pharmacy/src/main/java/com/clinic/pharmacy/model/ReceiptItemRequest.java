package com.clinic.pharmacy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.clinic.pharmacy.utils.DataConvention.*;
import static com.clinic.pharmacy.utils.ExceptionMessages.*;

@Getter
@Setter
@Builder
public class ReceiptItemRequest {

    @Builder.Default
    @NonNull
    @NotBlank(message = PRODUCT_CODE_REQUIRED)
    private String productCode = DEFAULT_STRING;

    @Builder.Default
    @NonNull
    @NotBlank(message = PRODUCT_NAME_REQUIRED)
    private String productName = DEFAULT_STRING;

    @Builder.Default
    @NonNull
    @NotNull(message = IMPORT_QUANTITY_REQUIRED)
    @Positive(message = IMPORT_QUANTITY_POSITIVE)
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
    @NotNull(message = IMPORT_PRICE_REQUIRED)
    private BigDecimal importPrice = DEFAULT_BIGDECIMAL;

    @Builder.Default
    @NonNull
    private BigDecimal sellingPrice = DEFAULT_BIGDECIMAL;

}
