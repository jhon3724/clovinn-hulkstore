function eliminar(id){
	swal({
		  title: "Esta seguro de eliminar el producto?",
		  text: "Una vez eliminado no puedes recuperarlo!",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((OK) => {
		  if (OK) {
			  $.ajax({
				  url:"/eliminar-producto/"+id,
				  success: function(res){
					  console.log(res);
				  }
			  });
		    swal("El producto ha sido eliminado satisfactoriamente!", {
		      icon: "success",
		    }).then((ok) =>{
		    	if(ok){
		    		location.href="/productos"
		    	}
		    });
		  }
		});
}

function controlCampoCosto(){
	if($("#accion").val() == "D"){
		$("#divCosto").hide();
	}else{
		$("#divCosto").show();
	}
}