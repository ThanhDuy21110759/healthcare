package com.clinic.pharmacy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.clinic.pharmacy.utils.DataConvention.*;
import static com.clinic.pharmacy.utils.ExceptionMessages.*;

@Data
@NoArgsConstructor
public class ReceiptItemCreateDTO {

    @JsonProperty("product_code")
    @NotBlank(message = PRODUCT_CODE_REQUIRED)
    private String productCode = DEFAULT_STRING;

    @JsonProperty("import_unit")
    private String importUnit = DEFAULT_STRING;

    @JsonProperty("product_name")
    @NotBlank(message = PRODUCT_NAME_REQUIRED)
    private String productName = DEFAULT_STRING;

    @JsonProperty("product_group")
    private String productGroup = DEFAULT_STRING;

    @JsonProperty("import_quantity")
    @NotNull(message = IMPORT_QUANTITY_REQUIRED)
    @Positive(message = IMPORT_QUANTITY_POSITIVE)
    private Integer importQuantity = DEFAULT_INTEGER;

    @JsonProperty("import_price")
    @NotNull(message = IMPORT_PRICE_REQUIRED)
    private BigDecimal importPrice = DEFAULT_BIGDECIMAL;

    @JsonProperty("selling_price")
    private BigDecimal sellingPrice = DEFAULT_BIGDECIMAL;

    @JsonProperty("item_vat_rate")
    private BigDecimal itemVatRate = DEFAULT_BIGDECIMAL;

    @JsonProperty("expiration_date")
    private LocalDate expirationDate = DEFAULT_LOCALDATE;

    @JsonProperty("keywords")
    private String keywords = DEFAULT_STRING;
}
