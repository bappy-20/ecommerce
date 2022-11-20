var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#AreaAddForm').validate({
	rules:{
	regionname:"required",
	areaname:"required",
	},messages:{
		regionname:"please enter region name",
		areaname:"Please enter your area name",
	},
	submitHandler:function(form){
		submitAreaForm();
	}	
});

jQuery('#areaEditForm').validate({
	rules:{
	areaname:"required",
	},messages:{
		areaname:"Please enter area name",
	},
	submitHandler:function(form){
		submitAreaEditForm();
	}	
});

function submitAreaForm() {
	this.event.preventDefault();
    let area = {};    
    area["areaName"] = $("#area_name").val();
    area["regionId1"] = $("#region_id").val();
    
    submitAreaFormToServer(area);
}


function submitAreaFormToServer(data) {

    let url = baseUrl +'/api/area'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
           swal(`${result.message}`);
           window.location.href =  baseUrl +'/admin/area';
          
        },
        error: function (e) {
        	 swal(`${e}`);
           console.log("ERROR: ", e);
        }
    });
}

function getArea(id){

	let url = baseUrl + "/api/area/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheAreaValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	        	//swal(`${e}`);
	            console.log("ERROR: ", e);
	        }
	    });

}
function setTheAreaValues(result){	
	$("#id").val(result.id);
	$("#area_name").val(result.areaName);
	$("#region_id").val(result.regionId1).change();
	
}

function submitAreaEditForm() {

	this.event.preventDefault();
	let area = {};
    let id = $("#id").val();
    area["id"] = id;    
    area["areaName"] = $("#area_name").val();
    area["regionId1"] = $("#region_id").val();   
    submitAreaEditFormToServer(area, id);   
     }   

	function submitAreaEditFormToServer(data, id) {

    let url = baseUrl + "/api/area/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {
          if(result.statusCode==500) {
        	  swal(`${result.message}`);
          } else {
        	  swal(`${result.message}`);
        	  window.location.href = baseUrl + "/admin/area";        	
          }
        },
        error: function (e) {
        	swal(`${e}`);
            console.log("ERROR: ", e);
        }
    });
}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-area/"+id;
		
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
			

	function createAreaList(){
        
        $.ajax({
        url: baseUrl+'/api/all-area-list',
        type: "GET",
        dataType: "json",
        success:function(data) {
        $.each(data, function(idx, obj){ 
             $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'">'+ obj.areaName + '</label></div>');
        });
            
        }
        });
             
}

	function submitAreaMappingForm(){
		this.event.preventDefault();
		var arealList=[];
//		$('input[name="area"]:checkbox').each(function () {
//		       if(this.checked){
//		    	   arealList.push($(this).val());  
//		       }
//		       
//		  });		
		
		$('#areas_id option:selected').each(function() {
			 arealList.push($(this).val());
		});
			var userId=$("#user_id").val();			
			submitAreaMappingFormToServer(arealList, userId) 
		
	}
	
	function submitAreaMappingFormToServer(data, id) {

	    let url = baseUrl + "/api/area-mapping/"+id;
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: url, 
	        data: JSON.stringify(data),
	        dataType : "JSON",
	        success: function (result) {
	          if(result.statusCode==500){
	        	  ///alert(result.message)
	        	  swal(`${result.message}`);
	          }else{
	        	  //alert("Success!");
	        	  swal(`${result.message}`);
	        	 window.location.href = baseUrl + "/admin/user";
	          }
	          
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	           /// swal(`${e}`);
	        }
	    });
	}	
	
	function createAreaList1(arealist,id){
        $("#user_id").val(id);
        $.ajax({
        url: baseUrl+'/api/all-area-list',
        type: "GET",
        dataType: "json",
        success:function(data) {
        $.each(data, function(idx, obj){ 
        	let flag=0;
       	 $.each(arealist, function( key, value ) {			
			    if( value.id==obj.id){
			    	flag++;
			    }
			 }); 
       	if(flag>0){
	    	 $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" checked>'+ obj.areaName + '</label></div>');
	    }else{
	    	 $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" >'+ obj.areaName + '</label></div>');
	    }
            
        });
            
        }
        });
          
}
