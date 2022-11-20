
var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitCarFrom() {
	this.event.preventDefault();
    let car = {};
    car["carNumber"] = $("#carNumber").val();
    car["carType"] = $("#carType").val();
    car["carFacility"] = $("#carFacility").val();
    car["carImage"] = $("#carImage").val();
    car["carDocument"] = $("#carDocument").val();
    car["carName"] = $("#carName").val();
    car["seatCount"] = $("#seatCount").val();
    
    car["licenseNumber"] = $("#licenseNumber").val();
    var date1 =  $("#licenseExpiredDate").val();
	var newdate1 = date1.split("/").reverse().join("-");
    car["licenseExpiredDate"] = newdate1;
    
    car["insuranceNumber"] = $("#insuranceNumber").val();
    var date2 =  $("#insuranceExpiredDate").val();
	var newdate2 = date2.split("/").reverse().join("-");
    car["insuranceExpiredDate"] = newdate2;
    
    car["engineChesisNumber"] = $("#engineChesisNumber").val();
  	var date =  $("#fitnessExpiredDate").val();
	var newdate = date.split("/").reverse().join("-");
    car["fitnessExpiredDate"] =newdate; 
    submitCarFromToServer(car);
}


function submitCarFromToServer(data) {

    let url = baseUrl + "/api/car"
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
        	
            if(result.statusCode==500){
            	
            	$('#result').text(result.message);
            }else{
            	 window.location.href = baseUrl + "/admin/car";
            }
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getCarInfo(id){
	let url = baseUrl + "/api/car/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	            setTheCarValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheCarValues(result){

	$("#id").val(result.id);
	$("#carNumber").val(result.carNumber);
	$("#carType").val(result.carType).change();
	$("#carFacility").val(result.carFacility).change();	
    $("#carName").val(result.carName);
    $("#seatCount").val(result.seatCount);
    $("#engineChesisNumber").val(result.engineChesisNumber);
    $("#carImage").val(result.carImage);
    $("#carDocument").val(result.carDocument);
    $("#licenseNumber").val(result.licenseNumber);
    $("#insuranceNumber").val(result.insuranceNumber);
    
    var date1 =  result.licenseExpiredDate;
	var newdate1 = date1.split("/").reverse().join("-");
    $("#licenseExpiredDate").val(newdate1);
    
    var date2 =  result.insuranceExpiredDate;
	var newdate2 = date2.split("/").reverse().join("-");
    $("#insuranceExpiredDate").val(newdate2);
    
    var date =  result.fitnessExpiredDate;
	var newdate = date.split("/").reverse().join("-");
    $("#fitnessExpiredDate").val(newdate);
}


function submitCarEditFrom() {

	this.event.preventDefault();
    let car = {};
    let id = $("#id").val();
    car["id"] = id;
    car["carNumber"] = $("#carNumber").val();
    car["carType"] = $("#carType").val();
    car["carName"] = $("#carName").val();
    car["carFacility"] = $("#carFacility").val();
    car["carImage"] = $("#carImage").val();
    car["carDocument"] = $("#carDocument").val();
    car["seatCount"] = $("#seatCount").val();
    car["engineChesisNumber"] = $("#engineChesisNumber").val();
       
    car["licenseNumber"] = $("#licenseNumber").val();
    var date1 =  $("#licenseExpiredDate").val();
	var newdate1 = date1.split("/").reverse().join("-");
    car["licenseExpiredDate"] = newdate1;
    
    car["insuranceNumber"] = $("#insuranceNumber").val();
    var date2 =  $("#insuranceExpiredDate").val();
	var newdate2 = date2.split("/").reverse().join("-");
    car["insuranceExpiredDate"] = newdate2;
    
    var date =  $("#fitnessExpiredDate").val();
	var newdate = date.split("/").reverse().join("-");
    car["fitnessExpiredDate"] =newdate; 
    submitCarEditFromToServer(car, id);
}

function submitCarEditFromToServer(data, id) {

    let url = baseUrl + "/api/car/"+id;
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
            	 window.location.href = baseUrl + "/admin/car";
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getDropDownValue(){
	$('select[name="userType"]').on('change', function() {
	    var userType = $(this).val();
	    
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
	    });	
}

function submitAssignCarForm() {

	this.event.preventDefault();
	let idarr=$(".select2").val();	
    let carId = $("#carId").val();
    let usertype=$("#userType").val();
    if(usertype=='Driver' && idarr.length>1){
    	$("#message").text('Please select one driver only');
    	
    }else{
    	submitAssignCarToServer(carId,idarr);
    }
    
}

function submitAssignCarToServer(carId, idarr) {
	
    let url = baseUrl +"/api/assign-user-to-car/"+carId+"/"+idarr;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url,
        success: function (result) {
       
            window.location.href = baseUrl + "/admin/user-assign-car";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
function deleteEntry(userid,carid) {
	let url = baseUrl +"/api/user/delete-from-car/"+userid+"/"+carid;
    let confirmDelete = confirm("Are you sure you want to delete?");
    
    if (confirmDelete){
    	  $.ajax({
              type: "DELETE",
              url: url,
              contentType: 'application/json; charset=utf-8',
              dataType: "JSON",
              success: function (response) {
            	  window.location.reload();
              },
              error: function () {
                  alert("error");
              }
          });
    }else{
    	alert("cancelled!");
    }
    	
                 
}
function submitAssignRouteForm() {

	this.event.preventDefault();
    let carId = $("#carId").val();
    let routeId=$(".select2").val();	
    
    	submitAssignRouteToServer(carId,routeId);
    
}


function submitAssignRouteToServer(carId, routeId) {
	
    let url = baseUrl +"/api/assign-route-to-car/"+carId+"/"+routeId;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url,
        success: function (result) {
       
            window.location.href = baseUrl + "/admin/route-assign-car-home";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getCarInfoForAssignRoute(id){
	let url = baseUrl + "/api/car/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	            setAssignRouteValues(result.data.rents);
	           
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}
function setAssignRouteValues(data) {
	console.log(data);
	data.forEach(function(item) {
		
		$('.box-body').append('<p><strong>Route name : </strong>' + item.roadName+ '</p>')
		.append('<p><strong>Route type : </strong>' + item.type+ '</p>')
		.append('<p><strong>Route Description : </strong>' + item.description+ '</p>')
		
		var checkpoint = item.route;		
		$('.box-body').append('<tr><td><strong>Check Point name : </strong></td>');
		checkpoint.forEach(function(item1) {
			$('.box-body').append('<td>' + item1 + ' &nbsp;&nbsp;&nbsp; </td>')

		});
		$('.box-body').append('</tr><br>');
		
		
		
		var rent = item.rent;	
		var rentFrom = item.rentFrom;	
		$('.box-body').append('<tr><td><strong>Rent from</strong></td><td>&nbsp;<strong>Rent</strong></td></tr>');
		rent.forEach((num1, index) => {
			  const num2 = rentFrom[index];
			 
			  $('.box-body').append('<tr><td>'+ num2 +'</td><td>&nbsp;&nbsp;&nbsp;'+num1+'</td></tr>')
			});
		
		
		$('.box-body').append('<hr>');
	});
}

function submitCarRequestForm() {
	this.event.preventDefault();
    let carRequest = {};
    carRequest["carId"] = $("#carId").val();
    carRequest["carType"] = $("#carType").val();
    
    carRequest["passengerNumber"] = $("#passengerNumber").val();
    carRequest["rent"] = $("#rent").val();
    carRequest["requesterName"] = $("#requesterName").val();    
    carRequest["mobile"] = $("#mobile").val();
    carRequest["rent"] = $("#rent").val();
    carRequest["address"] = $("#address").val();    
    carRequest["startPoint"] = $("#startPoint").val();
    carRequest["endPoint"] = $("#endPoint").val();
    carRequest["tripTime"] = $("#reservationtime").val();

    submitCarRequestToServer(carRequest);
}

function submitCarRequestToServer(data) {

    let url = baseUrl + "/api/car-request"
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
        	
            if(result.statusCode==500){
            	
            	$('#result').text(result.message);
            }else{
            	 window.location.href = baseUrl + "/admin/booked-car-home";
            }
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getCarRequestInfo(id){
	let url = baseUrl + "/api/car-book-request/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	            setTheCarRequestValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheCarRequestValues(result){

	$("#id").val(result.id);	
    $("#carId").val(result.carId).change();	
    $("#carType").val(result.carType).change();	
    
    $("#passengerNumber").val(result.passengerNumber);
    $("#rent").val(result.rent);
    $("#requesterName").val(result.requesterName);    
    $("#mobile").val(result.mobile);
    $("#rent").val(result.rent);
    $("#address").val(result.address);    
    $("#startPoint").val(result.startPoint);
    $("#endPoint").val(result.endPoint);
    $("#reservationtime").val(result.tripTime);
}

function submitCarRequestEditFrom() {

	this.event.preventDefault();
    let carRequest = {};
    let id = $("#id").val();
    carRequest["id"] = id;
    carRequest["carId"] = $("#carId").val();
    carRequest["carType"] = $("#carType").val();
   
    carRequest["passengerNumber"] = $("#passengerNumber").val();
    carRequest["rent"] = $("#rent").val();
    carRequest["requesterName"] = $("#requesterName").val();    
    carRequest["mobile"] = $("#mobile").val();
    carRequest["rent"] = $("#rent").val();
    carRequest["address"] = $("#address").val();    
    carRequest["startPoint"] = $("#startPoint").val();
    carRequest["endPoint"] = $("#endPoint").val();
    carRequest["tripTime"] = $("#reservationtime").val();
    submitCarRequestEditFromToServer(carRequest, id);
}

function submitCarRequestEditFromToServer(data, id) {

    let url = baseUrl + "/api/car-request/"+id;
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
            	 window.location.href = baseUrl + "/admin/booked-car-home";
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getValueFromDropDownValue(){
	$('select[name="carname"]').on('change', function() {
	    var carname = $(this).val();
	    var tripTime= $("#reservationtime").val();
	   
	    var res = tripTime.substring(0, 10);

	    var newdate1 = res.split("/").reverse().join("-");
	   
	    if(carname!=null) {        
	        var table = $('#empTable').dataTable({
	            "deferRender" : true,
	            "searching": false,
	            "ordering": false,
	            "lengthChange": false,
	            "destroy":true,
	            "sAjaxSource" : baseUrl+'/api/book-request-by-car-id/'+carname+'/'+newdate1,
	            "sAjaxDataProp" : "",
	            "columns" : [	                  
	                {
	                data : 'carId'
	               }, 
	              
	             {
	              data : 'startPoint'
	             },
	            {
	                data : 'endPoint'
	            },
	            {
	                data : 'rent'
	               },
		      {
		      data : 'tripStartTime'
		         } ,
			   {
			     data : 'tripEndTime'
			      }  
	            ]
	        });
	    } 
	    });	
}

function getDropDownValueForCar(){
	$('select[name="carType"]').on('change', function() {
	    var carType = $(this).val();
	    
	    if(carType!=null) {        
	    $.ajax({
	    url: baseUrl+'/api/get-car-by-type/'+carType,
	    type: "GET",
	    dataType: "json",
	    success:function(data) {
	    $('select[name="carname"]').empty();
	    $('select[name="carname"]').append('<option value=""> -- Please Select -- </option>');
	    $.each(data, function(idx, obj){ 
	         $('select[name="carname"]')
	         .append('<option value="'+ obj.id +'">'+ obj.carName +'</option>');
	    });
	        
	    }
	    });
	    }else{
	        $('select[name="carname"]').append('<option value="">Not found</option>');
	    } 
	    });	
}
