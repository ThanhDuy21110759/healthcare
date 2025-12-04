package com.clinic.pharmacy.repository;

import com.clinic.pharmacy.entity.WarehouseReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WarehouseReceiptRepository extends JpaRepository<WarehouseReceipt, Long> {

    List<WarehouseReceipt> findByReceiptDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT r FROM WarehouseReceipt r WHERE r.autoCode = :code")
    List<WarehouseReceipt> findByAutoCode(@Param("code") String code);
}
