package prueba1.controllers.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import prueba1.models.Demasia;
import prueba1.models.Merma;
import prueba1.models.Reporte;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExportExcelMerma {
    public static ByteArrayInputStream listToExcelFile(List<Merma> mermas, Reporte reporte) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("DEMASIAS");
            if (mermas.size()!=0){
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
                celda.setCellValue("REPORTE MERMA");
                celda.setCellStyle(header);
                //FECHA
                if(reporte.getFcInicio()!=""&reporte.getFcFin()!=""){
                    celda=head2.createCell(0);
                    celda.setCellValue("Del "+ reporte.getFcInicio().substring(0,10)+" al "+reporte.getFcFin().substring(0,10));
                    celda.setCellStyle(heads2);
                }
                if (reporte.getCodUni()!=null){
                    celda=head2.createCell(3);
                    celda.setCellValue("Unidad Operativa: "+mermas.get(0).getCod_uniOpe().getNom_uniOpe());
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
                cell.setCellValue("Tipo de HC");
                cell.setCellStyle(headerCellStyle);

                cell = row.createCell(4);
                cell.setCellValue("Cantidad (KG)");
                cell.setCellStyle(headerCellStyle);

                for(int i = 0; i < mermas.size(); i++) {
                    Row dataRow = sheet.createRow(i + 3);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    dataRow.createCell(0).setCellValue(formatter.format(mermas.get(i).getFecha()));
                    dataRow.createCell(1).setCellValue(mermas.get(i).getCod_uniOpe().getNom_uniOpe());
                    dataRow.createCell(2).setCellValue(mermas.get(i).getId_usuario().getNombre());
                    dataRow.createCell(3).setCellValue(mermas.get(i).getCod_tipoHoja().getNombre());
                    dataRow.createCell(4).setCellValue(mermas.get(i).getCantidadNeta());
                }

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);

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
