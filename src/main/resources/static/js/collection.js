var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitColectionForm(){
	this.event.preventDefault();
	//alert("hi");
	 var startDate = $("#startDate").val();
	    var endDate = $("#endDate").val();
	    if(startDate!="" && endDate!=""){
	    	var startDate1 = startDate.split("/").reverse().join("-");	
	    	var endDate1 = endDate.split("/").reverse().join("-");   		
	    	submitSalaryFormToServer(startDate1,endDate1);
	    }else{
	    	alert("Input field must not be null");
	    }
	
}

function submitSalaryFormToServer(startDate,endDate) {
	//alert("yes Called")
	  let url = baseUrl + "/api/collection-date-range/"+startDate+"/"+endDate
			    var table = $('#empTable').dataTable({
			        "deferRender" : true,
			        "destroy":true,
			        "sAjaxSource" : url,
			        "sAjaxDataProp" : "",
			        dom: 'Bfrtip',
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
			        "columns" : [              
			            {
			            data : 'orderId'
			           }, 
			           {
			             data : 'recieveAmount'
			            },
			         {
			          data : 'dueAmount'
			         },
			        {
			            data : 'total'
			        },
			        {
			            data : 'collectionDate'
			        }
			        ],
			        drawCallback: function () {
			    	 var totalReceived = $('#empTable').DataTable().column(1).data().sum();
			    	 var totalDue = $('#empTable').DataTable().column(2).data().sum();
				     var totalAmount = $('#empTable').DataTable().column(3).data().sum();
				       $('#totalAmount').text(totalAmount);
				       $('#totalReceived').text(totalReceived);
				       $('#totalDue').text(totalDue);
				      } 
			    });
	  
	 

}