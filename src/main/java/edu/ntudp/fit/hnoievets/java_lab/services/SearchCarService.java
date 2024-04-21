package edu.ntudp.fit.hnoievets.java_lab.services;

import edu.ntudp.fit.hnoievets.java_lab.model.SearchHistory;
import edu.ntudp.fit.hnoievets.java_lab.packages.excelwriter.ExcelWriter;
import edu.ntudp.fit.hnoievets.java_lab.packages.parser.model.CarInfo;
import edu.ntudp.fit.hnoievets.java_lab.packages.parser.AutoriaParser;
import edu.ntudp.fit.hnoievets.java_lab.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class SearchCarService {

    @Autowired
    private AutoriaParser autoriaParser;

    @Autowired
    private ExcelWriter excelWriter;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public List<CarInfo> getCars(String brand, String model, Year year, int limit) {
        String strYear = year != null ? year.toString() : "";

        List<CarInfo> cars = autoriaParser.parseByQuery(brand, model, strYear, limit);

        this.createHistoryRecord(brand, model, strYear, limit);

        if (!cars.isEmpty()) {
            this.writeSheetWithCars(cars, brand, model, strYear, limit);
        }

        return cars;
    }

    public Iterable<SearchHistory> getHistory(int limit) {
        return searchHistoryRepository.findLimitedLastSearchHistory(Limit.of(limit));
    }

    public FileSystemResource getExcelFileResource(String fileName) {
        return new FileSystemResource(excelWriter.getDefaultPath() + fileName);
    }

    private void createHistoryRecord(String brand, String model, String year, int limit) {
        char QUERY_SEPARATOR = '-';

        searchHistoryRepository.save(new SearchHistory(brand + QUERY_SEPARATOR +
                model + QUERY_SEPARATOR +
                year + QUERY_SEPARATOR +
                limit
        ));
    }

    private void writeSheetWithCars(List<CarInfo> cars, String brand, String model, String strYear, int limit) {
        char NAME_SEPARATOR = '_';

        excelWriter.writeSheetWithCars(excelWriter.getDefaultPath() +
                brand + NAME_SEPARATOR +
                (model.isEmpty() ? "" : model + NAME_SEPARATOR) +
                (strYear.isEmpty() ? "" : strYear + NAME_SEPARATOR) +
                limit +
                excelWriter.getDefaultExtension(), cars
        );
    }

}
