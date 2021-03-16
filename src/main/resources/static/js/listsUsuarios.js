//ID INPUTS
document.addEventListener("DOMContentLoaded", function(event) {

    const formUsuario=document.querySelector("#formUsuario")
    const listUsuarios = document.querySelector("#listUsuarios");

    var pageNumber= 1;
    var pageSize=6;

    let usuarios = $.ajax({
        type: 'GET',
        url:'/usuarios/',
        success:[function (result) {
            usuarios=result
        }]
    });
    let usuariosHtml="";//save list html

    function paginate(array,page_size,page_number) {
        return array.slice((page_number - 1) * page_size, page_number * page_size);
    }
    function paginar(lista){
        if(listUsuarios!=null){
        var pageCont = Math.ceil(lista.length/pageSize);
        var pagination=paginate(lista,pageSize,pageNumber)
        usuariosHtml="";
        pagination.forEach(usuario =>{
            usuariosHtml += `
                <tr>
                     <th scope="row">${usuario.nombre}</th>
                     <td>${usuario.telefono}</td>
                     <td>${usuario.correo}</td>
                     <td>${usuario.id_rol.descripcion}</td>
                     <td>${usuario.cod_uniOpe.nom_uniOpe}</td>
                     <td>${usuario.usua}</td>
                     <td>${usuario.id_estado.descripcion}</td>
                     <td>${usuario.serie_compra}</td>
                     <td>
                     <a class="fas fa-fw fa-edit" href="/auth/editUsuario/${usuario.id_usuario}"></a>
                     <a class="fas fa-fw fa-trash-alt" href="/auth/eliminarUsuario/${usuario.id_usuario}"></a>
                     </td>
                </tr>
                `
        });
        usuariosHtml += "";
        usuariosHtml += pageNumber >1 ? "<button id='anterior'>Anterior</button>":"";
        usuariosHtml += pageNumber < pageCont ? ("<button id='siguiente'>Siguiente</button>"):"";
        listUsuarios.innerHTML="";
        listUsuarios.innerHTML=usuariosHtml;

        if(pageNumber < pageCont){
            var a=document.querySelector("#siguiente");
            a.addEventListener("click",function next() {
                pageNumber++;
                paginar(lista);
            });
        }
        if(pageNumber>1){
            var a=document.querySelector("#anterior");
            a.addEventListener("click",function previous() {
                pageNumber--;
                paginar(lista);
            });
        }
        }
    }
    function showUsuarios() {
        $.ajax({
            type: 'GET',
            url:'/usuarios/',
            success:[function (result) {
                paginar(result)
            }]
        });
    }
    const filtrar= ()=>{
        const list=new Array();
        const text= formUsuario.value.toLowerCase();
        for (let usuario of usuarios){
            let nombre = usuario.nombre.toLowerCase();
            if(nombre.indexOf(text)!==-1){
                list.push(usuario)
            }
        }
        paginar(list);
    }
    //ACCIONES
    showUsuarios();
    if (formUsuario!=null){
        formUsuario.addEventListener('keyup',filtrar)
    }

});
