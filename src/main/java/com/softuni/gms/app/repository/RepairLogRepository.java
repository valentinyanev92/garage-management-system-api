package com.softuni.gms.app.repository;

import com.softuni.gms.app.model.RepairLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairLogRepository extends MongoRepository<RepairLog, String> {
}

