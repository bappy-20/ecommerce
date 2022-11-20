var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#roleAddForm').validate({
	rules:{
	name:"required",
	},messages:{
		name:"Please enter name",
	},
	submitHandler:function(form){
		submitRoleForm();		
	}	
});

jQuery('#roleEditForm').validate({
	rules:{
	name:"required",
	},messages:{
		name:"Please enter name",
	},
	submitHandler:function(form){
		submitRoleEditForm();
	}	
});

function submitRoleForm() {
	this.event.preventDefault();
    let region = {};  
    region["name"] = $("#name").val();
    let orgId = $("#orgId").val();
    region["description"]=$("#description").val();
    submitRoleFormToServer(region,orgId);
}

function submitRoleFormToServer(data,orgId) {

    let url = baseUrl +'/api/user-role/'+orgId
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
           window.location.href =  baseUrl +'/superadmin/role';            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
function getRole(id){

	let url = baseUrl + "/api/user-role/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheRoleValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}
function setTheRoleValues(result){	
	$("#id").val(result.id);
	$("#name").val(result.name);
	$("#description").text(result.description);
}
function submitRoleEditForm() {

	this.event.preventDefault();
	let region = {};
    let id = $("#id").val();
    region["id"] = id;    
    region["name"] = $("#name").val();
    region["description"]=$("#description").val();
    submitRoleEditFormToServer(region, id);
} 
     
	function submitRoleEditFormToServer(data, id) {

    let url = baseUrl + "/api/user-role/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {          
            window.location.href = baseUrl + "/admin/manage-role";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	function deleteEntry(id) {
		let url = baseUrl +"/api/user-role/"+id;
	    var msg = confirm("Are you sure you want to delete?");
	    
	    if (msg){
	    	   $.ajax({
	              type: "DELETE",
	              url: url,
	              contentType: 'application/json; charset=utf-8',
	              dataType: "JSON",
	              success: function (response) {
	                  alert("Deletion Success!");
	            	  window.location.reload();
	              },
	              error: function () {
	                  alert("error");
	              }
	          }); 
	    }	                 
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-distributor/"+id;
		
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