var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

$(".className").change(function(){
	 
	 var productId=$(this).closest('td').siblings().find('#productId').val();
		 var quantity=$(this).val();
		 var tt=$(this);
		// alert(quantity)
		// let url = baseurl + "/api/get-product-value/"+productId+"/"+quantity;
		let url = baseurl + "/api/get-product-price/"+productId;
		 var value=0;
		 $.ajax({
	            type: "GET",
	            url: url,
	            success: function (result) {
	            	if(result.statusCode==200){
	            		var totalNow = result.data.mrp *quantity;
	            		//tt.closest('td').siblings().find('#total').val(result.totalPrice);
	            		tt.closest('td').siblings().find('#total').val(totalNow);
	            		
	            		var discountNow = tt.closest('td').siblings().find('#discounttt').val();
	            		var discountedAmount = totalNow - discountNow;
	            		tt.closest('td').siblings().find('#discountamount').val(discountedAmount);
	            		  
	            		sumTotal(productId,quantity,totalNow,tt);
	            		
	            		
	            	}
	            	if(result.statusCode==203){
	            		alert(result.message);
	            		tt.closest('td').siblings().find('#total').val(k);
                       //tt.closest('td').siblings().find('#total').val(result.totalPrice);
                      // tt.closest('td').siblings().find('#discountamount').val(result.discounted);
                       }
	            	if(result.statusCode==500){
                       alert("Something with goes wrong "+result.message);
                    }
	            	if(result.statusCode==204){
                    alert(result.message);
                 }	            	
	            },
	            error: function (e) {
	                console.log("ERROR: ", e);
	            }
	        });
	 
});
	
function sumTotal(productId,quantity,totalNow,tt){
	
var table = document.getElementById("T"),
			grandTotalValue = 0;
		discountValue = 0;
		totalValue = 0;
 for(var i = 1; i < table.rows.length; i++)
 {
	  grandTotalValue = grandTotalValue + parseInt(table.rows[i].cells[5].children[0].value);
	discountValue = discountValue + parseInt(table.rows[i].cells[4].children[0].value);
	totalValue = totalValue + parseInt(table.rows[i].cells[3].children[0].value); 
 }
 
//alert(grandTotalValue+" "+discountValue+" "+totalValue);
 $("#netGrandTotal").text(grandTotalValue + " Tk");
 $("#netDiscount").text(discountValue + " Tk");
 $("#netTotal").text(totalValue + " Tk");
 
 CashBackcampaignAvailable(totalValue,discountValue,grandTotalValue);
 ProductWiseCampaignAvailable(productId,quantity,totalValue,discountValue,grandTotalValue,totalNow,tt);
 
	}

function CashBackcampaignAvailable(totalValue,discountValue,grandTotalValue){
	
	$.ajax({
	    url: baseUrl+'/api/get-available-cash-back-campaign',
	    type: "GET",
	    dataType: "json",
	    success:function(result) {
	    	if(result!=""){
	    		var requiredInvoiceAmount = parseFloat(result[0].requiredInvoiceAmount);
	    		var discounType = result[0].discountType;
	    		var discountAmount = parseFloat(result[0].discountAmount);
	    			    		
	    		if(totalValue >= requiredInvoiceAmount){
	    			
	    			if(discounType == "BDT") {
	    				discountValue+=discountAmount;
	    				grandTotalValue =totalValue - discountValue;
	    				
	    				alert(grandTotalValue)
	    				
	    				 $("#netGrandTotal").text(grandTotalValue + " Tk");
	    				 $("#netDiscount").text(discountValue + " Tk");
	    				
		    		}
		    		
		    		if(discounType == "Percentage") {
	    				   var net = (totalValue*discountAmount)/100;
	    				   discountValue+= net;
	    				   grandTotalValue-=discountValue;	
	    				
	    				 $("#netGrandTotal").text(grandTotalValue + " Tk");
	    				 $("#netDiscount").text(discountValue + " Tk");
		    		}
	    		
	    		}
	    		
	    		}
	    	
	    	}
	});
}

