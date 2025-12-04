package com.softuni.gms.repository;

import com.softuni.gms.model.MessageLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageLogRepository extends MongoRepository<MessageLog, String> {
}
