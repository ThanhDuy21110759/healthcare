BEGIN;

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
VALUES
    ('PNK-2025-0001','HD-001','Nhập kho từ danh mục','Võ Trường Sơn','TN999',123,0,'2025-01-10',NULL,'Nhập thuốc mới','Xe máy','Grab','Nguyễn Văn A',5.0),
    ('PNK-2025-0002','HD-002','Nhập kho từ danh mục','Trần Thị B','TN001',124,100000.00,'2025-01-11','2025-01-11','Hàng bổ sung','Ô tô','GHN','Nguyễn Văn B',8.0),
    ('PNK-2025-0003','HD-003','Nhập trả lại','Lê Văn C','TN002',125,250000.00,'2025-01-12',NULL,'Trả lại hàng lỗi','Xe máy','GHTK','Phạm Thị C',5.0),
    ('PNK-2025-0004','HD-004','Nhập kho từ danh mục','Nguyễn Thị D','TN003',126,50000.00,'2025-01-13','2025-01-13','Nhập bổ sung thiết bị','Ô tô','Viettel Post','Đỗ Văn D',10.0),
    ('PNK-2025-0005','HD-005','Nhập kho từ danh mục','Phạm Văn E','TN004',127,75000.00,'2025-01-14',NULL,'Nhập thuốc kháng sinh','Xe máy','Grab','Trần Văn E',8.0),
    ('PNK-2025-0006','HD-006','Nhập kho từ danh mục','Hoàng Thị F','TN005',128,120000.00,'2025-01-15','2025-01-15','Nhập vật tư y tế','Ô tô','GHN','Vũ Thị F',5.0),
    ('PNK-2025-0007','HD-007','Nhập kho từ danh mục','Đặng Văn G','TN006',129,0.00,'2025-01-16',NULL,'Nhập hàng khuyến mãi','Xe máy','GHTK','Ngô Văn G',0.0),
    ('PNK-2025-0008','HD-008','Nhập kho từ danh mục','Bùi Thị H','TN007',130,300000.00,'2025-01-17','2025-01-17','Nhập thuốc điều trị','Ô tô','Viettel Post','Lê Thị H',5.0),
    ('PNK-2025-0009','HD-009','Nhập kho từ danh mục','Võ Văn I','TN008',131,45000.00,'2025-01-18',NULL,'Nhập lô mới','Xe máy','Grab','Phan Văn I',8.0),
    ('PNK-2025-0010','HD-010','Nhập trả lại','Trịnh Thị J','TN009',132,20000.00,'2025-01-19','2025-01-19','Trả lại hàng hết hạn','Ô tô','GHN','Đỗ Thị J',5.0);

INSERT INTO warehouse_receipt_item (
    receipt_id,
    product_code,
    product_name,
    registration_number,
    active_ingredient,
    declared_active_ingredient,
    concentration,
    packing,
    manufacturer,
    origin_country,
    allow_interconnect,
    product_group,
    import_quantity,
    lot_number,
    expiration_date,
    location,
    import_unit,
    conversion_rate,
    item_vat_rate,
    import_price,
    selling_price,
    keywords
)
VALUES
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0001'),'DQG00034598','Chorsamine-20',NULL,NULL,NULL,NULL,'Hộp','ABC Pharma','Việt Nam',TRUE,'Thuốc',10,'233232','2027-02-22','Kệ A1','Hộp',30,5.0,30000,35000,'khớp'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0002'),'DQG00012345','Paracetamol 500mg',NULL,'Paracetamol',NULL,'500mg','Vỉ','PharmaCo','Việt Nam',FALSE,'Thuốc',20,'LOT-PA-01','2027-12-31','Kệ B1','Vỉ',10,5.0,20000,25000,'giảm đau'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0003'),'DQG00067890','Amoxicillin 500mg',NULL,'Amoxicillin',NULL,'500mg','Vỉ','HealthCorp','Ấn Độ',FALSE,'Kháng sinh',15,'LOT-AM-02','2026-06-30','Kệ C2','Vỉ',10,5.0,45000,52000,'nhiễm khuẩn'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0004'),'DQG00011111','Vitamin C 1000mg',NULL,'Ascorbic Acid',NULL,'1000mg','Hộp','NutriPlus','Mỹ',TRUE,'Vitamin',30,'LOT-VC-03','2028-01-01','Kệ D4','Hộp',5,0.0,60000,70000,'tăng đề kháng'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0005'),'DQG00022222','Ibuprofen 400mg',NULL,'Ibuprofen',NULL,'400mg','Vỉ','MediCare','Đức',FALSE,'Thuốc',25,'LOT-IB-04','2026-09-15','Kệ E1','Vỉ',10,5.0,38000,45000,'kháng viêm'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0006'),'DQG00033333','Omeprazole 20mg',NULL,'Omeprazole',NULL,'20mg','Vỉ','GastroMed','Pháp',FALSE,'Thuốc dạ dày',40,'LOT-OM-05','2027-03-20','Kệ F2','Vỉ',10,5.0,55000,62000,'dạ dày'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0007'),'DQG00044444','Cefpodoxime 100mg',NULL,'Cefpodoxime',NULL,'100mg','Vỉ','Antibiotix','Hàn Quốc',FALSE,'Kháng sinh',18,'LOT-CE-06','2026-12-12','Kệ G3','Vỉ',10,5.0,72000,80000,'nhiễm khuẩn'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0008'),'DQG00055555','Loratadine 10mg',NULL,'Loratadine',NULL,'10mg','Vỉ','AllergyFree','Canada',FALSE,'Chống dị ứng',22,'LOT-LO-07','2027-05-05','Kệ H1','Vỉ',10,5.0,27000,32000,'dị ứng'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0009'),'DQG00066666','Metformin 500mg',NULL,'Metformin',NULL,'500mg','Vỉ','DiabetCare','Anh',FALSE,'Tiểu đường',35,'LOT-ME-08','2028-02-02','Kệ I2','Vỉ',10,5.0,41000,48000,'tiểu đường'),
    ((SELECT receipt_id FROM warehouse_receipt WHERE auto_code='PNK-2025-0010'),'DQG00077777','Aspirin 81mg',NULL,'Acetylsalicylic Acid',NULL,'81mg','Vỉ','CardioSafe','Úc',TRUE,'Tim mạch',50,'LOT-AS-09','2027-08-08','Kệ J3','Vỉ',10,0.0,15000,18000,'tim mạch');

COMMIT;
