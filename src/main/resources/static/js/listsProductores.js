document.addEventListener("DOMContentLoaded", function(event) {
    const formProductor=document.querySelector('#formProductor');const list=new Array();
    const btnBuscarP=document.querySelector('#btnBuscarP');

    var pageNumber= 1;
    var pageSize=13;

    const productores = listProductores;
    var productoresHtml="";

    function paginate(array,page_size,page_number) {
        return array.slice((page_number - 1) * page_size, page_number * page_size);
    }
    function showProductores() {
        paginar(productores)
    }
    showProductores();
    const filtar= ()=>{
        const text= formProductor.value.toLowerCase();
        for (let productor of productores){
            let nombre = productor.nombre.toLowerCase();
            if(nombre.indexOf(text)!==-1){
                list.push(productor)
            }
        }
        console.log(list);
        paginar(list);
    }
    function paginar(lista){
        var pageCont = Math.ceil(lista.length/pageSize);

        var pagination=paginate(lista,pageSize,pageNumber)
        productoresHtml="";
        pagination.forEach(productores =>{
            productoresHtml += `
                    <tr>
                    <th scope="row">${productores.cedula}</th>
                    <td>${productores.nombre}</td>
                    <td>${productores.dni}"</td>
                    <td>${productores.nombPredio}</td>
                    <td>${productores.ubicacion}</td>
                    <td>${productores.cod_uniOpe.nom_uniOpe}</td>
                    </tr>
                `
        });
        productoresHtml += "";
        productoresHtml += pageNumber >1 ? "<button id='anterior'>Anterior</button>":"";
        productoresHtml += pageNumber < pageCont ? ("<button id='siguiente'>Siguiente</button>"):"";
        document.querySelector("#listProductor").innerHTML="";
        document.querySelector("#listProductor").innerHTML=productoresHtml;

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
    btnBuscarP.addEventListener('click',filtar)
});