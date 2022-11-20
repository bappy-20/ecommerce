var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#campaignAddForm').validate({
	rules:{
	campaignname:"required",
	campaignType:"required",
	},messages:{
		campaignname:"Please enter campaign name",
		campaignType:"Please enter campaign type",
	},
	submitHandler:function(form){
		
		submitCampaignForm();		
	}	
});


jQuery('#campaignEditForm').validate({
	rules:{
	campaignName:"required",
	campaignType:"required",
	},messages:{
		campaignName:"Please enter campaign name",
		campaignType:"Please enter campaign type",
	},
	submitHandler:function(form){
		submitCampaignEditForm();
	}	
});


function submitCampaignForm() {
	this.event.preventDefault();
    let campaign = {};
    campaign["campaignName"] = $("#campaignName").val();
   // campaign["campaignType"] = $("#campaignType").val();
    campaign["campaignType"] = $("#campaignType  option:selected").val();
    campaign["startTime"] = $("#startTime").val();
    campaign["expiredDate"] = $("#expiredDate").val();    
    if($("#startTime").val()!= "" && $("#expiredDate").val()!= "") {  	
  		  submitCampaignFormToServer(campaign);
   	}else{
   		alert("Start Date and Expired Date must not be null");
   	} 
}

function submitCampaignFormToServer(data) {

    let url = baseUrl +'/api/save-campaign'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    	 swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/campaign';         
           },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
            ///alert("Problem");
        }
    });
}
function getCampaign(id){

	let url = baseUrl + "/api/get-campaign/"+id;
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
	$("#campaignName").val(result.campaignName);
//	$("#campaignType").val(result.campaignType);
	$("#campaignType").val(result.campaignType).change();
    $("#startTime").val(result.startTime);
    $("#expiredDate").val(result.expiredDate);
    $("#status").val(result.status);
}
	function submitCampaignEditForm() {
	
	this.event.preventDefault();
	let campaign = {};
    let id = $("#id").val();
    campaign["id"] = id;    
    campaign["campaignName"] = $("#campaignName").val();
    campaign["campaignType"] = $("#campaignType").val();
    campaign["startTime"] = $("#startTime").val();
    campaign["expiredDate"] = $("#expiredDate").val();  
    campaign["status"] = $("#status").val(); 
    submitCampaignEditFormToServer(campaign, id);   
  }         
	function submitCampaignEditFormToServer(data, id) {

    let url = baseUrl + "/api/update-campaign/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	 swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/campaign";
             
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-campaign/"+id;
		
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

	
	/*
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
	*/	