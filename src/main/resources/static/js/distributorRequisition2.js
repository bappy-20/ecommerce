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

jQuery('#quickForm').validate({
	rules:{
	distributor_id:"required",
	unit_price:"required",
	receivedQty:"required",
	},messages:{
		distributor_id:"Please enter distributor",
		unit_price:"Please enter unit price",
		receivedQty:"Please enter received quantity",
	},
	submitHandler:function(form){
		//submitBrandForm();
		productUpdate();
	}	
});

	function productUpdate() {	
		this.event.preventDefault();
		var idCheck=false;
	    if ($("#product_id").val() != null && $("#product_id").val() != '') { 
	    	
	    	var t = document.getElementById('productTable');
	  	    $(t).find('> tbody > tr ').each(function () {
	  	    	if($(this).find("td:eq(0)").text()==$("#product_id").val()){
	  	    
	  	    	  idCheck=true;
	  	    	}	  	    		  	      
	  	    });
	  	    
	  	    if(idCheck){
	  	    	alert("Product already added to the list");
	  	    }else{
	  	    	var discount=0;
		    	var grandTotal=0;
		    	var product_id = $("#product_id").val();
		    	var received_qty = $("#received_qty").val();
		    	var unit_price = $("#unit_price").val();
		    	var totalPrice = $("#unit_price").val()*$("#received_qty").val();
		    	netTotal+=parseFloat(totalPrice);
		    	netDiscount=parseFloat($("#netDiscount").text());
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
		let grand1=totalPrice-discount;		
		if ($("#productTable tbody").length == 0) {
	        $("#productTable").append("<tbody></tbody>");
	    }		
	    $("#productTable tbody").append(
	        "<tr>" +
	        "<td hidden=true >" + product_id + "</td>" +
	        "<td>" + selectedText + "</td>" +
	        "<td>" + received_qty + "</td>" +
	        "<td>" + unit_price + "</td>" +
	        "<td>" + totalPrice + "</td>" +
	        "<td>" + discount + "</td>" +
	        "<td>" + grand1 + "</td>" +
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
	    		 $("#netDiscount").text(netDiscount); 
	    		   $("#netTotal").text(netTotal); 
	    		   $("#netGrandTotal").text(netTotal-netDiscount); 
	    		}else{
		    		$("#netDiscount").text(netDiscount); 
		    	    $("#netTotal").text(netTotal); 
		    	    $("#netGrandTotal").text(netTotal-netDiscount); 
		    	}
	    	
	    	}else{
	    		$("#netDiscount").text(netDiscount); 
	    	    $("#netTotal").text(netTotal); 
	    	    $("#netGrandTotal").text(netTotal-netDiscount); 
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
		var currentRow=$(ctl).closest("tr");  
		var productId=currentRow.find("td:eq(0)").text(); 
        var col1=currentRow.find("td:eq(4)").text(); 
        var col2=currentRow.find("td:eq(5)").text(); 
        var col3=currentRow.find("td:eq(6)").text(); 
             
       netTotal-=parseFloat(col1);
        
        let dis1=$("#netDiscount").text();
        if(netTotal<cashBackRequiredAmount){
   		 cashFlag=0;
   	     col2+=cashBackAmount;
   	     cashBackAmount=0;
   	   }
        dis1-=col2;
        netGrandTotal=netTotal-dis1;
        
        $("#netDiscount").text(dis1); 
        $("#netTotal").text(netTotal); 
        $("#netGrandTotal").text(netGrandTotal);
        $(ctl).parents("tr").remove();
    
        removeFreeItem(productId);
    
	}

	function removeFreeItem(productId){
		
		var ft = document.getElementById('freeTable');
  	    $(ft).find('> tbody > tr ').each(function () {
  	    	if($(this).find("td:eq(0)").find("input").val()==productId){
  	    		
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
	
	function productListSubmitToServer() {
		this.event.preventDefault();
		
		var today = new Date();
		var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
		var dateTime = date+' '+time;
    	
		
		let distributorRequisition= {};		
		var uniqueNumber= Math.floor(new Date().valueOf() * Math.random());
		distributorRequisition["dealerId"]=$("#dealerId").text();
		distributorRequisition["requisitionNumber"]=uniqueNumber;
		distributorRequisition["productWiseOfferId"]=$("#productwiseOfferId").text();
		distributorRequisition["cashBackId"]=$("#cashBackOfferId").text();
		distributorRequisition["totalPrice"]=$("#netTotal").text();
		distributorRequisition["netDiscount"]=$("#netDiscount").text();
		distributorRequisition["grandTotal"]=$("#netGrandTotal").text();
		distributorRequisition["status"]="0";
		
		var T = document.getElementById('productTable');
		var distributorProductList=[];
	    $(T).find('> tbody > tr').each(function () {	    	
	    	let distributorProduct= {};
	    	distributorProduct["productId"]=$(this).find("td:eq(0)").text();
	    	distributorProduct["productName"]=$(this).find("td:eq(1)").text();
	    	distributorProduct["receivedQty"]=$(this).find("td:eq(2)").text();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(3)").text();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(4)").text();
	    	distributorProduct["discount"]=$(this).find("td:eq(5)").text();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(6)").text();
	    	distributorProductList.push(distributorProduct);
	
	        }); 
	    distributorRequisition["distributorRequiredProduct"]=distributorProductList;
		var freeProductList=[];
		 var FT = document.getElementById('freeTable');
	    $(FT).find('> tbody > tr').each(function () {	    	
	    	let freeProduct= {};
	    	freeProduct["productId"]=$(this).find("td:eq(0)").text();
	    	freeProduct["freeItemId"]=$(this).find("td:eq(1)").text();
	    	freeProduct["productName"]=$(this).find("td:eq(2)").text();
	    	freeProduct["receivedQty"]=$(this).find("td:eq(3)").text();
	    	freeProduct["productUnitPrice"]=$(this).find("td:eq(4)").text();
	    	freeProduct["totalPrice"]=$(this).find("td:eq(5)").text();
	    	freeProduct["discount"]=$(this).find("td:eq(6)").text();
	    	freeProduct["grandTotal"]=$(this).find("td:eq(7)").text();
	    	freeProductList.push(freeProduct);	
	        }); 
	    distributorRequisition["freeProductlist"]=freeProductList;
	  
	    if(distributorProductList.length>0){
	    	
	    	 showInDataTable(distributorRequisition);	
	    } 
	    else {
	    	alert("Please Enter at least one product!");

	    }
	}
	
	function allDistributorproductListSubmitToServer() {
		this.event.preventDefault();
		
		var today = new Date();
		var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
		var dateTime = date+' '+time;
		
		let distributorRequisition= {};		
		var uniqueNumber= Math.floor(new Date().valueOf() * Math.random());
		distributorRequisition["dealerId"]=$("#distributor_id").val();
		distributorRequisition["requisitionNumber"]=uniqueNumber;
		distributorRequisition["productWiseOfferId"]=$("#productwiseOfferId").text();
		distributorRequisition["cashBackId"]=$("#cashBackOfferId").text();
		distributorRequisition["totalPrice"]=$("#netTotal").text();
		distributorRequisition["netDiscount"]=$("#netDiscount").text();
		distributorRequisition["grandTotal"]=$("#netGrandTotal").text();
		distributorRequisition["status"]="1";
		
		var T = document.getElementById('productTable');
		var distributorProductList=[];
	    $(T).find('> tbody > tr').each(function () {	    	
	    	let distributorProduct= {};
	    	distributorProduct["productId"]=$(this).find("td:eq(0)").text();
	    	distributorProduct["productName"]=$(this).find("td:eq(1)").text();
	    	distributorProduct["receivedQty"]=$(this).find("td:eq(2)").text();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(3)").text();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(4)").text();
	    	distributorProduct["discount"]=$(this).find("td:eq(5)").text();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(6)").text();
	    	distributorProductList.push(distributorProduct);
	
	        }); 
	    distributorRequisition["distributorRequiredProduct"]=distributorProductList;
		var freeProductList=[];
		 var FT = document.getElementById('freeTable');
	    $(FT).find('> tbody > tr').each(function () {	    	
	    	let freeProduct= {};
	    	freeProduct["productId"]=$(this).find("td:eq(0)").text();
	    	freeProduct["freeItemId"]=$(this).find("td:eq(1)").text();
	    	freeProduct["productName"]=$(this).find("td:eq(2)").text();
	    	freeProduct["receivedQty"]=$(this).find("td:eq(3)").text();
	    	freeProduct["productUnitPrice"]=$(this).find("td:eq(4)").text();
	    	freeProduct["totalPrice"]=$(this).find("td:eq(5)").text();
	    	freeProduct["discount"]=$(this).find("td:eq(6)").text();
	    	freeProduct["grandTotal"]=$(this).find("td:eq(7)").text();
	    	freeProductList.push(freeProduct);	
	        }); 
	    distributorRequisition["freeProductlist"]=freeProductList;
	  
	    if(distributorProductList.length>0){
	    	if($("#distributor_id").val()==""){
	    		alert("Please select distributor");
			}else{
				//alert("hi");
				submitToServer(distributorRequisition);
			}	    	 	
	    } 
	    else {
	    	alert("Please Enter at least one product!");

	    }
	   

	}
	function productListConfirmSubmitToServer() {
		this.event.preventDefault();
		
		let distributorRequisition= {};		
		var uniqueNumber= Math.floor(new Date().valueOf() * Math.random());

		distributorRequisition["dealerId"]=$("#dealerId").text();
		distributorRequisition["requisitionNumber"]=uniqueNumber;
		distributorRequisition["productWiseOfferId"]=$("#productwiseOfferId").text();
		distributorRequisition["cashBackId"]=$("#cashBackOfferId").text();
		distributorRequisition["totalPrice"]=$("#netTotal").text();
		distributorRequisition["netDiscount"]=$("#netDiscount").text();
		distributorRequisition["grandTotal"]=$("#netGrandTotal").text();
		distributorRequisition["status"]="1";
		
		 var T = document.getElementById('productTable');
		var distributorProductList=[];
	    $(T).find('> tbody > tr').each(function () {	    	
	    	let distributorProduct= {};
	    	distributorProduct["productId"]=$(this).find("td:eq(0)").text();
	    	distributorProduct["productName"]=$(this).find("td:eq(1)").text();
	    	distributorProduct["receivedQty"]=$(this).find("td:eq(2)").text();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(3)").text();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(4)").text();
	    	distributorProduct["discount"]=$(this).find("td:eq(5)").text();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(6)").text();
	    	distributorProductList.push(distributorProduct);
	
	        }); 
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
	    alert(JSON.stringify(freeProductList));
	    distributorRequisition["freeProductlist"]=freeProductList;
	    distributorRequisition["distributorRequiredProduct"]=distributorProductList;
	    if(distributorProductList.length>0){
	    	 submitToServer(distributorRequisition);	
	    }else{
	    	alert("Please Enter at least one product!");
	    }
	}
	
	function submitToServer(data) {

	    let url = baseUrl +'/api/distributor-requisition'
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: url,
	        data: JSON.stringify(data),
	        dataType : "json",
	        success: function (result) {
	        	alert("Thanks for confirming requisition\nPlease wait for further process.");	            
	            window.location.href=baseUrl+"/admin/distributor-requisition";
	            
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	}
	
	function showInDataTable(data) {

	    let url = baseUrl +'/api/distributor-requisition-delearId'
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: url,
	        data: JSON.stringify(data),
	        dataType : "json",
	        success: function (result) {
	    	alert("called");
	        	alert("Thanks for confirming requisition\nPlease wait for further process.");	            
	            window.location.href=baseUrl+"/admin/distributor-requisition";            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	}
	
	/*
	$('.className').keypress(function (e) {
		 var key = e.which;
		 if(key == 13)  // the enter key code
		  {
			 event.preventDefault();
			 var discountVal=0;
			 var productId=$(this).closest('td').siblings().find('#productId').val();			 
			 var received_qty=$(this).val();
			 var tt=$(this);
			 var total=tt.closest('td').siblings().find('#unitPrice').val()*received_qty;
			 
//			 var stock=$(this).closest('td').siblings().find('#stockQuantityy').val();
//		        if(received_qty > stock) {
//		        	alert("required quantity cross stock quantity");
//					 
//				 }
//		        else {
//		        	alert("ok");
//		        }
//			
		        $.ajax({
				    url: baseUrl+'/api/get-campaign-by-product/'+productId,
				    type: "GET",
				    dataType: "json",
				    success:function(result) {				    	
				    	if(result.statusCode==200){
				    		if(result.data[0].offerType=="value"){
					    		if(result.data[0].discountType=="BDT"){
					    			
					    			  discountVal=result.data[0].discountAmount*received_qty;		    			 
						    		}else{
						    			 discountVal=((totalPrice*result.data[0].discountAmount)/100)*received_qty;				    		
						    		}
						    		
						    	   tt.closest('td').siblings().find('#discount').val(discountVal);
						    	   tt.closest('td').siblings().find('#grandTotal').val(total-discountVal);
						    	   tt.closest('td').siblings().find('#totalPrice').val(total);
				    		}else{
				    			tt.closest('td').siblings().find('0').val(discountVal);
				    	 		tt.closest('td').siblings().find('#totalPrice').val(total);
				    	 		tt.closest('td').siblings().find('#grandTotal').val(total);
				    		}
				    	   
				    	 	}else{
				    	 		tt.closest('td').siblings().find('0').val(discountVal);
				    	 		tt.closest('td').siblings().find('#totalPrice').val(total);
				    	 		tt.closest('td').siblings().find('#grandTotal').val(total);						    	   
				    	}			    	
				    }		        
				    });	
		        
		  }
		});*/
	
	$(".className").change(function(){
		
		 var discountVal=0;
		 var productId=$(this).closest('td').siblings().find('#productId').val();			 
		 let received_qty=$(this).val();
		 var tt=$(this);
		 var total=tt.closest('td').siblings().find('#unitPrice').val()*received_qty;
		 
		 let stock=$(this).closest('td').siblings().find('#stockQuantityy').val();
	//	 alert(received_qty+" "+stock);
	        if(parseInt(received_qty) > parseInt(stock)) {
	        	alert("required quantity cross stock quantity");
				 
			 }
	        else {
	        //	alert("ok");
	        }
		
	        $.ajax({
			    url: baseUrl+'/api/get-campaign-by-product/'+productId,
			    type: "GET",
			    dataType: "json",
			    success:function(result) {				    	
			    	if(result.statusCode==200){
			    		if(result.data[0].offerType=="value"){
				    		if(result.data[0].discountType=="BDT"){
				    			
				    			  discountVal=result.data[0].discountAmount*received_qty;	
				    			
					    		}else{
					    			 discountVal=((totalPrice*result.data[0].discountAmount)/100)*received_qty;				    		
					    		}
					    		
					    	   tt.closest('td').siblings().find('#discount').val(discountVal);
					    	   tt.closest('td').siblings().find('#grandTotal').val(total-discountVal);
					    	   tt.closest('td').siblings().find('#totalPrice').val(total);
					    	   sumTotal();
					    	   
			    		}else{
			    			tt.closest('td').siblings().find('0').val(discountVal);
			    	 		tt.closest('td').siblings().find('#totalPrice').val(total);
			    	 		tt.closest('td').siblings().find('#grandTotal').val(total);
			    	 		sumTotal();
			    		}
			    	   
			    	 	}else{
			    	 		tt.closest('td').siblings().find('0').val(discountVal);
			    	 		tt.closest('td').siblings().find('#totalPrice').val(total);
			    	 		tt.closest('td').siblings().find('#grandTotal').val(total);	
			    	 	  sumTotal();
			    	}			    	
			    }		        
			    });	
		 
		});
	
	function sumTotal(){
		  var table = document.getElementById("T"),
				grandTotalValue = 0;
			discountValue = 0;
			totalValue = 0;
        
        for(var i = 1; i < table.rows.length; i++)
        {
      	  grandTotalValue = grandTotalValue + parseInt(table.rows[i].cells[9].children[0].value);
      	 discountValue = discountValue + parseInt(table.rows[i].cells[8].children[0].value);
      	totalValue = totalValue + parseInt(table.rows[i].cells[7].children[0].value);
        }
        
      //  alert(grandTotalValue+" "+discountValue+" "+totalValue);
        $("#netGrandTotal").text(grandTotalValue);
        $("#netDiscount").text(discountValue);
        $("#netTotal").text(totalValue);
		}
	
	
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
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(4)").find('input').val();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(5)").find('input').val();
	    	distributorProduct["discount"]=$(this).find("td:eq(6)").find('input').val();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(7)").find('input').val();
	    	distributorProductList.push(distributorProduct);	
	        }); 
	    distributorRequisition["distributorRequiredProduct"]=distributorProductList;
	    distributorRequisition["status"]="1";
	    if(distributorProductList.length>0){

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

	function productListSubmitToServerBySuppluyChain() {
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
	    	distributorProduct["stockQty"]=$(this).find("td:eq(3)").find('input').val();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(6)").find('input').val();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(7)").find('input').val();
	    	distributorProduct["discount"]=$(this).find("td:eq(8)").find('input').val();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(9)").find('input').val();
	    	distributorProductList.push(distributorProduct);
	    	
	        }); 
	    distributorRequisition["requisitionSupplyChainApprove"]=distributorProductList;
	    distributorRequisition["totalPriceOfSupplyChain"]=$("#netTotal").text();
		distributorRequisition["netDiscountOfSupplyChain"]=$("#netDiscount").text();
		distributorRequisition["grandTotalOfSupplyChain"]=$("#netGrandTotal").text();
	    distributorRequisition["status"]="2";
	    if(distributorProductList.length>0){
	    	updateRequisitionToServer(distributorRequisition,reqId);	
	    }else{
	    	alert("Please Enter at least one product!");
	    }
	   
	}	
	
	
	function productListSubmitToServerByAccounce() {
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
	    	distributorProduct["stockQty"]=$(this).find("td:eq(3)").find('input').val();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(6)").find('input').val();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(7)").find('input').val();
	    	distributorProduct["discount"]=$(this).find("td:eq(8)").find('input').val();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(9)").find('input').val();
	    	distributorProductList.push(distributorProduct);	
	        }); 
	    distributorRequisition["requisitionAccounceApprove"]=distributorProductList;
	    distributorRequisition["status"]="3";
	    if(distributorProductList.length>0){

	    	updateRequisitionToServer(distributorRequisition,reqId);	
	    }else{
	    	alert("Please Enter at least one product!");
	    }
	   
	}
	
	function productListSubmitToServerByOperation() {
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
	    	distributorProduct["stockQty"]=$(this).find("td:eq(3)").find('input').val();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(6)").find('input').val();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(7)").find('input').val();
	    	distributorProduct["discount"]=$(this).find("td:eq(8)").find('input').val();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(9)").find('input').val();
	    	distributorProductList.push(distributorProduct);	
	        }); 
	    distributorRequisition["requisitionOperationApprove"]=distributorProductList;
	    distributorRequisition["status"]="4";
	    if(distributorProductList.length>0){

	    	updateRequisitionToServer(distributorRequisition,reqId);	
	    }else{
	    	alert("Please Enter at least one product!");
	    }
	   
	}

	
	function productListSubmitToServerByWarhouse() {
		this.event.preventDefault();
		var reqId=$('#id').val();
		let distributorRequisition= {};		
		var T = document.getElementById('T');
		var distributorProductList=[];
		var count=0;
	    $(T).find('> tbody > tr').each(function () {	    	
	    	let distributorProduct= {};
	    	if($(this).find("td:eq(2)").find('input').val()<$(this).find("td:eq(10)").find('input').val()){
	    		count++;
	    	}
	    	distributorProduct["productId"]=$(this).find("td:eq(0)").find('input').val();
	    	distributorProduct["productName"]=$(this).find("td:eq(1)").find('input').val();
	    	distributorProduct["receivedQty"]=$(this).find("td:eq(2)").find('input').val();
	    	distributorProduct["productUnitPrice"]=$(this).find("td:eq(6)").find('input').val();
	    	distributorProduct["totalPrice"]=$(this).find("td:eq(7)").find('input').val();
	    	distributorProduct["discount"]=$(this).find("td:eq(8)").find('input').val();
	    	distributorProduct["grandTotal"]=$(this).find("td:eq(9)").find('input').val();
	    	distributorProduct["deliveredQuantity"]=$(this).find("td:eq(10)").find('input').val();
	    	distributorProductList.push(distributorProduct);	
	        }); 
	    distributorRequisition["requisitionDelivered"]=distributorProductList;
	    distributorRequisition["status"]="5";
	    if(count>0){
	    	distributorRequisition["deliveryStatus"]="Partial_Delivery";
	    }else{
	    	distributorRequisition["deliveryStatus"]="Delivered";
	    }	    
	    if(distributorProductList.length>0){

	    	updateRequisitionToServer(distributorRequisition,reqId);	
	    }else{
	    	alert("Please Enter at least one product!");
	    }
	   
	}
	
	
	function updateRequisitionToServer(data,id) {
		
	    let url = baseUrl +'/api/update-distributor-requisition-by-other-users/'+id;
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

	function submitDeliveredRequisitionReceivedForm() {	
		this.event.preventDefault();
		var reqId=$('#id').val();
		
		let distributorRequisitionReceive= {};
		
		 var T = document.getElementById('productRequisitionReceiveTable');
		 var distributorReceivedProductList=[];	
		 $(T).find('> tbody > tr').each(function () {	    	
		    	let distRequisistionReceive= {};
		    	distRequisistionReceive["productId"]=$(this).find("td:eq(0)").text();
		    	distRequisistionReceive["productName"]=$(this).find("td:eq(1)").text();
		    	distRequisistionReceive["receivedQty"]=$(this).find("td:eq(2)").text();
		    	distRequisistionReceive["productUnitPrice"]=$(this).find("td:eq(3)").text();
		    	distRequisistionReceive["totalPrice"]=$(this).find("td:eq(4)").text();
		    	distRequisistionReceive["discount"]=$(this).find("td:eq(5)").text();
		    	distRequisistionReceive["grandTotal"]=$(this).find("td:eq(6)").text();
		    	distRequisistionReceive["toReceive"]=$(this).find("td:eq(7)").find('input').val();
		    	distRequisistionReceive["damaged"]=$(this).find("td:eq(8)").find('input').val();
		    	distRequisistionReceive["dateExpaired"]=$(this).find("td:eq(9)").find('input').val();
		    	distRequisistionReceive["lost"]=$(this).find("td:eq(10)").find('input').val();
		    	
		    	distributorReceivedProductList.push(distRequisistionReceive);
		        
		 });
		 
		 distributorRequisitionReceive["distributorRequiredReceive"]=distributorReceivedProductList;
		
		    if(distributorReceivedProductList.length>0){
		    	submitToServerReceive2(distributorRequisitionReceive,reqId);	
		    }
		    else{
		    	alert("Please Enter at least one product!");
		    }		
	}
	
function submitToServerReceive2(data,id) {
	
	    let url = baseUrl +'/api/update-distributor-requisition-receive/'+id;
	    $.ajax({
	        type: "PUT",
	        contentType: "application/json",
	        url: url,
	        data: JSON.stringify(data),
	        dataType : "json",
	        success: function (result) {
	    	 window.location.href =  baseUrl+'/admin/delivered-requisition';            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	}
	
	
	
	
	