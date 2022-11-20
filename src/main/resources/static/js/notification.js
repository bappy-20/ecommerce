var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#notificationAddForm').validate({
	rules:{
	notification_type:"required",
	title:"required",
	message:"required",
	},messages:{
		notification_type:"Please enter notification type",
		title:"Please enter notification title",
		message:"Please enter your message",
	},
	submitHandler:function(form){
		submitNotificationForm();		
	}	
});

jQuery('#notificationEditForm').validate({
	rules:{
	notification_type:"required",
	title:"required",
	message:"required",
	},messages:{
		notification_type:"Please enter notifiction type",
		title:"Please enter message title",
		message:"Please enter your messsage",
	},
	submitHandler:function(form){
		submitNotificationEditForm();
	}	
});

function submitNotificationForm() {
	this.event.preventDefault();
    let notification = {};
    notification["notificationType"] = $("#notification_type").val();
    notification["title"] = $("#title").val();
    notification["message"] = $("#message").val().trim();
    notification["fileName"] = $("#fileName").val();
    submitNotificationFormToServer(notification);
}

function submitNotificationFormToServer(data) {

    let url = baseUrl +'/api/notification'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
    	 	swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/notification';            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
function getNotification(id){

	let url = baseUrl + "/api/notification/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheNotificationValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}
function setTheNotificationValues(result){	
	$("#id").val(result.id);
	$("#notification_type").val(result.notificationType).change();
	$("#title").val(result.title);
	$("#message").val(result.message);
    $("#fileName").val(result.fileName);
 
}
function submitNotificationEditForm() {
	
	this.event.preventDefault();
	let notification = {};
    let id = $("#id").val();
    notification["id"] = id; 
    notification["notificationType"] = $("#notification_type").val();
    notification["title"] = $("#title").val();
    notification["message"] = $("#message").val().trim();
    notification["fileName"] = $("#fileName").val(); 
    
    submitNotificationEditFormToServer(notification, id);
 } 
	function submitNotificationEditFormToServer(data, id) {

    let url = baseUrl + "/api/notification/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/notification";
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-notification/"+id;
		
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

	