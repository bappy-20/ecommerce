var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#productMapppingAddForm').validate({
	rules:{
	company_id:"required",
	brand_id:"required",
	product_id:"required",
	product_category:"required",
	mesuring_type:"required",
	measuringQuantity:"required",
	startingStock:"required",
	safety_stock:"required",
	},messages:{
		company_id:"please enter Comapany name",
		brand_id:"Please enter your brand name",
		product_id:"please enter product name",
		product_category:"Please enter product category name",	
		mesuring_type:"please enter measuring type",
		measuringQuantity:"Please enter measuring quantity",	
		startingStock:"please enter starting stock",
		safety_stock:"Please enter your safety stock",
		},
	submitHandler:function(form){
		submitProductForm();
	}	
});

jQuery('#productMapppinEditForm').validate({
	rules:{
	company_id:"required",
	brand_id:"required",
	product_id:"required",
	product_category:"required",
	mesuring_type:"required",
	measuringQuantity:"required",
	startingStock:"required",
	safety_stock:"required",
	},messages:{
		company_id:"please enter Comapany name",
		brand_id:"Please enter your brand name",
		product_id:"please enter product name",
		product_category:"Please enter product category name",	
		mesuring_type:"please enter measuring type",
		measuringQuantity:"Please enter measuring quantity",	
		startingStock:"please enter starting stock",
		safety_stock:"Please enter your safety stock",
		},
	submitHandler:function(form){
			submitProductEditForm();
	}	
});


function submitProductForm() {
	
	this.event.preventDefault();
    let product = {};
    product["companyId"] = $("#company_id").val();
    product["barCode"] = $("#barCode").val();
    product["productId"] = $("#product_id").val();
    product["productName"] = $("#product_id option:selected" ).text();
    product["sku"] = $("#sku").val();
    product["productCategory"] = $("#product_category").val(); 
    product["productSubCategory"] = $("#product_sub_category").val(); 
    product["mesuringType"] = $("#mesuring_type").val();
    product["mesuringQuantity"] = $("#mesuringQuantity").val();
    product["startingStock"] = $("#starting_stock").val();
    product["safetyStock"] = $("#safety_stock").val();   
    product["supplierId"] = $("#supplier_id").val();
    product["brandId"] = $("#brand_id").val();    
    submitProductFormToServer(product);
}

function submitProductFormToServer(data) {

    let url = baseUrl +'/api/product-mapping'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    		swal(`${result.message}`);
            window.location.href = baseUrl+'/admin/product-mapping';           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getProduct(id){

	let url = baseUrl + "/api/product-mapping/"+id;
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
	$("#company_id").val(result.companyId).change();
	$("#barCode").val(result.barCode);	    
	$("#product_id").val(result.productId).change();
	$("#sku").val(result.sku);	
    $("#product_category").val(result.productCategory).change(); 
    $("#product_sub_category").val(result.productSubCategory).change();     
    $("#mesuring_type").val(result.mesuringType).change();
    $("#mesuringQuantity").val(result.mesuringQuantity);
    $("#starting_stock").val(result.startingStock);
    $("#safety_stock").val(result.safetyStock);
    $("#supplier_id").val(result.supplierId);
    $("#brand_id").val(result.brandId).change();
}
	function submitProductEditForm() {

	this.event.preventDefault();
	let product = {};
    let id = $("#id").val();
    product["id"] = id;  
    product["companyId"] = $("#company_id").val();
    product["barCode"] = $("#barCode").val();
    product["productId"] = $("#product_id").val();
    product["productName"] = $("#product_id option:selected").text();
    product["sku"] = $("#sku").val();
    product["productCategory"] = $("#product_category").val();
    product["productSubCategory"] = $("#product_sub_category").val();     
    product["mesuringType"] = $("#mesuring_type").val(); 
    product["mesuringQuantity"] = $("#mesuringQuantity").val(); 
    product["startingStock"] = $("#starting_stock").val();
    product["safetyStock"] = $("#safety_stock").val();  
    product["supplierId"] = $("#supplier_id").val();
    product["brandId"] = $("#brand_id").val();
    submitProductEditFormToServer(product, id);
    
}    
	function submitProductEditFormToServer(data, id) {

    let url = baseUrl + "/api/product-mapping/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          swal(`${result.message}`);
          window.location.href = baseUrl + "/admin/product-mapping";
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-product-mapping/"+id;
		
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
		$('select[name="product_category"]').on('change', function() {
		    var product_category = $(this).val();
		    
		    if(product_category!=null) {        
		    $.ajax({
		    url: baseUrl+'/api/get-sub-category/'+product_category,
		    type: "GET",
		    dataType: "json",
		    success:function(data) {
		    
		    $('select[name="product_sub_category"]').empty();
		    $('select[name="product_sub_category"]').append('<option value=""> -- Please Select -- </option>');
		    $.each(data, function(idx, obj){ 
		         $('select[name="product_sub_category"]')
		         .append('<option value="'+ obj.id +'">'+ obj.name +'</option>');
		    });
		        
		    }
		    });
		    }else{
		        $('select[name="product_sub_category"]').append('<option value="">Not found</option>');
		    } 
		    });	
	}
	
	function getDropDownValueForBrand(){
		$('select[name="company_id"]').on('change', function() {
		    var company_id = $(this).val();
		    
		    if(company_id!=null) {        
		    $.ajax({
		    url: baseUrl+'/api/get-brand-list/'+company_id,
		    type: "GET",
		    dataType: "json",
		    success:function(data) {
		    
		    $('select[name="brand_id"]').empty();
		    $('select[name="brand_id"]').append('<option value=""> -- Please Select -- </option>');
		    $.each(data, function(idx, obj){ 
		         $('select[name="brand_id"]')
		         .append('<option value="'+ obj.id +'">'+ obj.brandName +'</option>');
		    });
		        
		    }
		    });
		    }else{
		        $('select[name="brand_id"]').append('<option value="">Not found</option>');
		    } 
		    });	
	}

