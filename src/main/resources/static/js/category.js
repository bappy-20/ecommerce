var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitCategoryForm() {
	this.event.preventDefault();
    let category = {};  
    category["name"] = $("#name").val();
    
    submitCategoryFormToServer(category);
}


function submitCategoryFormToServer(data) {

    let url = baseUrl +'/api/category'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
  	  		swal(`${result.message}`);
            window.location.href =  baseUrl+'/admin/category';
            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}




function getCategory(id){

	let url = baseUrl + "/api/category/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheCategoryValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheCategoryValues(result){	
	$("#id").val(result.id);
	$("#name").val(result.name);
	
 
}


function submitCategoryEditForm() {

	this.event.preventDefault();
	let category = {};
    let id = $("#id").val();
    category["id"] = id;    
    category["name"] = $("#name").val();
    
    
    submitCategoryEditFormToServer(category, id);
    
     } 
     
    
	function submitCategoryEditFormToServer(data, id) {

    let url = baseUrl + "/api/category/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    		swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/category";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-category/"+id;
		
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
