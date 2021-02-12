package prueba1.Service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import prueba1.models.Boleta;
import prueba1.models.Compra;
import prueba1.models.Usuario;
import prueba1.repository.CompraRepository;
import prueba1.repository.UsuarioRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CompraServiceImpl implements CompraService{
    @Autowired
    private final ApplicationContext applicationContext;
    @Autowired
    private final CompraRepository compraRepository;
    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final UnidadOpeService unidadOpeService;
    @Autowired
    private final RepresentanteService representanteService;

    public CompraServiceImpl(CompraRepository compraRepository, UsuarioRepository usuarioRepository, ApplicationContext applicationContext, UsuarioService usuarioService, UnidadOpeService unidadOpeService, RepresentanteService representanteService) {
        this.compraRepository = compraRepository;
        this.applicationContext = applicationContext;
        this.usuarioService = usuarioService;
        this.unidadOpeService = unidadOpeService;
        this.representanteService = representanteService;
    }

    @Override
    public void save(Integer id, Compra compra){
        //OBTENER NUM COMPRAS DE USUARIO
        Usuario u = usuarioService.findById(id);
        Integer num=u.getNum_compras();
        if (num==null){
            num=+1;
        }
        int n=num.toString().length();//TAMAÑO DEL NUMERO
        switch (n)//CANTIDAD DE DIGITOS (6)
        {
            case 1:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"00000"+num.toString());
                break;
            case 2:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"0000"+num.toString());
                break;
            case 3:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"000"+num.toString());
                break;
            case 4:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"00"+num.toString());
                break;
            case 5:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"0"+num.toString());
                break;
            case 6:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+num.toString());

        }
        //ENVIO FECHA HOY
        Date date=new Date();
        compra.setFecha(date);
        usuarioService.compra(id);
        unidadOpeService.saveCajaBoveda(compra.getCod_uniOpe().getCod_uniOpe(),compra.getTotalCompra(),2);
        compraRepository.save(compra);
    }
    @Override
    public void exportReport(Compra compra, HttpServletResponse response) throws IOException, JRException {
        Boleta b= new Boleta();
        b.setNum_liquidacion(compra.getNum_liquidacion());
        b.setDireccion(compra.getCod_uniOpe().getDireccion());//fiscal
        b.setDireccionRepre(representanteService.findByDni(compra.getDni_repre()).getDireccion());
        b.setLugarventa(compra.getCod_uniOpe().getDireccion());//direccion Repre
        b.setNombre(representanteService.findByDni(compra.getDni_repre()).getNombre());
        b.setCedula(compra.getCedula_productor().getCedula());

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(compra.getFecha());
        b.setFecha(strDate);

        b.setDni(compra.getDni_repre());
        b.setCod_tipoHoja(compra.getCod_tipoHoja().getCod_tipoHoja());
        b.setCantidad(compra.getPesoNeto());
        b.setArticulo(compra.getCod_tipoHoja().getCod_tipoHoja());
        double uni=compra.getValorCompra()/compra.getPesoBruto();
        BigDecimal bd = new BigDecimal(uni).setScale(2, RoundingMode.HALF_UP);
        double newuni=bd.doubleValue();
        b.setValoruni(newuni);
        b.setValorcompra(compra.getValorCompra());
        b.setSon(compra.getSon());
        b.setSubtotal(compra.getValorCompra());
        b.setIgv(compra.getIgv());
        b.setTotalCompra(compra.getTotalCompra());

        response.setContentType("text/html");
        List<Boleta> list=new ArrayList<>();
        list.add(b);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        InputStream jrmxmlInput=this.getClass().getResourceAsStream("/report/boleta.jrxml");
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
        response.addHeader("Content-Disposition","inline; filename=N."+compra.getNum_liquidacion()+".pdf;");

        OutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(pdfReportStream.toByteArray());
        responseOutputStream.close();
        pdfReportStream.close();
    }
    @Override
    public Compra getById(Integer id){
        return compraRepository.getOne(id);
    }
    @Override
    public List<Compra> list(){
        return compraRepository.findAll();
    }
    @Override
    public List<Compra> listByIdUsuario(Integer id){
        return compraRepository.listCompraByIdUsuario(id);
    }
}
