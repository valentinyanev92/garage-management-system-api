package com.softuni.gms.app.repository;

import com.softuni.gms.app.model.InvoiceLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceLogRepository extends MongoRepository<InvoiceLog, UUID> {

    List<InvoiceLog> findAllByRepairId(UUID repairId);
}
