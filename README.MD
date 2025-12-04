# 📦 Warehouse Receipt Feature — Database Specification

## 1. Overview

This document describes the database schema for the warehouse import (receiving) feature, including:

- Warehouse receipt (header)
- Receipt items (medicine / product details)
- Relationship model

This structure follows a standard SQL relational model.

---

## 2. Database Schema

### 2.1 Table: `warehouse_receipt`

Stores warehouse import receipts.

```sql
CREATE TABLE warehouse_receipt (
    receipt_id BIGSERIAL PRIMARY KEY,
    auto_code VARCHAR(50),
    invoice_number VARCHAR(50),
    receipt_type VARCHAR(255),
    stock_keeper VARCHAR(100),
    warehouse_code VARCHAR(100),
    supplier_id BIGINT,
    debt_amount DECIMAL(18,2),

    receipt_date DATE NOT NULL,
    invoice_date DATE,
    note TEXT,

    transport_method VARCHAR(255),
    transport_unit VARCHAR(255),
    deliverer VARCHAR(255),
    vat_rate DECIMAL(5,2),

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);
```

#### Field description

| Column           | Description              | Mô tả tiếng Việt       |
| ---------------- | ------------------------ | ---------------------- |
| receipt_id       | Primary key              | Mã phiếu nhập (PK)     |
| auto_code        | Receipt code (generated) | Mã phiếu               |
| invoice_number   | Supplier invoice number  | Số hóa đơn             |
| receipt_type     | Type of receipt          | Loại phiếu nhập        |
| stock_keeper     | Warehouse keeper         | Thủ kho nhập           |
| warehouse_code   | Target warehouse         | Kho nhập               |
| supplier_id      | Foreign key to supplier  | Mã nhà cung cấp        |
| debt_amount      | Amount owed              | Công nợ                |
| receipt_date     | Date of receipt          | Ngày nhập              |
| invoice_date     | Invoice date             | Ngày hóa đơn           |
| note             | Additional comments      | Ghi chú                |
| transport_method | Transport type           | Phương tiện vận chuyển |
| transport_unit   | Logistic carrier         | Đơn vị vận chuyển      |
| deliverer        | Person delivering goods  | Người giao hàng        |
| vat_rate         | VAT applied              | VAT (%)                |
| created_at       | Timestamp creation       | Ngày tạo               |
| updated_at       | Timestamp update         | Ngày cập nhật          |

---

### 2.2 Table: `warehouse_receipt_item`

Stores individual items inside a receipt.

```sql
CREATE TABLE warehouse_receipt_item (
    item_id BIGSERIAL PRIMARY KEY,
    receipt_id BIGINT NOT NULL REFERENCES warehouse_receipt(receipt_id),

    product_code VARCHAR(100),
    product_name VARCHAR(255),
    registration_number VARCHAR(100),
    active_ingredient VARCHAR(255),
    declared_active_ingredient VARCHAR(255),
    concentration VARCHAR(100),
    packing VARCHAR(255),
    manufacturer VARCHAR(255),
    origin_country VARCHAR(255),
    allow_interconnect BOOLEAN,

    product_group VARCHAR(255),
    import_quantity INT,
    lot_number VARCHAR(100),
    expiration_date DATE,
    location VARCHAR(255),

    import_unit VARCHAR(100),
    conversion_rate DECIMAL(10,2),
    item_vat_rate DECIMAL(5,2),
    import_price DECIMAL(18,2),
    selling_price DECIMAL(18,2),
    keywords TEXT,

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);
```

#### Field description

| Column                     | Description                    | Mô tả tiếng Việt         |
| -------------------------- | ------------------------------ | ------------------------ |
| receipt_id                 | FK to `warehouse_receipt`      | Mã phiếu nhập            |
| product_code               | Standardized product ID        | Mã ĐQG / Mã hàng hóa     |
| product_name               | Name of product / medicine     | Tên hàng hóa / tên thuốc |
| registration_number        | Registration / approval number | Số đăng ký               |
| active_ingredient          | Main active ingredient         | Hoạt chất chính          |
| declared_active_ingredient | Declared ingredient            | Hoạt chất đăng ký        |
| concentration              | Concentration value            | Hàm lượng                |
| packing                    | Packaging format               | Quy cách đóng gói        |
| manufacturer               | Manufacturer                   | Hãng sản xuất            |
| origin_country             | Country of origin              | Nước sản xuất            |
| allow_interconnect         | Boolean linking allowed        | Cho phép liên thông      |
| product_group              | Product category               | Nhóm hàng hóa            |
| import_quantity            | Quantity imported              | Số lượng nhập            |
| lot_number                 | Production lot                 | Số lô                    |
| expiration_date            | Expiry date                    | Hạn sử dụng              |
| location                   | Warehouse placement            | Vị trí trong kho         |
| import_unit                | Packaging unit                 | Đơn vị nhập              |
| conversion_rate            | Unit conversion ratio          | Tỷ lệ quy đổi            |
| item_vat_rate              | VAT applied                    | VAT (%)                  |
| import_price               | Purchase price                 | Giá nhập                 |
| selling_price              | Suggested retail price         | Giá bán                  |
| keywords                   | Search keywords                | Từ khóa                  |

---

## 3. Relationship Model

```
warehouse_receipt (1) ─── (N) warehouse_receipt_item
```

- One receipt contains multiple item entries.
- Items cannot exist without a receipt.

---

## 4. Example Insert Queries

### 4.1 Insert into `warehouse_receipt`

```sql
INSERT INTO warehouse_receipt (
    auto_code,
    invoice_number,
    receipt_type,
    stock_keeper,
    warehouse_code,
    supplier_id,
    debt_amount,
    receipt_date,
    invoice_date,
    note,
    transport_method,
    transport_unit,
    deliverer,
    vat_rate
)
VALUES (
    'PNK-2025-0001',
    'HD-001',
    'Nhập kho từ danh mục',
    'Võ Trường Sơn',
    'TN999',
    123,
    0,
    '2025-01-10',
    NULL,
    'Nhập thuốc mới',
    'Xe máy',
    'Grab',
    'Nguyễn Văn A',
    5.0
);
```

---

### 4.2 Insert into `warehouse_receipt_item`

```sql
INSERT INTO warehouse_receipt_item (
    receipt_id,
    product_code,
    product_name,
    import_quantity,
    lot_number,
    expiration_date,
    import_unit,
    conversion_rate,
    import_price,
    selling_price
)
VALUES (
    1,
    'DQG00034598',
    'Chorsamine-20',
    10,
    '233232',
    '2022-02-22',
    'Hộp',
    30,
    30000,
    35000
);
```

---

### 4.3 Insert into `warehouse_receipt_item` (Autonomous Import)

_Allow inserting items without a corresponding receipt._

```sql
INSERT INTO warehouse_receipt_item (
    product_code,
    import_unit,
    product_name,
    product_group,
    import_quantity,
    import_price,
    selling_price,
    item_vat_rate,
    expiration_date,
    keywords
)
VALUES (
    'DQG00098765',
    'Hộp',
    'Paracetamol 500mg',
    'Thuốc giảm đau',
    50,
    12000,
    15000,
    5.0,
    '2026-12-31',
    'paracetamol; giảm đau; hạ sốt'
);
```

---

## 5. Versioning

| Version | Date       | Changes                                  |
| ------- | ---------- | ---------------------------------------- |
| 1.0     | 16/11/2025 | Base schema definition                   |
| 2.0     | 5/12/2025  | Insert autonomous import without receipt |
