package com.example.stockmarkets.service;

import com.example.stockmarkets.document.DowJonesIndex;
import com.example.stockmarkets.repository.DowJonesIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DowJonesIndexService {
    private DowJonesIndexRepository repository;

    @Autowired
    public DowJonesIndexService(DowJonesIndexRepository repository) {
        this.repository = repository;
    }

    public DowJonesIndex saveDowJonesIndex(DowJonesIndex dowJonesIndex){
        return repository.save(dowJonesIndex);
    }

    public List<DowJonesIndex> findDowJonesIndexByStock(String stock){
        return repository.findByStock(stock);
    }

    public Optional<DowJonesIndex> deleteDowJonesIndex(String id) {
        Optional<DowJonesIndex> result = repository.findById(id);
        repository.deleteById(id);
        return result;
    }

}
