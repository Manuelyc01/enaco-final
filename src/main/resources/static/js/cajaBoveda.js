document.addEventListener("DOMContentLoaded", function(event) {

    const formUnidadOpe = document.querySelector('#formUnidadOpe');
    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');
    const cajaBovedaUni=document.querySelector("#cajaBovedaUni");
    const btn=document.querySelector("#btn");//CONFIRMACION REGISTRO
    const montoIn=document.querySelector("#montoIn");//CONFIRMACION REGISTRO
    const alert=document.querySelector("#alert");//ALERTA
    const selectTipoTransac=document.querySelector("#selectTipoTransac");//TIPO TRANSC
    //VALIDACION COMPRA
    const pesoBruto=document.querySelector("#pesoBruto");
    const totalCompra=document.querySelector("#totalCompra");
    const tara=document.querySelector("#tara");
    const humedad=document.querySelector("#humedad");

    let listUnidad=$.ajax({
        type: 'GET',
        url:'/unidadesOpe/',
        success:[function (result) {
            listUnidad=result;
        }]
    });
    let cajaBoveda;
    function d() {
        let value = selectUnidadOpe.value;
        if(cajaBovedaUni!=null){
        for (let unidadOpe of listUnidad){
            let cod = unidadOpe.cod_uniOpe.toLowerCase();
            if(value==cod){
                cajaBoveda= unidadOpe.cajaBoveda;
                if (cajaBoveda!=null){

                    cajaBovedaUni.innerHTML=`
                         <input  type="search" value="${parseFloat(cajaBoveda).toFixed(2)}" class="form-control"disabled/>
                    `
                }else {
                    cajaBovedaUni.innerHTML=`
                         <input  type="search" value="0.00" class="form-control"disabled/>
                    `
                }
                }
            }
        }
    }
    function f() {
        let value = montoIn.value;
        let type=selectTipoTransac.value;
        if(value<=cajaBoveda && type==2){
            btn.innerHTML=`
            <button type="submit" class="btn btn-primary">REGISTRAR</button>
            `;
            alert.innerHTML=``
        }else if (value>cajaBoveda && type==2){
            btn.innerHTML=`
            <button class="btn btn-danger" disabled>REGISTRAR</button>
            `;
            alert.innerHTML=`<span style="color: red">Monto superior a la caja bóveda</span>`
        }
    }
    function x() {
        let value = totalCompra.value;
        if (value>cajaBoveda){
            btn.innerHTML=`
            <button class="btn btn-danger" disabled>COMPRAR</button>
            `;
            alert.innerHTML=`<span style="color: red">Monto superior a la caja bóveda</span>`
        }else if(value<=cajaBoveda){
            btn.innerHTML=`
            <button type="submit" class="btn btn-primary">COMPRAR</button>
            `;
            alert.innerHTML=``
        }
    }
    if (montoIn!=null){//ALERTA
        montoIn.addEventListener('keyup',f)
        selectTipoTransac.addEventListener('change',f)
    }
    if (formUnidadOpe!=null){
        formUnidadOpe.addEventListener('keyup',d)
    }
    if (selectUnidadOpe!=null){
        selectUnidadOpe.addEventListener('change',d)
    }
    if(pesoBruto!=null){
        pesoBruto.addEventListener('keyup',x);
        tara.addEventListener('keyup',x);
        humedad.addEventListener('keyup',x)
    }

    //REGISTROS
    const listCajaBoveda = document.querySelector("#listCajaBoveda");
    var pageNumber= 1;
    var pageSize=8;
    $.ajax({
        type: 'GET',
        url:'/cajabovedas/',
        success:[function (result) {
            paginar(result)
        }]
    });
    let cajaBhtml="";
    function paginate(array,page_size,page_number) {
        return array.slice((page_number - 1) * page_size, page_number * page_size);
    }
    function paginar(lista){
        if(listCajaBoveda!=null){
            var pageCont = Math.ceil(lista.length/pageSize);
            var pagination=paginate(lista,pageSize,pageNumber)
            cajaBhtml="";
            pagination.forEach(cajaB =>{
                const event=new Date(cajaB.fecha);
                event.setUTCHours(event.getUTCHours()-5);
                const str = event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "---Hora: ");
                cajaBhtml+=`
                    <tr>
                    <th scope="row">${cajaB.id_usuario.nombre}</th>
                    <th scope="row">${cajaB.cod_uniOpe.nom_uniOpe}</th>
                    <th scope="row">${cajaB.id_tipoTransac.nombre}</th>
                    <th scope="row">${str}</th>
                    <th scope="row">S/ ${cajaB.monto}</th>
                    </tr>
                `
            });
            cajaBhtml += "";
            cajaBhtml += pageNumber >1 ? "<button id='anterior'>Anterior</button>":"";
            cajaBhtml += pageNumber < pageCont ? ("<button id='siguiente'>Siguiente</button>"):"";

            listCajaBoveda.innerHTML=""
            listCajaBoveda.innerHTML=cajaBhtml;
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

});