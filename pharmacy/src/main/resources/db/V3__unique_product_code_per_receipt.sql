ALTER TABLE warehouse_receipt_item
    ADD CONSTRAINT uq_wri_receipt_product UNIQUE (receipt_id, product_code);
