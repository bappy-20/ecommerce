var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitIncomingProductForm() {
	this.event.preventDefault();
    let incomingProduct = {};
    incomingProduct["productId"] = $("#product_id").val();
    incomingProduct["purchasePrice"] = $("#purchase_price").val();
    incomingProduct["receivedQty"] = $("#received_qty").val();
    incomingProduct["supplierId"] = $("#supplier_id").val();
        
    submitIncomingProductFormToServer(incomingProduct);
}

function submitIncomingProductFormToServer(data) {

    let url = baseUrl +'/api/incoming-Product'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
            swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/incomingProduct';                      
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getIncomingProduct(id){

	let url = baseUrl + "/api/incoming-Product/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheIncomingProductValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheIncomingProductValues(result){	
	$("#id").val(result.id);
	$("#product_id").val(result.productId1);
	$("#purchase_price").val(result.purchasePrice);
	$("#unit_price").val(result.productIdUnitprice);
	$("#received_qty").val(result.receivedQty);
   
}

	function submitIncomingProductEditForm() {

	this.event.preventDefault();
	let incomingProduct = {};
    let id = $("#id").val();
    incomingProduct["id"] = id;    
    incomingProduct["productId1"] = $("#product_id").val();
    incomingProduct["purchasePrice"] = $("#purchase_price").val();
    incomingProduct["productIdUnitprice"] = $("#unit_price").val();
    incomingProduct["receivedQty"] = $("#received_qty").val();   
     
    submitIncomingProductEditFormToServer(incomingProduct, id);
    
     } 
         
	function submitIncomingProductEditFormToServer(data, id) {

    let url = baseUrl + "/api/incoming-Product/"+id
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	 	swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/incomingProduct";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-purchase-product/"+id;	
		
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
	
	function deleteEntryReturn(id) {		
		let url = baseurl+"/api/delete-return-purchase-product/"+id;	
		
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

	function productUpdate() {
		
	    if ($("#product_id").val() != null && $("#product_id").val() != '') {
	        // Add product to Table
//	    	var k = $("#product_id").val();
//	    	alert(k);
	    	  var selectedText = $("#product_id option:selected").html();
	    	  if($("#discountType").val()=="" && $("#discount1").val()!=""){
	    		  alert("Please Select a Discount Type")
	    	  }else{
	    		  if($("#discountType").val()!=""){
		    		  
		    		  if($("#discountType").val()=='Percent'){
		    			 
		    			  var discount=($("#discount1").val()*$("#purchase_price").val())/100;
		    			  var grandPrice=$("#purchase_price").val()-discount;
		    			  
			    	  }else if($("#discountType").val()=='BDT'){
			    		  var discount=$("#discount1").val();
			    		  var grandPrice=$("#purchase_price").val()-discount;
			    	  }
			    	  var discountType=$("#discountType").val();
		    	  }else{
		    		  var discount=0;
		    		  var grandPrice=$("#purchase_price").val();
		    		  var discountType="n/a";
		    	  }
		    	if($("#productBatchId").val()==""){
		    		 var productBatchId=0;
		    	}else{
		    		 var productBatchId=$("#productBatchId").val();
		    	}
		        productAddToTable(selectedText,discount,grandPrice,productBatchId,discountType);
		        
		        formClear();

		        // Focus to product name field
		        $("#product_id").focus();
	    	  }
	    	
	    }else{
	    	alert("Please select a product first");
	    }
	}

	function productAddToTable(selectedText,discount,grandPrice,productBatchId,discountType) {
	    // First check if a <tbody> tag exists, add one if not
	    this.event.preventDefault();
	    if ($("#productTable tbody").length == 0) {
	        $("#productTable").append("<tbody></tbody>");
	    }
	  
	    // Append product to the table
	    $("#productTable tbody").append(
	        "<tr>" +
	        "<td hidden='true'>" + $("#product_id").val() + "</td>" +
	        "<td>" + selectedText + "</td>" +
	        "<td>" + $("#received_qty").val() + "</td>" +
	        "<td>" + $("#unit_price").val() + "</td>" +
	        "<td>" + $("#purchase_price").val() + "</td>" +
	        "<td>" +discountType + "</td>" +
	        "<td>" + discount + "</td>" +
	        "<td>" + grandPrice + "</td>" +
	        "<td>" + productBatchId + "</td>" +
	        "<td>" +
	        "<button type='button' onclick='productDelete(this);'class='btn btn-danger'>" +
	        "<span class='glyphicon glyphicon-remove' />" +
	        "</button>" +
	        "</td>" +
	        "</tr>");
	}

	function formClear() {
	    $("#product_id").val("");
	    $("#received_qty").val("");
	    $("#unit_price").val("");
	    $("#purchase_price").val("");
	    
	    $("#discountType").val("");
	    $("#discount1").val("");
	    $("#productBatchId").val("");
	}

	function productDelete(ctl) {
	    $(ctl).parents("tr").remove();
	}
	
	function productListSubmitToServer() {
		
	this.event.preventDefault();
	 var T = document.getElementById('productTable');
	 var flag=1;
	 var productList="";
	 
	var supplierId=$('#supplier_id').val();
	var total_price=$('#total_price').val();
	var purchase_number=$('#purchase_number').val();
	
	if($('#discount').val()==""){
		var totalDiscount=0;
	}else{
		var totalDiscount=$('#discount').val();
	}
	var purchase_date=$('#purchase_date').val();
	
	var startDate = purchase_date.split("/").reverse().join("-");
	var purchase_note=$('#purchase_note').val();
	
    $(T).find('> tbody > tr > td').each(function () {
           
        	  productList+=$(this).text()+",";
              
             if(flag==10){
            	 productList+="###";
                 flag=0;                        
             }
             flag++;
          }); 
        if(productList!=""){
        	
            $.ajax({
                type: "post",
                url: baseUrl+"/api/submit-purchase-details",
                data: {
                	supplierId:""+supplierId+"", 
                	totalPrice:""+total_price+"", 
                	purchaseNumber:""+purchase_number+"", 
                	discount:""+totalDiscount+"",  
                	purchaseDate:""+startDate+"", 
                	purchaseNote:""+purchase_note+"", 
                	product:""+productList+"" 
                	}, // parameters
                    success: function(data){
                    if(data=="success"){
                    	 window.location.href = baseUrl + "/admin/incomingProduct";
                    }
               },
           })
        }else{
        	alert("Please add at least one product");
        }


}
	
	function returnProductListSubmitToServer() {
		 var T = document.getElementById('productTable');
		 var flag=1;
		 var productList="";
		 
		var supplierId=$('#supplier_id').val();
		var total_price=$('#total_price').val();
		var purchase_number=$('#purchase_number').val();
		var purchase_date=$('#purchase_date').val();
		
		var startDate = purchase_date.split("/").reverse().join("-");
		var purchase_note=$('#purchase_note').val();
		
	    $(T).find('> tbody > tr > td').each(function () {
	           
	        	  productList+=$(this).text()+",";
	              
	             if(flag==6){
	            	 productList+="###";
	                 flag=0;                        
	             }
	             flag++;
	          }); 
	          
	    $.ajax({
	        type: "post",
	        url: baseUrl+"/api/submit-purchase-return",
	        data: {
	        	supplierId:""+supplierId+"", 
	        	totalPrice:""+total_price+"", 
	        	purchaseNumber:""+purchase_number+"", 
	        	purchaseDate:""+startDate+"", 
	        	purchaseNote:""+purchase_note+"", 
	        	product:""+productList+"" 
	        	}, // parameters
	        success: function(data){
	            if(data=="success"){
	            	 window.location.href = baseUrl + "/admin/return-purhcase-home";
	            }
	       },
	   })

	}
	
//	function getDropDownValue3(){
//		$('select[name="batchatchId"]').on('change', function() {
//		    var batchId = $(this).val();
//		    
//		    if(userType!=null) {        
//		    $.ajax({
//		    url: baseUrl+'/api/user-by-type/'+userType,
//		    type: "GET",
//		    dataType: "json",
//		    success:function(data) {
//		    
//		    $('select[name="userName"]').empty();
//		    $('select[name="userName"]').append('<option value=""> -- Please Select -- </option>');
//		    $.each(data, function(idx, obj){ 
//		         $('select[name="userName"]')
//		         .append('<option value="'+ obj.id +'">'+ obj.username +'</option>');
//		    });
//		        
//		    }
//		    });
//		    }else{
//		        $('select[name="userName"]').append('<option value="">Not found</option>');
//		    } 
//		    });	
//	}
	
	function getDropDownNew22() {
		$('select[name="product_id"]').on('change', function() {
			alert("hi");
		});
	}
	
	
	function getDropDownNew(){
		$('select[name="product_id"]').on('change', function() {
		    //var batchId = $(this).val();
			 var productId = $('#product_id').val();
			 //alert(productId);
		    
		    if(productId!=null) {       
		    	//alert(productId);
		    $.ajax({
		    url: baseUrl+ '/api/product-batch-by-ProductId/'+productId,
		    type: "GET",
		    dataType: "json",
		    success:function(result) {
		    
		    $('select[name="productBatchId"]').empty();
		    $('select[name="productBatchId"]').append('<option value=""> -- Please Select -- </option>');
		    $.each(result.data, function(idx, obj){ 
		         $('select[name="productBatchId"]')
		         .append('<option value="'+ obj.batchNumber +'">'+ obj.batchNumber +'</option>');
		    });
		        
		    }
		    });
		    }else{
		        $('select[name="productBatchId"]').append('<option value="">Not found</option>');
		    } 
		    });	
	}
	
	
	
	function getDropDownValue(){

			 var productMappingId = $('#product_id').val();
			 if(productMappingId!=null) {
				 $.ajax({
					 url: baseUrl+ '/api/product-mapping-for-ProductId/'+productMappingId,
					 type:"GET",
				     dataType: "json",
				     success: function (result) {
					 var productId = result.data;
					 
					 $.ajax({
						 url: baseUrl+ '/api/product-batch-by-ProductId/'+productId,
						 type:"GET",
					     dataType: "json",
					     success: function (result){					 
						  $('select[name="productBatchId"]').empty();
						    $('select[name="productBatchId"]').append('<option value =""> -- Please Select -- </option>');
						    $.each(result.data, function(idx, obj){ 
						         $('select[name="productBatchId"]')
						         .append('<option value="'+ obj.batchNumber +'">'+ obj.batchNumber +'</option>');
						    });
												 
					 }
						 
					 })
		          
				        },
				        error: function (e) {
				            //console.log("ERROR: ", e);
				        	alert("problemetic");
				        }
				 })
				 
			 }
			 else{
			        $('select[name="productBatchId"]').append('<option value="">Not found</option>');
			    } 
	

}
	
