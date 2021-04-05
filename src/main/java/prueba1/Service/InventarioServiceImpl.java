package prueba1.Service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.models.*;
import prueba1.repository.InventarioRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService{

    @Autowired
    private final InventarioRepository repository;

    public InventarioServiceImpl(InventarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Inventario inventario){
        repository.save(inventario);
    }

    @Override
    public List<Inventario> list(){
        return repository.list();
    }

    @Override
    public List<Inventario> listByProductAlmacen(String cod_tipoHoja, String cod_uniOpe){
        return repository.listByProductAlmacen(cod_tipoHoja,cod_uniOpe);
    }
    @Override
    public List<Inventario> registrosFechaAlmacen(String inicio, String fin,String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaAlmacen(ini,fn,cod);
    }
    @Override
    public List<Inventario> registrosFechaAlmacenHc(String inicio, String fin,String cod,String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaAlmacenHc(ini,fn,cod,codHc);
    }
    @Override
    public List<Inventario> listByProductAlmacenOne(TipoHojaCoca cod_tipoHoja, UnidadOperativa cod_uniOpe){
        return repository.listByProductAlmacenOne(cod_tipoHoja,cod_uniOpe,PageRequest.of(0,1));
    }
    @Override
    public List<Inventario> stockHcAlmacen(String cod_tipoHoja, String cod_uniOpe){
        return repository.stockHcAlmacen(cod_tipoHoja,cod_uniOpe,PageRequest.of(0,1));
    }
    @Override
    public List<Inventario> listByUni(String cod_uniOpe){
        return repository.listByUni(cod_uniOpe);
    }
    //ACTA DE INVERTARIO
    @Override
    public List<TipoHojaCoca> actaHojas(String inicio, String fin, String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
       /* return repository.actaHojas(ini,fn,cod);*/
        return repository.actaHojas(cod);
    }
    @Override
    public Double actaSaldo(String inicio, String fin, String cod, String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        List<Inventario> in = repository.actaSaldo(ini, fn, cod, codHc, PageRequest.of(0, 1));
        if (in.size()==0){
            return 0.00;
        }else {
            return in.get(0).getStockInicial();
        }
    }
    @Override
    public List<Ingreso> actaIngreso(String inicio, String fin, String cod, String codHc) throws ParseException {
        List<Ingreso> ingresos=new ArrayList<>();
        for (int i=1;i<5;i++){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date ini= format.parse(inicio.replace("T"," "));
            Date fn= format.parse(fin.replace("T"," "));
            List<Double> d = repository.actaIngreso(ini, fn, cod, codHc,i, PageRequest.of(0, 1));
            Ingreso ingreso=new Ingreso();
            ingreso.setId(i);
            if (d.get(0)==null){
               ingreso.setMonto(0.00);
            }else {
                ingreso.setMonto(d.get(0));
            }
            ingresos.add(ingreso);
        }
        return ingresos;
    }
    @Override
    public List<IngresoSalida> actaIngresoSalida(String inicio, String fin, String cod, String codHc) throws ParseException {
        List<IngresoSalida> ingresos=new ArrayList<>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));

        List<Double> i = repository.actaIngresoTransferencia(ini, fn, cod, codHc, PageRequest.of(0, 1));
        List<Double> s = repository.actaSalidaTransferencia(ini, fn, cod, codHc, PageRequest.of(0, 1));

        IngresoSalida ingreso=new IngresoSalida();
        ingreso.setId(1);
        if (i.get(0)==null){
            ingreso.setMonto(0.00);
        }else {
            ingreso.setMonto(i.get(0));
        }
        IngresoSalida ingreso2=new IngresoSalida();
        ingreso2.setId(2);
        if (s.get(0)==null){
            ingreso2.setMonto(0.00);
        }else {
            ingreso2.setMonto(s.get(0));
        }
        ingresos.add(ingreso);
        ingresos.add(ingreso2);
        return ingresos;
    }
    @Override
    public void exportReport(List<ActaRegistro> actaRegistros, HttpServletResponse response) throws IOException, JRException {

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource( actaRegistros);

        InputStream jrmxmlInput=this.getClass().getResourceAsStream("/report/pruebaActa.jrxml");
        JasperDesign design= JRXmlLoader.load(jrmxmlInput);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        JRPdfExporter pdfExporter=new JRPdfExporter();
        pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
        pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
        pdfExporter.exportReport();

        response.setContentType("application/pdf");
        response.setHeader("Content-Length",String.valueOf(pdfReportStream.size()));
        response.addHeader("Content-Disposition","inline; filename=ACTA");

        OutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(pdfReportStream.toByteArray());
        responseOutputStream.close();
        pdfReportStream.close();
    }

}
