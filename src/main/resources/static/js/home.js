var baseUrl= "";
function setBaseUrl(url){
	baseUrl = url;
}

function getTopSellingProduct(){
	let url = baseUrl + "/api/get-top-selling-product";
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	            setProductToGraph(result);
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setProductToGraph(data){
	let value=[];
	let productCount=[];
	let label=[];
	let total=0;
	let passenger=0;
	let count=1;
	if(data!=null){
		data.forEach(function(item){
				label.push(item[0]);
				productCount.push(item[1]);
				value.push(item[3]);
				
			});	
	}

	createBarChart(label,productCount,value);

}

function createBarChart(label,rent,passengerCount){
	
    var areaChartData = {
    	      labels  :label,
    	      datasets: [
    	        {
    	          label               : 'Sale Value',
    	          fillColor           : 'rgba(210, 214, 222, 1)',
    	          strokeColor         : 'rgba(210, 214, 222, 1)',
    	          pointColor          : 'rgba(210, 214, 222, 1)',
    	          pointStrokeColor    : '#c1c7d1',
    	          pointHighlightFill  : '#fff',
    	          pointHighlightStroke: 'rgba(220,220,220,1)',
    	          data                : passengerCount
    	        },
    	        {
    	          label               : 'Quantity',
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
      barValueSpacing         : 10,
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

function getSaleBydate(){
	let url = baseUrl + "/api/get-sale-by-date";
	$.ajax({
	        type: "GET",
	        url: url,
	        dataType : "JSON",
	        success: function (result) {
	       
	        	setSaleToLineGraph(result);
	        	
	            
	        },
	        error: function (e) {
	            console.log("ERROR: ", e);
	        }
	    });

}

function setSaleToLineGraph(data){
	let value=[];
	let lineLabel=[];
	let total=0;
	let passenger=0;
	
	data.forEach(function(item){		
		lineLabel.push(item[0]);
		value.push(item[1]);
		
	});	
	
	createLineChart(lineLabel,value);
}
function createLineChart(lineLabel,value){
	  
    var areaChartData = {
      labels  : lineLabel,
      datasets: [
       
        {
          label               : 'Sale value',
          fillColor           : 'rgba(60,141,188,0.9)',
          strokeColor         : 'rgba(60,141,188,0.8)',
          pointColor          : '#3b8bba',
          pointStrokeColor    : 'rgba(60,141,188,1)',
          pointHighlightFill  : '#fff',
          pointHighlightStroke: 'rgba(60,141,188,1)',
          data                : value
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
      pointDotRadius          : 10,
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

function getTodayAttendance(){
    var table = $('#empTable').dataTable({
        "deferRender" : true,
        "destroy":true,
        "sAjaxSource" : baseUrl+'/api/attendance-today',
        "sAjaxDataProp" : "",
        "columns" : [
           
         {
          data : 'employeeId'
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