var baseUrl= "";
var netDiscount=0;
var netTotal=0;
var netGrandTotal=0;
var cashFlag=0;
var cashBackRequiredAmount=0;
var cashBackAmount=0;

function setBaseUrl(url){
	baseUrl = url;
}
	function productUpdate() {	
		this.event.preventDefault();
		var idCheck=false;
	    if ($("#product_id").val() != null && $("#product_id").val() != '') { 
	    	
	    	var t = document.getElementById('T');
	  	    $(t).find('> tbody > tr ').each(function () {
	  	    	if($(this).find("td:eq(0)").find("input").val()==$("#product_id").val()){
	  	    
	  	    	  idCheck=true;
	  	    	}	  	    		  	      
	  	    });
	  	    
	  	    if(idCheck){
	  	    	alert("Produ already added to the list");
	  	    }else{
	  	    	netTotal=parseFloat($("#netTotal").text());
		    	netDiscount=parseFloat($("#netDiscount").text());
		    	netGrandTotal=parseFloat($("#netGrandTotal").text());
		    	
	  	    	var discount=0;
		    	var grandTotal=0;
		    	var product_id = $("#product_id").val();
		    	var received_qty = $("#received_qty").val();
		    	var unit_price = $("#unit_price").val();
		    	var totalPrice = $("#unit_price").val()*$("#received_qty").val();
		    	netTotal+=parseFloat(totalPrice);
		        var selectedText = $("#product_id option:selected").html();
		        $.ajax({
				    url: baseUrl+'/api/get-campaign-by-product/'+product_id,
				    type: "GET",
				    dataType: "json",
				    success:function(result) {
				    	
				    	if(result.statusCode==200){
				    		$.each(result.data, function(idx, obj){	
				    			if(obj.offerType=="value"){
				    				if(obj.discountType=="BDT"){
						    			  discount+=obj.discountAmount*received_qty;
						    			  netDiscount+=parseFloat(obj.discountAmount*received_qty);			    			 
							    		}else{
							    			 discount+=((totalPrice*obj.discountAmount)/100)*received_qty;
							    			 netDiscount+=parseFloat(obj.discountAmount*received_qty);
							    		}
				
							    	  
				    			}else{
				    				let free_product_id=obj.freeProductId;
				    				let freeItem=obj.freeItem;
				    				let freeQuantity=obj.quantity;
				    				let require=obj.requiredQuantity;
				    				if(received_qty>=require){
				    					createFreeProductTable(product_id,free_product_id,freeItem,freeQuantity,0,0,0,0);
				    				}
				    				
				    			}
				    		});
				    		 grandTotal=totalPrice-netDiscount;
					    	 netGrandTotal+=parseFloat(grandTotal);
				    		 productAddToTable(product_id,selectedText,received_qty,unit_price,totalPrice,discount,grandTotal);
					    	   
				    	}else{
				    		grandTotal=totalPrice;
				    		netGrandTotal+=parseFloat(grandTotal);
				    	   productAddToTable(product_id,selectedText,received_qty,unit_price,totalPrice,discount,grandTotal);
				    	}			    	
				    }
		        
				    });	 
		        
	  	    }
	    	
	        formClear();
	        $("#product_id").focus();
	    }
	}

	function productAddToTable(product_id,selectedText,received_qty,unit_price,totalPrice,discount,grandTotal) {
				
		if ($("#T tbody").length == 0) {
	        $("#T").append("<tbody></tbody>");
	    }		
	    $("#T tbody").append(
	        "<tr>" +
	        "<td hidden='true' ><input class='form-control' type='text' value='" + product_id + "'></td>" +
	        "<td><input class='form-control' type='text' value='" + selectedText + "'></td>" +
	        "<td><input class='form-control' type='text' value=" + received_qty + "></td>" +
	        "<td><input class='form-control' type='text' value=" + unit_price + "></td>" +
	        "<td hidden='true' ><input class='form-control' type='text' value='" + product_id + "'></td>" +
	        "<td><input class='form-control' type='text' value=" + totalPrice + "></td>" +
	        "<td><input class='form-control' type='text' value=" + discount + "></td>" +
	        "<td><input class='form-control' type='text' value=" + grandTotal + "></td>" +
	        "<td>" +
	        "<button type='button' onclick='productDelete(this);'class='btn btn-danger'>" +
	        "<span class='glyphicon glyphicon-remove' />" +
	        "</button>" +
	        "</td>" +
	        "</tr>");
	    
	    setNetTotalAmount(netDiscount,netTotal,netGrandTotal);	    
	}

	function createFreeProductTable(product_id,free_product_id,selectedText,received_qty,unit_price,totalPrice,discount,grandTotal) {
		
		if ($("#freeTable tbody").length == 0) {
	        $("#freeTable").append("<tbody></tbody>");
	    }		
	    $("#freeTable tbody").append(
	        "<tr>" +
	        "<td hidden=true ><input class='form-control' type='text' value='" + product_id + "'></td>" +
	        "<td hidden=true ><input class='form-control' type='text' value='" + free_product_id + "'></td>" +
	        "<td><input class='form-control' type='text' value='" + selectedText + "' disabled='true'></td>" +
	        "<td><input class='form-control' type='text' value='" + received_qty + "' disabled='true'></td>" +
	        "<td><input class='form-control' type='text' value='" + unit_price + "' disabled='true'></td>" +
	        "<td><input class='form-control' type='text' value='" + totalPrice + "' disabled='true'></td>" +
	        "<td><input class='form-control' type='text' value='" + discount + "' disabled='true'></td>" +
	        "<td><input class='form-control' type='text' value='" + grandTotal + "' disabled='true'></td>" +
	        "<td><input class='form-control' type='text' value='" + grandTotal + "' disabled='true'></td>" +
	      
	        "</tr>");
	    	    
	}
	function setNetTotalAmount(netDiscount,netTotal,netGrandTotal){			
	    $.ajax({
	    url: baseUrl+'/api/get-available-cash-back-campaign',
	    type: "GET",
	    dataType: "json",
	    success:function(result) {
	    	if(result!=""){
	    		if(cashFlag==0){	    			
		    		$.each(result, function(idx, obj){		    			
	    				if(netTotal>=parseFloat(obj.requiredInvoiceAmount)){
	    					cashFlag++;	
	    				   cashBackRequiredAmount=parseFloat(obj.requiredInvoiceAmount);
	    				   cashBackAmount=parseFloat(obj.discountAmount);
			    	 	   netDiscount+=parseFloat(obj.discountAmount);
			    		   netGrandTotal-=parseFloat(obj.discountAmount);				    				 
			    	      
		    			}	    				    			
	    		});	
	    		 /*$("#netDiscount").text(netDiscount); 
	    		   $("#netTotal").text(netTotal); 
	    		   $("#netGrandTotal").text(netGrandTotal); */
	    		}else{
		    		/*$("#netDiscount").text(netDiscount); 
		    	    $("#netTotal").text(netTotal); 
		    	    $("#netGrandTotal").text(netGrandTotal); */
		    	}
	    	
	    	}
	    }
 });

}	

