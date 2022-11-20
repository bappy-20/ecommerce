var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#employeeAddForm').validate({
	rules:{
	employeeid:"required",
	employeename:"required",
	emp_category:"required",
	reporting_id:"required",
		
	},messages:{
		employeeid:"Please enter employee id",
		employeename:"Please enter employee name",
		emp_category:"Please enter employee category",
		reporting_id:"Please enter reporting boss name",
	},
	submitHandler:function(form){
		submitEmployeeForm();
	}	
});


jQuery('#employeeEditForm').validate({
	rules:{
	employeeid:"required",
	employeename:"required",
	emp_category:"required",
	reporting_id:"required",
		
	},messages:{
		employeeid:"Please enter employee id",
		employeename:"Please enter employee name",
		emp_category:"Please enter employee category",
		reporting_id:"Please enter reporting boss name",
	},
	submitHandler:function(form){
		submitEmployeeEditForm();
	}	
});

function submitEmployeeForm() {
	this.event.preventDefault();
    let employee = {};
    employee["employeeId"] = $("#employee_id").val();
    employee["empName"] = $("#emp_name").val();
    employee["password"] = $("#password").val();
    employee["empAddress"] = $("#emp_address").val();
    employee["empPhone"] = $("#emp_phone").val(); 
    employee["empCategory"] = $("#emp_category").val();  
    employee["reportingId"] = $("#reporting_id").val();
    employee["empImage"] = $("#logo").val();
    
    employee["fatherName"] = $("#fatherName").val(); 
    employee["motherName"] = $("#motherName").val();  
   
    var date =  $("#dateOfBirth").val();
	var newdate = date.split("/").reverse().join("-");
	employee["dateOfBirth"] =newdate;     
    employee["emergencyContact"] = $("#emergencyContact").val();    
    employee["generalBankInfo"] = $("#generalBankInfo").val(); 
    employee["mobileBankInfo"] = $("#mobileBankInfo").val();  
    employee["empNid"] = $("#nid").val();
    
    submitEmployeeFormToServer(employee);
}

function submitEmployeeFormToServer(data) {

    let url = baseUrl +'/api/get-employee'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            alert("create successful!")
            window.location.href =  baseUrl +'/admin/employee';
                   
        },
        error: function (e) {
            console.log("ERROR: ", e);
            alert("Problem");
        }
    });
}


function getEmployee(id){

	let url = baseUrl + "/api/get-employee/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheEmployeeValues(result.data);
	            console.log(result);
	            alert("hi");
	           
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheEmployeeValues(result){	
	$("#id").val(result.id);
	$("#employee_id").val(result.employeeId);
	$("#emp_name").val(result.empName);
	/*$("#password").val(result.password);*/
    $("#emp_address").val(result.empAddress);
    $("#emp_phone").val(result.empPhone);
    $("#emp_category").val(result.empCategory);
    $("#reporting_id").val(result.reportingId);
    $("#logo").val(result.empImage);
    
   $("#fatherName").val(result.fatherName); 
   $("#motherName").val(result.motherName);  
   var date= result.dateOfBirth;
   var newdate = date.split("/").reverse().join("-");
   
   $("#dateOfBirth").val(newdate);	    
   $("#emergencyContact").val(result.emergencyContact);    
   $("#generalBankInfo").val(result.generalBankInfo); 
   $("#mobileBankInfo").val(result.mobileBankInfo);  
   $("#nid").val(result.empNid);
 
}

	function submitEmployeeEditForm() {

	this.event.preventDefault();
	let employee = {};
    let id = $("#id").val();
    employee["id"] = id;    
    employee["employeeId"] = $("#employee_id").val();
    employee["empName"] = $("#emp_name").val();
    /*employee["password"] = $("#password").val();*/
    employee["empAddress"] = $("#emp_address").val();
    employee["empPhone"] = $("#emp_phone").val();  
    employee["empCategory"] = $("#emp_category").val();  
    employee["reportingId"] = $("#reporting_id").val();  
    employee["empImage"] = $("#logo").val();     
    
    employee["fatherName"] = $("#fatherName").val(); 
    employee["motherName"] = $("#motherName").val();  
   
    var date =  $("#dateOfBirth").val();
	var newdate = date.split("/").reverse().join("-");
	employee["dateOfBirth"] =newdate;     
    employee["emergencyContact"] = $("#emergencyContact").val();    
    employee["generalBankInfo"] = $("#generalBankInfo").val(); 
    employee["mobileBankInfo"] = $("#mobileBankInfo").val();  
    employee["empNid"] = $("#nid").val();
    
    submitEmployeeEditFormToServer(employee, id);
    
  } 
        
	function submitEmployeeEditFormToServer(data, id) {

    let url = baseUrl + "/api/get-employee/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          
            window.location.href = baseUrl + "/admin/employee";
             
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


	function getEmployeeProfile(id){
		let url = baseUrl + "/api/get-employee/"+id;
		$.ajax({
		        type: "GET",
		        url: url,
		        dataType : "JSON",
		        success: function (result) {
		        
		            setTheEmployeeProfileValues(result.data);
		            console.log(result);
		           
		        },
		        error: function (e) {
		            console.log("ERROR: ", e);
		        }
		    });

	}

	function setTheEmployeeProfileValues(result){	
		$("#id").val(result.id);
		$("#employee_id").val(result.employeeId);
		$("#emp_name").val(result.empName);
		$("#password").val(result.password);
	    $("#emp_address").val(result.empAddress);
	    $("#emp_phone").val(result.empPhone);
	    $("#emp_category").val(result.empCategory);
	    $("#reporting_id").val(result.reportingId);
	    $("#logo").val(result.empImage);
	    
	   $("#fatherName").val(result.fatherName); 
	   $("#motherName").val(result.motherName);  
	   var date= result.dateOfBirth;
	   var newdate = date.split("/").reverse().join("-");
	   
	   $("#dateOfBirth").val(newdate);	    
	   $("#emergencyContact").val(result.emergencyContact);    
	   $("#generalBankInfo").val(result.generalBankInfo); 
	   $("#mobileBankInfo").val(result.mobileBankInfo);  
	   $("#nid").val(result.empNid);
	 
	}
