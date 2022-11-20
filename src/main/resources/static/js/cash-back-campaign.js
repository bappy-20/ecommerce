var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
function submitForm() {
	this.event.preventDefault();
    let campaign = {};
    campaign["campaignId"] = $("#campaignId").val();
    campaign["requiredInvoiceAmount"] = $("#requiredInvoiceAmount").val();
    campaign["discountType"] = $("#discountType").val();
    campaign["discountAmount"] = $("#discountAmount").val(); 
    
    if($("#campaignId").val()!= "" && $("#requiredInvoiceAmount").val()!= "" 
    	&& $("#discountType").val()!= "" && $("#discountAmount").val()!= "") {
    	
  		  submitFormToServer(campaign);
   	}else{
   		alert("Please fill up all the input field");
   	} 
}

function submitFormToServer(data) {

    let url = baseUrl +'/api/save-cash-back-campaign'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/cash-back-campaign';
           
           },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}


function getCashBackCampaign(id){

	let url = baseUrl + "/api/get-cash-back-campaign/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {	        
	            setTheCampaignValues(result.data);
	            console.log(result);
	           
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheCampaignValues(result){	
    
	$("#id").val(result.id);	
	$("#requiredInvoiceAmount").val(result.requiredInvoiceAmount);
    $("#discountType").val(result.discountType);
    $("#discountAmount").val(result.discountAmount);

}
	function submitEditForm() {
	this.event.preventDefault();
	let campaign = {};
    let id = $("#id").val();
     campaign["id"] = id;    
	 campaign["requiredInvoiceAmount"] = $("#requiredInvoiceAmount").val();
	 campaign["discountType"] = $("#discountType").val();
	 campaign["discountAmount"] = $("#discountAmount").val(); 	
	 
	 if($("#campaignId").val()!= "" && $("#requiredInvoiceAmount").val()!= "" 
	    	&& $("#discountType").val()!= "" && $("#discountAmount").val()!= "") {
		 submitEditFormToServer(campaign, id);   
	 }else{
		 alert("Please fill up all the input field");
	 }
   
  } 
        
	function submitEditFormToServer(data, id) {

    let url = baseUrl + "/api/update-cash-back-campaign/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	 	swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/cash-back-campaign";
             
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-cash-back-campaign/"+id;		
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
		$('select[name="campType"]').on('change', function() {
		    var campType = $(this).val();
		    if(campType!=null) {        
		    $.ajax({
		    url: baseUrl+'/api/get-campaign-by-campaign-type/'+campType,
		    type: "GET",
		    dataType: "json",
		    success:function(data) {
		   
		    $('select[name="campaignId"]').empty();
		    $('select[name="campaignId"]').append('<option value=""> -- Please Select -- </option>');
		    $.each(data, function(idx, obj){ 
		         $('select[name="campaignId"]')
		         .append('<option value="'+ obj.id +'">'+ obj.campaignName +'</option>');
		    });
		        
		    }
		    });
		    }else{
		        $('select[name="campaignId"]').append('<option value=""> -- Not found -- </option>');
		    } 
		    });	
	}