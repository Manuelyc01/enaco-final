document.addEventListener("DOMContentLoaded", function(event) {

    const formTipoHc=document.querySelector("#formTipoHc")
    const listTipoHc=document.querySelector("#listTipoHc")


    var pageNumber= 1;
    var pageSize=10;

    let tipos = $.ajax({
        type: 'GET',
        url:'/listTipoHc/',
        success:[function (result) {
            tipos=result
        }]
    });
    let tiposHtml="";
    function paginate(array,page_size,page_number) {
        return array.slice((page_number - 1) * page_size, page_number * page_size);
    }
    function paginar(lista){
        if (listTipoHc!=null){
            var pageCont = Math.ceil(lista.length/pageSize);
            var pagination=paginate(lista,pageSize,pageNumber)
            tiposHtml="";
            pagination.forEach(tipo =>{
                tiposHtml += `
                <tr>
                     <th scope="row">${tipo.cod_tipoHoja}</th>
                     <td>${tipo.nombre}</td>
                </tr>
                `
            });
            tiposHtml += "";
            tiposHtml += pageNumber >1 ? "<button id='anterior'>Anterior</button>":"";
            tiposHtml += pageNumber < pageCont ? ("<button id='siguiente'>Siguiente</button>"):"";
            document.querySelector("#listTipoHc").innerHTML="";
            document.querySelector("#listTipoHc").innerHTML=tiposHtml;

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
    function showTipos() {
        $.ajax({
            type: 'GET',
            url:'/listTipoHc/',
            success:[function (result) {
                paginar(result)
            }]
        });
    }
    const filtrar= ()=>{
        const list=new Array();
        const text= formTipoHc.value.toLowerCase();
        for (let tipo of tipos){
            let nombre = tipo.nombre.toLowerCase();
            if(nombre.indexOf(text)!==-1){
                list.push(tipo)
            }
        }
        paginar(list);
    }
    showTipos();
    if(formTipoHc!=null){
        formTipoHc.addEventListener('keyup',filtrar)
    }
});