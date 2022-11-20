var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitOrganizationsFrom()  {
	this.event.preventDefault();
    let organization = {};
    organization["orgName"] = $("#orgName").val();
    organization["ownerName"] = $("#ownerName").val();
    organization["contactName"] = $("#contactName").val();
    organization["mobile"] = $("#mobile").val();
    organization["email"] = $("#email").val();
    organization["address"] = $("#address").val();
    organization["website"] = $("#website").val();   
    organization["about"] = $("#about").val();
    organization["logo"] = $("#logo").val();
    submitOrganizationsFromToServer(organization);
}


function submitOrganizationsFromToServer(data) {
	
    let url = baseUrl + "/api/organizations"
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {

            if(result.statusCode==500){
            	
            	$('#result').text(result.message);
            }else{
            	 window.location.href = baseUrl + "/superadmin/org";
            }
           
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getOrganizations(id){
	let url = baseUrl + "/api/organizations/"+id;

	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheOrganizationValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheOrganizationValues(result){

	$("#id").val(result.id);
	$("#orgName").val(result.orgName);
    $("#ownerName").val(result.ownerName);
    $("#contactName").val(result.contactName);
    $("#mobile").val(result.mobile);
    $("#email").val(result.email);
    $("#address").val(result.address);
    $("#website").val(result.website);   
    $("#about").val(result.about);
    $("#logo").val(result.logo);

}


function submitOrganizationsEditFrom() {

	this.event.preventDefault();
    let organization = {};
    let id = $("#id").val();
    organization["id"] = id;
    organization["orgName"] = $("#orgName").val();
    organization["ownerName"] = $("#ownerName").val();
    organization["contactName"] = $("#contactName").val();
    organization["mobile"] = $("#mobile").val();
    organization["email"] = $("#email").val();
    organization["address"] = $("#address").val();
    organization["website"] = $("#website").val();   
    organization["about"] = $("#about").val();
    organization["logo"] = $("#logo").val();
    submitOrganizationsEditFromToServer(organization, id);
}

function submitOrganizationsEditFromToServer(data, id) {

    let url = baseUrl + "/api/organizations/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
        		if(result.statusCode==500){
            	
            	$('#result').text(result.message);
            }else{
            	 window.location.href = baseUrl + "/superadmin/org";
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getOrgProfile(id){
 
	let url = baseUrl + "/api/organizations/profile";
	
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	            setTheOrganizationProfileValues(result);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}   

function setTheOrganizationProfileValues(result){

	$("#id").text(result.id);
	$("#orgName").text(result.orgName);
    $("#ownerName").text(result.ownerName);
    $("#contactName").text(result.contactName);
    $("#mobile").text(result.mobile);
    $("#email").text(result.email);
    $("#address").text(result.address);
    $("#website").text(result.website);   
    $("#about").text(result.about);
   

}
