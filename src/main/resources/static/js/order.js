var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
	function productUpdate() {
		
	    if ($("#product_id").val() != null && $("#product_id").val() != '') {
	        // Add product to Table
	    	  var selectedText = $("#product_id option:selected").html();
	        productAddToTable(selectedText);

	        // Clear form fields
	        formClear();

	        // Focus to product name field
	        $("#product_id").focus();
	    }
	}

	function productAddToTable(selectedText) {
	    // First check if a <tbody> tag exists, add one if not
	    if ($("#productTable tbody").length == 0) {
	        $("#productTable").append("<tbody></tbody>");
	    }
	  
	    // Append product to the table
	    $("#productTable tbody").append(
	        "<tr>" +
	        "<td>" + $("#product_id").val() + "</td>" +
	        "<td>" + selectedText + "</td>" +
	        "<td>" + $("#received_qty").val() + "</td>" +
	        "<td>" + $("#unit_price").val() + "</td>" +
	        "<td>" + $("#purchase_price").val() + "</td>" +
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
	}

	function productDelete(ctl) {
	    $(ctl).parents("tr").remove();
	}
	
	
	function productListSubmitToServer1() {
		 var T = document.getElementById('returnTable');
		 var flag=1;
		 var productList="";
		 
		var retailId=$('#retail_id').val();
		var total_price=$('#total_price').val();
		var orderId=$('#orderId').val();		
		var return_date=$('#return_date').val();
		var startDate = return_date.split("/").reverse().join("-");
		var return_note=$('#return_note').val();
		
		 var returnoObjList=[];
		    $(T).find('> tbody > tr').each(function () {
	            var returnoObj={};
	            returnoObj["productId1"]=$(this).find("td:eq(0)").text();
	            returnoObj["productName"]=$(this).find("td:eq(1)").text();
	            returnoObj["productIdUnitprice"]=$(this).find("td:eq(4)").text();
	            returnoObj["receivedQty"]=$(this).find("td:eq(2)").text();
	            returnoObj["returnQuantity"]=$(this).find("td:eq(3)").find('input').val();
	            returnoObj["total"]=$(this).find("td:eq(5)").text();
	            returnoObjList.push(returnoObj);
		     }); 
	          
	    $.ajax({
	        type: "post",
	        url: baseUrl+"/api/submit-order-return-again",
	        data: {
	        	retailId:""+retailId+"", 
	        	totalPrice:""+total_price+"", 
	        	orderNumber:""+orderId+"", 
	        	returnDate:""+startDate+"", 
	        	returnNote:""+return_note+"", 
	        	product:""+JSON.stringify(returnoObjList)+"" 
	        	}, // parameters
	        success: function(data){
	            if(data=="success"){
	            	//alert("hi")
	            	 window.location.href = baseUrl + "/admin/return-order-home";
	            }
	       },
	   })
	
	}
	

//	function productListSubmitToServer1() {
//	 var T = document.getElementById('returnTable');
//	 var flag=1;
//	 var productList="";
//	 
//	var retailId=$('#retail_id').val();
//	var total_price=$('#total_price').val();
//	var orderId=$('#orderId').val();
//	///alert(retailId+" "+total_price+" "+orderId)
//	
//	var return_date=$('#return_date').val();
//	
//	var startDate = return_date.split("/").reverse().join("-");
//	var return_note=$('#return_note').val();
//	
//    $(T).find('> tbody > tr > td').each(function () {
//           
//        	  productList+=$(this).text()+",";
//              
//             if(flag==6){
//            	 productList+="###";
//                 flag=0;                        
//             }
//             flag++;
//          }); 
//          
//    $.ajax({
//        type: "post",
//        url: baseUrl+"/api/submit-order-return",
//        data: {
//        	retailId:""+retailId+"", 
//        	totalPrice:""+total_price+"", 
//        	orderNumber:""+orderId+"", 
//        	returnDate:""+startDate+"", 
//        	returnNote:""+return_note+"", 
//        	product:""+productList+"" 
//        	}, // parameters
//        success: function(data){
//            if(data=="success"){
//            	//alert("hi")
//            	 window.location.href = baseUrl + "/admin/return-order-home";
//            }
//       },
//   })
//
//}
	
	
	function getDropDownValue(){
		$('select[name="orderId"]').on('change', function() {
		   // var orderId = $(this).val();	
		   var orderId = $('#orderId').find(":selected").text();
		    if(orderId!=null) {   
		    	$('#productList').show(250);
		    	
		    $.ajax({
		    url: baseUrl+'/api/return-order-by-order-id/'+orderId,
		    type: "GET",
		    dataType: "json",
		    success:function(data) {
		    createTableWithReturnProduct(data);		    
		    }
		    });
		    }
		    });	
		
	}

	function createTableWithReturnProduct(data) {
		 $("#returnTable").find("tr:gt(0)").remove();
	    if ($("#returnTable tbody").length == 0) {
	    	
	        $("#returnTable").append("<tbody></tbody>");
	    }	  
	    $.each(data.data, function(idx, obj){ 
	    	 $("#returnTable tbody").append(
		        "<tr>" +
		        "<td hidden=true >" + obj.productId + "</td>" +
		        "<td>" + obj.productName + "</td>" +   
		        "<td>" + obj.productQuantity + "</td>" +
		        "<td><input class='form-control className' type='text' value=''></td>" +
		        "<td>" + obj.productUnitPrice + "</td>" +
		        "<td class='total'>" + obj.totalPrice + "</td>" +
		        "</tr>");
	    });
	   
	  //  calc_total();
	}
	
	$(document).on('change', '.className', function(){
		var returnQty=$(this).val();
		var tt=$(this);
	
		//alert(returnQuantity)
		var total = tt.closest('td').next().text()*returnQty;
		 //var total=tt.closest('td').next().text()*returnQty-tt.closest('td').next().next().find('input').val();
//		 $(this).closest('td').next().next().text(total);
		 
			var orderedQuantityText = tt.closest('td').prev().text();
			
			var orderedQuantity = parseInt(orderedQuantityText);
			if(returnQty < orderedQuantity) {
				$(this).closest('td').next().next().text(total);
				calc_total();
			}
			else {
				alert("You Can't Return More than Ordered Quantity!")
			}
	   
	});
	
	function calc_total(){
		  var sum = 0;
		  $(".total").each(function(){
		    sum += parseFloat($(this).text());
		  });
		  $('#total_price').val(sum);
		}
