var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitPriceForm() {
	this.event.preventDefault();
    let price = {};
    price["productId"] = $("#productId").val();
    price["batchNumber"] = $("#batchNumber").val(); 
    
	var manufatureDate1 = $("#manufatureDate").val().split("/").reverse().join("-");
	var expireDate1 = $("#expireDate").val().split("/").reverse().join("-");
	
    price["manufatureDate"] =manufatureDate1;
    price["expireDate"] = expireDate1;     
    submitPriceFormToServer(price);
}

function submitPriceFormToServer(data) {

    let url = baseUrl +'/api/product-batch'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    		swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/product-batch';           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getPrice(id){

	let url = baseUrl + "/api/product-batch/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setThePriceValues(result.data);
	            console.log(result);
	             
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setThePriceValues(result){	
	$("#id").val(result.id);
	$("#productId").val(result.productId);	
	$("#manufatureDate").val(result.manufatureDate);
	$("#expireDate").val(result.expireDate);
    $("#batchNumber").val(result.batchNumber);
    
}

	function submitPriceEditForm() {

	this.event.preventDefault();
	let price = {};
    let id = $("#id").val();
    price["id"] = id;    
    price["productId"] = $("#productId").val();
    price["batchNumber"] = $("#batchNumber").val();    
	//var manufatureDate1 = $("#manufatureDate").val().split("-").reverse().join("/");
	//var expireDate1 = $("#expireDate").val().split("-").reverse().join("/");
    
	var manufatureDate1 = $("#manufatureDate").val();
	var expireDate1 = $("#expireDate").val();
    price["manufatureDate"] =manufatureDate1;
    price["expireDate"] = expireDate1;   
    submitPriceEditFormToServer(price, id);
    
     } 
     
    
	function submitPriceEditFormToServer(data, id) {

    let url = baseUrl + "/api/product-batch/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    		swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/product-batch";          
        },
        error: function (e) {
            console.log("ERROR: ", e);
      	  swal(`${e}`);

        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-product-batch/"+id;
		
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
