package prueba1.Service;

import net.sf.jasperreports.engine.JRException;
import prueba1.models.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface InventarioService {
    void save(Inventario inventario);

    List<Inventario> list();

    List<Inventario> listByProductAlmacen(String cod_tipoHoja, String cod_uniOpe);

    List<Inventario> registrosFechaAlmacen(String inicio, String fin, String cod) throws ParseException;

    List<Inventario> registrosFechaAlmacenHc(String inicio, String fin, String cod, String codHc) throws ParseException;

    List<Inventario> listByProductAlmacenOne(TipoHojaCoca cod_tipoHoja, UnidadOperativa cod_uniOpe);

    List<Inventario> stockHcAlmacen(String cod_tipoHoja, String cod_uniOpe);

    List<Inventario> listByUni(String cod_uniOpe);

    //ACTA DE INVERTARIO
    List<TipoHojaCoca> actaHojas(String inicio, String fin, String cod) throws ParseException;

    Double actaSaldo(String inicio, String fin, String cod, String codHc) throws ParseException;

    List<Ingreso> actaIngreso(String inicio, String fin, String cod, String codHc) throws ParseException;

    List<IngresoSalida> actaIngresoSalida(String inicio, String fin, String cod, String codHc) throws ParseException;

    void exportReport(List<ActaRegistro> actaRegistros, HttpServletResponse response) throws IOException, JRException;
}
