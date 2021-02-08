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
    


});