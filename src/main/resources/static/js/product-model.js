var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#productAddForm').validate({
	rules:{
	productname:"required",
	},messages:{
		productname:"Please enter product name",
	},
	submitHandler:function(form){
		submitProductForm();		
	}	
});

jQuery('#productEditForm').validate({
	rules:{
	productname:"required",
	},messages:{
		productname:"Please enter product name",
	},
	submitHandler:function(form){
		submitProductEditForm();
	}	
});

function submitProductForm() {
	this.event.preventDefault();
    let product = {};
    product["productName"] = $("#product_name").val();  
    product["productLabel"] = $("#product_label").val();
    product["shortDiscription"] = $("#short_discription").val();     
    product["productImage"] = $("#logo").val();
    
    submitProductFormToServer(product);
}

function submitProductFormToServer(data) {

    let url = baseUrl +'/api/product-model'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    		swal(`${result.message}`);
            window.location.href = baseUrl+'/admin/product-model';          
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getProduct(id){

	let url = baseUrl + "/api/product-model/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {        
	            setTheProductValues(result.data);
	            console.log(result);	          
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheProductValues(result){	
	$("#id").val(result.id);
	$("#product_name").val(result.productName);
	$("#product_label").val(result.productLabel);
    $("#short_discription").val(result.shortDiscription);
    $("#logo").val(result.productImage);
   
}
	function submitProductEditForm() {

	this.event.preventDefault();
	let product = {};
    let id = $("#id").val();
    product["id"] = id;    
    product["productName"] = $("#product_name").val();
    product["productLabel"] = $("#product_label").val();
    product["shortDiscription"] = $("#short_discription").val();  
   
    product["productImage"] = $("#logo").val(); 
   
    submitProductEditFormToServer(product, id);
    
} 
     
    
	function submitProductEditFormToServer(data, id) {

    let url = baseUrl + "/api/product-model/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	  swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/product-model";
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
      	  swal(`${e}`);

        }
    });
}
	
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-product-model/"+id;
		
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