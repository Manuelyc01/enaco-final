document.addEventListener("DOMContentLoaded", function(event) {

    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');
    const TipoHc =document.querySelector('#TipoHc');
    const fcInicio =document.querySelector('#fcInicio');
    const fcFin =document.querySelector('#fcFin');
    const formHc =document.querySelector('#formHc');
    const spanACTA =document.querySelector('#spanACTA');
    const registrosTabla =document.querySelector('#registrosTabla');

    const codRep =document.querySelector('#codRep');
    const spanSelectR =document.querySelector('#spanSelectR');
    const btnReport =document.querySelector('#btnReport');
    const thTable =document.querySelector('#theadTable');

    const btnBuscarAlmacen =document.querySelector('#btnBuscarAlmacen');

    const listTipoHcAlmacen= document.querySelector('#listTipoHcAlmacen');


    function registrosU() {
        let value=selectUnidadOpe.value;
        $.ajax({
            type: 'GET',
            url:'/listRegistrosUni/'+value,
            success:[function (result) {
                if(listTipoHcAlmacen!=null){
                    listTipoHcAlmacen.innerHTML=``
                    if(result.length!=0){
                        listTipoHcAlmacen.innerHTML+=``
                        for(let regist of result){
                            const event=new Date(regist.fecha);
                            event.setUTCHours(event.getUTCHours()-5);//cambio de horario
                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

                            listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                        }
                    }else {
                        listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                    }
                }
            }]
        });
    }
    function registros() {
        let valueUni = selectUnidadOpe.value;
        let valueHoja = TipoHc.value;
        //INVENTARIO
        if(valueHoja==0 && fcInicio.value=='' && fcFin.value=='' ){
            $.ajax({
                type: 'GET',
                url:'/listRegistrosUni/'+valueUni,
                success:[function (result) {
                    if(listTipoHcAlmacen!=null){
                        listTipoHcAlmacen.innerHTML=``
                        if(result.length!=0){
                            listTipoHcAlmacen.innerHTML+=``
                            for(let regist of result){
                                const event=new Date(regist.fecha);
                                event.setUTCHours(event.getUTCHours()-5);
                                const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

                                listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                            }
                        }else {
                            listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                        }
                    }
                }]
            });
        }
        else if(valueHoja==0 && fcInicio.value!='' && fcFin.value!='' ){
            //FILTRADO POR FECHAS
            const inicio = fcInicio.value.replace("T", " ");
            const fin = fcFin.value.replace("T", " ");
            $.ajax({
                type: 'GET',
                url:'/filterDate/'+inicio+'/'+fin+'/'+valueUni,
                success:[function (result) {
                    if(listTipoHcAlmacen!=null){
                        listTipoHcAlmacen.innerHTML=``
                        if(result.length!=0){
                            listTipoHcAlmacen.innerHTML+=``
                            for(let regist of result){
                                const event=new Date(regist.fecha);
                                event.setUTCHours(event.getUTCHours()-5);
                                const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

                                listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                            }
                        }else {
                            listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                        }
                    }
                }]
            });

        }
        else if(valueHoja!=0 && fcInicio.value=='' && fcFin.value=='' ){
            $.ajax({
                type: 'GET',
                url:'/viewRegisters/'+valueUni+'/'+valueHoja,
                success: [function (result) {
                    if(listTipoHcAlmacen!=null){
                        listTipoHcAlmacen.innerHTML=``
                        if(result.length!=0){
                            listTipoHcAlmacen.innerHTML+=``
                            for(let regist of result){
                                const event=new Date(regist.fecha);
                                event.setUTCHours(event.getUTCHours()-5);
                                const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
                                listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                            }
                        }else {
                            listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                        }
                    }
                }]
            });
        }
        else if(valueHoja!=0 && fcInicio.value!='' && fcFin.value!='' ){
            //FILTRADO POR FECHAS
            const inicio = fcInicio.value.replace("T", " ");
            const fin = fcFin.value.replace("T", " ");
            $.ajax({
                type: 'GET',
                url:'/filterDate/'+inicio+'/'+fin+'/'+valueUni+'/'+valueHoja,
                success:[function (result) {
                    if(listTipoHcAlmacen!=null){
                        listTipoHcAlmacen.innerHTML=``
                        if(result.length!=0){
                            listTipoHcAlmacen.innerHTML+=``
                            for(let regist of result){
                                const event=new Date(regist.fecha);
                                event.setUTCHours(event.getUTCHours()-5);
                                const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

                                listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                            }

                        }else {
                            listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                        }
                    }
                }]
            });
        }


    }
    //REGISTROS
    function registers(){
        let codUnidad = selectUnidadOpe.value;
        let codReporte = codRep.value;
        let codHojaC=TipoHc.value;
        let fcI = fcInicio.value;
        let fcF= fcFin.value;


        if (codReporte!='0' && codUnidad!='0'){
            switch (codReporte) {
                case '1':
                    if (codHojaC=='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/listRegistrosUni/'+codUnidad,
                            success:[function (result) {
                                if(listTipoHcAlmacen!=null){
                                    listTipoHcAlmacen.innerHTML=``
                                    if(result.length!=0){
                                        listTipoHcAlmacen.innerHTML+=``
                                        for(let regist of result){
                                            const event=new Date(regist.fecha);
                                            event.setUTCHours(event.getUTCHours()-5);
                                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

                                            listTipoHcAlmacen.innerHTML += `
                                                                            <tr>
                                                                            <td scope="row">${str}</td>
                                                                            <td scope="col">${regist.id_usuario.nombre}</td>
                                                                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                                                                            <td scope="col">${regist.id_movimiento.nombre}</td>
                                                                            <td scope="col">${regist.documento}</td>
                                                                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                            <td scope="col">${regist.pesoNeto}</td>
                                                                            <td scope="col">${regist.stockInicial}</td>
                                                                            <td scope="col">${regist.stockFinal}</td>
                                                                            </tr>
                                                                        `
                                        }
                                    }else {
                                        listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                                    }
                                }
                            }]
                        });
                    }
                    else if(codHojaC=='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDate/'+inicio+'/'+fin+'/'+codUnidad,
                            success:[function (result) {
                                if(listTipoHcAlmacen!=null){
                                    listTipoHcAlmacen.innerHTML=``
                                    if(result.length!=0){
                                        listTipoHcAlmacen.innerHTML+=``
                                        for(let regist of result){
                                            const event=new Date(regist.fecha);
                                            event.setUTCHours(event.getUTCHours()-5);
                                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

                                            listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                                        }
                                    }else {
                                        listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                                    }
                                }
                            }]
                        });
                    }
                    else if (codHojaC!='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/viewRegisters/'+codUnidad+'/'+codHojaC,
                            success: [function (result) {
                                if(listTipoHcAlmacen!=null){
                                    listTipoHcAlmacen.innerHTML=``
                                    if(result.length!=0){
                                        listTipoHcAlmacen.innerHTML+=``
                                        for(let regist of result){
                                            const event=new Date(regist.fecha);
                                            event.setUTCHours(event.getUTCHours()-5);
                                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
                                            listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                                        }
                                    }else {
                                        listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                                    }
                                }
                            }]
                        });
                    }
                    else if (codHojaC!='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDate/'+inicio+'/'+fin+'/'+codUnidad+'/'+codHojaC,
                            success:[function (result) {
                                if(listTipoHcAlmacen!=null){
                                    listTipoHcAlmacen.innerHTML=``
                                    if(result.length!=0){
                                        listTipoHcAlmacen.innerHTML+=``
                                        for(let regist of result){
                                            const event=new Date(regist.fecha);
                                            event.setUTCHours(event.getUTCHours()-5);
                                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

                                            listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${str}</td>
                            <td scope="col">${regist.id_usuario.nombre}</td>
                            <td scope="col">${regist.cod_almacen.nom_uniOpe}</td>
                            <td scope="col">${regist.id_movimiento.nombre}</td>
                            <td scope="col">${regist.documento}</td>
                            <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                            <td scope="col">${regist.pesoNeto}</td>
                            <td scope="col">${regist.stockInicial}</td>
                            <td scope="col">${regist.stockFinal}</td>
                            </tr>
                        `
                                        }

                                    }else {
                                        listTipoHcAlmacen.innerHTML += `
                            <h4>Sin Registros</h4>
                        `
                                    }
                                }
                            }]
                        });

                    }
                    break;
                case '4':
                    if (codHojaC=='0' && fcI=='' && fcF==''){
                        $.ajax({
                             type: 'GET',
                             url:'/listRegistrosUniCompras/'+codUnidad,
                             success:[function (result) {
                                 if(listTipoHcAlmacen!=null){
                                     listTipoHcAlmacen.innerHTML=``
                                     if(result.length!=0){
                                         listTipoHcAlmacen.innerHTML+=``
                                         for(let regist of result){
                                             const event=new Date(regist.fecha);
                                             event.setUTCHours(event.getUTCHours()-5);
                                             const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
                                             listTipoHcAlmacen.innerHTML += `
                                                                             <tr>
                                                                             <td scope="row">${str}</td>
                                                                             <td scope="col">${regist.cod_uniOpe.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.id_repre.nombre}</td>
                                                                             <td scope="col">${regist.num_liquidacion}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.pesoNeto}</td>
                                                                             </tr>
                                                                         `
                                         }
                                         setTimeout(function(){ console.log(representantes) }, 500);
                                     }else {
                                         listTipoHcAlmacen.innerHTML += `
                             <h4>Sin Registros</h4>
                         `
                                     }
                                 }
                             }]
                         });
                    }
                    else if(codHojaC=='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDateCompras/'+inicio+'/'+fin+'/'+codUnidad,
                            success:[function (result) {
                                if(listTipoHcAlmacen!=null){
                                    listTipoHcAlmacen.innerHTML=``
                                    if(result.length!=0){
                                        listTipoHcAlmacen.innerHTML+=``
                                        for(let regist of result){
                                            const event=new Date(regist.fecha);
                                            event.setUTCHours(event.getUTCHours()-5);
                                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
                                            listTipoHcAlmacen.innerHTML += `
                                                                             <tr>
                                                                             <td scope="row">${str}</td>
                                                                             <td scope="col">${regist.cod_uniOpe.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.id_repre.nombre}</td>
                                                                             <td scope="col">${regist.num_liquidacion}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.pesoNeto}</td>
                                                                             </tr>
                                                                         `
                                        }
                                        setTimeout(function(){ console.log(representantes) }, 500);
                                    }else {
                                        listTipoHcAlmacen.innerHTML += `
                             <h4>Sin Registros</h4>
                         `
                                    }
                                }
                            }]
                        });
                    }
                    else if (codHojaC!='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/viewRegistersCompras/'+codUnidad+'/'+codHojaC,
                            success: [function (result) {
                                if(listTipoHcAlmacen!=null){
                                    listTipoHcAlmacen.innerHTML=``
                                    if(result.length!=0){
                                        listTipoHcAlmacen.innerHTML+=``
                                        for(let regist of result){
                                            const event=new Date(regist.fecha);
                                            event.setUTCHours(event.getUTCHours()-5);
                                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
                                            listTipoHcAlmacen.innerHTML += `
                                                                             <tr>
                                                                             <td scope="row">${str}</td>
                                                                             <td scope="col">${regist.cod_uniOpe.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.id_repre.nombre}</td>
                                                                             <td scope="col">${regist.num_liquidacion}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.pesoNeto}</td>
                                                                             </tr>
                                                                         `
                                        }
                                        setTimeout(function(){ console.log(representantes) }, 500);
                                    }else {
                                        listTipoHcAlmacen.innerHTML += `
                             <h4>Sin Registros</h4>
                         `
                                    }
                                }
                            }]
                        });
                    }
                    else if (codHojaC!='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDateCompras/'+inicio+'/'+fin+'/'+codUnidad+'/'+codHojaC,
                            success:[function (result) {
                                if(listTipoHcAlmacen!=null){
                                    listTipoHcAlmacen.innerHTML=``
                                    if(result.length!=0){
                                        listTipoHcAlmacen.innerHTML+=``
                                        for(let regist of result){
                                            const event=new Date(regist.fecha);
                                            event.setUTCHours(event.getUTCHours()-5);
                                            const str =event.toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
                                            listTipoHcAlmacen.innerHTML += `
                                                                             <tr>
                                                                             <td scope="row">${str}</td>
                                                                             <td scope="col">${regist.cod_uniOpe.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.id_repre.nombre}</td>
                                                                             <td scope="col">${regist.num_liquidacion}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.pesoNeto}</td>
                                                                             </tr>
                                                                         `
                                        }
                                        setTimeout(function(){ console.log(representantes) }, 500);
                                    }else {
                                        listTipoHcAlmacen.innerHTML += `
                             <h4>Sin Registros</h4>
                         `
                                    }
                                }
                            }]
                        });
                    }
                    break;
                case '5':
                    listTipoHcAlmacen.innerHTML=`5`
                    break;
                case '6':
                    listTipoHcAlmacen.innerHTML=`6`
                    break;
                case '7':
                    listTipoHcAlmacen.innerHTML=`7`
                    break;
                case '8':
                    listTipoHcAlmacen.innerHTML=`8`
                    break;
                case '9':
                    listTipoHcAlmacen.innerHTML=`9`
                    break;
                case '10':
                    listTipoHcAlmacen.innerHTML=`10`
                    break;
            }
        }else {
            registrosTabla.innerHTML=`<br><h3><span class="fas fa-fw fa-exclamation-circle"></span>Seleccionar reporte</h3>
            `;
        }

    }


    let hojasC=$.ajax({
        type: 'GET',
        url:'/listTipoHc/',
        success:[function (result) {
            hojasC=result;
        }]
    });//HOJAS DE COCA
    function filtrar() {
        TipoHc.innerHTML='';
        const text=formHc.value.toLowerCase();
        for(let hc of hojasC){
            let val = hc.cod_tipoHoja.toLowerCase();
            if(val.indexOf(text) !== -1){
                TipoHc.innerHTML += `
                            <option value="${hc.cod_tipoHoja}">
                                        <span>${hc.cod_tipoHoja}</span>---<span>${hc.nombre}</span>
                             </option>
                        `
            }
        }
        if(TipoHc.innerHTML === ''){
            TipoHc.innerHTML+= `
                            <option value="0">Sin registro</option>
                        `
        }
    }//FILTRAR HC POR COD.

    function mostrarBtn() {
        const  val=codRep.value;
        const  su=selectUnidadOpe.value;
        const  st=TipoHc.value;
        if(val=='0'){
            btnReport.style.visibility='hidden'
            spanACTA.innerHTML='';
        }else if(val=='1' || val=='2'|| val=='3'|| val=='4'|| val=='5'|| val=='6'|| val=='7'|| val=='8'|| val=='9'){
            if (su!='0'){
                btnReport.disabled=false
                btnReport.style.visibility = 'visible';
                btnReport.innerHTML='Descargar<span class="fas fa-fw fa-file-excel"></span>'
                btnReport.className = 'btn btn-success'
                spanACTA.innerHTML=''
            }else {
                btnReport.disabled=true
                btnReport.style.visibility = 'visible';
                btnReport.innerHTML='Descargar<span class="fas fa-fw fa-file-excel"></span>'
                btnReport.className = 'btn btn-success'
                spanACTA.innerHTML='<span style="color: forestgreen"><span class="fas fa-fw fa-exclamation-circle"></span>Seleccionar Unidad</span>'
            }

        }else if (val=='10'){
            if (su!='0'&& fcInicio.value!=''&&fcFin.value!=''){
                btnReport.disabled=false
                btnReport.style.visibility = 'visible'
                btnReport.className = 'btn btn-danger'
                btnReport.innerHTML='Descargar<span class="fas fa-fw fa-file-pdf"></span>';
                spanACTA.innerHTML=''
            }else {
                btnReport.disabled=true
                btnReport.style.visibility = 'visible'
                btnReport.className = 'btn btn-danger'
                btnReport.innerHTML='Descargar<span class="fas fa-fw fa-file-pdf"></span>';
                spanACTA.innerHTML='<span style="color: red"><span class="fas fa-fw fa-exclamation-circle"></span> Acta requiere: Oficina, fecha inicio y fecha fin </span>'
            }
        }
    }//BOTON REPORTE EXCEL & PDF
    function seleccionarReporte() {
        let val = codRep.value;
        if(val=='0'){
            spanSelectR.style.visibility = 'visible';
        }else if(val=='1' || val=='2'|| val=='3'|| val=='4'|| val=='5'|| val=='6'|| val=='7'|| val=='8'|| val=='9'|| val=='10'){
            spanSelectR.style.visibility='hidden'
        }
    }//SPAN REPORTE
    function theadTable() {
        let value = codRep.value;
        switch (value) {
            case '0':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3><span class="fas fa-fw fa-exclamation-circle"></span>Seleccionar reporte</h3>`
                thTable.innerHTML=''
                break;
            case '1':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Registros Kardex</h3>`
                thTable.innerHTML=`
                    <tr>
                        <th scope="col">Fecha-Hora</th>
                        <th scope="col">Usuario</th>
                        <th scope="col">Almacén</th>
                        <th scope="col">Movimiento</th>
                        <th scope="col">Documento</th>
                        <th scope="col">Tipo de HC</th>
                        <th scope="col">Peso Neto</th>
                        <th scope="col">Stock Inicial</th>
                        <th scope="col">Stock Final</th>
                    </tr>
                `
                break;
            case '4':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Registros de compras en oficina</h3>`
                thTable.innerHTML=`
                        <tr>
                            <th scope="col">Fecha-Hora</th>
                            <th scope="col">Uni. Operativa</th>
                            <th scope="col">Usuario</th>
                            <th scope="col">Representante</th>
                            <th scope="col">Documento</th>
                            <th scope="col">Tipo de HC</th>
                            <th scope="col">Cantidad (KG)</th>
                        </tr>
                `
                break;
            case '5':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Registros de demasías</h3>`
                thTable.innerHTML=`
                        <tr>
                            <th scope="col">Fecha-Hora</th>
                            <th scope="col">Uni. Operativa</th>
                            <th scope="col">Usuario</th>
                            <th scope="col">Documento</th>
                            <th scope="col">Tipo de HC</th>
                            <th scope="col">Cantidad (KG)</th>
                        </tr>
                `
                break;
            case '6':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Registros de decomisos</h3>`
                thTable.innerHTML=`
                        <tr>
                            <th scope="col">Fecha-Hora</th>
                            <th scope="col">Uni. Operativa</th>
                            <th scope="col">Usuario</th>
                            <th scope="col">Documento</th>
                            <th scope="col">Tipo de HC</th>
                            <th scope="col">Cantidad (KG)</th>
                        </tr>
                `
                break;
            case '7':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Registros de mermas</h3>`
                thTable.innerHTML=`
                        <tr>
                            <th scope="col">Fecha-Hora</th>
                            <th scope="col">Uni. Operativa</th>
                            <th scope="col">Usuario</th>
                            <th scope="col">Tipo de HC</th>
                            <th scope="col">Cantidad (KG)</th>
                        </tr>
                `
                break;
            case '8':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Movimientos de caja bóveda</h3>`
                thTable.innerHTML=`
                        <tr>
                            <th scope="col">Fecha-Hora</th>
                            <th scope="col">Uni. Operativa</th>
                            <th scope="col">Usuario</th>
                            <th scope="col">Tipo Transacción</th>
                            <th scope="col">Monto (Soles)</th>
                            <th scope="col">Saldo Inicial</th>
                            <th scope="col">Saldo Final</th>
                        </tr>
                `
                break;
            case '9':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Salidas por transferencia</h3>`
                thTable.innerHTML=`
                        <tr>
                            <th scope="col">Fecha-Hora</th>
                            <th scope="col">Uni. Operativa</th>
                            <th scope="col">Usuario</th>
                            <th scope="col">Destino</th>
                            <th scope="col">Transportista</th>
                            <th scope="col">Placa de Vehículo</th>
                            <th scope="col">Tipo HC</th>
                            <th scope="col">Cantidad (KG)</th>
                        </tr>
                `
                break;
            case '10':
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Acta inventario de Hoja de Coca</h3>`
                thTable.innerHTML=`
                    <tr>
                        <th scope="col">Fecha-Hora</th>
                        <th scope="col">Usuario</th>
                        <th scope="col">Almacén</th>
                        <th scope="col">Movimiento</th>
                        <th scope="col">Documento</th>
                        <th scope="col">Tipo de HC</th>
                        <th scope="col">Peso Neto</th>
                        <th scope="col">Stock Inicial</th>
                        <th scope="col">Stock Final</th>
                    </tr>
                `
                break;
        }
    }//THEAD DE TABLA


    if (formHc !=null){
        formHc.addEventListener('keyup',filtrar)
    }
    if (selectUnidadOpe!=null){/*
        selectUnidadOpe.addEventListener('change',registrosU)*/
        selectUnidadOpe.addEventListener('change',mostrarBtn)
        selectUnidadOpe.addEventListener('change',registers)
    }
    if (codRep!=null){
        codRep.addEventListener('change',mostrarBtn)
        codRep.addEventListener('change',seleccionarReporte)
        codRep.addEventListener('change',theadTable)
    }
    if (TipoHc!=null){
        TipoHc.addEventListener('change',mostrarBtn)
    }
    if (fcInicio!=null){
        fcInicio.addEventListener('change',mostrarBtn)
    }
    if (fcFin!=null){
        fcFin.addEventListener('change',mostrarBtn)
    }
    if(btnBuscarAlmacen!=null){
        btnBuscarAlmacen.addEventListener('click',registers)
        /*
        btnBuscarAlmacen.addEventListener('click',registros)*/
    }
});