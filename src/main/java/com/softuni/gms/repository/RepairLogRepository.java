package com.softuni.gms.repository;

import com.softuni.gms.model.RepairLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairLogRepository extends MongoRepository<RepairLog, String> {
}
