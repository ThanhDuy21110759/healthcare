package com.clinic.pharmacy.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ExceptionMessages {

    public static final String REQUEST_BODY_REQUIRED = "Request body is required";
    public static final String UNEXPECTED_ERROR = "Unexpected error";
    public static final String VALIDATION_ERROR = "Validation error";
    public static final String RECEIPT_NOT_FOUND = "Receipt not found: %s";
    public static final String RECEIPT_ID_POSITIVE = "receiptId must be positive";
    public static final String REQUEST_SET_EMPTY = "Request set must not be empty";
    public static final String PRODUCT_CODE_REQUIRED = "productCode is required";
    public static final String PRODUCT_NAME_REQUIRED = "productName is required";
    public static final String IMPORT_QUANTITY_REQUIRED = "importQuantity is required";
    public static final String IMPORT_QUANTITY_POSITIVE = "importQuantity must be positive";
    public static final String IMPORT_PRICE_REQUIRED = "importPrice is required";
    public static final String DUPLICATE_PRODUCT_CODE_IN_REQUEST_SET = "Duplicate product code in request set: %s";
    public static final String PRODUCT_CODE_EXISTS_IN_RECEIPT = "Product code already exists in receipt: %s";
}
