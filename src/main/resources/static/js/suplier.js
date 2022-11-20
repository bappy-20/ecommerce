var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#supplierAddForm').validate({
	rules:{
	name:"required",
	phone:"required",
	},messages:{
		name:"Please enter supplier name",
		phone:"Please enter supplier phone",
	},
	submitHandler:function(form){
		submitSupplierForm();		
	}	
});

jQuery('#supplierEditForm').validate({
	rules:{
	name:"required",
	phone:"required",
	},messages:{
		name:"Please enter supplier name",
		phone:"Please enter supplier phone number",
	},
	submitHandler:function(form){
		submitSupplierEditForm();
	}	
});

function submitSupplierForm() {
	this.event.preventDefault();
    let supplier = {};
    supplier["name"] = $("#name").val();
    supplier["address"] = $("#address").val();
    supplier["note"] = $("#note").val();
    supplier["phone"] = $("#phone").val();
   
    submitSupplierFormToServer(supplier);
}

function submitSupplierFormToServer(data) {

    let url = baseUrl +'/api/supplier'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
        swal(`${result.message}`);
            window.location.href =  baseUrl+'/admin/supplier';
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getSupplier(id){

	let url = baseUrl + "/api/supplier/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheSupplierValues(result.data);
	            console.log(result);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheSupplierValues(result){	
	$("#id").val(result.id);
	$("#name").val(result.name);
	$("#address").val(result.address);
	$("#note").val(result.note);
	$("#phone").val(result.phone);
    
 
}

	function submitSupplierEditForm() {

	this.event.preventDefault();
	let supplier = {};
    let id = $("#id").val();
    supplier["id"] = id;    
    supplier["name"] = $("#name").val();
    supplier["address"] = $("#address").val();
    supplier["note"] = $("#note").val();
    supplier["phone"] = $("#phone").val();
    submitSupplierEditFormToServer(supplier, id);
    
     } 
     
    
	function submitSupplierEditFormToServer(data, id) {

    let url = baseUrl + "/api/supplier/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
        swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/supplier";
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-supplier/"+id;
		
	    	  swal({
	    	      title: "Are you sure you want to delete?",
	    	      text: "You will not be able to recover this data!",
	    	      icon: "warning",
	    	      buttons: [
	    	        'No, cancel it!',
	    	        'Yes, I am sure!'
	    	      ],
	    	      dangerMode: true,
	    	    }).then(function(isConfirm) {
	    	      if (isConfirm) {
	    	        swal({
	    	          title: 'Yes!',
	    	          text: 'Deletion Successful!!',
	    	          icon: 'success'
	    	        }).then(function() {
	    	        	 $.ajax({
	    	                 type: "DELETE",
	    	                 url: url,
	    	                 contentType: 'application/json; charset=utf-8',
	    	                 dataType: "JSON",
	    	                 success: function (response) {
	    	               	  window.location.reload();
	    	                 },
	    	                 error: function () {
	    	                     Swal.fire('error')
	    	                 }
	    	             }); 
	    	          
	    	        });
	    	      } else {
	    	        swal("Cancelled", "Your data is safe :)", "error");
	    	      }
	    	    });
		}



