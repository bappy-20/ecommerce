var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function submitTripForm() {
    this.event.preventDefault();
    var id=$("#carId").val();    
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/trip-history-curdate/'+id,
        "sAjaxDataProp" : "",
        "columns" : [
            {
            	data: 'id',
                "render": function (nTd, sData, oData, iRow, iCol) {
                  return "<a href='"+baseUrl+"/admin/trip-history-details/"+oData.id+"'>"+oData.orgId+oData.carId+oData.id+"</a>";
                }
               },
              
            {
            data : 'startTime'
           }, 
           {
             data : 'endPoint'
            },
         {
          data : 'assignRoute'
         },
        {
            data : 'tripType'
        },
        {
            data : 'status'
           } 
        
       
    
        ]
    });
}

function submitTripHistoryByDateForm() {
    this.event.preventDefault();
    
    var id=$("#carId").val();   
    
    var date1 =  $("#startDate").val();
	var startDate = date1.split("/").reverse().join("-");
	 
	var date2 =  $("#endDate").val();
	var endDate = date2.split("/").reverse().join("-");
    
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/trip-history-curdate/'+id+"/"+startDate+"/"+endDate,
        "sAjaxDataProp" : "",
        "columns" : [
            {
            	data: 'id',
                "render": function (nTd, sData, oData, iRow, iCol) {
                  return "<a href='"+baseUrl+"/admin/trip-history-details/"+oData.id+"'>"+oData.orgId+oData.carId+oData.id+"</a>";
                }
               },
              
            {
            data : 'startTime'
           }, 
           {
             data : 'endPoint'
            },
         {
          data : 'assignRoute'
         },
        {
            data : 'tripType'
        },
        {
            data : 'status'
           } 
        
       
    
        ]
    });
    
   
}

function getTripHistoryById(id){
	let url = baseUrl + "/api/trip-history/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	            setTripHistoryById(result.data);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTripHistoryById(tripHistory){
	
	$("#id").text(tripHistory.id);
	$("#tripType").text(tripHistory.tripType);
	$("#startTime").text(tripHistory.startTime);
	$("#endTime").text(tripHistory.endTime);
	$("#startPoint").text(tripHistory.startPoint);
	$("#endPoint").text(tripHistory.endPoint);
	$("#assignRoute").text(tripHistory.assignRoute);
	$("#status").text(tripHistory.status);	
	getCarInfo(tripHistory.carId);	
	getOrganizations(tripHistory.orgId);
	var triplogs=tripHistory.tripLogs;
		
	var total=0;
	triplogs.forEach(function(item){
		
		  $('tbody').append('<tr><td>'+item.checkPointId+'</td><td>'+item.checkerName+'</td><td>'+item.createdAt
			+'</td><td>'+item.passenger+'</td><td>'+item.rent+'</td><td>'+(item.rent*item.passenger)+'</td><td><input  id="file" hidden="true"><button class="btn btn-info pull-right" onclick="getImageList()">Attachment</button></td></tr>')
			
			$('#file').val(JSON.stringify(item.files));
		  total+=(item.rent*item.passenger);
		});
	 $('tbody').append('<tr><th colspan="5" >Total</th><td><strong>'+total+'</strong></td></tr>')
					
}

function getImageList(){
	var img =$('#file').val();
	var obj = $.parseJSON(img);
	
	$.each(obj, function( key, value ) {
		 $('#imagelist').append('<img class="margin" style="height:200px;width:200px;" src="'+baseUrl+'/api/downloadFile/'+value.fileName+'">');
		});
	
	 $('#modal-default').modal('show');
}

$("#modal-default").on("hidden.bs.modal", function(){
    $("#imagelist").html("");
});


function getCarInfo(id){
	let url = baseUrl + "/api/car/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        	$("#carId").text(result.data.carName);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function getOrganizations(id){
	let url = baseUrl + "/api/organizations/"+id;

	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	        	$("#orgId").text(result.data.orgName);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}
var checkPointName=null;
function getCheckPoint(id){

	let url = baseUrl + "/api/check-point/"+id;
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       checkPointName=JSON.stringify(result.data.name);
	          
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });
	
	return checkPointName;
}


