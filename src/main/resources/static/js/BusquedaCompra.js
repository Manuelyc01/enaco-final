document.addEventListener("DOMContentLoaded", function(event) {
    //CEDULA PRODUCTOR
    const formCedula= document.querySelector('#formCedula');
    const resultP=document.querySelector('#resultP');
    const msg=document.querySelector('#msg');
    //DNI REPRESENTANTE
    const formDni=document.querySelector('#formDni');
    const resultR1=document.querySelector('#resultR1');
    const resultR2=document.querySelector('#resultR2');
    const resultR3=document.querySelector('#resultR3');

    //EVENTO CLICK
    formCedula.addEventListener('keyup',buscarP)
    function buscarP() {
        let cedula = formCedula.value;
        $.ajax({
           type: 'GET',
           url:'/buscarP/'+cedula,
           success:[function (result) {
                resultP.innerHTML=`
                           <input type="text" class="form-control"
                                placeholder="${result}"
                            disabled > 
                        `
           }]
        });

    }
    formDni.addEventListener('keyup',buscarR);
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