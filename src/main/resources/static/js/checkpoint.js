var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
function setLatLgn(latlng){
	var res = latlng.toString().replace("(", "");
	var res = res.toString().replace(")", "");
	var res = res.split(",");
	
	 $("#lat").val(res[0]);
	 $("#lng").val(res[1]);
	
}
function submitCheckPointFrom() {
	this.event.preventDefault();
    let checkPoint = {};
    checkPoint["name"] = $("#name").val();
    checkPoint["lat"] = $("#lat").val();
    checkPoint["lng"] = $("#lng").val();
    /*checkPoint["startPoint"] = $("#startPoint").val();
    checkPoint["endPoint"] = $("#endPoint").val();*/
    submitCheckPointFromToServer(checkPoint);
}


function submitCheckPointFromToServer(data) {

    let url = baseUrl+'/api/check-point'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
            window.location.href = baseUrl+'/admin/checkpoint';
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}



function getCheckPoint(id){

	let url = baseUrl + "/api/check-point/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheCheckPointValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheCheckPointValues(result){

	$("#id").val(result.id);
	$("#name").val(result.name);
	$("#lat").val(result.lat);
    $("#lng").val(result.lng);
   /* $("#startPoint").val(result.startPoint).change();
    $("#endPoint").val(result.endPoint).change();*/

}


function submitCheckPointEditFrom() {

	this.event.preventDefault();
    let checkPoint = {};
    let id = $("#id").val();
    checkPoint ["id"] = id;
    checkPoint["name"] = $("#name").val();
    checkPoint["lat"] = $("#lat").val();
    checkPoint["lng"] = $("#lng").val();
    /*checkPoint["startPoint"] = $("#startPoint").val();
    checkPoint["endPoint"] = $("#endPoint").val();*/
    submitCheckPointEditFromToServer(checkPoint, id);
}

function submitCheckPointEditFromToServer(data, id) {

    let url = baseUrl + "/api/check-point/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
            //console.log(result);
            window.location.href = baseUrl +"/admin/checkpoint";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getDropDownValue(){
	    var userType = "Checker";
	    
	    if(userType!=null) {        
	    $.ajax({
	    url: baseUrl+'/api/user-by-type/'+userType,
	    type: "GET",
	    dataType: "json",
	    success:function(data) {
	    
	    $('select[name="userName"]').empty();
	    $('select[name="userName"]').append('<option value=""> -- Please Select -- </option>');
	    $.each(data, function(idx, obj){ 
	         $('select[name="userName"]')
	         .append('<option value="'+ obj.id +'">'+ obj.username +'</option>');
	    });
	        
	    }
	    });
	    }else{
	        $('select[name="userName"]').append('<option value="">Not found</option>');
	    } 
	  
}
   