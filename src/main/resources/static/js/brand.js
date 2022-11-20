var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#frm').validate({
	rules:{
		brandname:"required",
		company_id:"required",
		
	},messages:{
		brandname:"Please enter brand name",
		company_id:"Please enter company name",
	},
	submitHandler:function(form){
		submitBrandForm();		
	}	
});

jQuery('#frmEdit').validate({
	rules:{
		brandname:"required",
		company_id:"required",
	},messages:{
		brandname:"Please enter brand name",
		company_id:"Please enter company name",
	},
	submitHandler:function(form){
		submitBrandEditForm();
	}	
});

function submitBrandForm() {
	this.event.preventDefault();
    let brand = {};
    brand["brandName"] = $("#brandName").val();
    brand["companyId"] = $("#company_id").val();
    brand["logo"] = $("#logo").val();
    submitBrandToServer(brand);
}

function submitBrandToServer(data) {

    let url = baseUrl +'/api/save-brand'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
    	   swal(`${result.message}`);
           window.location.href =  baseUrl+'/admin/brand';             
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}

function getbrand(id){

	let url = baseUrl + "/api/get-brand/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheBrandValues(result.data);
	            console.log(result);
	           
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheBrandValues(result){	
    
	$("#id").val(result.id);
	$("#brandName").val(result.brandName);
	$("#company_id").val(result.companyId);
    $("#logo").val(result.logo);
}

	function submitBrandEditForm() {
	this.event.preventDefault();
	let brand = {};
    let id = $("#id").val();
    brand["id"] = id;      
    brand["brandName"] = $("#brandName").val();
    brand["companyId"] = $("#company_id").val();
    brand["logo"] = $("#logo").val();
       
    submitBrandEditFormToServer(brand, id);
    
  } 
        
	function submitBrandEditFormToServer(data, id) {

    let url = baseUrl + "/api/update-brand/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	   swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/brand";
             
        },
        error: function (e) {
        	swal(`${e}`);
            console.log("ERROR: ", e);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-brand/"+id;
		
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
