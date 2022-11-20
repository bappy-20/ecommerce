var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
jQuery('#retailTypeAddForm').validate({
	rules:{
	retailtype:"required",
	},messages:{
		retailtype:"Please enter retaol type name",
	},
	submitHandler:function(form){
		submitRetailTypeForm();		
	}	
});

jQuery('#retailTypeEditForm').validate({
	rules:{
	retailtype:"required",
	},messages:{
		retailtype:"Please enter retail type name",
	},
	submitHandler:function(form){
		submitRetailTypeEditForm();
	}	
});

function submitRetailTypeForm() {
	this.event.preventDefault();
    let retailType = {};
    retailType["retailType"] = $("#retail_type").val();
   
    submitRetailTypeFormToServer(retailType);
}

function submitRetailTypeFormToServer(data) {

    let url = baseUrl +'/api/retail-Type'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    		swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/retailType';
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
function getRetailType(id){
	let url = baseUrl + "/api/retail-Type/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheRetailTypeValues(result.data);
	            console.log(result);	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}

function setTheRetailTypeValues(result){	
	$("#id").val(result.id);
	$("#retail_type").val(result.retailType);
 
}

	function submitRetailTypeEditForm() {

	this.event.preventDefault();
	let retailType = {};
    let id = $("#id").val();
    retailType["id"] = id;    
    retailType["retailType"] = $("#retail_type").val();
    
    submitRetailTypeEditFormToServer(retailType, id);
    
     }     
	function submitRetailTypeEditFormToServer(data, id) {

    let url = baseUrl + "/api/retail-Type/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	 	swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/retailType";
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-retail-type/"+id;
		
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