function ProductWiseCampaignAvailable(productId,quantity,totalValue,discountValue,grandTotalValue,totalNow,tt){
	
	$.ajax({
		 url: baseUrl+'/api/get-campaign-by-product/'+productId,
		    type: "GET",
		    dataType: "json",
		    success:function(result) {
					var discounType = result.data[0].discountType;
					var discountAmount = parseFloat(result.data[0].discountAmount);
					// discountValue += quantity*discountAmount;
					 grandTotalValue-=discountValue;
							 //alert(grandTotalValue)
								if(discounType == "BDT") {
				    				discountValue += quantity*discountAmount;
				    				
				    				tt.closest('td').siblings().find('#discounttt').val(discountValue);
				    				
				    				var discountedAmount =  tt.closest('td').siblings().find('#discountamount').val();
				    				   //alert(discountedAmount);
				    				   var grandTotalOfTheseProduct = discountedAmount-discountValue;
				    				   
				    				   tt.closest('td').siblings().find('#discountamount').val(grandTotalOfTheseProduct);
				    				   
				    				
				    				grandTotalValue =totalValue - discountValue;
				    				
				    				var table = document.getElementById("T"),
				    						grandTotalValue1 = 0;
				    					discountValue1 = 0;
				    					totalValue1 = 0;
				    			 for(var i = 1; i < table.rows.length; i++)
				    			 {
				    				
				    				grandTotalValue1 = grandTotalValue1 + parseInt(table.rows[i].cells[5].children[0].value);
				    				discountValue1 = discountValue1 + parseInt(table.rows[i].cells[4].children[0].value);
				    				totalValue1 = totalValue1 + parseInt(table.rows[i].cells[3].children[0].value); 
				    				//alert(grandTotalValue1)
				    			 }
				    			 
				    			 
				    				
			    				   
			    				 $("#netGrandTotal").text(grandTotalValue1 + " Tk");
			    				 $("#netDiscount").text(discountValue1 + " Tk");
			    				 $("#netTotal").text(totalValue1 + " Tk");
			    				 
			    				 //CashBackcampaignAvailable(totalValue1,discountValue1,grandTotalValue1);
			    				 
				    				
					    		}
					    		
					    		if(discounType == "Percentage") {
				    				   var net = (totalNow*discountAmount)/100;
				    				   discountValue+= net;
				    				   
				    				  // alert()
				    				   
				    				   tt.closest('td').siblings().find('#discounttt').val(discountValue);
				    				   
				    				   
				    				   var discountedAmount =  tt.closest('td').siblings().find('#discountamount').val();
				    				   //alert(discountedAmount);
				    				   var grandTotalOfTheseProduct = discountedAmount-discountValue;
				    				   
				    				   tt.closest('td').siblings().find('#discountamount').val(grandTotalOfTheseProduct);
				    				   
				    				   grandTotalValue-=discountValue;	
				    				   
				    				  /// tt.closest('td').siblings().find('#discountamount').val(grandTotalValue);
				    				
				    					var table = document.getElementById("T"),
					    					grandTotalValue1 = 0;
					    					discountValue1 = 0;
					    					totalValue1 = 0;
					    			 for(var i = 1; i < table.rows.length; i++)
					    			 {
					    				
					    				grandTotalValue1 = grandTotalValue1 + parseInt(table.rows[i].cells[5].children[0].value);
					    				discountValue1 = discountValue1 + parseInt(table.rows[i].cells[4].children[0].value);
					    				totalValue1 = totalValue1 + parseInt(table.rows[i].cells[3].children[0].value); 
					    				///alert(grandTotalValue1)
					    			 }
					    			 
					    			 
					    				
				    				   
				    				 $("#netGrandTotal").text(grandTotalValue1 + " Tk");
				    				 $("#netDiscount").text(discountValue1 + " Tk");
				    				 $("#netTotal").text(totalValue1 + " Tk");
				    				 
				    				// CashBackcampaignAvailable(totalValue1,discountValue1,grandTotalValue1);
					    		}
		
	}
		
	});
}

$("#btn-save").click(function(event) {
    event.preventDefault();
    //alert("hi")
   
     var T = document.getElementById('T');
    var delivery="";
    var flag=0;
         $(T).find('> tbody > tr > td > input').each(function () {
             delivery+=$(this).val()+",";
              flag++;
             if(flag==6){
                 delivery+="###";
                 flag=0;                        
             }
         }); 
         alert(orderId+""+delivery)
         
    $.ajax({
         type: "get",
         url: baseurl+"/api/update-delivery-details",
         data: {orderId:""+orderId+"", deliverydetails:""+delivery+"" }, // parameters
         success: function(data){
             if(data=="success"){
            	 //alert("hi")
                   window.location.reload();
             }
        },
    })

});

function deleteEntry(id) {		
	let url = baseurl+"/api/delete-order-history/"+id;
	
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








