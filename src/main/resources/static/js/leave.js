var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#leaveAddForm').validate({
	rules:{
	employeeId:"required",
	leaveType:"required",
	},messages:{
		employeeId:"Please enter employee name",
		leaveType:"Please enter leave type",
	},
	submitHandler:function(form){
		submitLeaveForm();		
	}	
});

jQuery('#leaveEditForm').validate({
	rules:{
	employeeId:"required",
	leaveType:"required",
	},messages:{
		employeeId:"Please enter employee name",
		leaveType:"Please enter leave type",
	},
	submitHandler:function(form){
		submitLeaveEditForm();
	}	
});
function submitLeaveForm() {
	this.event.preventDefault();
    let leave = {};    
    leave["employeeId"] = $("#employeeId").val();
    leave["leaveType"] = $("#leaveType").val();
    leave["fromDate"] = $("#fromDate").val();
    leave["toDate"] = $("#toDate").val();
    leave["comment"] = $("#comment").val();
    //alert("hi")
    submitLeaveFormToServer(leave);
}

function submitLeaveFormToServer(data) {
    let url = baseUrl +'/api/save-leaveModel'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
            swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/leave';
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
function getLeave(id){
	let url = baseUrl + "/api/get-leave/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	            setTheLeaveValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}


function setTheLeaveValues(result){	
	$("#id").val(result.id);
	$("#employeeId").val(result.employeeId);
	$("#leaveType").val(result.leaveType);
	$("#fromDate").val(result.fromDate);
	$("#toDate").val(result.toDate);
	$("#comment").val(result.comment);
	$("#status").val(result.status);
}
function submitLeaveEditForm() {
	this.event.preventDefault();
	let leave = {};
    let id = $("#id").val();
    leave["id"] = id;    
    leave["employeeId"] = $("#employeeId").val();
    leave["leaveType"] = $("#leaveType").val();
    leave["fromDate"] = $("#fromDate").val();
     leave["toDate"] = $("#toDate").val();
    leave["comment"] = $("#comment").val();
    leave["status"] = $("#status").val();     
    submitLeaveEditFormToServer(leave, id);
} 
	function submitLeaveEditFormToServer(data, id) {
    let url = baseUrl + "/api/update-leave/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          if(result.statusCode==500){
        	  alert(result.message)
        	  swal(`${result.message}`);
          }else{
        	  swal(`${result.message}`);
        	  window.location.href = baseUrl + "/admin/leave";
          }  
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-leave/"+id;
		
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

	
	function submitPendingLeaveEditForm() {
		this.event.preventDefault();
		let leave = {};
	    let id = $("#id").val();
	    leave["id"] = id;    
	    leave["employeeId"] = $("#employeeId").val();
	    leave["leaveType"] = $("#leaveType").val();
	    leave["fromDate"] = $("#fromDate").val();
	    leave["toDate"] = $("#toDate").val();
	    leave["comment"] = $("#comment").val();
	    leave["dayCount"] = $("#dayCount").val();
	      
	    submitPendingLeaveEditFormToServer(leave, id);
	   //changeStatus(id);    
	} 
	
function submitPendingLeaveEditFormToServer(data, id) {
	    let url = baseUrl + "/api/update-leave/"+id;
	    $.ajax({
	        type: "PUT",
	        contentType: "application/json",
	        url: url, 
	        data: JSON.stringify(data),
	        dataType : "JSON",
	        success: function (result) {
	          if(result.statusCode==500){
	        	  alert(result.message)
	          }else{
	        	  window.location.href = baseUrl + "/admin/pendingLeave";
	        	  changeStatus(id);  
	          }  
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	}

	function getPendingLeave(id){
		let url = baseUrl + "/api/get-leave/"+id;
		$.ajax({
		        type: "GET",
		        url: url,
		        dataType : "JSON",
		        success: function (result) {
		            setThePendingLeaveValues(result.data);
		            console.log(result);
		        },
		        error: function (e) {
		            console.log("ERROR: ", e);
		        }
		    });
	}


	function setThePendingLeaveValues(result){	
		$("#id").val(result.id);
		$("#employeeId").val(result.employeeId);
		$("#leaveType").val(result.leaveType);
		$("#fromDate").val(result.fromDate);
		$("#toDate").val(result.toDate);
		$("#comment").val(result.comment);
		$("#dayCount").val(result.dayCount);
	}
