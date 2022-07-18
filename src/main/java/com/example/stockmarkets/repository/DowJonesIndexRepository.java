package com.example.stockmarkets.repository;

import com.example.stockmarkets.document.DowJonesIndex;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DowJonesIndexRepository extends MongoRepository<DowJonesIndex, String> {
    List<DowJonesIndex> findByStock(String stock);
}
