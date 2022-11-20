var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#marketAddForm').validate({
	rules:{
	territoryname:"required",
	marketname:"required",
	},messages:{
		territoryname:"Please enter territory name",
		marketname:"please enter market name"
	},
	submitHandler:function(form){
		submitMarketForm();		
	}	
});

jQuery('#marketEditForm').validate({
	rules:{
	territory_id:"required",
	marketname:"required",
	},messages:{
		territory_id:"Please enter territory name",
		marketname:"please enter market name"
	},
	submitHandler:function(form){
		submitMarketEditForm();
	}	
});

function submitMarketForm() {
	this.event.preventDefault();
    let market = {};
    market["marketName"] = $("#market_name").val();
    market["address"] = $("#address").val();
    market["territoryId"] = $("#territory_id").val();
    
    submitMarketFormToServer(market);
}

function submitMarketFormToServer(data) {

    let url = baseUrl +'/api/market'
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: JSON.stringify(data),
        dataType : "json",
        success: function (result) {
            //console.log(result);
  	  		swal(`${result.message}`);
            window.location.href =  baseUrl +'/admin/market';            
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

function getMarket(id){

	let url = baseUrl + "/api/market/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {	        
	            setTheMarketValues(result.data);
	            console.log(result);
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}


function setTheMarketValues(result){	
	$("#id").val(result.id);
	$("#market_name").val(result.marketName);
	$("#address").val(result.address);
	$("#territory_id").val(result.territoryId).change();
	
}
	function submitMarketEditForm() {

	this.event.preventDefault();
	let market = {};
    let id = $("#id").val();
    market["id"] = id;    
    market["marketName"] = $("#market_name").val();
    market["address"] = $("#address").val();
    market["territoryId"] = $("#territory_id").val();    
    submitMarketEditFormToServer(market, id);
    
     } 
     
    
	function submitMarketEditFormToServer(data, id) {

    let url = baseUrl + "/api/market/"+id;
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
                window.location.href = baseUrl + "/admin/market";
                }
           
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
	function deleteEntry(id) {		
		let url = baseurl+"/api/delete-market/"+id;
		
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
        url: baseUrl+'/api/all-market-list',
        type: "GET",
        dataType: "json",
        success:function(data) {
        $.each(data, function(idx, obj){ 
             $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'">'+ obj.marketName + '</label></div>');
        });
            
        }
        });
             
}

	function submitMappingForm(){
		this.event.preventDefault();
		var arealList=[];

		$('#marketList option:selected').each(function() {
			 arealList.push($(this).val());
		});
		
			var userId=$("#user_id").val();	
			submitMappingFormToServer(arealList, userId) 		
	}

	function submitMappingFormToServer(data, id) {

	    let url = baseUrl + "/api/market-mapping/"+id;
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
        url: baseUrl+'/api/all-market-list',
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
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" checked>'+ obj.marketName + '</label></div>');
	    }else{
	    	 $('#chk')
             .append('<div class="checkbox"><label><input name="area" type="checkbox" value="'+obj.id +'" >'+ obj.marketName + '</label></div>');
	    }
            
        });
            
        }
        });
          
}
