//ID INPUTS
document.addEventListener("DOMContentLoaded", function(event) {
    //LISTA
    //INPUT BUSQUEDA
    const formUnidadOpe = document.querySelector('#formUnidadOpe');
    var pageNumber= 1;
    var pageSize=14;
    $.ajax({
        type: 'GET',
        url:'/unidadesOpe/',
        success:[function (result) {
            paginar(result)
        }]
    });
    let unidadHtml="";//save list html
    function paginate(array,page_size,page_number) {
        return array.slice((page_number - 1) * page_size, page_number * page_size);
    }
    function paginar(lista){
        var pageCont = Math.ceil(lista.length/pageSize);
        var pagination=paginate(lista,pageSize,pageNumber)
        unidadHtml="";
        pagination.forEach(unidadOpe =>{
            unidadHtml += `
            <tr>
            <th scope="row">${unidadOpe.cod_uniOpe}</th>
            <td>${unidadOpe.nom_uniOpe}</td>
            <td>${unidadOpe.direccion}</td>
            <td></td>
            <td>${unidadOpe.cod_agencia.nombre}</td>
            <td>${unidadOpe.uniMedCompra}</td>
        </tr>
                `
        });
        unidadHtml += "";
        unidadHtml += pageNumber >1 ? "<button id='anterior'>Anterior</button>":"";
        unidadHtml += pageNumber < pageCont ? ("<button id='siguiente'>Siguiente</button>"):"";
        document.querySelector("#listUnidadOpe").innerHTML="";

        document.querySelector("#listUnidadOpe").innerHTML=unidadHtml;
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
    function showUnidad() {
    }
    const filtar= ()=>{
        const list=new Array();
        const text= formUnidadOpe.value.toLowerCase();
        for (let unidad of listUnidad){
            let nombre = unidad.nom_uniOpe.toLowerCase();
            if(nombre.indexOf(text)!==-1){
                list.push(unidad)
            }
        }
        paginar(list);
    }
    showUnidad();
    formUnidadOpe.addEventListener('keyup',filtar)

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

