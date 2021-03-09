document.addEventListener("DOMContentLoaded", function(event) {

    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');
    const TipoHc =document.querySelector('#TipoHc');
    const btnStock =document.querySelector('#btnStock');
    const txtStock =document.querySelector('#txtStock');
    const cantidadN =document.querySelector('#cantidadN');
    const divCantidad =document.querySelector('#divCantidad');

    function stock() {
        let valueUni = selectUnidadOpe.value;
        let valueHoja = TipoHc.value;
        $.ajax({
            type: 'GET',
            url:'/viewStock/'+valueUni+'/'+valueHoja,
            success: [function (result) {
                txtStock.setAttribute('value',result+" Kg");
            }]
        });
    }
    function x() {
        let valueUni = selectUnidadOpe.value;
        let valueHoja = TipoHc.value;
        $.ajax({
            type: 'GET',
            url:'/viewStock/'+valueUni+'/'+valueHoja,
            success: [function (result) {
                let value1 = cantidadN.value;
                if(result<value1){
                    divCantidad.innerHTML=``
                    divCantidad.innerHTML=`<span style="color: red">Cantidad superior a stock</span>`
                }else {
                    divCantidad.innerHTML=``
                }
            }]
        });
    }
    if(btnStock!=null){
        btnStock.addEventListener('click',stock);
    }
    if(cantidadN!=null){
        cantidadN.addEventListener('keyup',x);
    }


});