document.addEventListener("DOMContentLoaded", function(event) {

    const selectUnidadOpe = document.querySelector('#selectUnidadOpe');
    const TipoHc = document.querySelector('#TipoHc');
    const fcInicio = document.querySelector('#fcInicio');
    const fcFin = document.querySelector('#fcFin');
    const formHc = document.querySelector('#formHc');
    const spanACTA = document.querySelector('#spanACTA');
    const registrosTabla = document.querySelector('#registrosTabla');

    const codRep = document.querySelector('#codRep');
    const spanSelectR = document.querySelector('#spanSelectR');
    const btnReport = document.querySelector('#btnReport');
    const thTable = document.querySelector('#theadTable');
    const inputDlt = document.querySelector('#inputDlt');
    const inputDlt2 = document.querySelector('#inputDlt2');
    const nombreLabel = document.querySelector('#nombreLabel');
    const fecha1 = document.querySelector('#fecha1');
    const fecha2 = document.querySelector('#fecha2');

    const btnBuscarAlmacen = document.querySelector('#btnBuscarAlmacen');

    const listTipoHcAlmacen = document.querySelector('#listTipoHcAlmacen');

    $(document).ready(function() {
        theadTable();
        mostrarBtn()
    });

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
                    if (codHojaC=='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/listRegistrosUniDemasia/'+codUnidad,
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
                                                                             <td scope="col">${regist.documento}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                    else if (codHojaC=='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDateDemasias/'+inicio+'/'+fin+'/'+codUnidad,
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
                                                                             <td scope="col">${regist.documento}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                            url:'/viewRegistersDemasias/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.documento}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                            url:'/filterDateDemasias/'+inicio+'/'+fin+'/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.documento}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                case '6':
                    if (codHojaC=='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/listRegistrosUniDecomiso/'+codUnidad,
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
                                                                             <td scope="col">${regist.docReferencia}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                    else if (codHojaC=='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDateDecomisos/'+inicio+'/'+fin+'/'+codUnidad,
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
                                                                             <td scope="col">${regist.docReferencia}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                            url:'/viewRegistersDecomisos/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.docReferencia}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                            url:'/filterDateDecomisos/'+inicio+'/'+fin+'/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.docReferencia}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                case '7':
                    if (codHojaC=='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/listRegistrosUniMerma/'+codUnidad,
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
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                    else if (codHojaC=='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDateMerma/'+inicio+'/'+fin+'/'+codUnidad,
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
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                            url:'/viewRegistersMerma/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                            url:'/filterDateMerma/'+inicio+'/'+fin+'/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidadNeta}</td>
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
                case '8':
                    if (codHojaC=='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/listRegistrosUniCajaBoveda/'+codUnidad,
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
                                                                             <td scope="col">${regist.id_tipoTransac.nombre}</td>
                                                                             <td scope="col">${regist.monto}</td>
                                                                             <td scope="col">${regist.saldoInicial}</td>
                                                                             <td scope="col">${regist.saldoFinal}</td>
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
                    else if (codHojaC=='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDateCajaBoveda/'+inicio+'/'+fin+'/'+codUnidad,
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
                                                                             <td scope="col">${regist.id_tipoTransac.nombre}</td>
                                                                             <td scope="col">${regist.monto}</td>
                                                                             <td scope="col">${regist.saldoInicial}</td>
                                                                             <td scope="col">${regist.saldoFinal}</td>
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
                            url:'/viewRegistersCajaBoveda/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.id_tipoTransac.nombre}</td>
                                                                             <td scope="col">${regist.monto}</td>
                                                                             <td scope="col">${regist.saldoInicial}</td>
                                                                             <td scope="col">${regist.saldoFinal}</td>
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
                            url:'/filterDateCajaBoveda/'+inicio+'/'+fin+'/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.id_tipoTransac.nombre}</td>
                                                                             <td scope="col">${regist.monto}</td>
                                                                             <td scope="col">${regist.saldoInicial}</td>
                                                                             <td scope="col">${regist.saldoFinal}</td>
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
                case '9':
                    if (codHojaC=='0' && fcI=='' && fcF==''){
                        $.ajax({
                            type: 'GET',
                            url:'/listRegistrosUniTransferencia/'+codUnidad,
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
                                                                             <td scope="col">${regist.origen.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.destino.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.transportista}</td>
                                                                             <td scope="col">${regist.placaVehiculo}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidad}</td>
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
                    else if (codHojaC=='0' && fcI!='' && fcF!=''){
                        //FILTRADO POR FECHAS
                        const inicio = fcI.replace("T", " ");
                        const fin = fcF.replace("T", " ");
                        $.ajax({
                            type: 'GET',
                            url:'/filterDateTransferencia/'+inicio+'/'+fin+'/'+codUnidad,
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
                                                                             <td scope="col">${regist.origen.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.destino.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.transportista}</td>
                                                                             <td scope="col">${regist.placaVehiculo}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidad}</td>
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
                            url:'/viewRegistersTransferencia/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.origen.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.destino.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.transportista}</td>
                                                                             <td scope="col">${regist.placaVehiculo}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidad}</td>
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
                            url:'/filterDateTransferencia/'+inicio+'/'+fin+'/'+codUnidad+'/'+codHojaC,
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
                                                                             <td scope="col">${regist.origen.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.id_usuario.nombre}</td>
                                                                             <td scope="col">${regist.destino.nom_uniOpe}</td>
                                                                             <td scope="col">${regist.transportista}</td>
                                                                             <td scope="col">${regist.placaVehiculo}</td>
                                                                             <td scope="col">${regist.cod_tipoHoja.cod_tipoHoja}</td>
                                                                             <td scope="col">${regist.cantidad}</td>
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
                case '10':
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
        TipoHc.innerHTML+=`<option value="0">
                                    Seleccionar...
                                </option>`
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
            if (su!='0'){
                btnReport.disabled=true
                btnReport.style.visibility = 'hidden'
                spanACTA.innerHTML=''
                spanACTA.innerHTML+=`<a class="btn btn-danger" href="/auth/reportActa/${su}/${st}">Descargar<span class="fas fa-fw fa-file-pdf"></span></a>`
            }else {
                btnReport.disabled=true
                btnReport.style.visibility = 'hidden'
                spanACTA.innerHTML='<span style="color: #ff0000"><span class="fas fa-fw fa-exclamation-circle"></span> Acta requiere: Oficina </span>'
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
                inputDlt.style.visibility='hidden'
                inputDlt2.style.visibility='hidden'
                fecha1.style.visibility='hidden'
                fecha2.style.visibility='hidden'
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3><span class="fas fa-fw fa-exclamation-circle"></span>Seleccionar reporte</h3>`
                thTable.innerHTML=''
                break;
            case '1':
                x();
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
                x();
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
                x();
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
                x();
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
                x();
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
                y();
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
                x();
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
                z();
                listTipoHcAlmacen.innerHTML=``
                registrosTabla.innerHTML=`<br><h3>Acta inventario de Hoja de Coca</h3>`
                thTable.innerHTML=``
                break;
        }
        function x() {
            inputDlt.style.visibility='visible'
            inputDlt2.style.visibility='visible'

            fecha1.style.visibility='visible'
            fecha2.style.visibility='visible'

            nombreLabel.innerHTML=`FILTRAR TIPO HOJA DE COCA:`
            TipoHc.setAttribute("name","codHc");
            TipoHc.innerHTML=''
            TipoHc.innerHTML+=`<option value="0">
                                    Seleccionar...
                                </option>`
            for (let hc of hojasC){
                TipoHc.innerHTML += `
                            <option value="${hc.cod_tipoHoja}">
                                        <span>${hc.cod_tipoHoja}</span>---<span>${hc.nombre}</span>
                             </option>
                        `
            }
        }
        function y() {
            fecha1.style.visibility='visible'
            fecha2.style.visibility='visible'
            inputDlt.style.visibility='hidden'
            inputDlt2.style.visibility='visible'
            nombreLabel.innerHTML=`FILTRAR TIPO TRANSACCION`
            TipoHc.setAttribute("name","transaccion");
            TipoHc.innerHTML=`
                    <select class="form-control" id="TipoHc">
                                <option value="0">
                                    Seleccionar...
                                </option>
                                <option value="1">
                                    Ingreso
                                </option>
                                <option value="2">
                                    Reembolso
                                </option>
                                <option value="3">
                                    Liquidacion compra
                                </option>
                            </select>
                    `
        }
        function z() {
            inputDlt.style.visibility='hidden'
            inputDlt2.style.visibility='visible'

            fecha1.style.visibility='hidden'
            fecha2.style.visibility='hidden'

            nombreLabel.innerHTML=`SELECCIONAR PERIODO`
            TipoHc.setAttribute("name","periodo");
            TipoHc.innerHTML=`
                    <select class="form-control" id="TipoHc">
                                <option value="0">
                                    Enero
                                </option>
                                <option value="1">
                                    Febrero
                                </option>
                                <option value="2">
                                    Marzo
                                </option>
                                <option value="3">
                                    Abril
                                </option>
                                <option value="4">
                                    Mayo
                                </option>
                                <option value="5">
                                    Junio
                                </option>
                                <option value=6>
                                    Julio
                                </option>
                                <option value="7">
                                    Agosto
                                </option>
                                <option value="8">
                                    Septiembre
                                </option>
                                <option value="9">
                                    Octubre
                                </option>
                                <option value="10">
                                    Noviembre
                                </option>
                                <option value="11">
                                    Diciembre
                                </option>
                            </select>
                    `

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
    }
});