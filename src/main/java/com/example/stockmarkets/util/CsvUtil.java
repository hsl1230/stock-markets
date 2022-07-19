package com.example.stockmarkets.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.stockmarkets.document.DowJonesIndex;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvUtil {
    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    private static DowJonesIndex createDowJonesIndexFrom(CSVRecord csvRecord) {
        DowJonesIndex data = new DowJonesIndex();
        data.setQuarter(Integer.parseInt(csvRecord.get("quarter")));
        data.setStock(csvRecord.get("stock"));

        String pattern = "M/d/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        try {
            data.setDate(LocalDate.parse(csvRecord.get("Date"), formatter));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        data.setOpen(getCurrencyOf(csvRecord.get("open")));
        data.setHigh(getCurrencyOf(csvRecord.get("high")));
        data.setLow(getCurrencyOf(csvRecord.get("low")));
        data.setClose(getCurrencyOf(csvRecord.get("close")));
        data.setVolume(Long.parseLong(csvRecord.get("volume")));
        data.setPercentChangePrice(getDoubleValueOf(csvRecord.get("percent_change_price")));
        data.setPercentChangeVolumeOverLastWeek(getDoubleValueOf(csvRecord.get("percent_change_volume_over_last_wk")));
        data.setPreviousWeeksVolume(getLongValueOf(csvRecord.get("previous_weeks_volume")));
        data.setNextWeeksOpen(getCurrencyOf(csvRecord.get("next_weeks_open")));
        data.setNextWeeksClose(getCurrencyOf(csvRecord.get("next_weeks_close")));
        data.setPercentChangeNextWeeksPrice(getDoubleValueOf(csvRecord.get("percent_change_next_weeks_price")));
        data.setDaysToNextDividend(getIntValueOf(csvRecord.get("days_to_next_dividend")));
        data.setPercentReturnNextDividend(getDoubleValueOf(csvRecord.get("percent_return_next_dividend")));

        return data;
    }

    private static Double getDoubleValueOf(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        } else {
            return Double.parseDouble(value);
        }
    }

    private static Double getCurrencyOf(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        } else {
            return getDoubleValueOf(value.replaceAll("[^\\d.]+", ""));
        }
    }

    private static Long getLongValueOf(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        } else {
            return Long.parseLong(value);
        }
    }

    private static Integer getIntValueOf(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static List<DowJonesIndex> parseCsvFile(InputStream is) {
        BufferedReader fileReader = null;
        CSVParser csvParser = null;

        List<DowJonesIndex> dowJonesIndexes = new ArrayList<>();

        try {
            fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.builder()
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .setIgnoreHeaderCase(true)
                            .setTrim(true)
                            .build());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                DowJonesIndex dowJonesIndex = createDowJonesIndexFrom(csvRecord);
                dowJonesIndexes.add(dowJonesIndex);
            }

            return dowJonesIndexes;
        } catch (Exception e) {
            logger.error("Reading CSV Error!", e);
            throw new RuntimeException("Reading CSV Error!", e);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (csvParser != null) {
                    csvParser.close();
                }
            } catch (IOException e) {
                logger.error("Closing fileReader/csvParser Error!", e);
            }
        }
    }
}
