var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#CampaignTypeAddForm').validate({
	rules:{
	campaigntypename:"required",
	},messages:{
		campaigntypename:"Please enter campaign type name",
	},
	submitHandler:function(form){
		
		submitCampaignTypeForm();		
	}	
});

jQuery('#campaignTypeEditForm').validate({
	rules:{
	campaigntypename:"required",
	},messages:{
		campaigntypename:"Please enter your campaign type name",
	},
	submitHandler:function(form){
		
		submitCampaignTypeEditForm();
	}	
});

function submitCampaignTypeForm() {
	this.event.preventDefault();
    let campaignType = {};
    campaignType["campaignType"] = $("#campaignType1").val();
    campaignType["note"] = $("#note").val();
      
    submitCampaignTypeFormToServer(campaignType);
}

function submitCampaignTypeFormToServer(data) {

    let url = baseUrl +'/api/save-campaign-type'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            alert("create successful!")
            window.location.href =  baseUrl +'/admin/campaign-type';
           
           },
        error: function (e) {
            console.log("ERROR: ", e);
            alert("Problem");
        }
    });
}

function getCampaignType(id){

	let url = baseUrl + "/api/get-campaign-type/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheCampaignTypeValues(result.data);
	            console.log(result);
	           
	           
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheCampaignTypeValues(result){	
	$("#id").val(result.id);
	$("#campaignType1").val(result.campaignType);
	$("#note").val(result.note);
}
	function submitCampaignTypeEditForm() {

	this.event.preventDefault();
	let campaignType = {};
    let id = $("#id").val();
    campaignType["id"] = id;    
    campaignType["campaignType"] = $("#campaignType1").val();
    campaignType["note"] = $("#note").val();
    submitCampaignTypeEditFormToServer(campaignType, id);   
  } 
        
	function submitCampaignTypeEditFormToServer(data, id) {

    let url = baseUrl + "/api/update-campaign-type/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          
            window.location.href = baseUrl + "/admin/campaign-type";
             
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}