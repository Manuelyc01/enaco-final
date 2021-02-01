//ID INPUTS
document.addEventListener("DOMContentLoaded", function(event) {
    //INPUT BUSQUEDA
    const formUnidadOpe = document.querySelector('#formUnidadOpe');

    //RESULTADOS
    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');
    const direccionUni=document.querySelector('#direccionUni');
    //LIST UNI CONTROLLER
    let listUnidad=$.ajax({
        type: 'GET',
        url:'/unidadesOpe/',
        success:[function (result) {
            listUnidad=result;
        }]
    });

    //FILTRAR USUARIOS
    const filtrar = () =>{
        selectUnidadOpe.innerHTML='';
        const texto = formUnidadOpe.value.toLowerCase();
        for(let unidadOpe of listUnidad){
            let nombre = unidadOpe.nom_uniOpe.toLowerCase();
            if(nombre.indexOf(texto) !== -1){
                selectUnidadOpe.innerHTML += `
                            <option value="${unidadOpe.cod_uniOpe}">
                                        <span>${unidadOpe.cod_uniOpe}</span>---<span>${unidadOpe .nom_uniOpe}</span>
                             </option>
                        `
            }
        }
        if(selectUnidadOpe.innerHTML === ''){
            selectUnidadOpe.innerHTML+= `
                            <option value="0">Unidad no encontrada</option>
                        `
        }
    }
    //DIRECCION UNIDAD
    function d() {
        let value = selectUnidadOpe.value;
        for (let unidadOpe of listUnidad){
            let cod = unidadOpe.cod_uniOpe.toLowerCase();
            if(value==cod){
                if(direccionUni!=null){
                    direccionUni.innerHTML=`
                         <input  type="search" placeholder="${unidadOpe.direccion}" class="form-control"disabled/>
                    `
                }
            }
        }
    }
    formUnidadOpe.addEventListener('keyup',filtrar)
    //INPUT BUSQUEDA
    formUnidadOpe.addEventListener('keyup',d)
    selectUnidadOpe.addEventListener('change',d)

});

