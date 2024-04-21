package edu.ntudp.fit.hnoievets.java_lab.packages.excelwriter;

import edu.ntudp.fit.hnoievets.java_lab.packages.parser.model.CarInfo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Component
public class ExcelWriter {
    private String defaultPath = "D:\\";
    private String defaultExtension = ".xlsx";

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public String getDefaultExtension() {
        return defaultExtension;
    }

    public void setDefaultExtension(String defaultExtension) {
        this.defaultExtension = defaultExtension;
    }

    public void writeSheetWithCars(String path, List<CarInfo> cars) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        int rowNum = 0;
        int cellNum = 0;

        XSSFRow row = sheet.createRow(rowNum++);

        for (Field field : CarInfo.class.getDeclaredFields()) {
            XSSFCell cell = row.createCell(cellNum++);
            cell.setCellValue(field.getName());
        }

        for (CarInfo car : cars) {
            row = sheet.createRow(rowNum++);
            cellNum = 0;

            XSSFCell cell = row.createCell(cellNum++);
            cell.setCellValue(car.getName());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getPrice());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getCurrency());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getGeneration());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getMileage());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getEngine());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getAkp());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getVinCode());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getCheckedVinCode());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getAfterAccident());

            cell = row.createCell(cellNum++);
            cell.setCellValue(car.getLocation());

            cell = row.createCell(cellNum);
            cell.setCellValue(car.getLink());
        }

        try {
            FileOutputStream out = new FileOutputStream(path);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
