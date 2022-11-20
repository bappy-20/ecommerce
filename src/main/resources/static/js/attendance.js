var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}
jQuery('#attendanceofEmployeeByName').validate({
	rules:{
	employeeid:"required",
	},messages:{
		employeeid:"Please enter employee name",
	},
	submitHandler:function(form){
		
		submitAttendanceOfAnEmployeeByName();		
	}	
});

function submitAttendanceByDateForm() {
    this.event.preventDefault();
    
    var date = $("#select_date").val();
	var selectDate = date.split("/").reverse().join("-");
	attendanceSummery(selectDate);
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/attendance-daywise'+"/"+selectDate,
        "sAjaxDataProp" : "",
        dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
           {
             data : 'id'
            },
         {
          data : 'employeeId'
         },
        {
            data : 'employeeName'
        },
        
        {
            data : 'inTime'
           },
            {
             data : 'addressIn'
            },
         {
          data : 'outTime'
         },
        {
            data : 'addressOut'
        },
        {
            data : 'status'
           }
        ]
    });
}

function submitAttendanceBetweenTwoDates() {
    this.event.preventDefault();    
    var date1 = $("#startDate").val();
	var startDate = date1.split("/").reverse().join("-");
	var date2 =  $("#endDate").val();
	var endDate = date2.split("/").reverse().join("-");
  
    $('#start_date').text(startDate);
    $('#end_date').text(endDate);  
     
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/attendance-between-twodate1'+"/"+startDate+"/"+endDate,
        "sAjaxDataProp" : "",
        dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
           {
             data : 'id'
            },
         {
          data : 'employeeId',
          "render": function (nTd, sData, oData, iRow, iCol) {
                  //return "<a href='"+baseUrl+"/admin/attendance-ofAnEmployee/"+oData.employeeId+"/"+startDate+"/"+endDate+"'>"+oData.employeeId+"</a>";
                  return "<a href='"+baseUrl+"/admin/attendance-ofAnEmployee/"+oData.employeeId+"/"+startDate+"/"+endDate+"/"+oData.empName+"'>"+oData.employeeId+"</a>";
                
                }
         },
        {
            data : 'empName'
        },
          {
            data : 'empCategory'
        },              
        {
           data : 'present'
           },
            {
             data : 'absent'
            },
         {
          data : 'latIn'
         },
        {
            data : 'earlyOut'
        },
        {
            data : 'inleave'
           },
        {
            data : 'total'
           }
           
        ]
    });
     
}
	
	function submitAttendanceOfAnEmployee(employeeId,startDate,endDate,employeeName) {
	
   	 $('#st_date').text(startDate);
     $('#en_date').text(endDate);
     $('#empl_id').text(employeeId);
	 $('#empl_name').text(employeeName);
	 
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/attendance-ofAnEmployee'+"/"+employeeId+"/"+startDate+"/"+endDate,
        "sAjaxDataProp" : "",
        dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
           {
             data : 'id'
            },
         {
          data : 'logDate'
          },
        {
            data : 'inTime'
        },
          {
            data : 'checkInAddress'
        },              
        {
           data : 'outTime'
           },
            {
             data : 'checkOutAddress'
            },
         {
          data : 'status'
         }           
        ]
    });
     
}

function submitAttendanceOfAnEmployeeByName() {
    this.event.preventDefault();   
    
    var employeeId=$("#employee_id").val(); 
    var employeeName=$( "#employee_id option:selected" ).text();
    var date1 = $("#startDate").val();
	var startDate = date1.split("/").reverse().join("-");
	var date2 =  $("#endDate").val();
	var endDate = date2.split("/").reverse().join("-");
	
    $('#st_date').text(startDate);
    $('#en_date').text(endDate);
    $('#empl_id').text(employeeId);
	$('#empl_name').text(employeeName);
     
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/attendance-ofAnEmployeeByName'+"/"+employeeId+"/"+startDate+"/"+endDate,
        "sAjaxDataProp" : "",
        dom: 'Bfrtip',
        buttons: [
             'excel', 'pdf', 'print'
        ],
        "columns" : [
           {
             data : 'id'
            },
         {
          data : 'logDate'
          },
        {
            data : 'inTime'
        },
          {
            data : 'checkInAddress'
        },              
        {
           data : 'outTime'
           },
            {
             data : 'checkOutAddress'
            },
         {
          data : 'status'
         }           
        ]
    });
     
}

function attendanceSummery(todayDate){
		
	let url = baseUrl + "/api/attendance-summery/"+todayDate;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        
	        	setSummeryValues(result);
	            console.log(result);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
}

function setSummeryValues(result){	
	var arr=[];
	arr=result[0];
	$("#total").text(arr[0]);
	$("#present").text(arr[1]);
	$("#leave").text(arr[2]);
	$("#early").text(arr[3]);
	$("#late").text(arr[4]);
    /*var absent=arr[0]-(arr[1]+arr[2]+arr[3]+arr[4]);
	$("#absent").text(absent);*/
}
