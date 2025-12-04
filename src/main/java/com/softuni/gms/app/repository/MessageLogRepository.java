package com.softuni.gms.app.repository;

import com.softuni.gms.app.model.MessageLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageLogRepository extends MongoRepository<MessageLog, String> {
}
