package edu.ntudp.fit.hnoievets.java_lab.controllers;

import edu.ntudp.fit.hnoievets.java_lab.model.SearchHistory;
import edu.ntudp.fit.hnoievets.java_lab.packages.parser.model.CarInfo;
import edu.ntudp.fit.hnoievets.java_lab.services.SearchCarService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/search/car")
@Validated
public class SearchCarController {
    @Autowired
    private SearchCarService searchCarService;

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping()
    public ResponseEntity<List<CarInfo>> getCars(
            @RequestParam @NotBlank(message = "Car brad must be pointed")
            String brand,
            @RequestParam(required = false, defaultValue = "")
            String model,
            @PastOrPresent
            Year year,
            @Positive(message = "Limit of cars must be more than 0")
            int limit
    ) {
        return ResponseEntity.ok(searchCarService.getCars(brand, model, year, limit));
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/history")
    public ResponseEntity<Iterable<SearchHistory>> getSearchCarHistory(
            @RequestParam(required = false, defaultValue = "5")
            @Positive(message = "Limit of history records must be more than 0")
            int limit
    ) {
        return ResponseEntity.ok(searchCarService.getHistory(limit));
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/{fileName}")
    public ResponseEntity<FileSystemResource> getExcelFileResource(@PathVariable String fileName) {
        FileSystemResource resource = searchCarService.getExcelFileResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .body(resource);
    }
}
