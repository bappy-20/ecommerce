var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitProductForm() {
	this.event.preventDefault();
    let product = {};
    product["productName"] = $("#product_name").val();
    product["sku"] = $("#sku").val();
    product["productLabel"] = $("#product_label").val();
    product["productCategory"] = $("#product_category").val();
    product["shortDiscription"] = $("#short_discription").val(); 
    product["mesuringType"] = $("#mesuring_type").val();  
    product["productPrice"] = $("#product_price").val();
    product["productMrpPrice"] = $("#product_mrpPrice").val()
    product["startingStock"] = $("#starting_stock").val();
    product["safetyStock"] = $("#safety_stock").val(); 
    product["discountType"] = $("#discount_type").val();   
    product["availableDiscount"] = $("#available_discount").val(); 
    if($("#discount").val()=='1'){
    	product["discount"] =true;
    }else{
    	product["discount"] =false;
    }
    
    product["availableOffer"] = $("#available_offer").val();
    if($("#offer").val()=='1'){
    	product["offer"] =true;
    }else{
    	product["offer"] =false;
    } 
    product["productImage"] = $("#logo").val(); 
    product["supplierId"] = $("#supplier_id").val();
    product["brandId"] = $("#brand_id").val();
    
    submitProductFormToServer(product);
}

function submitProductFormToServer(data) {

    let url = baseUrl +'/api/product'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
           alert("Product create succesful!")
            window.location.href = baseUrl+'/admin/product';
           
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getProduct(id){

	let url = baseUrl + "/api/product/"+id;
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
	$("#sku").val(result.sku);
	$("#product_label").val(result.productLabel);
    $("#product_category").val(result.productCategory).change();
    $("#short_discription").val(result.shortDiscription);
    $("#mesuring_type").val(result.mesuringType).change();
    $("#product_price").val(result.productPrice);
    $("#product_mrpPrice").val(result.productMrpPrice);
    $("#starting_stock").val(result.startingStock);
    $("#safety_stock").val(result.safetyStock);
    $("#discount_type").val(result.discountType).change();
    $("#available_discount").val(result.availableDiscount);
    if(result.discount==true){
    	 $("#discount").val("1").change();
    }else{
    	 $("#discount").val("0").change();
    }
   
    $("#available_offer").val(result.availableOffer);
    if(result.offer==true){
   	 $("#offer").val("1").change();
   }else{
   	 $("#offer").val("0").change();
   }
    
    $("#logo").val(result.productImage);
    $("#supplier_id").val(result.supplierId);
    $("#brand_id").val(result.brandId);
}
	function submitProductEditForm() {

	this.event.preventDefault();
	let product = {};
    let id = $("#id").val();
    product["id"] = id;    
    product["productName"] = $("#product_name").val();
    product["sku"] = $("#sku").val();
    product["productLabel"] = $("#product_label").val();
    product["productCategory"] = $("#product_category").val();
    product["shortDiscription"] = $("#short_discription").val(); 
    product["mesuringType"] = $("#mesuring_type").val();  
    product["productPrice"] = $("#product_price").val();
    product["productMrpPrice"] = $("#product_mrpPrice").val()
    product["startingStock"] = $("#starting_stock").val();
    product["safetyStock"] = $("#safety_stock").val(); 
    product["discountType"] = $("#discount_type").val();   
    product["availableDiscount"] = $("#available_discount").val(); 
    if($("#discount").val()=='1'){
    	product["discount"] =true;
    }else{
    	product["discount"] =false;
    }
    
    product["availableOffer"] = $("#available_offer").val();
    if($("#offer").val()=='1'){
    	product["offer"] =true;
    }else{
    	product["offer"] =false;
    } 
    product["productImage"] = $("#logo").val(); 
    product["supplierId"] = $("#supplier_id").val();
    product["brandId"] = $("#brand_id").val();
    submitProductEditFormToServer(product, id);
    
} 
     
    
	function submitProductEditFormToServer(data, id) {

    let url = baseUrl + "/api/product/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          alert("update successful!");
            window.location.href = baseUrl + "/admin/product";
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}



