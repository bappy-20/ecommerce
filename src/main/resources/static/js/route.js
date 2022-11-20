var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitRouteFrom() {
	this.event.preventDefault();
    let route = {};
    route["regionName"] = $("#region_name").val();
    route["areaName"] = $("#area_name").val();
    route["territoryName"] = $("#territory_name").val();
    route["distributorName"] = $("#distributor_name").val();
    route["routeName"] = $("#route_name").val(); 
    route["routeDetails"] = $("#route_details").val();
    
    
    submitRouteFromToServer(route);
}

function submitRouteFromToServer(data) {

    let url = baseUrl +'/api/route'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
            window.location.href =  '/admin/route';
            
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getRoute(id){

	let url = baseUrl + "/api/route/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheRouteValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheRouteValues(result){		
	$("#region").val(result.regionName).change();
	$("#area").val(result.areaName).change();
	$("#teritory").val(result.territoryName).change();
    $("#routeName").val(result.routeName);   
}

	function submitRouteEditFrom() {

	this.event.preventDefault();
	let route = {};
    let id = $("#id").val();
    route["id"] = id;    
    route["regionName"] = $("#region_name").val();
    route["areaName"] = $("#area_name").val();
    route["territoryName"] = $("#territory_name").val();
    route["distributorName"] = $("#distributor_name").val();
    route["routeName"] = $("#route_name").val();  
    route["routeDetails"] = $("#route_details").val();  
   
    
    submitRouteEditFromToServer(route, id);
    
 } 
     
    
	function submitRouteEditFromToServer(data, id) {

    let url = baseUrl + "/api/route/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          
            window.location.href = baseUrl + "/admin/route";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-route/"+id;
		
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




