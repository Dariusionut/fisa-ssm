package ro.fisa.ssm.persistence.utils.documents;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import ro.fisa.ssm.utils.OptionalUtils;

import java.util.Optional;

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

}
