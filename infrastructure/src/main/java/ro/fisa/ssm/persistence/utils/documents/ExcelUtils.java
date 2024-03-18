package ro.fisa.ssm.persistence.utils.documents;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.utils.OptionalUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static ro.fisa.ssm.utils.DocumentUtils.DocumentExtension.DOT_XLSX;

/**
 * Created at 3/16/2024 by Darius
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelUtils {

    public static Optional<String> getCellStringValue(final Row row, final int cellIndex) {
        final Cell cell = row.getCell(cellIndex);
        return getCellStringValue(cell);
    }

    public static Optional<String> getCellStringValue(final Cell cell) {
        return OptionalUtils.getOptional(cell)
                .filter(c -> !c.getCellType().equals(CellType.BLANK) && c.getCellType().equals(CellType.STRING))
                .map(Cell::getStringCellValue)
                .map(String::trim);

    }

    public static Optional<Double> getCellDoubleValue(final Row row, final int cellIndex) {
        final Cell cell = row.getCell(cellIndex);
        return OptionalUtils.getOptional(cell)
                .filter(c -> !c.getCellType().equals(CellType.BLANK) && c.getCellType().equals(CellType.NUMERIC))
                .map(Cell::getNumericCellValue);
    }

    public static boolean isRowEmpty(final Row row) {
        if (row == null) {
            return true;
        }
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }

        return true;
    }

    public static Workbook getWorkbookFromDocument(final AppDocument document) throws IOException {
        final String lowercaseFileName = document.getOriginalFileName().toLowerCase();
        final InputStream inputStream = document.getInputStream();

        return lowercaseFileName.endsWith(DOT_XLSX) ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
    }

    public static Sheet getSheetFromDocument(final AppDocument document, int sheetNumber) throws IOException {
        final Workbook wb = getWorkbookFromDocument(document);
        return wb.getSheetAt(sheetNumber);
    }
}
