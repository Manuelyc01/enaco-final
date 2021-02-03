document.addEventListener("DOMContentLoaded", function(event) {
    //SELECT TIPO
    const selectTipoHc =document.querySelector('#selectTipoHc');
    const precioUnitario=document.querySelector('#precioUnitario');
    //FORM COMPRA
    const pesoBruto=document.querySelector("#pesoBruto");
    const tara=document.querySelector("#tara");
    const humedad=document.querySelector("#humedad");
    const pesoNeto=document.querySelector("#pesoNeto");

    const valorCompra=document.querySelector("#valorCompra");
    const resultIgv=document.querySelector("#igv");
    const totalCompra=document.querySelector("#totalCompra")
    var prc; var igv;


    function calcular(){
        let pb;let pt;let ph;let pesoN;
        let valorC;let valorIgv;let totalC;
        pb=pesoBruto.value;pt= tara.value;ph = humedad.value;
        //CALC
        pesoN= parseFloat(pb-pt-ph).toFixed(2);
        valorC=parseFloat(pesoN*prc).toFixed(2);
        valorIgv=parseFloat(valorC*igv).toFixed(2);
        totalC=parseFloat(valorC-(-valorIgv)).toFixed(2);
        pesoNeto.innerHTML=`
                         <input  type="search" placeholder="${pesoN}Kg" class="form-control"disabled/>
                        `;
        valorCompra.innerHTML=`
                         <input  type="search" placeholder="S/${valorC}" class="form-control"disabled/>
                        `;
        resultIgv.innerHTML=`
                         <input  type="search" placeholder="S/${valorIgv}" class="form-control"disabled/>
                        `;
        totalCompra.innerHTML=`
                         <input  type="search" placeholder="S/${totalC}" class="form-control"disabled/>
                        `;

    };
    pesoBruto.addEventListener('keyup',calcular);
    tara.addEventListener('keyup',calcular);
    humedad.addEventListener('keyup',calcular)

    let listCostoHc=$.ajax({
        type: 'GET',
        url:'/listCostoHc/',
        success:[function (result) {
            listCostoHc=result;
        }]
    });
    function d() {
        let value = selectTipoHc.value;
        for (let tipo of listCostoHc){
            let cod = tipo.cod_tipoHoja.cod_tipoHoja;
            if(value==cod){
                if(precioUnitario!=null){
                    var price= parseFloat(tipo.precioSinIgv).toFixed(2)
                    precioUnitario.innerHTML=`
                         <input  type="search" placeholder="${price}" class="form-control"disabled/>
                        `
                    prc=price;
                    igv=tipo.igv/100;
                }
            }
        }
    }
    selectTipoHc.addEventListener('change',d);
    selectTipoHc.addEventListener('change',calcular);
});
