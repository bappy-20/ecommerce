var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitMeasurementUnitForm() {
	this.event.preventDefault();
    let measurementUnit = {};    
    measurementUnit["name"] = $("#name").val();
    //measurementUnit["regionId1"] = $("#region_id").val();
    submitMeasurementUnitFormToServer(measurementUnit);
}


function submitMeasurementUnitFormToServer(data) {

    let url = baseUrl +'/api/save-measurementUnit'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
            swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/measurementUnit';           
            
        },
        error: function (e) {
        	  swal(`${e}`);
            console.log("ERROR: ", e);
        }
    });
}

function getMeasurementUnit(id){

	let url = baseUrl + "/api/get-measurementUnit/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheMeasurementUnitValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTheMeasurementUnitValues(result){	
	$("#id").val(result.id);
	$("#name").val(result.name);
	//$("#region_id").val(result.regionId1).change();
	
}

function submitMeasurementUnitEditForm() {

	this.event.preventDefault();
	let measurementUnit = {};
    let id = $("#id").val();
    measurementUnit["id"] = id;    
    measurementUnit["name"] = $("#name").val();
    //measurementUnit["regionId1"] = $("#region_id").val();   
    submitMeasurementUnitEditFormToServer(measurementUnit, id);
    
     }      
    
	function submitMeasurementUnitEditFormToServer(data, id) {

    let url = baseUrl + "/api/update-measurementUnit/"+id;
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
        	  window.location.href = baseUrl + "/admin/measurementUnit";
          }  
        },
        error: function (e) {
            console.log("ERROR: ", e);
            swal(`${e}`);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-measurementUnit/"+id;
		
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