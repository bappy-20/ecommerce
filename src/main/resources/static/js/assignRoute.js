var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitAssignRouteFrom() {
	this.event.preventDefault();
    let assignRoute = {};
    assignRoute["regionName"] = $("#region_name").val();
    assignRoute["areaName"] = $("#area_name").val();
    assignRoute["territoryName"] = $("#territory_name").val();
    assignRoute["distributorName"] = $("#distributor_name").val();
    assignRoute["employeeId"] = $("#employee_id").val(); 
    assignRoute["dayName"] = $("#day_name").val();  
    assignRoute["routeDetails"] = $("#route_details").val();        
    submitAssignRouteFromToServer(assignRoute);
}

function submitAssignRouteFromToServer(data) {

    let url = baseUrl +'/api/assign-Route'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
            window.location.href =  baseUrl +'/admin/assignRoute';            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getAssignRoute(id){
	let url = baseUrl + "/api/assign-Route/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheAssignRouteValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheAssignRouteValues(result){	
	$("#id").val(result.id);
	$("#region_name").val(result.regionName);
	$("#area_name").val(result.areaName);
	$("#territory_name").val(result.territoryName);
    $("#distributor_name").val(result.distributorName);
    $("#employee_id").val(result.employeeId);
    $("#day_name").val(result.dayName);
    $("#route_details").val(result.routeDetails); 
}

	function submitAssignRouteEditFrom() {

	this.event.preventDefault();
	let assignRoute = {};
    let id = $("#id").val();
    assignRoute["id"] = id;    
    assignRoute["regionName"] = $("#region_name").val();
    assignRoute["areaName"] = $("#area_name").val();
    assignRoute["territoryName"] = $("#territory_name").val();
    assignRoute["distributorName"] = $("#distributor_name").val();
    assignRoute["employeeId"] = $("#employee_id").val();  
    assignRoute["dayName"] = $("#day_name").val();  
    assignRoute["routeDetails"] = $("#route_details").val();  
   
    
    submitAssignRouteEditFromToServer(assignRoute, id);
    
 } 
     
    
	function submitAssignRouteEditFromToServer(data, id) {

    let url = baseUrl + "/api/assign-Route/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          
            window.location.href = baseUrl + "/admin/assignRoute";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}



