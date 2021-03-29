document.addEventListener("DOMContentLoaded", function(event) {

    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');
    const TipoHc =document.querySelector('#TipoHc');
    const fcInicio =document.querySelector('#fcInicio');
    const fcFin =document.querySelector('#fcFin');
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
                            const str = (new Date(regist.fecha)).toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
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
        if(valueHoja==0 && fcInicio.value=='' && fcFin.value==''){
            $.ajax({
                type: 'GET',
                url:'/listRegistrosUni/'+valueUni,
                success:[function (result) {
                    if(listTipoHcAlmacen!=null){
                        listTipoHcAlmacen.innerHTML=``
                        if(result.length!=0){
                            listTipoHcAlmacen.innerHTML+=``
                            for(let regist of result){
                                const str = (new Date(regist.fecha)).toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

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
        }else if(valueHoja==0 && fcInicio.value!='' && fcFin.value!=''){
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
                                const str = (new Date(regist.fecha)).toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

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

        }else if(valueHoja!=0 && fcInicio.value=='' && fcFin.value==''){
            $.ajax({
                type: 'GET',
                url:'/viewRegisters/'+valueUni+'/'+valueHoja,
                success: [function (result) {
                    if(listTipoHcAlmacen!=null){
                        listTipoHcAlmacen.innerHTML=``
                        if(result.length!=0){
                            listTipoHcAlmacen.innerHTML+=``
                            for(let regist of result){
                                const str = (new Date(regist.fecha)).toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");
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
        }else if(valueHoja!=0 && fcInicio.value!='' && fcFin.value!=''){
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
                                const str = (new Date(regist.fecha)).toISOString().slice(0, 19).replace(/-/g, "/").replace("T", "    ");

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
    if (selectUnidadOpe!=null){
        selectUnidadOpe.addEventListener('change',registrosU)
    }

    if(btnBuscarAlmacen!=null){
        btnBuscarAlmacen.addEventListener('click',registros)
    }
});