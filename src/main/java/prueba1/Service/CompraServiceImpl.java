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
import prueba1.models.*;
import prueba1.repository.CompraRepository;
import prueba1.repository.UsuarioRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
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
    @Autowired
    private final TipoTransacService tipoTransacService;
    @Autowired
    private final CajaBovedaService cajaBovedaService;
    @Autowired
    private final MovimientoService movimientoService;
    @Autowired
    private final InventarioService inventarioService;

    public CompraServiceImpl(CompraRepository compraRepository, UsuarioRepository usuarioRepository, ApplicationContext applicationContext, UsuarioService usuarioService, UnidadOpeService unidadOpeService, RepresentanteService representanteService, TipoTransacService tipoTransacService, CajaBovedaService cajaBovedaService, MovimientoService movimientoService, InventarioService inventarioService) {
        this.compraRepository = compraRepository;
        this.applicationContext = applicationContext;
        this.usuarioService = usuarioService;
        this.unidadOpeService = unidadOpeService;
        this.representanteService = representanteService;
        this.tipoTransacService = tipoTransacService;
        this.cajaBovedaService = cajaBovedaService;
        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
    }

    @Override
    public void save(Integer id, Compra compra) {
        //OBTENER NUM COMPRAS DE USUARIO
        Usuario u = usuarioService.findById(id);
        Integer num;
        if (u.getNum_compras()!=null){
            num=u.getNum_compras()+1;
        }else {
            num=1;
        }
        int n = num.toString().length();//TAMA??O DEL NUMERO
        switch (n)//CANTIDAD DE DIGITOS (6)
        {
            case 1:
                compra.setNum_liquidacion(u.getSerie_compra() + "-" + "00000" + num.toString());
                break;
            case 2:
                compra.setNum_liquidacion(u.getSerie_compra() + "-" + "0000" + num.toString());
                break;
            case 3:
                compra.setNum_liquidacion(u.getSerie_compra() + "-" + "000" + num.toString());
                break;
            case 4:
                compra.setNum_liquidacion(u.getSerie_compra() + "-" + "00" + num.toString());
                break;
            case 5:
                compra.setNum_liquidacion(u.getSerie_compra() + "-" + "0" + num.toString());
                break;
            case 6:
                compra.setNum_liquidacion(u.getSerie_compra() + "-" + num.toString());

        }
        //ENVIO FECHA HOY
        Date date = new Date();
        Representante representante = representanteService.findByDni(compra.getDni_repre());

        compra.setId_repre(representante);
        compra.setFecha(date);

        usuarioService.compra(id);
        unidadOpeService.saveCajaBoveda(compra.getCod_uniOpe().getCod_uniOpe(), compra.getTotalCompra(), 2);


        //REGISTRO CAJA BOVEDA
        CajaBoveda cajaBoveda=new CajaBoveda();
        cajaBoveda.setId_usuario(compra.getId_usuario());
        cajaBoveda.setCod_uniOpe(compra.getCod_uniOpe());
        TipoTransaccion tipoT = tipoTransacService.getById(3);
        cajaBoveda.setId_tipoTransac(tipoT);
        cajaBoveda.setFecha(compra.getFecha());
        cajaBoveda.setMonto(compra.getTotalCompra());


        //REGISTRO INVENTARIO
        Inventario inventario=new Inventario();
        inventario.setId_usuario(compra.getId_usuario());
        inventario.setCod_almacen(compra.getCod_uniOpe());
        Movimiento mv = movimientoService.findById(1);
        inventario.setId_movimiento(mv);
        inventario.setDocumento(compra.getNum_liquidacion());
        inventario.setCod_tipoHoja(compra.getCod_tipoHoja());
        inventario.setPesoNeto(compra.getPesoNeto());
        //STOCK INICIAL--STOCK FINAL
        List<Inventario> inventarios = inventarioService.listByProductAlmacen(compra.getCod_tipoHoja().getCod_tipoHoja(), compra.getCod_uniOpe().getCod_uniOpe());
        if (inventarios.size()==0){
            inventario.setStockInicial(0.00);
            inventario.setStockFinal(+compra.getPesoNeto());
        }else {
            List<Inventario> inv1 = inventarioService.listByProductAlmacenOne(compra.getCod_tipoHoja(), compra.getCod_uniOpe());
            Inventario inventario1 = inv1.get(0);
            inventario.setStockInicial(inventario1.getStockFinal());
            inventario.setStockFinal(inventario1.getStockFinal()+compra.getPesoNeto());
        }
        compraRepository.save(compra);
        inventarioService.save(inventario);
        cajaBovedaService.save(cajaBoveda);
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
        return compraRepository.list();
    }
    @Override
    public List<Compra> listByIdUsuario(Integer id){
        return compraRepository.listCompraByIdUsuario(id);
    }
    @Override
    public Compra findById(Integer id){
        return  compraRepository.getOne(id);
    }


    @Override
    public List<Compra> listByUni(String cod){
        return compraRepository.lisByUni(cod);
    }
    @Override
    public List<Compra> registrosFechaCompra(String inicio, String fin, String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return compraRepository.filterFechaCompraHc(ini,fn,cod);
    }
    @Override
    public List<Compra> listByProductCompra(String cod_tipoHoja, String cod_uniOpe){
        return compraRepository.listByProductCompra(cod_tipoHoja,cod_uniOpe);
    }
    @Override
    public List<Compra> registrosFechaCompraHc(String inicio, String fin, String cod, String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return compraRepository.filterFechaCompraHc(ini,fn,cod,codHc);
    }
}
