var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function getDropDownValue(){
	$('select[name="userType"]').on('change', function() {
	    var userType = $(this).val();
	    
	    if(userType!=null) {        
	    $.ajax({
	    url: baseurl+'/api/user-by-type/'+userType,
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

function submitSalaryFrom() {
	this.event.preventDefault();
    let salary = {};
    salary["carId"] = $("#carId").val();
    salary["userType"] = $("#userType").val();
    salary["userId"] = $("#userId").val();
    salary["salaryType"] = $("#salaryType").val();
    if($('#salaryType').val()=='daily'){
    	alert('daily');
    	salary["dayorMonth"] = $("#day").val();	
    }else{
    	salary["dayorMonth"] = $("#monthly").val();	
    }
    salary["amount"] = $("#amount").val();     
    submitSalaryFromToServer(salary);
}

function submitSalaryFromToServer(data) {

    let url = baseUrl + "/api/salary"
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
            	 window.location.href = baseUrl + "/admin/salary-home";
            }
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getSalaryInfo(id){
	let url = baseUrl + "/api/salary/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {	       
	            setTheSalaryValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheSalaryValues(result){	
	$("#id").val(result.id);
	$("#carId").val(result.carId).change();
	$("#userType").val(result.userType).change();
	$("#userId").val(result.userId).change();	
    $("#salaryType").val(result.salaryType);
    $("#month").val(result.dayorMonth);
    $("#day").val(result.dayorMonth);
    $("#amount").val(result.amount);
    
}

function submitSalaryEditFrom() {
	this.event.preventDefault();
	let salary = {};
    let id = $("#id").val();
    salary["id"] = id;    
    salary["carId"] = $("#carId").val();
    salary["userType"] = $("#userType").val();
    salary["userId"] = $("#userId").val();
    salary["salaryType"] = $("#salaryType").val();
    if($('#salaryType').val() == 'daily'){
    	alert('daily');
    	salary["dayorMonth"] = $("#day").val();	
    }else{
    	salary["dayorMonth"] = $("#monthly").val();	
    }
    
    salary["amount"] = $("#amount").val();    
    
    submitSalaryEditFromToServer(salary, id);
}

function submitSalaryEditFromToServer(data, id) {

    let url = baseUrl + "/api/salary/"+id;
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
            	 window.location.href = baseUrl + "/admin/salary-home";
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
