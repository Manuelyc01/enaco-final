//ID INPUTS
document.addEventListener("DOMContentLoaded", function(event) {
    //INPUT BUSQUEDA
    const formUnidadOpe = document.querySelector('#formUnidadOpe');

    //RESULTADOS
    const selectUnidadOpe =document.querySelector('#selectUnidadOpe');

    //MODEL LIST
    const listUnidad=listUnidadOpe;

    //FILTRAR USUARIOS
    const filtrar = () =>{
        selectUnidadOpe.innerHTML='';
        const texto = formUnidadOpe.value.toLowerCase();
        for(let unidadOpe of listUnidad){
            let nombre = unidadOpe.nom_uniOpe.toLowerCase();
            if(nombre.indexOf(texto) !== -1){
                selectUnidadOpe.innerHTML += `
                            <option value="${unidadOpe.id_UniOpe}">
                                        <span>${unidadOpe.cod_uniOpe}</span>---<span>${unidadOpe .nom_uniOpe}</span>
                             </option>
                        `
            }
        }
        if(selectUnidadOpe.innerHTML === ''){
            selectUnidadOpe.innerHTML+= `
                            <option value="0">Unidad no encontrada</option>
                        `
        }
    }
    formUnidadOpe.addEventListener('keyup',filtrar)
});

