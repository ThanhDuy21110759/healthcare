package com.clinic.pharmacy.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "warehouse_receipt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long id;

    @Column(name = "auto_code", length = 50)
    private String autoCode;

    @Column(name = "invoice_number", length = 50)
    private String invoiceNumber;

    @Column(name = "receipt_type", length = 255)
    private String receiptType;

    @Column(name = "stock_keeper", length = 100)
    private String stockKeeper;

    @Column(name = "warehouse_code", length = 100)
    private String warehouseCode;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "debt_amount", precision = 18, scale = 2)
    private BigDecimal debtAmount;

    @Column(name = "receipt_date", nullable = false)
    private LocalDate receiptDate;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "note")
    private String note;

    @Column(name = "transport_method")
    private String transportMethod;

    @Column(name = "transport_unit")
    private String transportUnit;

    @Column(name = "deliverer")
    private String deliverer;

    @Column(name = "vat_rate", precision = 5, scale = 2)
    private BigDecimal vatRate;

    @Column(name = "created_at")
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WarehouseReceiptItem> items = new ArrayList<>();
}
