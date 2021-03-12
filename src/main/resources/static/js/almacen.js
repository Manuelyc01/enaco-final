document.addEventListener("DOMContentLoaded", function(event) {

    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');
    const TipoHc =document.querySelector('#TipoHc');
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
                            listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${regist.id_inventario}</td>
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
        $.ajax({
            type: 'GET',
            url:'/viewRegisters/'+valueUni+'/'+valueHoja,
            success: [function (result) {
                if(listTipoHcAlmacen!=null){
                    listTipoHcAlmacen.innerHTML=``
                    if(result.length!=0){
                        listTipoHcAlmacen.innerHTML+=``
                        for(let regist of result){
                            listTipoHcAlmacen.innerHTML += `
                            <tr>
                            <td scope="row">${regist.id_inventario}</td>
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
    if (selectUnidadOpe!=null){
        selectUnidadOpe.addEventListener('change',registrosU)
    }

    if(btnBuscarAlmacen!=null){
        btnBuscarAlmacen.addEventListener('click',registros)
    }
});