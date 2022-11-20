var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
jQuery('#companyAddForm').validate({
	rules:{
	companyname:"required",
	},messages:{
		companyname:"Please enter company name",
	},
	submitHandler:function(form){
		submitCompanyForm();		
	}	
});

jQuery('#companyEditForm').validate({
	rules:{
	companyname:"required",
	},messages:{
		companyname:"Please enter comapany name",
	},
	submitHandler:function(form){
		submitCompanyEditForm();
	}	
});

function submitCompanyForm() {
	this.event.preventDefault();
    let brand = {};
    brand["companyName"] = $("#companyName").val();
    brand["companyOrigin"] = $("#companyOrigin").val();
    brand["logo"] = $("#logo").val();
    submitCompanyToServer(brand);
}

function submitCompanyToServer(data) {

    let url = baseUrl +'/api/save-company'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    	   swal(`${result.message}`);
           window.location.href =  baseUrl+'/admin/company';
        },
        error: function (e) {
        	swal(`${e}`);
            console.log("ERROR: ", e);
        }
    });
}

function getCompany(id){

	let url = baseUrl + "/api/get-company/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	        	setTheCompanyValues(result.data);
	            console.log(result);
	           
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheCompanyValues(result){	
    
	$("#id").val(result.id);
	$("#companyName").val(result.companyName);
	$("#companyOrigin").val(result.companyOrigin);
    $("#logo").val(result.logo);
 
}
	function submitCompanyEditForm() {
	this.event.preventDefault();
	let brand = {};
    let id = $("#id").val();
    brand["id"] = id;      
    brand["companyName"] = $("#companyName").val();
    brand["companyOrigin"] = $("#companyOrigin").val();
    brand["logo"] = $("#logo").val();
       
    submitCompanyEditFormToServer(brand, id);
    
  } 
        
	function submitCompanyEditFormToServer(data, id) {

    let url = baseUrl + "/api/update-company/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	   swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/company";
             
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-company/"+id;
		
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



