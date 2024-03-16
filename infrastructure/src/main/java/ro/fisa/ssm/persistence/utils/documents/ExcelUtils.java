package ro.fisa.ssm.persistence.utils.documents;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.fisa.ssm.model.AppDocument;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

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
        return Optional.ofNullable(cell)
                .filter(c -> !c.getCellType().equals(CellType.BLANK) && c.getCellType().equals(CellType.STRING))
                .map(Cell::getStringCellValue)
                .map(String::trim);

    }

    public static void forEachRow(final Sheet sheet,
                                  final int startFrom,
                                  final Consumer<Row> consumer
    ) {
        final Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            final Row row = rowIterator.next();

            if ((row.getRowNum() <= startFrom)) {
                continue;
            }
            consumer.accept(row);
        }
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
