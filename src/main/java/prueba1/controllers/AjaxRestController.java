package prueba1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import prueba1.Service.ProductorService;
import prueba1.Service.RepresentanteService;
import prueba1.Service.UnidadOpeService;
import prueba1.Service.UsuarioService;
import prueba1.models.Productor;
import prueba1.models.Representante;
import prueba1.models.UnidadOperativa;
import prueba1.models.Usuario;

import java.util.List;

@RestController
@RequestMapping(value = {"/private","" })
public class AjaxRestController {
    @Autowired
    private final ProductorService productorService;
    @Autowired
    private final RepresentanteService representanteService;
    @Autowired
    private final UnidadOpeService unidadOpeService;
    @Autowired
    private final UsuarioService usuarioService;

    public AjaxRestController(ProductorService productorService, RepresentanteService representanteService, UnidadOpeService unidadOpeService, UsuarioService usuarioService) {
        this.productorService = productorService;
        this.representanteService = representanteService;
        this.unidadOpeService = unidadOpeService;
        this.usuarioService = usuarioService;
    }
    //LISTS
    @RequestMapping(
            value = "unidadesOpe",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UnidadOperativa>> listUni(){
        List<UnidadOperativa> unidadOperativas = unidadOpeService.listar();
        try {
            return ResponseEntity.accepted().body(unidadOperativas);
        }catch (Exception e){
            return new ResponseEntity<List<UnidadOperativa>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "productores",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Productor>> listProduc(){
        List<Productor> productors = productorService.list();
        try {
            return ResponseEntity.accepted().body(productors);
        }catch (Exception e){
            return new ResponseEntity<List<Productor>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "usuarios",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Usuario>> listUsu(){
        List<Usuario> usuarios = usuarioService.listar();
        try {
            return ResponseEntity.accepted().body(usuarios);
        }catch (Exception e){
            return new ResponseEntity<List<Usuario>>(HttpStatus.BAD_REQUEST);
        }
    }

    //BUSCAR PRODUCTOR
    @RequestMapping(
            value = "buscarP/{id}",
            method = RequestMethod.GET,
            produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> buscarProd(@PathVariable("id")String id){
        Productor productor = productorService.findByCedula(id);
        try {
            ResponseEntity<String> responseEntity=
                new ResponseEntity<String>(productor.getNombre(), HttpStatus.OK);
            return responseEntity;

        }catch (Exception e){
            return new ResponseEntity<String>("Productor no registrado", HttpStatus.OK);
        }
    }
    //BUSCAR REPRESENTANTE
    @RequestMapping(
            value = "buscarR/{dni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Representante> buscarRepre(@PathVariable("dni")String dni){
        Representante representante = representanteService.findByDni(dni);
        try {
            return ResponseEntity.accepted().body(representante);
        }catch (Exception e){
            return new ResponseEntity<Representante>(HttpStatus.BAD_REQUEST);
        }
    }

}
