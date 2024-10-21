$(document).ready(function () {
    $("tr #btnDelete").click(function (e) {
        e.preventDefault();
        var idp = $(this).parent().find('#idprod').val();        
        swal({
            title: "Esta Seguro de Eliminar?",
            text: "Una una Vez Eliminado, Debera Agregar de Nuevo!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((willDelete) => {
            if (willDelete) {
                eliminar(idp);
                swal(" ¡Oh! ¡Registro Borrado! ", {
                    icon: "success",
                }).then((willDelete) => {
                    if (willDelete) {
                        parent.location.href = "Controlador?accion=Carrito";
                    }
                });
            }
        });
    });
    function eliminar(idp) {
        var url = "Controlador?accion=Delete&id=" + idp;
        console.log("hol");
        $.ajax({
            type: 'POST',
            url: url,
            async: true,
            success: function (r) {
            }
        });
    }

    $("tr #Cantidad").click(function (e) {
        var idp = $(this).parent().find('#idpro').val();
        var cantidad = $(this).parent().find('#Cantidad').val();
        var url = "Controlador?accion=ActualizarCantidad";
        console.log(idp, cantidad);
        $.ajax({
            type: 'POST',
            url: url,
            data: "id=" + idp + "&cantidad=" + cantidad,
            success: function (data, textStatus, jqXHR) {
               parent.location.href = "Controlador?accion=carrito";
            }
        });
    });    
   
});