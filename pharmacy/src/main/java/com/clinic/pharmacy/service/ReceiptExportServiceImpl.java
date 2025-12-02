package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import com.clinic.pharmacy.entity.WarehouseReceiptItem;
import com.clinic.pharmacy.repository.WarehouseReceiptItemRepository;
import com.clinic.pharmacy.repository.WarehouseReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
class ReceiptExportServiceImpl implements ReceiptExportService {

    private final WarehouseReceiptRepository receiptRepository;
    private final WarehouseReceiptItemRepository itemRepository;

    @Value("${export.csv.delimiter:,}")
    private String csvDelimiter;

    @Value("${export.csv.charset:UTF-8}")
    private String csvCharset;

    @Override
    public void exportReceiptsToCsv(LocalDate start, LocalDate end, OutputStream out) {
        List<WarehouseReceipt> receipts = getReceipts(start, end);
        String header = String.join(csvDelimiter,
                "receipt_id", "auto_code", "invoice_number", "receipt_type", "stock_keeper",
                "warehouse_code", "supplier_id", "debt_amount", "receipt_date", "invoice_date",
                "transport_method", "transport_unit", "deliverer", "vat_rate") + "\n";
        write(out, header);
        for (WarehouseReceipt r : receipts) {
            String row = String.join(csvDelimiter,
                    safe(r.getId()), safe(r.getAutoCode()), safe(r.getInvoiceNumber()),
                    safe(r.getReceiptType()), safe(r.getStockKeeper()),
                    safe(r.getWarehouseCode()), safe(r.getSupplierId()), safe(r.getDebtAmount()),
                    safe(r.getReceiptDate()), safe(r.getInvoiceDate()),
                    safe(r.getTransportMethod()), safe(r.getTransportUnit()), safe(r.getDeliverer()),
                    safe(r.getVatRate())) + "\n";
            write(out, row);
            List<WarehouseReceiptItem> items = itemRepository.findByReceipt(r);
            String itemHeader = String.join(csvDelimiter,
                    "item_id", "receipt_id", "product_code", "product_name", "import_quantity",
                    "lot_number", "expiration_date", "import_unit", "conversion_rate", "import_price",
                    "selling_price") + "\n";
            write(out, itemHeader);
            for (WarehouseReceiptItem it : items) {
                String irow = String.join(csvDelimiter,
                        safe(it.getId()), safe(r.getId()), safe(it.getProductCode()),
                        safe(it.getProductName()), safe(it.getImportQuantity()),
                        safe(it.getLotNumber()), safe(it.getExpirationDate()), safe(it.getImportUnit()),
                        safe(it.getConversionRate()),
                        safe(it.getImportPrice()), safe(it.getSellingPrice())) + "\n";
                write(out, irow);
            }
        }
    }

    @Override
    public List<WarehouseReceipt> getReceipts(LocalDate start, LocalDate end) {
        return receiptRepository.findByReceiptDateBetween(start, end);
    }

    private void write(OutputStream out, String s) {
        try {
            out.write(s.getBytes(Charset.forName(csvCharset)));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write export", e);
        }
    }

    private String safe(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
