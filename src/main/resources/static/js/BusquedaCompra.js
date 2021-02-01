document.addEventListener("DOMContentLoaded", function(event) {
    //CEDULA PRODUCTOR
    const buscCedula = document.querySelector('#buscCedula');
    const formCedula= document.querySelector('#formCedula');
    const resultP=document.querySelector('#resultP');
    //DNI REPRESENTANTE
    const buscDni=document.querySelector('#buscDni');
    const formDni=document.querySelector('#formDni');
    const resultR1=document.querySelector('#resultR1');
    const resultR2=document.querySelector('#resultR2');
    const resultR3=document.querySelector('#resultR3');

    //EVENTO CLICK
    buscCedula.addEventListener('click',buscarP);
    function buscarP(variable) {
        let cedula = formCedula.value;
        $.ajax({
           type: 'GET',
           url:'/buscarP/'+cedula,
           success:[function (result) {
               variable = result
                resultP.innerHTML=`
                           <input type="text" class="form-control"
                                placeholder="${result}"
                            disabled > 
                        `
           }]
        });

    }
    buscDni.addEventListener('click',buscarR);
    function buscarR() {
        let dni = formDni.value;
        $.ajax({
            type: 'GET',
            url:'/buscarR/'+dni,
            success: [function (result) {
                resultR1.innerHTML=`
                <input type="text" class="form-control"
                                placeholder="${result.nombre}"
                            disabled > 
                        `,
                    resultR2.innerHTML=`
                <input type="text" class="form-control"
                                placeholder="${result.direccion}"
                            disabled >    
                    `,
                    resultR3.innerHTML=`
                <input type="text" class="form-control"
                                placeholder="${result.ubigeo}"
                            disabled >    
                    `

            }]
        });
    }
});