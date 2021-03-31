package prueba1.controllers;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import prueba1.Service.UnidadOpeService;
import prueba1.models.Inventario;
import prueba1.models.Reporte;
import prueba1.models.UnidadOperativa;

public class ExportExcelKardex {

    public static ByteArrayInputStream listToExcelFile(List<Inventario> inventario, Reporte reporte) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Kardex");

            Row head = sheet.createRow(0);
            CellStyle header = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 16);
            titleFont.setFontName("Arial");
            header.setFont(titleFont);

            Row head2 = sheet.createRow(1);
            CellStyle heads2 = workbook.createCellStyle();
            Font titleFont2 = workbook.createFont();
            titleFont2.setFontHeightInPoints((short) 12);
            titleFont2.setFontName("Arial Narrow");
            heads2.setFont(titleFont2);

            Cell celda=head.createCell(0);
            celda.setCellValue("REPORTE KARDEX");
            celda.setCellStyle(header);
            //FECHA
            if(reporte.getFcInicio()!=""&reporte.getFcFin()!=""){
                celda=head2.createCell(0);
                celda.setCellValue("Del "+ reporte.getFcInicio().substring(0,10)+" al "+reporte.getFcFin().substring(0,10));
                celda.setCellStyle(heads2);
            }
            if (reporte.getCodUni()!=null){
                celda=head2.createCell(3);
                celda.setCellValue("Unidad Operativa: "+inventario.get(0).getCod_almacen().getNom_uniOpe());
                celda.setCellStyle(heads2);
            }

            Row row = sheet.createRow(2);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell cell = row.createCell(0);
            cell.setCellValue("Fecha-Hora");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Usuario");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Almacén");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Movimiento");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Documento");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Tipo de HC");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue("Peso Neto");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(7);
            cell.setCellValue("Stock Inicial");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(8);
            cell.setCellValue("Stock final");
            cell.setCellStyle(headerCellStyle);

            for(int i = 0; i < inventario.size(); i++) {
                Row dataRow = sheet.createRow(i + 3);
                dataRow.createCell(0).setCellValue(inventario.get(i).getFecha());
                dataRow.createCell(1).setCellValue(inventario.get(i).getId_usuario().getNombre());
                dataRow.createCell(2).setCellValue(inventario.get(i).getCod_almacen().getNom_uniOpe());
                dataRow.createCell(3).setCellValue(inventario.get(i).getId_movimiento().getNombre());
                dataRow.createCell(4).setCellValue(inventario.get(i).getDocumento());
                dataRow.createCell(5).setCellValue(inventario.get(i).getCod_tipoHoja().getNombre());
                dataRow.createCell(6).setCellValue(inventario.get(i).getPesoNeto());
                dataRow.createCell(7).setCellValue(inventario.get(i).getStockInicial());
                dataRow.createCell(8).setCellValue(inventario.get(i).getStockFinal());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
