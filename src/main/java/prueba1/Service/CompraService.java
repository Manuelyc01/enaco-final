package prueba1.Service;

import net.sf.jasperreports.engine.JRException;
import org.springframework.web.servlet.ModelAndView;
import prueba1.models.Compra;
import prueba1.models.Usuario;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CompraService {

    void save(Integer id, Compra compra);

    void exportReport(Compra compra, HttpServletResponse response) throws FileNotFoundException, JRException, IOException;

    Compra getById(Integer id);

    List<Compra> list();

    List<Compra> listByIdUsuario(Integer id);

    Compra findById(Integer id);

    List<Compra> listByUni(String cod);

    List<Compra> registrosFechaCompra(String inicio, String fin, String cod) throws ParseException;

    List<Compra> listByProductCompra(String cod_tipoHoja, String cod_uniOpe);

    List<Compra> registrosFechaCompraHc(String inicio, String fin, String cod, String codHc) throws ParseException;
}
