var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

jQuery('#retailVisit').validate({
	rules:{
	employeename:"required",
	},messages:{
		employeename:"Please enter employee name",
	},
	submitHandler:function(form){
		submitRetailVisitDateWiseForm();		
	}	
});

function submitRetailVisitDateWiseForm() {
	 this.event.preventDefault();  
	
	var employeeId=$("#employee_id").val();  
    var employeeName= $("#employee_id option:selected").text();
    var date = $("#select_date").val();
	var selectedDate = date.split("/").reverse().join("-"); 
	
	$('#selected_date').text(selectedDate);
	$('#employee_name').text(employeeName);
	
     var table = $('#empTable').dataTable({
        "deferRender" : true,
         "destroy" : true,
        "sAjaxSource" : baseUrl+'/api/retailVisit-ofAnEmployeeByName'+"/"+employeeId+"/"+selectedDate,
        "sAjaxDataProp" : "",
        dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
        	{
                data : 'retailId'
               },
               {
                   data : 'retailName'
                  },
                {
                 data : 'retailAddress'
                },
                {
                    data : 'systemAddress'
                   },
                 {
                 data : 'lat'
                },
               {
                 data : 'retailLong'
                },
               {
                 data : 'srId'
                },
               {
                 data : 'visitDate'
                } 
            ]
        });
}