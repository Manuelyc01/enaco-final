//ID INPUTS
document.addEventListener("DOMContentLoaded", function(event) {
    const formUsuario = document.querySelector('#formUsuario');
    const formProductor = document.querySelector('#formProductor');
    const formCosto = document.querySelector('#formCosto');

    const resultado =document.querySelector('#listUsuarios')
    var listUsuarios = listUsu;
    const filtrar = () =>{
        resultado.innerHTML='';
        const texto = formUsuario.value.toLowerCase();
        for(let usuario of listUsuarios){
            let nombre = usuario.nombre.toLowerCase();
            if(nombre.indexOf(texto) !== -1){
                resultado.innerHTML += `
                            <th scope="row">${usuario.nombre}</th>
                            <td>${usuario.telefono}</td>
                            <td>${usuario.correo}</td>
                            <td>${usuario.id_rol.descripcion}</td>
                            <td></td>
                            <td>${usuario.usua}</td>
                            <td>${usuario.id_estado.descripcion}</td>
                            <td>
                                <a class="btn btn-info" href="/auth/editUsuario/${usuario.id_usuario}">Editar</a>
                                <a class="btn btn-danger" href="/auth/eliminarUsuario/${usuario.id_usuario}">Eliminar</a>
                            </td>
                        `
            }
        }
        if(resultado.innerHTML === ''){
            resultado.innerHTML+= `
                            <tr>Usuario no encontrado...</tr>
                        `
        }
    }
    formUsuario.addEventListener('keyup',filtrar)
});

