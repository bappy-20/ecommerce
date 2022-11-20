var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#retailCompailnAddForm').validate({
	rules:{
	title:"required",
	retailid:"required",
	},messages:{
		title:"Please enter title",
		retailid:"Please enter retail name",
	},
	submitHandler:function(form){
		submitRetailComplainForm();		
	}	
});

jQuery('#retailComplainEditForm').validate({
	rules:{
	title:"required",
	retailid:"required",
	},messages:{
		title:"Please enter title",
		retailid:"Please enter retail name",
	},
	submitHandler:function(form){
		submitRetailComplainEditForm();
	}	
});

function submitRetailComplainForm() {
	this.event.preventDefault();
    let retailComplain = {};    
    retailComplain["title"] = $("#title").val();
    retailComplain["retailId"] = $("#retailId").val();
    retailComplain["note"] = $("#note").val();
    submitRetailComplainFormToServer(retailComplain);
}
function submitRetailComplainFormToServer(data) {
    let url = baseUrl +'/api/save-retailComplain'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
            swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/retailComplain';
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
function getRetailComplain(id){
	let url = baseUrl + "/api/get-retailComplain/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	            setTheRetailComplainValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}


function setTheRetailComplainValues(result){	
	$("#id").val(result.id);
	$("#title").val(result.title);
	$("#retailId").val(result.retailId);
	$("#note").val(result.note);
}
function submitRetailComplainEditForm() {
	this.event.preventDefault();
	let retailComplain = {};
    let id = $("#id").val();
    retailComplain["id"] = id;    
    retailComplain["title"] = $("#title").val();
    retailComplain["retailId"] = $("#retailId").val();
    retailComplain["note"] = $("#note").val();   
    submitRetailComplainEditFormToServer(retailComplain, id);
} 
	function submitRetailComplainEditFormToServer(data, id) {
    let url = baseUrl + "/api/update-retailComplain/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          if(result.statusCode==500){
        	 // alert(result.message)
        	  swal(`${result.message}`);
          }else{
        	  swal(`${result.message}`);
        	  window.location.href = baseUrl + "/admin/retailComplain";
          }  
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-retailComplain/"+id;
		
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
