var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#territoryAddForm').validate({
	rules:{
	territoryname:"required",
	},messages:{
		territoryname:"Please enter territory name",
	},
	submitHandler:function(form){
		submitTerritoryForm();		
	}	
});

jQuery('#terrrirtoryEditForm').validate({
	rules:{
	territoryname:"required",
	},messages:{
		territoryname:"Please enter territory name",
	},
	submitHandler:function(form){
		submitTerritoryEditForm();
	}	
});

function submitTerritoryForm() {
	this.event.preventDefault();
    let territory = {};
    territory["territoryName"] = $("#territory_name").val();
    territory["areaId1"] = $("#area_id").val();
  
    submitTerritoryFormToServer(territory);
}

function submitTerritoryFormToServer(data) {

    let url = baseUrl +'/api/territory'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
  	  		swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/territory';
         
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getTerritory(id){

	let url = baseUrl + "/api/territory/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	            setTheTerritoryValues(result.data);
	            console.log(result);
	          
            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheTerritoryValues(result){	
	$("#id").val(result.id);
	$("#territory_name").val(result.territoryName);
	$("#area_id").val(result.areaId1).change();
	
}

	function submitTerritoryEditForm() {

	this.event.preventDefault();
	let territory = {};
    let id = $("#id").val();
    territory["id"] = id;    
    territory["territoryName"] = $("#territory_name").val();
    territory["areaId1"] = $("#area_id").val();
       
    submitTerritoryEditFormToServer(territory, id);
    
     } 
     
    
	function submitTerritoryEditFormToServer(data, id) {

    let url = baseUrl + "/api/territory/"+id;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: url, 
        data: JSON.stringify(data),
        dataType : "JSON",
        success: function (result) {          
        	 if(result.statusCode==500){
	        	 swal(`${result.message}`);
             }else{
	        	  swal(`${result.message}`);
            	 window.location.href = baseUrl + "/admin/territory";
             }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-territory/"+id;
		
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
        url: baseUrl+'/api/all-territory-list',
        type: "GET",
        dataType: "json",
        success:function(data) {
        $.each(data, function(idx, obj){ 
             $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'">'+ obj.territoryName + '</label></div>');
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
		
		$('#territories_id option:selected').each(function() {
			 arealList.push($(this).val());
		});
			var userId=$("#user_id").val();			
			submitMappingFormToServer(arealList, userId) 
			
		
	}

	function submitMappingFormToServer(data, id) {

	    let url = baseUrl + "/api/territory-mapping/"+id;
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
	
	function createCheckboxList1(territoriesList,id){
        $("#user_id").val(id);
        $.ajax({
        url: baseUrl+'/api/all-territory-list',
        type: "GET",
        dataType: "json",
        success:function(data) {
        $.each(data, function(idx, obj){ 
        	let flag=0;
       	 $.each(territoriesList, function( key, value ) {			
			    if( value.id==obj.id){
			    	flag++;
			    }
			 }); 
       	if(flag>0){
	    	 $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" checked>'+ obj.territoryName + '</label></div>');
	    }else{
	    	 $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" >'+ obj.territoryName + '</label></div>');
	    }
            
        });
            
        }
        });
          
}

