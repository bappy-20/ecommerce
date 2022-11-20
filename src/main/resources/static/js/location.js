var baseUrl= "";

function setBaseUrl(url){
	baseUrl = url;
}

function initMap() {
    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 6,
      center: { lat: 24.96969655, lng: 92.21060601 },
    });
    directionsRenderer.setMap(map);
    document.getElementById("submit").addEventListener("click", () => {
    	 this.event.preventDefault();
        geocodeLatLng(directionsService, directionsRenderer);  
     
    });
  }

  function geocodeLatLng( directionsService, directionsRenderer) {            
      
       var employeeId=$("#employee_id").val();   
       var employeeName=$( "#employee_id option:selected" ).text();
       var date1 = $("#startDate").val();
       var startDate = date1.split("/").reverse().join("-");  
       if(startDate!="" && employeeId!=""){
           let url =baseUrl+'/api/location-ofAnEmployeeByName'+"/"+employeeId+"/"+startDate;
           getAllLocationOfanEmployee(employeeId,startDate);
           $.ajax({
                   type: "GET",
                   url: url,
                   dataType : "JSON",
                   success: function (result) {           
                      
                       var mainArr=result.data;
                       var len1=mainArr.length;
                       if(len1<1){
                          // alert("No Location found ! ");
                           swal("No Location found !");
                       }
                     var geocoder = new google.maps.Geocoder;                       
                     var counter=0;
                     calculateAndDisplayRoute(directionsService, directionsRenderer,mainArr);                           
                   },
                   error: function (e) {
                       console.log("ERROR: ", e);
                   }
               });
       }else{
    	   //alert('Please Filled out all input properly');
    	   swal("Please Filled out all input properly");
       }


    }
  
  function calculateAndDisplayRoute(directionsService, directionsRenderer,mainArr) {
    
   const waypts = [];
   const checkboxArray = document.getElementById("waypoints");
   var l=mainArr.length-1;
   for (let i = 0; i < mainArr.length; i++) {             
       waypts.push({
         location:String(mainArr[i]),
         stopover: true
       });
    
   }
   directionsService.route(
     {
         origin:String(mainArr[0]),
         destination: String(mainArr[l]),
         waypoints: waypts,
       optimizeWaypoints: true,
       travelMode: google.maps.TravelMode.DRIVING,
     },
     (response, status) => {
       if (status === "OK") {
         directionsRenderer.setDirections(response);
         const route = response.routes[0];
         const summaryPanel = document.getElementById("directions-panel");
        
       } else {
         window.alert("Directions request failed due to " + status);
       }
     }
   );
 }
 
  function getallEmpLocation(){
	    let url = baseUrl + "/api/emp-location-by-maxtime";
	    $.ajax({
	            type: "GET",
	            url: url,
	            dataType : "JSON",
	            success: function (result) {
	            	console.log(result.data);
	                geocodeLatLng1(result.data);           
	            },
	            error: function (e) {
	                console.log("ERROR: ", e);
	            }
	        });
	}


	function geocodeLatLng1(data) {
	     var geocoder = new google.maps.Geocoder; 
	     var mainArr=[];
	     var subArr=[];
	     var counter=0;
	   var len=data.length;
	     data.forEach(function(item){
	    	
	    	 var subArr=[];
	          
            subArr.push(item.empName+" : "+item.date);
            subArr.push(parseFloat(item.lat));
            subArr.push(parseFloat(item.lng));
            subArr.push(counter);
            mainArr.push(subArr);                   
            counter++;
            if(len==counter){
            	 initializeMap(mainArr);
            }
               
	  });
	    
	}
	function initializeMap(carparksArray) {
		//alert(carparksArray);
	        var citylat =24.49215;
	        var citylng = 91.74741666666665;
	    var map = new google.maps.Map(document.getElementById("map"), {
	        zoom:8,
	        center: new google.maps.LatLng(citylat, citylng),
	        mapTypeId: google.maps.MapTypeId.ROADMAP
	    });
	    var infowindow = new google.maps.InfoWindow();
	    var marker, i;
	    for (i = 0; i < carparksArray.length; i++) {
	    	//alert(carparksArray[i][1]+" ## "+ carparksArray[i][2]);
	        marker = new google.maps.Marker({
	            position: new google.maps.LatLng(carparksArray[i][1], carparksArray[i][2]),
	            map: map
	        });

	        google.maps.event.addListener(marker, 'click', (function (marker, i) {
	            return function () {
	                infowindow.setContent(carparksArray[i][0]);
	                /*infowindow.open(map, marker);*/
	                infowindow.open({
	                    anchor: marker,
	                    map,
	                    shouldFocus: true,
	                  });
	            }
	        })(marker, i));
	       
	    }
	}
	
	function getAllLocationOfanEmployee(employeeId,startDate) {
		 let url =baseUrl+'/api/all-location'+"/"+employeeId+"/"+startDate;
	    var table = $('#empTable').dataTable({
	        "deferRender" : true,
	        "destroy":true,
	        "sAjaxSource" :url,
	        "sAjaxDataProp" : "",
	        dom: 'Bfrtip',
	        buttons: [
	             'excel', 'pdf', 'print'
	        ],
	        "columns" : [
	        	{
	                data : 'empId'
	               }, 
	                 
	                {
	                 data : 'userDataTime'
	                },
	                 {
	                 data : 'latitude'
	                },
	               {
	                 data : 'longitude'
	                },
	                {
	                 data : 'address'
	                },
	                
	            ]
	        });
	     
	}
	
	function deleteEntry(id) {		
		let url = baseurl+"/api/location/"+id;
		
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


