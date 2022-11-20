var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

/*function submitPriceForm() {
	this.event.preventDefault();
	
    let price = {};
    price["productId"] = $("#product_id").val();
    price["productPrice"] = $("#product_price").val();
    price["dealerPrice"] = $("#dealer_price").val();
    price["retailPrice"] = $("#retailPrice").val();
    price["mrp"] = $("#mrp").val();  
    
    if($("#product_id").val()=="" || $("#product_price").val()==""  || $("#dealer_price").val()==""  
    		|| $("#retailPrice").val()=="" || $("#mrp").val()==""){
    	alert("Input field must not be empty");
    }else{
    	submitPriceFormToServer(price);
    }   
}
*/

function submitPriceForm() {
	this.event.preventDefault();
	
    let price = {};
    price["productId"] = $("#product_id").val();
    price["productPrice"] = $("#product_price").val();
    price["dealerPrice"] = $("#dealer_price").val();
    price["retailPrice"] = 0 ;
    price["mrp"] = 0 ;  
    
    if($("#product_id").val()=="" || $("#dealer_price").val()==""){
    	//alert("Input field must not be empty");
    	swal("Input field must not be empty");
    }else{
    	submitPriceFormToServer(price);
    }   
}


function submitPriceFormToServer(data) {

    let url = baseUrl +'/api/price'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    	swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/price';
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}


function getPrice(id){

	let url = baseUrl + "/api/price/"+id;
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
	$("#product_id").val(result.productId);
	$("#product_name").val(result.productName);
	$("#product_price").val(result.productPrice);
	$("#dealer_price").val(result.dealerPrice);
    $("#retailPrice").val(result.retailPrice);
    $("#mrp").val(result.mrp);
    
}
function submitPriceEditForm() {

	this.event.preventDefault();
	let price = {};
    let id = $("#id").val();
    price["id"] = id;    
    price["productId"] = $("#product_id").val();
    price["productPrice"] = $("#product_price").val();
    price["dealerPrice"] = $("#dealer_price").val();
    price["retailPrice"] = $("#retailPrice").val();
    price["mrp"] = $("#mrp").val();      
    submitPriceEditFormToServer(price, id);
    
     } 
         
	function submitPriceEditFormToServer(data, id) {

    let url = baseUrl + "/api/price/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	swal(`${result.message}`);
          window.location.href = baseUrl + "/admin/price";            
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-price/"+id;
		
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

	


	function getDropDownValue(){
		$('select[name="category_id"]').on('change', function() {
		    var category_id = $(this).val();
		    
		    if(category_id!=null) {        
		    $.ajax({
		    url: baseUrl+'/api/product-by-category/'+category_id,
		    type: "GET",
		    dataType: "json",
		    success:function(data) {
		    
		    $('select[name="product_id"]').empty();
		    $('select[name="product_id"]').append('<option value=""> -- Please Select -- </option>');
		    $.each(data, function(idx, obj){ 
		         $('select[name="product_id"]')
		         .append('<option value="'+ obj.id +'">'+ obj.productName +'</option>');
		    });
		        
		    }
		    });
		    }else{
		        $('select[name="product_id"]').append('<option value="">Product Not found</option>');
		    } 
		    });	
	}