function getTripHistoryByCurdate(){
	let url = baseUrl + "/api/trip-history-today";
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	            setTripHistoryToGraph(result.data);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTripHistoryToGraph(tripHistory){
	let rent=[];
	let passengerCount=[];
	let label=[];
	let total=0;
	let passenger=0;
	let count=1;
	if(tripHistory!=null){
		tripHistory.forEach(function(item){
			let triplogs=item.tripLogs;	
				triplogs.forEach(function(item1){			
				total+=(item1.rent*item1.passenger);
				passenger+=item1.passenger;
				
				});
				label.push('Trip '+count);
				rent.push(total);
				passengerCount.push(passenger);
				count++;
			});	
	}

	createBarChart(label,rent,passengerCount);

}

function createBarChart(label,rent,passengerCount){
	
    var areaChartData = {
    	      labels  :label,
    	      datasets: [
    	        {
    	          label               : 'Passengers',
    	          fillColor           : 'rgba(210, 214, 222, 1)',
    	          strokeColor         : 'rgba(210, 214, 222, 1)',
    	          pointColor          : 'rgba(210, 214, 222, 1)',
    	          pointStrokeColor    : '#c1c7d1',
    	          pointHighlightFill  : '#fff',
    	          pointHighlightStroke: 'rgba(220,220,220,1)',
    	          data                : passengerCount
    	        },
    	        {
    	          label               : 'Rent',
    	          fillColor           : 'rgba(60,141,188,0.9)',
    	          strokeColor         : 'rgba(60,141,188,0.8)',
    	          pointColor          : '#3b8bba',
    	          pointStrokeColor    : 'rgba(60,141,188,1)',
    	          pointHighlightFill  : '#fff',
    	          pointHighlightStroke: 'rgba(60,141,188,1)',
    	          data                : rent
    	        }
    	      ]
    	    }
	 //-------------
    //- BAR CHART -
    //-------------
    var barChartCanvas                   = $('#barChart').get(0).getContext('2d')
    var barChart                         = new Chart(barChartCanvas)
    var barChartData                     = areaChartData
    barChartData.datasets[1].fillColor   = '#00a65a'
    barChartData.datasets[1].strokeColor = '#00a65a'
    barChartData.datasets[1].pointColor  = '#00a65a'
    var barChartOptions                  = {
      //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
      scaleBeginAtZero        : true,
      //Boolean - Whether grid lines are shown across the chart
      scaleShowGridLines      : true,
      //String - Colour of the grid lines
      scaleGridLineColor      : 'rgba(0,0,0,.05)',
      //Number - Width of the grid lines
      scaleGridLineWidth      : 1,
      //Boolean - Whether to show horizontal lines (except X axis)
      scaleShowHorizontalLines: true,
      //Boolean - Whether to show vertical lines (except Y axis)
      scaleShowVerticalLines  : true,
      //Boolean - If there is a stroke on each bar
      barShowStroke           : true,
      //Number - Pixel width of the bar stroke
      barStrokeWidth          : 2,
      //Number - Spacing between each of the X value sets
      barValueSpacing         : 5,
      //Number - Spacing between data sets within X values
      barDatasetSpacing       : 1,
      //String - A legend template
      legendTemplate          : '<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].fillColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>',
      //Boolean - whether to make the chart responsive
      responsive              : true,
      maintainAspectRatio     : true
    }

    barChartOptions.datasetFill = false
    barChart.Bar(barChartData, barChartOptions);
}

function getTripHistoryBydate(){
	let url = baseUrl + "/api/trip-history-date-wise";
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	        	setTripHistoryToLineGraph(result.data);
	        	
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setTripHistoryToLineGraph(tripHistory){
	let rent=[];
	let passengerCount=[];
	let lineLabel=[];
	let total=0;
	let passenger=0;
	
	tripHistory.forEach(function(item){
	var triplogs=item.tripLogs;	
		triplogs.forEach(function(item1){			
		total+=(item1.rent*item1.passenger);
		passenger+=item1.passenger;
		
		});
		
		lineLabel.push(item.tripDate);
		rent.push(total);
		passengerCount.push(passenger);
		
	});	
	
	createLineChart(lineLabel,rent,passengerCount);
}
function createLineChart(lineLabel,rent,passengerCount){
	  
    var areaChartData = {
      labels  : lineLabel,
      datasets: [
        {
          label               : 'Passenger',
          fillColor           : 'rgba(210, 214, 222, 1)',
          strokeColor         : 'rgba(210, 214, 222, 1)',
          pointColor          : 'rgba(210, 214, 222, 1)',
          pointStrokeColor    : '#c1c7d1',
          pointHighlightFill  : '#fff',
          pointHighlightStroke: 'rgba(220,220,220,1)',
          data                : passengerCount
        },
        {
          label               : 'Rent',
          fillColor           : 'rgba(60,141,188,0.9)',
          strokeColor         : 'rgba(60,141,188,0.8)',
          pointColor          : '#3b8bba',
          pointStrokeColor    : 'rgba(60,141,188,1)',
          pointHighlightFill  : '#fff',
          pointHighlightStroke: 'rgba(60,141,188,1)',
          data                : rent
        }
      ]
    }

    var areaChartOptions = {
      //Boolean - If we should show the scale at all
      showScale               : true,
      //Boolean - Whether grid lines are shown across the chart
      scaleShowGridLines      : false,
      //String - Colour of the grid lines
      scaleGridLineColor      : 'rgba(0,0,0,.05)',
      //Number - Width of the grid lines
      scaleGridLineWidth      : 1,
      //Boolean - Whether to show horizontal lines (except X axis)
      scaleShowHorizontalLines: true,
      //Boolean - Whether to show vertical lines (except Y axis)
      scaleShowVerticalLines  : true,
      //Boolean - Whether the line is curved between points
      bezierCurve             : true,
      //Number - Tension of the bezier curve between points
      bezierCurveTension      : 0.3,
      //Boolean - Whether to show a dot for each point
      pointDot                : false,
      //Number - Radius of each point dot in pixels
      pointDotRadius          : 4,
      //Number - Pixel width of point dot stroke
      pointDotStrokeWidth     : 1,
      //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
      pointHitDetectionRadius : 20,
      //Boolean - Whether to show a stroke for datasets
      datasetStroke           : true,
      //Number - Pixel width of dataset stroke
      datasetStrokeWidth      : 2,
      //Boolean - Whether to fill the dataset with a color
      datasetFill             : true,
      //String - A legend template
      legendTemplate          : '<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].lineColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>',
      //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
      maintainAspectRatio     : true,
      //Boolean - whether to make the chart responsive to window resizing
      responsive              : true
    }

    //- LINE CHART -
    //--------------
    var lineChartCanvas          = $('#lineChart').get(0).getContext('2d')
    var lineChart                = new Chart(lineChartCanvas)
    var lineChartOptions         = areaChartOptions
    lineChartOptions.datasetFill = false
    lineChart.Line(areaChartData, lineChartOptions);
	
}
