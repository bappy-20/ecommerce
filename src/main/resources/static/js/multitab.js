 duallistbox(document.getElementById('test'))

    function getValues() {
     // document.getElementById('values').innerText = [...document.getElementById('test').selectedOptions].map(o => o.textContent).join(', ')
    }
    getValues();
	
	
	var currentTab = 0; // Current tab is set to be the first tab (0)
showTab(currentTab); // Display the current tab

function showTab(n) {
  // This function will display the specified tab of the form...
  var x = document.getElementsByClassName("tab");
  x[n].style.display = "block";
  //... and fix the Previous/Next buttons:
  if (n == 0) {
    document.getElementById("prevBtn").style.display = "none";
  } else {
    document.getElementById("prevBtn").style.display = "inline";
  }
  if (n == 1) {
    document.getElementById("nextBtn").innerHTML = "Submit";
  } else {
    document.getElementById("nextBtn").innerHTML = "Next";
  }
  //... and run a function that will display the correct step indicator:
  fixStepIndicator(n)
}

let viewMessage = (message)=> {
	$("#message_bar").text(message);
	$("#message_bar").show();
}

let submitForm =()=>
{

let routeName = $("#route_name").val();
let intervalName = [...document.getElementById('test').selectedOptions].map(o => o.textContent);
let intervalCost = [];
let routeCostInput = $("input[id^='routecost_']");
for( let i=0;i<routeCostInput.length; i++)
{
	if(!Boolean(routeCostInput[i].value))
	{
	  $('#message-container').text('Route Cost could not be empty');
		$('#message-container').show();
		return;
	} 
	intervalCost.push(routeCostInput[i].value);
}

let postData = {
	route_name: routeName,
	interval_name: intervalName,
	interval_cost: intervalCost,
	type:$('#forward_route').prop('checked')?'Forward':'Reverse'
}
saveRoutePointCost(postData);

}

var data;
function nextPrev(n) {
  // This function will figure out which tab to display
  var x = document.getElementsByClassName("tab");
  // Exit the function if any field in the current tab is invalid:
  if (n == 1 && currentTab == 0)
  {
  if(!Boolean($("#route_name").val())){
		$('#message-container').text('Route Name  Can not be null');
		$('#message-container').show();
		return;
	}
  
  if([...document.getElementById('test').selectedOptions].length<2)
  {
  	let message = "select At Lest 2 check points";
  	$('#message-container').text(message);
		$('#message-container').show();
  	return;
  }
  $('#message-container').text('');
	 data  = [...document.getElementById('test').selectedOptions].map(o => {
		return {check_point_id:o.value,
		check_point_name: o.innerHTML};
		
	});
	
	var htmlText = '';
	for(let i=0;i<data.length-1; i++)
	{
		htmlText= htmlText+ '<div class="form__group"><label for="name" style="display:block" class="form__label"> Route From '+data[i].check_point_name +' To '+ data[i+1].check_point_name+' </label><input required type="text" class="form__input" style="max-width:70%" id="routecost_'+(i+1)+'" required/></div>';
		document.getElementById("cost_containner").innerHTML = htmlText;
	}
	  x[currentTab].style.display = "none";
  }
  if(n== -1 && currentTab == 1){
  	x[currentTab].style.display = "none";
  }
  // Hide the current tab:

  // Increase or decrease the current tab by 1:
  currentTab = currentTab + n;
  // if you have reached the end of the form...
  if (currentTab >= x.length) {
  
  let routeCostInput = $("input[id^='routecost_']");
for( let i=0;i<routeCostInput.length; i++)
{
	if(routeCostInput[i].value == undefined ){
	routeCostInput[i].focus();
		return;
	}
}
  
  
    submitForm();
    return false;
  }
  // Otherwise, display the correct tab:
  showTab(currentTab);
}

function validateForm() {
  // This function deals with validation of the form fields
  var x, y, i, valid = true;
  x = document.getElementsByClassName("tab");
  y = x[currentTab].getElementsByTagName("input");
  // A loop that checks every input field in the current tab:
  for (i = 0; i < y.length; i++) {
    // If a field is empty...
    if (y[i].value == "") {
      // add an "invalid" class to the field:
      y[i].className += " invalid";
      // and set the current valid status to false
      valid = false;
    }
  }
  // If the valid status is true, mark the step as finished and valid:
  if (valid) {
    document.getElementsByClassName("step")[currentTab].className += " finish";
  }
  return valid; // return the valid status
}

function fixStepIndicator(n) {
  // This function removes the "active" class of all steps...
  var i, x = document.getElementsByClassName("step");
  for (i = 0; i < x.length; i++) {
    x[i].className = x[i].className.replace(" active", "");
  }
  //... and adds the "active" class on the current step:
  x[n].className += " active";
}
	
