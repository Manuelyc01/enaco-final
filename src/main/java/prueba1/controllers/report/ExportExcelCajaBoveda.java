package prueba1.controllers.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prueba1.models.CajaBoveda;
import prueba1.models.Demasia;
import prueba1.models.Reporte;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportExcelCajaBoveda {
    public static ByteArrayInputStream listToExcelFile(List<CajaBoveda> cajaBovedas, Reporte reporte) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("CAJA BOVEDA");
            if (cajaBovedas.size()!=0){
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
                celda.setCellValue("REPORTE CAJA BOVEDA");
                celda.setCellStyle(header);
                //FECHA
                if(reporte.getFcInicio()!=""&reporte.getFcFin()!=""){
                    celda=head2.createCell(0);
                    celda.setCellValue("Del "+ reporte.getFcInicio().substring(0,10)+" al "+reporte.getFcFin().substring(0,10));
                    celda.setCellStyle(heads2);
                }
                if (reporte.getCodUni()!=null){
                    celda=head2.createCell(3);
                    celda.setCellValue("Unidad Operativa: "+cajaBovedas.get(0).getCod_uniOpe().getNom_uniOpe());
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
                cell.setCellValue("Uni. Operativa");
                cell.setCellStyle(headerCellStyle);

                cell = row.createCell(2);
                cell.setCellValue("Usuario");
                cell.setCellStyle(headerCellStyle);

                cell = row.createCell(3);
                cell.setCellValue("Tipo Transacción");
                cell.setCellStyle(headerCellStyle);

                cell = row.createCell(4);
                cell.setCellValue("Monto Soles");
                cell.setCellStyle(headerCellStyle);

                cell = row.createCell(5);
                cell.setCellValue("Saldo Inicial ( S/ )");
                cell.setCellStyle(headerCellStyle);

                cell = row.createCell(6);
                cell.setCellValue("Saldo Final ( S/ )");
                cell.setCellStyle(headerCellStyle);

                for(int i = 0; i < cajaBovedas  .size(); i++) {
                    Row dataRow = sheet.createRow(i + 3);

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    dataRow.createCell(0).setCellValue(formatter.format(cajaBovedas.get(i).getFecha()));
                    dataRow.createCell(1).setCellValue(cajaBovedas.get(i).getCod_uniOpe().getNom_uniOpe());
                    dataRow.createCell(2).setCellValue(cajaBovedas.get(i).getId_usuario().getNombre());
                    dataRow.createCell(3).setCellValue(cajaBovedas.get(i).getId_tipoTransac().getNombre());
                    dataRow.createCell(4).setCellValue(cajaBovedas.get(i).getMonto());
                    dataRow.createCell(5).setCellValue(cajaBovedas.get(i).getSaldoInicial());
                    dataRow.createCell(6).setCellValue(cajaBovedas.get(i).getSaldoFinal());
                }

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);

            }else {
                Row head = sheet.createRow(0);
                CellStyle header = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setFontHeightInPoints((short) 16);
                titleFont.setFontName("Arial");
                header.setFont(titleFont);

                Cell celda=head.createCell(0);
                celda.setCellValue("SIN REGISTROS");
                celda.setCellStyle(header);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
