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
            swal(`${e}`);
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
	

	
	function getDropDownValue(){
		$('select[name="purchase_number"]').on('change', function() {
		    var purchaseNumber = $(this).val();		   
		    if(purchaseNumber!=null) {   
		    	$('#productList').show(250);
		    	
		    $.ajax({
		    url: baseUrl+'/api/return-product-list/'+purchaseNumber,
		    type: "GET",
		    dataType: "json",
		    success:function(data) {
		    $('#supplier_id').val(data.statusCode);
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
		        "<td><input type='checkbox'></td>" +
		        "<td hidden=true >" + obj.productId + "</td>" +
		        "<td>" + obj.productName + "</td>" +
		        "<td>" + obj.receivedQty + "</td>" +
		        "<td><input class='form-control className' type='text' value='" + obj.receivedQty + "'></td>" +        
		        "<td>" + obj.unitPrice + "</td>" +
		        "<td><input class='form-control deduct' value='0' type='text'></td>" +
		        "<td class='total'>" + obj.purchasePrice + "</td>" +
		        "</tr>");
	    });
	    
	 // alert("hi")
	    calc_deduct()
	    calc_total();
	}

	$(document).on('change', '.className', function(){
		var returnQty=$(this).val();
		var tt=$(this);
//		 var total=tt.closest('td').next().text()*returnQty-tt.closest('td').next().next().find('input').val();
//		 $(this).closest('td').next().next().next().text(total);
//		 calc_deduct()
//		 calc_total();		 
		 var purchesQuantity = tt.closest('td').prev().text();
		 
		 if(returnQty> purchesQuantity) {
			 alert("Return Quantity is crossing purches quantity");
			 
		 }
		 else {
			 var total=tt.closest('td').next().text()*returnQty-tt.closest('td').next().next().find('input').val();
			 $(this).closest('td').next().next().next().text(total);
			 calc_deduct()
			 calc_total();
			// alert("yes")
			 
		 }
		

	   
	});
	
	$(document).on('change', '.deduct', function(){
		var deduct1=$(this).val();
		var tt1=$(this);
		 var total1=tt1.closest('td').prev().text()*tt1.closest('td').prev().prev().find('input').val()-deduct1;
		 $(this).closest('td').next().text(total1);
		 calc_deduct()
		 calc_total();
	   
	});
	 
	function calc_total(){
		
		  var sum = 0;
		  $(".total").each(function(){
		    sum += parseFloat($(this).text());
		  });
		  $('#total_price').val(sum);
		}
	
	function calc_deduct(){
		
		  var sum1 = 0;
		  $(".deduct").each(function(){
		    sum1 += parseFloat($(this).val());
		  });
		  $('#net_deduction_price').val(sum1);
		}
	
	function returnProductListSubmitToServer() {
		 var T = document.getElementById('returnTable');
		 var flag=1;
		 var productList="";		 
		var supplierId=$('#supplier_id').val();
		var total_price=$('#total_price').val();
		var purchase_number=$('#purchase_number').val();
		var purchase_date=$('#purchase_date').val();		
		var startDate = purchase_date.split("/").reverse().join("-");
		var purchase_note=$('#purchase_note').val();
		var net_deduction_price=$('#net_deduction_price').val();
		 var returnoObjList=[];
	    $(T).find('> tbody > tr').each(function () {
	    //	if($(this).find("td:eq(0)").find('input').prop("checked") == true){
            var returnoObj={};
            returnoObj["productId1"]=$(this).find("td:eq(1)").text();
            returnoObj["productName"]=$(this).find("td:eq(2)").text();
            returnoObj["productIdUnitprice"]=$(this).find("td:eq(5)").text();
            returnoObj["receivedQty"]=$(this).find("td:eq(3)").text();
            returnoObj["supplierId"]=supplierId;
            returnoObj["returnQuantity"]=$(this).find("td:eq(4)").find('input').val();
            returnoObj["deductionAmount"]=$(this).find("td:eq(6)").find('input').val();
            returnoObj["purchasePrice"]=$(this).find("td:eq(7)").text();
            returnoObj["total"]=$(this).find("td:eq(7)").text();
            returnoObjList.push(returnoObj);
         //   }	    	
	     }); 
	          //alert(JSON.stringify(returnoObjList));
	    $.ajax({
	        type: "post",
	        url: baseUrl+"/api/submit-purchase-return",
	        data: {
	        	supplierId:""+supplierId+"", 
	        	totalPrice:""+total_price+"", 
	        	purchaseNumber:""+purchase_number+"", 
	        	purchaseDate:""+startDate+"", 
	        	purchaseNote:""+purchase_note+"", 
	        	deductionPrice:""+net_deduction_price+"", 
	        	product:""+JSON.stringify(returnoObjList)+""
	        	}, // parameters
	        success: function(data){
	            if(data=="success"){
	            	 window.location.href = baseUrl + "/admin/return-purhcase-home";
	            }
	       },
	   })

	}