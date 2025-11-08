package com.softuni.gms.app.repository;

import com.softuni.gms.app.model.InvoiceLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface InvoiceLogRepository extends MongoRepository<InvoiceLog, String> {

    List<InvoiceLog> findAllByRepairId(UUID repairId);

    Optional<InvoiceLog> findFirstByRepairIdOrderByGeneratedAtDesc(UUID repairId);
}
