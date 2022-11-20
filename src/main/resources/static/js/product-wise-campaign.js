var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

	function submitProductWiseCampaignForm(){
	this.event.preventDefault();
    let prdWiseCamp = {};
    prdWiseCamp["campaignId"] = $("#camp").val();
    prdWiseCamp["productId"] = $("#prdList").val()
    prdWiseCamp["offerType"] = $("#offerType").val();
    prdWiseCamp["discountAmount"] = $("#dis_amount").val();
    prdWiseCamp["discountOn"] = $("#discount_on").val();
    prdWiseCamp["discountType"] = $("#discountType").val();
    prdWiseCamp["requiredQuantity"] = $("#quantityRequired").val();
    prdWiseCamp["freeProductId"] = $("#freeItem").val();
    prdWiseCamp["freeItem"] = $("#freeItem").val();
    prdWiseCamp["freeItemQuantity"] = $("#freeItemQuantity").val(); 
    prdWiseCamp["quantity"] = $("#freeItemQuantity").val();     
    submitprdwisecampToServer(prdWiseCamp);
    
	}
	
	function submitprdwisecampToServer(data){
	
	let url = baseUrl +'/api/save-product-wise-campaign'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
            swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/product-wise-campaign';        
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	

function productWiseCampaignUpdate(){
		
			$('select[name="prdlist"]').on('change', function() {
		    var prdlist = $(this).val();
		 
		    if(prdlist!=null) {        
		    $.ajax({
		    url: baseUrl+'/api/get-product-price/'+prdlist,
		    type: "GET",
		    dataType: "json",
		    success:function(result) {
		    		//alert(JSON.stringify(result));
		    		$("#dealPrice").val(result.data.dealerPrice);
		    		$("#rtlPrice").val(result.data.retailPrice);
		    		$("#mrprice").val(result.data.mrp);		
		    		}
		    	});
		    }
		    });	
	}

function prdCampSubmitToServer() {
	var campType = $("#campType  option:selected").html();
	var camp = $("#camp option:selected").html();
	
	var campaignId = $("#camp").val();
	var productId = $("#prdList").val();
	var freeItemId = $("#freeItem").val();
	var quantityRequired = $("#quantityRequired").val();
	var freeItemQuantity = $("#freeItemQuantity").val();
	
	if ($("#prdList").val() != null && $("#prdList").val() != '') {
		var selectedText = $("#prdList option:selected").html();
		if ($("#offerType").val() != null && $("#offerType").val() != '') {			
			var oferType = $("#offerType").val();			
			if ($("#offerType").val() == 'value') {
				if($("#discountType").val() != null && $("#discountType").val() !='') {					
					var discountType = $("#discountType").val();					
					if ($("#discountType").val() == 'Percentage') {
						if ($("#discount_on").val() == 'Rp') {
							var discount = $("#dis_amount").val();
							var discountOn = $("#discount_on").val();
						}
						else if ($("#discount_on").val() == 'Dp') {
							var discount = $("#dis_amount").val();
							var discountOn = $("#discount_on").val();
						}
						else if ($("#discount_on").val() == 'Mrp') {
							var discount = $("#dis_amount").val();
							var discountOn = $("#discount_on").val();
						}
						
					}
					
					else if ($("#discountType").val() == 'Tk') {
						if ($("#discount_on").val() == 'Rp') {
							var discount = $("#dis_amount").val();
							var discountOn = $("#discount_on").val();
						}
						else if ($("#discount_on").val() == 'Dp') {
							var discount = $("#dis_amount").val();
							var discountOn = $("#discount_on").val();
						}
						else if ($("#discount_on").val() == 'Mrp') {
							var discount = $("#dis_amount").val();
							var discountOn = $("#discount_on").val();
						}
						
					}
			
				}
				else {
					alert("please selecte discount type");
				}				
			}
			else if($("#offerType").val() == 'quantity') {
				var quantityRequired = $("#quantityRequired").val();
				var freeItem = $("#freeItem option:selected").html();
				var freeItemQuantity = $("#freeItemQuantity").val();
			}
		/*campaignAddToTable(campType,camp,selectedText,oferType,discountType,discountOn,discount,quantityRequired,freeItem,freeItemQuantity);*/
		campaignAddToTable(campType,campaignId,camp,productId,selectedText,oferType,discountType,discountOn,discount,quantityRequired,freeItemId,freeItem,freeItemQuantity);
		}
		else {
			alert("Please Select offer Type");
		}		
	}
	else {
		alert("Please Select a product from the product list");	
	}
		
}

function campaignAddToTable(campType,campaignId,camp,productId,selectedText,oferType,discountType,
		discountOn,discount,quantityRequired,freeItemId,freeItem,freeItemQuantity){
	
	if(discountType == null) {
		discountType = "0";
	}
	if(discountOn == null) {
		discountOn = "0";
	}
	if(discount == null) {
		discount = "0";
	}
	if(freeItemId=='') {
		freeItemId = 0;
	}
	if(freeItem == null) {
		freeItem = "0";
	}
	if(freeItemQuantity =='') {
		freeItemQuantity = 0;
	}
	if(quantityRequired == '') {
		quantityRequired = 0;
	}
	
	this.event.preventDefault();
    if ($("#productTable tbody").length == 0) {
        $("#productTable").append("<tbody></tbody>");
    }
    
    $("#productTable tbody").append( "<tr>" +
	        "<td hidden>" + campType + "</td>" +
	        "<td hidden>" + campaignId + "</td>" +
	        "<td>" + camp + "</td>" +
	        "<td hidden>" + productId + "</td>" +
	        "<td>" + selectedText + "</td>" +
	        "<td>" + oferType + "</td>" +
	        "<td>" + discountType + "</td>" +
	        "<td>" + discountOn + "</td>" +
	        "<td>" + discount + "</td>" +
	        "<td>" + quantityRequired + "</td>" +
	        "<td hidden>" + freeItemId + "</td>" +
	        "<td>" + freeItem + "</td>" +
	        "<td>" + freeItemQuantity + "</td>" +
	        "<td>" +
	        "<button type='button' onclick='productDelete(this);'class='btn btn-danger'>" +
	        "<span class='glyphicon glyphicon-remove' />" +
	        "</button>" +
	        "</td>" +
	        "</tr>");
}

function formClear() {
	$("#campType").val("");
	$("#camp").val("");
	$("#prdList").val("");
	$("#offerType").val("");
	$("#discountType").val("");
	$("#discount_on").val("");
	$("#quantityRequired").val("");
	$("#freeItem").val("");
	$("#dealPrice").val("");
	$("#rtlPrice").val("");
	$("#mrprice").val("");
	$("#dis_amount").val("");
	$("#freeItemQuantity").val("");
}

function productDelete(ctl) {
    $(ctl).parents("tr").remove();
}

function getProductWiseCampaign(id){

	let url = baseUrl + "/api/get-product-wise-campaign/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheProductWiseCampaignValues(result.data);
	            console.log(result);
	          
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheProductWiseCampaignValues(result){	
	
	$("#id").val(result.id);
	$("#camp").val(result.campaignId);
	$("#prdList").val(result.productId);
	$("#offerType").val(result.offerType);
    $("#discountType").val(result.discountType);
    $("#dis_amount").val(result.discountAmount);
    $("#discount_on").val(result.discountOn);
    $("#quantityRequired").val(result.requiredQuantity);
    $("#freeItem").val(result.freeProductId);
    $("#freeItemQuantity").val(result.freeItemQuantity);
    
}

	function submitPrdWiseCampEditForm() {
		
	this.event.preventDefault();
	let productWiseCampaign = {};
    let id = $("#id").val();
    productWiseCampaign["id"] = id;    
    productWiseCampaign["campaignId"] = $("#camp").val();
    productWiseCampaign["productId"] = $("#prdList").val();
    productWiseCampaign["offerType"] = $("#offerType").val();
    productWiseCampaign["discountType"] = $("#discountType").val();
    productWiseCampaign["discountAmount"] = $("#dis_amount").val(); 
    productWiseCampaign["discountOn"] = $("#discount_on").val();  
    productWiseCampaign["requiredQuantity"] = $("#quantityRequired").val();
    productWiseCampaign["freeProductId"] = $("#product_mrpPrice").val()
    productWiseCampaign["freeItemQuantity"] = $("#freeItemQuantity").val();
    
    submitProductWiseCampEditFormToServer(productWiseCampaign, id);
    
} 
 
    
	function submitProductWiseCampEditFormToServer(data, id) {

    let url = baseUrl + "/api/update-product-wise-campaign/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/product-wise-campaign";
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function submitPrdWiseCampaignForm2() {	
		var table = document.getElementById("productTable");
		var prdWiseCampaign = [];
		 $(table).find('> tbody > tr').each(function () {
			 let prdWiseCamp= {};
			 prdWiseCamp["campaignId"] = $(this).find("td:eq(1)").text();
		     prdWiseCamp["productId"] = $(this).find("td:eq(3)").text();
		     prdWiseCamp["offerType"] = $(this).find("td:eq(5)").text();
		     prdWiseCamp["discountAmount"] = $(this).find("td:eq(8)").text();
		     prdWiseCamp["discountOn"] = $(this).find("td:eq(7)").text();
		     prdWiseCamp["discountType"] = $(this).find("td:eq(6)").text();
		     prdWiseCamp["requiredQuantity"] = $(this).find("td:eq(9)").text();
		     prdWiseCamp["freeProductId"] = $(this).find("td:eq(10)").text();		     
		     prdWiseCamp["freeItem"] = $(this).find("td:eq(11)").text();
		     prdWiseCamp["freeItemQuantity"] = $(this).find("td:eq(12)").text();
		     prdWiseCamp["quantity"] = $(this).find("td:eq(12)").text();
		     prdWiseCampaign.push(prdWiseCamp);
			
		});
		 
	   submitprdwisecampToServer2(prdWiseCampaign);
	}
		
		function submitprdwisecampToServer2(data) {

			let url = baseUrl +'/api/save-product-wise-campaign-list'
				    $.ajax({
				        type: "POST",
				        contentType: "application/json",
				        url: url,
				        data: JSON.stringify(data),
				        dataType : "json",
				        success: function (result) {
				            console.log(result);
				            swal(`${result.message}`);
				            window.location.href =  baseUrl +'/admin/product-wise-campaign';        
				        },
				        error: function (e) {
				            console.log("ERROR: ", e);
				        }
				    });
			
		}
		
		function getDropDownValue(){
			$('select[name="offertype"]').on('change', function() {
			    var offertype = $(this).val();
			    	if(offertype=="value"){
			    		$("#valuediv").show(300);
			    		$("#quantitydiv").hide(300);
			    		
			    	}else{
			    		$("#quantitydiv").show(300);
			    		$("#valuediv").hide(300);
			    	}	    	
			    
			 });	
		}
		
		function deleteEntry(id) {		
			let url = baseurl+"/api/delete-product-wise-campaign/"+id;
			
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


