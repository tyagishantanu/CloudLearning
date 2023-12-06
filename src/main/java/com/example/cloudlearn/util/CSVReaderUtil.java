package com.example.cloudlearn.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVReaderUtil {
	
	private static final Logger logger = LogManager.getLogger(CSVReaderUtil.class);
	
	public static List<Map<String, String>> readCSV(String filePath) throws IOException {
    	List<Map<String, String>> dataList = new ArrayList<>();
    	try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> csvData = csvReader.readAll();

            if (csvData.size() < 2) {
                throw new IllegalArgumentException("CSV file should have at least two rows (headers and data).");
            }

            String[] headers = csvData.get(0);

            for (int i = 1; i < csvData.size(); i++) {
                String[] row = csvData.get(i);
                Map<String, String> rowData = new HashMap<>();

                for (int j = 0; j < headers.length; j++) {
                    if (j < row.length) {
                        rowData.put(headers[j], row[j]);
                    } else {
                        rowData.put(headers[j], ""); // Handle missing columns
                    }
                    dataList.add(rowData);
                }
        
            }
    	} catch (IOException | CsvException e) {
    		logger.error("Error occurred: ", e);
			e.printStackTrace();
		}
    	return dataList;
	}
}
