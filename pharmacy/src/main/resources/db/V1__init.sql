CREATE TABLE IF NOT EXISTS warehouse_receipt (
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

	created_at TIMESTAMPTZ DEFAULT NOW(),
	updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS warehouse_receipt_item (
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

	created_at TIMESTAMPTZ DEFAULT NOW(),
	updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_wri_receipt_id ON warehouse_receipt_item(receipt_id);
