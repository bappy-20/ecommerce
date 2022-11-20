var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
jQuery('#userTypeAddForm').validate({
	rules:{
	usertype:"required",
	},messages:{
		usertype:"Please enter user type",
	},
	submitHandler:function(form){
		submitUserTypeForm();		
	}	
});

jQuery('#userTypeEditForm').validate({
	rules:{
	usertype:"required",
	},messages:{
		usertype:"Please enter user type",
	},
	submitHandler:function(form){
		submitUserTypeEditForm();
	}	
});


function submitUserTypeForm() {
	this.event.preventDefault();
    let userType = {};  
    userType["userType"] = $("#user_type").val();
    userType["note"] = $("#note").val();
    
    submitUserTypeFormToServer(userType);
}

function submitUserTypeFormToServer(data) {

    let url = baseUrl +'/api/user-Type'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
            window.location.href =  baseUrl +'/admin/userType';            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getUserType(id){

	let url = baseUrl + "/api/user-Type/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheUserTypeValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}
function setTheUserTypeValues(result){	
	$("#id").val(result.id);
	$("#user_type").val(result.userType);
	$("#note").val(result.note);
}

function submitUserTypeEditForm() {
	this.event.preventDefault();
	let userType = {};
    let id = $("#id").val();
    userType["id"] = id;    
    userType["userType"] = $("#user_type").val();
    userType["note"] = $("#note").val();
    submitUserTypeEditFormToServer(userType, id);    

}          
	function submitUserTypeEditFormToServer(data, id) {
    let url = baseUrl + "/api/user-Type/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          
            window.location.href = baseUrl + "/admin/userType";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
