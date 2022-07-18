package com.example.stockmarkets.service;

import com.example.stockmarkets.document.DowJonesIndex;
import com.example.stockmarkets.repository.DowJonesIndexRepository;
import com.example.stockmarkets.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class CsvFileService {
    private DowJonesIndexRepository repository;

    @Autowired
    public CsvFileService(DowJonesIndexRepository repository) {
        this.repository = repository;
    }

    public void store(InputStream in) {
        try {
            // Using ApacheCommons Csv Utils to parse CSV file
            List<DowJonesIndex> lstIndexes = CsvUtil.parseCsvFile(in);

            // Save customers to database
            repository.saveAll(lstIndexes);
        } catch(Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage(), e);
        }
    }
}
