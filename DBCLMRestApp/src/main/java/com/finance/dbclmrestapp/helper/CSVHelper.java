package com.finance.dbclmrestapp.helper;

import com.finance.dbclmrestapp.model.Nace;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHelper {
    public static final String TYPE = "text/csv";
    public static final String[] HEADERs = {"Order","Level","Code","Parent","Description","This item includes","This item also includes","Rulings","This item excludes","Reference to ISIC Rev. 4"};
    public boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<Nace> csvToNaceList(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Nace> naceList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Nace nace = new Nace(
                        csvRecord.get(HEADERs[0]),
                        Integer.parseInt(csvRecord.get(HEADERs[1])),
                        csvRecord.get(HEADERs[2]),
                        csvRecord.get(HEADERs[3]),
                        csvRecord.get(HEADERs[4]),
                        csvRecord.get(HEADERs[5]),
                        csvRecord.get(HEADERs[6]),
                        csvRecord.get(HEADERs[7]),
                        csvRecord.get(HEADERs[8]),
                        csvRecord.get(HEADERs[9])
                );
                naceList.add(nace);
            }
            return naceList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}