function formClear() {
	$("#product_id").val("");
	$("#received_qty").val("");
	$("#unit_price").val("");
	$("#purchase_price").val("");
}

	function productDelete(ctl) {
		
		/*netTotal=parseFloat($("#netTotal").text());
    	netDiscount=parseFloat($("#netDiscount").text());
    	netGrandTotal=parseFloat($("#netGrandTotal").text());*/
    	
		var currentRow=$(ctl).closest("tr");  
		var productId=currentRow.find("td:eq(0)").find("input").val(); 
        var col1=currentRow.find("td:eq(5)").find("input").val(); 
        var col2=currentRow.find("td:eq(6)").find("input").val();  
        var col3=currentRow.find("td:eq(7)").find("input").val();  
             
        netTotal-=parseFloat(col1);
               
	   if(netTotal<cashBackRequiredAmount){
	     netDiscount-=cashBackAmount;
	     netGrandTotal+=cashBackAmount;
	     cashBackAmount=0;
	   }else{
		   netDiscount-=parseFloat(col2);
	      
	   }
	  netGrandTotal=netTotal-netDiscount;
    /*$("#netDiscount").text(netDiscount); 
    $("#netTotal").text(netTotal); 
    $("#netGrandTotal").text(netGrandTotal); */
    
    $(ctl).parents("tr").remove();
    
    removeFreeItem(productId);
    
	}

	function removeFreeItem(productId){
		
		var ft = document.getElementById('freeTable');
  	    $(ft).find('> tbody > tr ').each(function () {
  	    	if($(this).find("td:eq(0)").find('input').val()==productId){
  	    		
  	    		$(ft).find("tr").remove();
  	    		
  	    	}	  	    		  	      
  	    });
	}
	
	
	function getDropDownValue(){
		
		$('select[name="product_id"]').on('change', function() {
		    var product_id = $(this).val();	
		    if(product_id!=null) {        
		    $.ajax({
		    url: baseUrl+'/api/get-dealer-price/'+product_id,
		    type: "GET",
		    dataType: "json",
		    success:function(result) {
		    	
		    	$("#unit_price").val(result.data.dealerPrice);
		        
		    }
		    });
		    } 
	  });	
	}
	
	function getAvailableOffer(){		
		    $.ajax({
		    url: baseUrl+'/api/get-available-cash-back-campaign',
		    type: "GET",
		    dataType: "json",
		    success:function(result) {
		    	if(result!=""){
		    	      
		    		$("#cashback").show();
		    		$.each(result, function(idx, obj){
		    		
		    			 if ($("#cashBackTable tbody").length == 0) {
		    			        $("#cashBackTable").append("<tbody></tbody>");
		    			    }
		    			    // Append product to the table
		    			    $("#cashBackTable tbody").append(
		    			        "<tr>" +
		    			        "<td id='cashBackOfferId' hidden=true>" + obj.campaignId + "</td>" +
		    			        "<td>" + obj.campaignName + "</td>" +
		    			        "<td>" + obj.requiredInvoiceAmount + "</td>" +
		    			        "<td>" + obj.discountType + "</td>" +
		    			        "<td>" + obj.discountAmount + "</td>" +
		    			        "<td>" + obj.campaignStartDate + "</td>" +
		    			        "<td>" + obj.campaignEndDate + "</td>" +
		    			        "</tr>");
		    		});	
		    	 
		    	  
		    	}		    	
		        
		    }
	 });
	
	}
	
	function getAvailableProductWiseOffer(){		
	    $.ajax({
	    url: baseUrl+'/api/get-active-product-wise-campaign',
	    type: "GET",
	    dataType: "json",
	    success:function(result) {
	    	
	    	if(result!=""){
	    		 $("#productwise").show();
	    		$.each(result, function(idx, obj){ 
	    			 if ($("#productOfferTable tbody").length == 0) {
	    			        $("#productOfferTable").append("<tbody></tbody>");
	    			    }
	    			    // Append product to the table
	    			    $("#productOfferTable tbody").append(
	    			        "<tr>" +	
	    			        "<td id='productwiseOfferId' hidden=true>" + obj.campaignId + "</td>" +
	    			        "<td >" + obj.campaignName + "</td>" +
	    			        "<td>" + obj.productName + "</td>" +
	    			        "<td>" + obj.discountType + "</td>" +
	    			        "<td>" + obj.discountAmount + "</td>" +
	    			        "<td>" + obj.discountOn + "</td>" +
	    			        "<td>" + obj.freeProduct + "</td>" +
	    			        "<td>" + obj.freeItemQuantity + "</td>" +
	    			        "<td>" + obj.campaignStartDate + "</td>" +
	    			        "<td>" + obj.campaignEndDate + "</td>" +
	    			        "</tr>");
	    		});		    	  
	    	}		    		        
	    }
 });

}
	
	
	$('.className').keypress(function (e) {
		 var key = e.which;
		 if(key == 13)  // the enter key code
		  {
			 event.preventDefault();
			 
			 var productId=$(this).closest('td').siblings().find('#productId').val();			 
			 var received_qty=$(this).val();
			 var tt=$(this);
			 var total=tt.closest('td').siblings().find('#unitPrice').val()*received_qty;
			 
		        $.ajax({
				    url: baseUrl+'/api/get-campaign-by-product/'+productId,
				    type: "GET",
				    dataType: "json",
				    success:function(result) {				    	
				    	if(result.statusCode==200){
				    		if(result.data.discountType=="BDT"){
			    			  discount=result.data.discountAmount*received_qty;		    			 
				    		}else{
				    			 discount=((totalPrice*result.data.discountAmount)/100)*received_qty;				    		
				    		}	
				    	
				    	   tt.closest('td').siblings().find('#discount').val(discount);
				    	   tt.closest('td').siblings().find('#grandTotal').val(total-discount);
				    	   tt.closest('td').siblings().find('#totalPrice').val(total);				    	   
				    	 	}else{
				    	 		tt.closest('td').siblings().find('0').val(discount);
				    	 		tt.closest('td').siblings().find('#totalPrice').val(total);
				    	 		tt.closest('td').siblings().find('#grandTotal').val(total);						    	   
				    	}			    	
				    }		        
				    });			
		  }
		});
	
	function productListEditSubmitToServer() {
		this.event.preventDefault();
		var reqId=$('#id').val();
		let distributorRequisition= {};		
		var T = document.getElementById('T');
		var distributorProductList=[];
		
	    $(T).find('> tbody > tr').each(function () {	    	
	    	let distributorProduct= {};
	    	distributorProduct["productId"]=$(this).find("td:eq(0)").find('input').val();
	    	distributorProduct["productName"]=$(this).find("td:eq(1)").find('input').val();
	    	distributorProduct["receivedQty"]=$(this).find("td:eq(2)").find('input').val();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(3)").find('input').val();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(5)").find('input').val();
	    	distributorProduct["discount"]=$(this).find("td:eq(6)").find('input').val();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(7)").find('input').val();
	    	distributorProductList.push(distributorProduct);	
	        }); 
	    distributorRequisition["distributorRequiredProduct"]=distributorProductList;
	    distributorRequisition["status"]="1";
	    
	    var freeProductList=[];
		 var FT = document.getElementById('freeTable');
	    $(FT).find('> tbody > tr').each(function () {	    	
	    	let freeProduct= {};
	    	freeProduct["productId"]=$(this).find("td:eq(0)").find('input').val();
	    	freeProduct["freeItemId"]=$(this).find("td:eq(1)").find('input').val();
	    	freeProduct["productName"]=$(this).find("td:eq(2)").find('input').val();
	    	freeProduct["receivedQty"]=$(this).find("td:eq(3)").find('input').val();
	    	freeProduct["productUnitPrice"]=$(this).find("td:eq(4)").find('input').val();
	    	freeProduct["totalPrice"]=$(this).find("td:eq(5)").find('input').val();
	    	freeProduct["discount"]=$(this).find("td:eq(6)").find('input').val();
	    	freeProduct["grandTotal"]=$(this).find("td:eq(7)").find('input').val();
	    	freeProductList.push(freeProduct);	
	        }); 
	    distributorRequisition["freeProductlist"]=freeProductList;
	    
	    if(distributorProductList.length>0){
	    	console.log(distributorRequisition);
	    	submitEditFormToServer(distributorRequisition,reqId);	
	    }else{
	    	alert("Please Enter at least one product!");
	    }
	   
	}
	
	
	function submitEditFormToServer(data,id) {
		
	    let url = baseUrl +'/api/update-distributor-requisition/'+id;
	    $.ajax({
	        type: "PUT",
	        contentType: "application/json",
	        url: url,
	        data: JSON.stringify(data),
	        dataType : "json",
	        success: function (result) {
	        	alert("Requisition Approved successful.");
	        	/*window.location.href=baseUrl+"/admin/confirmed-requisition-home";*/
	        	 window.history.back();
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	}
	
	function forwardRequisition(){
		this.event.preventDefault();
		let reqId=$('#id').val();
		var dept=$('#dept').val();
		 let url = baseurl + "/api/update-requisition-status/"+reqId+"/"+dept;
		 $.ajax({
	         type: "PUT",
	         url: url,
	         success: function (result) {
	            alert("Requisition forward succesful");
	            window.history.back();
	         },
	         error: function (e) {
	             console.log("ERROR: ", e);
	         }
	     });
	}

	