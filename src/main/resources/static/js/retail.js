var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#retailAddForm').validate({
	rules:{
	retailname:"required",
	retail_type:"required",
	market_id:"required",
	},messages:{
		retailname:"Please enter retail name",
		retail_type:"Please enter retail type",
		market_id:"Please enter market name",
	},
	submitHandler:function(form){
		submitRetailForm();		
	}	
});

jQuery('#retailEditForm').validate({
	rules:{
	retailname:"required",
	retail_type:"required",
	market_id:"required",
	},messages:{
		retailname:"Please enter retial name",
		retail_type:"Please enter ratail type",
		market_id:"Please enter market name",
	},
	submitHandler:function(form){
		submitRetailEditForm();
	}	
});

function submitRetailForm() {
	this.event.preventDefault();
    let retail = {};
    retail["retailName"] = $("#retail_name").val();
    retail["retailType"] = $("#retail_type").val();
    retail["retailAddress"] = $("#retail_address").val();
    retail["retailOwner"] = $("#retail_owner").val();
    retail["retailPhone"] = $("#retail_phone").val();
    retail["retailLat"] = $("#retail_lat").val(); 
    retail["retailLong"] = $("#retail_long").val();  
    retail["nationalId"] = $("#national_id").val()
    retail["marketId"] = $("#market_id").val();  
    retail["storeImage1"] = $("#logo").val();   	
    submitRetailFormToServer(retail);
}

function submitRetailFormToServer(data) {

    let url = baseUrl +'/api/retail'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
        	 if(result.statusCode==500){
             	  //alert(result.message)
             	 swal(`${result.message}`);
               }else{
            	   swal(`${result.message}`);
            	   window.location.href =  baseUrl +'/admin/retail';
               }       
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getRetail(id){

	let url = baseUrl + "/api/retail/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheRetailValues(result.data);
	            console.log(result);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}
function setTheRetailValues(result){	
	$("#id").val(result.id);
	$("#retail_name").val(result.retailName);
	$("#retail_type").val(result.retailType).change();
	$("#retail_address").val(result.retailAddress);
	$("#retail_owner").val(result.retailOwner);
    $("#retail_phone").val(result.retailPhone);
    $("#retail_lat").val(result.retailLat);
    $("#retail_long").val(result.retailLong);  
    $("#national_id").val(result.nationalId);
    $("#market_id").val(result.marketId).change();
    $("#logo").val(result.storeImage1);  
   
}
	function submitRetailEditForm() {

	this.event.preventDefault();
	let retail = {};
    let id = $("#id").val();
    retail["id"] = id;    
    retail["retailName"] = $("#retail_name").val();
    retail["retailType"] = $("#retail_type").val();
    retail["retailAddress"] = $("#retail_address").val();
    retail["retailOwner"] = $("#retail_owner").val();
    retail["retailPhone"] = $("#retail_phone").val();
    retail["retailLat"] = $("#retail_lat").val(); 
    retail["retailLong"] = $("#retail_long").val();  
    retail["nationalId"] = $("#national_id").val()
    retail["marketId"] = $("#market_id").val();  
    retail["storeImage1"] = $("#logo").val();  
    
    submitRetailEditFormToServer(retail, id);
    
     }    
	function submitRetailEditFormToServer(data, id) {
	//	alert(id);
    let url = baseUrl + "/api/retail/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
    	 swal(`${result.message}`);
         window.location.href = baseUrl + "/admin/retail";
             
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-retail/"+id;
		
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
	
	
	function updatePending(id) {
		 let url = baseurl+"/api/update-pending/"+id;
	    	  swal({
	    	      title: "Are you sure you want to Approve?",
	    	     // icon: "warning",
	    	      buttons: [
	    	        'No, cancel it!',
	    	        'Yes, I am sure!'
	    	      ],
	    	   //   dangerMode: true,
	    	    }).then(function(isConfirm) {
	    	      if (isConfirm) {
	    	        swal({
	    	          title: 'Yes!',
	    	          text: 'Appproved Successful!!',
	    	          icon: 'success'
	    	        }).then(function() {
	    	        	 $.ajax({
	    	                 type: "PUT",
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
	    	        swal("Cancelled", "Not Approved :)", "error");
	    	      }
	    	    });
		}
	
	