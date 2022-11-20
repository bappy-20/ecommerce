var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
jQuery('#expenseTypeAddForm').validate({
	rules:{
	expensetype:"required",
	},messages:{
		expensetype:"Please enter expense type",
	},
	submitHandler:function(form){
		submitExpenseTypeForm();		
	}	
});

jQuery('#expenseTypeEditForm').validate({
	rules:{
	expensetype:"required",
	},messages:{
		expensetype:"Please enter expense type",
	},
	submitHandler:function(form){
		submitExpenseTypeEditForm();
	}	
});
function submitExpenseTypeForm() {
	this.event.preventDefault();
    let expenseType = {};    
    expenseType["expenseType"] = $("#expenseType1").val();
    submitExpenseTypeFormToServer(expenseType);
}
function submitExpenseTypeFormToServer(data) {
    let url = baseUrl +'/api/save-expenseType'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
            swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/expenseType';
        },
        error: function (e) {
            console.log("ERROR: ", e);
            
            swal(`${e}`);
        }
    });
}
function getExpenseType(id){
	let url = baseUrl + "/api/get-expenseType/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	            setTheExpenseTypeValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}


function setTheExpenseTypeValues(result){	
	$("#id").val(result.id);
	$("#expenseType1").val(result.expenseType)
}
function submitExpenseTypeEditForm() {
	this.event.preventDefault();
	let expenseType = {};
    let id = $("#id").val();
    expenseType["id"] = id;    
    expenseType["expenseType"] = $("#expenseType1").val();  
    submitExpenseTypeEditFormToServer(expenseType, id);
} 

	function submitExpenseTypeEditFormToServer(data, id) {
    let url = baseUrl + "/api/update-expenseType/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          if(result.statusCode==500){
        	  //alert(result.message)
        	  swal(`${result.message}`);
          }else{
        	  swal(`${result.message}`);
        	  window.location.href = baseUrl + "/admin/expenseType";
          }  
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-expenseType/"+id;
		
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

	
	