var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitDayModelForm() {
	this.event.preventDefault();
    let dayModel = {};  
    dayModel["empId"] = $("#emp_id").val();
    dayModel["dayName"] = $("#day_name").val();  
    dayModel["routeId"] = $("#route_id").val();  
    submitDayModelFormToServer(dayModel);
}
function submitDayModelFormToServer(data) {

    let url = baseUrl +'/api/day-Model'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
           // alert("added successfully!")
            swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/dayModel';
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
function getDayModel(id){

	let url = baseUrl + "/api/day-Model/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheDayModelValues(result.data);
	            console.log(result);
	           
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}
function setTheDayModelValues(result){	
	$("#id").val(result.id);
	$("#emp_id").val(result.empId).change();
	$("#day_name").val(result.dayName).change();
	$("#route_id").val(result.routeId).change();	
}

function submitDayModelEditForm() {

	this.event.preventDefault();
	let dayModel = {};
    let id = $("#id").val();
    dayModel["id"] = id;    
    dayModel["empId"] = $("#emp_id").val();
    dayModel["dayName"] = $("#day_name").val();  
    dayModel["routeId"] = $("#route_id").val();  
    
    submitDayModelEditFormToServer(dayModel, id);    
     } 
     
    
	function submitDayModelEditFormToServer(data, id) {

    let url = baseUrl + "/api/day-Model/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	 	swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/dayModel";
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-day-model/"+id;
		
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



