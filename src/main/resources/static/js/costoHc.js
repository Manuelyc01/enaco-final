document.addEventListener("DOMContentLoaded", function(event) {
    //VISTA COSTO
    const formCosto=document.querySelector("#formCosto")
    const listCostoHcHTML=document.querySelector("#listCostoHc")

    const formUnidadOpe = document.querySelector('#formUnidadOpe');
    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');

    var pageNumber= 1;
    var pageSize=13;
    let listCostoHc=$.ajax({//listTIPOHC
        type: 'GET',
        url:'/listCostoHc/',
        success:[function (result) {
            listCostoHc=result;
        }]
    });
    let costoHtml="";//save list html
    function paginate(array,page_size,page_number) {
        return array.slice((page_number - 1) * page_size, page_number * page_size);
    }
    function paginar(lista){
        if(listCostoHcHTML !=null){
        var pageCont = Math.ceil(lista.length/pageSize);
        var pagination=paginate(lista,pageSize,pageNumber)
        costoHtml="";
        pagination.forEach(costoHc =>{
            costoHtml += `
            <tr>
            <th scope="row">${costoHc.cod_uniOpe.nom_uniOpe}</th>
            <td>${costoHc.cod_tipoHoja.nombre}</td>
            <td>${costoHc.fecha_vigencia}</td>
            <td>${costoHc.precioSinIgv}</td>
            <td>${costoHc.igv}</td>
            <td>${costoHc.precioConIgv}</td>
        </tr>
                `
        });
        costoHtml += "";
        costoHtml += pageNumber >1 ? "<button id='anterior'>Anterior</button>":"";
        costoHtml += pageNumber < pageCont ? ("<button id='siguiente'>Siguiente</button>"):"";

            listCostoHcHTML.innerHTML="";
            listCostoHcHTML.innerHTML=costoHtml;

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
    function showCostos() {
        $.ajax({
            type: 'GET',
            url:'/listCostoHc/',
            success:[function (result) {
                paginar(result)
            }]
        });
    }
    const filtrar= ()=>{
        const list=new Array();
        const text= formCosto.value.toLowerCase();
        for (let costo of listCostoHc){
            let nombre = costo.cod_tipoHoja.nombre.toLowerCase();
            if(nombre.indexOf(text)!==-1){
                list.push(costo)
            }
        }
        paginar(list);
    }
    showCostos();
    if (formCosto!=null){
        formCosto.addEventListener('keyup',filtrar)
    }
    //SELECT TIPO
    const selectTipoHc =document.querySelector('#selectTipoHc');
    const precioUnitario=document.querySelector('#precioUnitario');
    //FORM COMPRA
    const pesoBruto=document.querySelector("#pesoBruto");
    const tara=document.querySelector("#tara");
    const humedad=document.querySelector("#humedad");
    const pesoNeto=document.querySelector("#pesoNeto");
    //valores inputs
    const valorCompra=document.querySelector("#valorCompra");
    const resultIgv=document.querySelector("#igv");
    const totalCompra=document.querySelector("#totalCompra");
    const txtSon=document.querySelector("#txtSon");

    var prc; var igv;var son;


    function calcular(){
        let pb;let pt;let ph;let pesoN;
        let valorC;let valorIgv;let totalC;
        pb=pesoBruto.value;pt= tara.value;ph = humedad.value;
        //CALC
        pesoN= parseFloat(pb-pt-ph).toFixed(2);
        valorC=parseFloat(pesoN*prc).toFixed(2);
        valorIgv=parseFloat(valorC*igv).toFixed(2);
        totalC=parseFloat(valorC-(-valorIgv)).toFixed(2);
        pesoNeto.setAttribute('value',pesoN);
        valorCompra.setAttribute('value',valorC);
        resultIgv.setAttribute('value',valorIgv);
        totalCompra.setAttribute('value',totalC);
        if(totalC >= 0){
            NumeroALetras(totalC);
            txtSon.setAttribute('value',son)
        }else {
            txtSon.setAttribute('value',"?")
        }

    };
    if(pesoBruto!=null){
        pesoBruto.addEventListener('keyup',calcular);
        tara.addEventListener('keyup',calcular);
        humedad.addEventListener('keyup',calcular)
    }
    //selecion tipoHC --precio
    function d() {
        let value = selectTipoHc.value;
        for (let tipo of listCostoHc){
            let cod = tipo.cod_tipoHoja.cod_tipoHoja;
            if(value==cod){
                if(precioUnitario!=null){
                    var price= parseFloat(tipo.precioSinIgv).toFixed(2)
                    precioUnitario.innerHTML=`
                         <input  type="search" value="${price}" class="form-control"disabled/>
                        `
                    prc=price;
                    igv=tipo.igv/100;
                }
            }else  if (value==0){
                var price= parseFloat(0).toFixed(2);
                precioUnitario.innerHTML=`
                         <input  type="search" value="${price}" class="form-control"disabled/>
                        `
                prc=price;
            }
        }
    }
    if(selectTipoHc!=null){
        selectTipoHc.addEventListener('change',d);
        selectTipoHc.addEventListener('change',calcular);
    }
    //FUNCTIONS SON
    function Unidades(num){

        switch(num)
        {
            case 1: return "UN";
            case 2: return "DOS";
            case 3: return "TRES";
            case 4: return "CUATRO";
            case 5: return "CINCO";
            case 6: return "SEIS";
            case 7: return "SIETE";
            case 8: return "OCHO";
            case 9: return "NUEVE";
        }

        return "";
    }
    function Decenas(num){

        decena = Math.floor(num/10);
        unidad = num - (decena * 10);

        switch(decena)
        {
            case 1:
                switch(unidad)
                {
                    case 0: return "DIEZ";
                    case 1: return "ONCE";
                    case 2: return "DOCE";
                    case 3: return "TRECE";
                    case 4: return "CATORCE";
                    case 5: return "QUINCE";
                    default: return "DIECI" + Unidades(unidad);
                }
            case 2:
                switch(unidad)
                {
                    case 0: return "VEINTE";
                    default: return "VEINTI" + Unidades(unidad);
                }
            case 3: return DecenasY("TREINTA", unidad);
            case 4: return DecenasY("CUARENTA", unidad);
            case 5: return DecenasY("CINCUENTA", unidad);
            case 6: return DecenasY("SESENTA", unidad);
            case 7: return DecenasY("SETENTA", unidad);
            case 8: return DecenasY("OCHENTA", unidad);
            case 9: return DecenasY("NOVENTA", unidad);
            case 0: return Unidades(unidad);
        }
    }//Unidades()
    function DecenasY(strSin, numUnidades){
        if (numUnidades > 0)
            return strSin + " Y " + Unidades(numUnidades)

        return strSin;
    }//DecenasY()
    function Centenas(num){

        centenas = Math.floor(num / 100);
        decenas = num - (centenas * 100);

        switch(centenas)
        {
            case 1:
                if (decenas > 0)
                    return "CIENTO " + Decenas(decenas);
                return "CIEN";
            case 2: return "DOSCIENTOS " + Decenas(decenas);
            case 3: return "TRESCIENTOS " + Decenas(decenas);
            case 4: return "CUATROCIENTOS " + Decenas(decenas);
            case 5: return "QUINIENTOS " + Decenas(decenas);
            case 6: return "SEISCIENTOS " + Decenas(decenas);
            case 7: return "SETECIENTOS " + Decenas(decenas);
            case 8: return "OCHOCIENTOS " + Decenas(decenas);
            case 9: return "NOVECIENTOS " + Decenas(decenas);
        }

        return Decenas(decenas);
    }//Centenas()
    function Seccion(num, divisor, strSingular, strPlural){
        cientos = Math.floor(num / divisor)
        resto = num - (cientos * divisor)

        letras = "";

        if (cientos > 0)
            if (cientos > 1)
                letras = Centenas(cientos) + " " + strPlural;
            else
                letras = strSingular;

        if (resto > 0)
            letras += "";

        return letras;
    }//Seccion()
    function Miles(num){
        divisor = 1000;
        cientos = Math.floor(num / divisor)
        resto = num - (cientos * divisor)

        strMiles = Seccion(num, divisor, "MIL", "MIL");
        strCentenas = Centenas(resto);

        if(strMiles == "")
            return strCentenas;

        return strMiles + " " + strCentenas;

        //return Seccion(num, divisor, "UN MIL", "MIL") + " " + Centenas(resto);
    }//Miles()
    function Millones(num){
        divisor = 1000000;
        cientos = Math.floor(num / divisor)
        resto = num - (cientos * divisor)

        strMillones = Seccion(num, divisor, "UN MILLON", "MILLONES");
        strMiles = Miles(resto);

        if(strMillones == "")
            return strMiles;

        return strMillones + " " + strMiles;

        //return Seccion(num, divisor, "UN MILLON", "MILLONES") + " " + Miles(resto);
    }//Millones()
    function NumeroALetras(num,centavos){
        var data = {
            numero: num,
            enteros: Math.floor(num),
            centavos: (((Math.round(num * 100)) - (Math.floor(num) * 100))),
            letrasCentavos: "",
        };
        if(centavos == undefined || centavos==false) {
            data.letrasMonedaPlural="SOLES";
            data.letrasMonedaSingular="SOL";
        }else{
            data.letrasMonedaPlural="CENTIMOS";
            data.letrasMonedaSingular="CENTIMO";
        }

        if (data.centavos > 0)
            data.letrasCentavos = "CON " + NumeroALetras(data.centavos,true);

        if(data.enteros == 0){
            son="CERO " + data.letrasMonedaPlural + " " + data.letrasCentavos;
            return son;
        }if (data.enteros == 1){
            son = Millones(data.enteros) + " " + data.letrasMonedaSingular + " " + data.letrasCentavos;
            return son;
        }else{
            son= Millones(data.enteros) + " " + data.letrasMonedaPlural + " " + data.letrasCentavos;
            return son;
        }
        }//NumeroALetras()


        //ACTUALIZAR LIST COSTOS
    function CostosF(){
        let value = selectUnidadOpe.value;
        console.log(value)
        $.ajax({
            type: 'GET',
            url:'/listCostoHcF/'+value,
            success:[function (result) {
                selectTipoHc.innerHTML=``
                if(result!=null){
                    selectTipoHc.innerHTML+=`
                    <option value="0">
                            Seleccionar...</option>`
                    for(let costo of result){
                        selectTipoHc.innerHTML += `
                            <option value="${costo.cod_tipoHoja.cod_tipoHoja}">
                            <span>${costo.cod_tipoHoja.cod_tipoHoja}</span>---<span>${costo.cod_tipoHoja.nombre}</span>
                        </option>
                        `
                    }
                }else {
                    selectTipoHc.innerHTML += `
                            <option value="0">
                            <span>SIN REGISTROS</span>
                        </option>
                        `
                }
            }]
        });
    }
        if (formUnidadOpe!=null){
            formUnidadOpe.addEventListener('keyup',CostosF)
        }
        if (selectUnidadOpe!=null){
            selectUnidadOpe.addEventListener('change',CostosF)
        }
});
