package com.clinic.pharmacy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import lombok.*;

@Entity
@Table(name = "warehouse_receipt_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseReceiptItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private WarehouseReceipt receipt;

    @Column(name = "product_code", length = 100)
    private String productCode;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "registration_number", length = 100)
    private String registrationNumber;

    @Column(name = "active_ingredient", length = 255)
    private String activeIngredient;

    @Column(name = "declared_active_ingredient", length = 255)
    private String declaredActiveIngredient;

    @Column(name = "concentration", length = 100)
    private String concentration;

    @Column(name = "packing")
    private String packing;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "allow_interconnect")
    private Boolean allowInterconnect;

    @Column(name = "product_group")
    private String productGroup;

    @Column(name = "import_quantity")
    private Integer importQuantity;

    @Column(name = "lot_number", length = 100)
    private String lotNumber;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "import_unit", length = 100)
    private String importUnit;

    @Column(name = "conversion_rate", precision = 10, scale = 2)
    private BigDecimal conversionRate;

    @Column(name = "item_vat_rate", precision = 5, scale = 2)
    private BigDecimal itemVatRate;

    @Column(name = "import_price", precision = 18, scale = 2)
    private BigDecimal importPrice;

    @Column(name = "selling_price", precision = 18, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "created_at")
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private OffsetDateTime updatedAt = OffsetDateTime.now();
}
