var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#frmRegionAdd').validate({
	rules:{
	regionname:"required",
	},messages:{
		regionname:"Please enter your region name",
	},
	submitHandler:function(form){
		submitRegionForm();		
	}	
});

jQuery('#formRegionEdit').validate({
	rules:{
	regionname:"required",
	},messages:{
		regionname:"Please enter your region name",
	},
	submitHandler:function(form){
		submitRegionEditForm();
	}	
});

function submitRegionForm() {
	this.event.preventDefault();
    let region = {};  
    region["regionName"] = $("#region_name").val();
    
    submitRegionFormToServer(region);
}

function submitRegionFormToServer(data) {

    let url = baseUrl +'/api/get-region'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            console.log(result);
      	  swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/region';            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getRegion(id){

	let url = baseUrl + "/api/region/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheRegionValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}
function setTheRegionValues(result){	
	$("#id").val(result.id);
	$("#region_name").val(result.regionName);
}

function submitRegionEditForm() {

	this.event.preventDefault();
	let region = {};
    let id = $("#id").val();
    region["id"] = id;    
    region["regionName"] = $("#region_name").val();
      
    submitRegionEditFormToServer(region, id);
} 
     
	function submitRegionEditFormToServer(data, id) {

    let url = baseUrl + "/api/region/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {  
    	swal(`${result.message}`);
            window.location.href = baseUrl + "/admin/region";
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-region/"+id;
		
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

	
function createCheckboxList(){      
        $.ajax({
        url: baseUrl+'/api/all-region-list',
        type: "GET",
        dataType: "json",
        success:function(data) {
        $.each(data, function(idx, obj){ 
             $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'">'+ obj.regionName + '</label></div>');
        });
            
        }
        });
             
}

	function submitMappingForm(){
		this.event.preventDefault();
		var arealList=[];
//		$('input[name="area"]:checkbox').each(function () {
//		       if(this.checked){
//		    	   arealList.push($(this).val());  
//		       }
//		       
//		  });	
		$('#regionss_id option:selected').each(function() {
			 arealList.push($(this).val());
		});
			var userId=$("#user_id").val();			
			submitMappingFormToServer(arealList, userId) 
			
		
	}

	function submitMappingFormToServer(data, id) {

	    let url = baseUrl + "/api/region-mapping/"+id;
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: url, 
	        data: JSON.stringify(data),
	        dataType : "JSON",
	        success: function (result) {
	          if(result.statusCode==500){
	        	  swal(`${result.message}`);
	          }else{
	        	  swal(`${result.message}`);
	        	  window.location.href = baseUrl + "/admin/user";
	          }
	          
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	}
	
	function createCheckboxList1(regionsList,id){
        $("#user_id").val(id);
        $.ajax({
        url: baseUrl+'/api/all-region-list',
        type: "GET",
        dataType: "json",
        success:function(data) {
        $.each(data, function(idx, obj){ 
        	let flag=0;
       	 $.each(regionsList, function( key, value ) {			
			    if( value.id==obj.id){
			    	flag++;
			    }
			 }); 
       	if(flag>0){
	    	 $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" checked>'+ obj.regionName + '</label></div>');
	    }else{
	    	 $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" >'+ obj.regionName + '</label></div>');
	    }
            
        });
            
        }
        });
          
}