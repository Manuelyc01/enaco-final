document.addEventListener("DOMContentLoaded", function(event) {
    let listCompras = document.querySelector("#listCompras");
    const id=id_usuario;

    var pageNumber= 1;
    var pageSize=7;
    let compras = $.ajax({
        type: 'GET',
        url:'/listCompras/'+id,
        success:[function (result) {
            compras=result
        }]
    });
    let comprasHtml="";
    function paginate(array,page_size,page_number) {
        return array.slice((page_number - 1) * page_size, page_number * page_size);
    }
    function showCompras() {
        $.ajax({
            type: 'GET',
            url:'/listCompras/'+id,
            success:[function (result) {
                paginar(result)
            }]
        });
    }
    showCompras();
    function paginar(lista){
        var pageCont = Math.ceil(lista.length/pageSize);
        var pagination=paginate(lista,pageSize,pageNumber)
        comprasHtml="";
        pagination.forEach(compras =>{
            comprasHtml += `
                    <tr>
                    <th scope="row">
                    <a class="fas fa-fw fa-eye" href="/compraRealizada/${compras.id_compra}"></a>${compras.num_liquidacion}</th>
                    <td>${compras.dni_repre}</td>
                    <td>${compras.fecha}</td>
                    <td>${compras.id_usuario.nombre}</td>
                    <td>
                    <a class="btn btn-info" href="/auth/report/${compras.id_compra}">Boleta</a>
                     </td>
                    </tr>
                `
        });
        comprasHtml += "";
        comprasHtml += pageNumber >1 ? "<button id='anterior'>Anterior</button>":"";
        comprasHtml += pageNumber < pageCont ? ("<button id='siguiente'>Siguiente</button>"):"";
        listCompras.innerHTML="";
        listCompras.innerHTML=comprasHtml;

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
});