package com.clinic.pharmacy.service;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

public interface ReceiptExportService {

    void exportReceiptsToCsv(LocalDate start, LocalDate end, OutputStream out);

    List<WarehouseReceipt> getReceipts(LocalDate start, LocalDate end);
}
