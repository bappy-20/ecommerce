let calculatCheckPoints = ()=> {
	let  dualBox = $("[name='checkPointList']").bootstrapDualListbox();
	let items = dualBox.find(":selected");
	if( items.length < 2) return;
	
	let checkPoints = [];
	for(let item of items) {

		let obj = {
		id: item.id,
		name:item.text
		}
		checkPoints.push(obj);
	}	
	
	create2ndFormtab(checkPoints);
};

let create2ndFormtab = (checkPoints)=> {
	let dynamicInputField = '';
	for(let i = 0; i<checkPoints.length-1; i++){
		dynamicInputField = dynamicInputField + '<div class="form-group">'+
 			    '<label for="rent_serial_'+(i+1)+'">'+checkPoints[i].name+' To '+checkPoints[i+1].name+':<span id="rent_required_'+(i+1)+'">*</span></label>'+
  				'<input type="text" class="form-control" name="rent_serial_'+(i+1)+'" id="rent_serial_'+(i+1)+'">'+
				'</div>';
	}
	
	$("#inter-chckpoint-cost").html(dynamicInputField);	
	$("#check-point-item-list").removeClass('show_to_skin')
	$("#check-point-cost-list").addClass('show_to_skin')
}
 
$("#rent_assign_2nd_bnt").on("click", function(e){
calculatCheckPoints();
  nextSection();
});
 
$("form").on("submit", function(e){
  if ($("#next").is(":visible") || $("fieldset.current").index() < 3){
    e.preventDefault();
  }
});
 
function goToSection(i){

if(i==1)
{
$("#rent_assign_1st_bnt").show();
}
else
{
$("#rent_assign_1st_bnt").hide();
}
  $("fieldset:gt("+i+")").removeClass("current").addClass("next");
  $("fieldset:lt("+i+")").removeClass("current");
  $("li").eq(i).addClass("current").siblings().removeClass("current");
  setTimeout(function(){
    $("fieldset").eq(i).removeClass("next").addClass("current active");
      if ($("fieldset.current").index() == 1){
        $("#rent_assign_2nd_bnt").hide();
        $("input[type=submit]").show();
      } else {
        $("#rent_assign_2nd_bnt").show();
        $("input[type=submit]").hide();
      }
  }, 80);
 
}
 
function nextSection(){
  var i = $("fieldset.current").index();
  if (i == 0){
  
  	if(!routeValid) return;
  
	dualBox = $("[name='checkPointList']").bootstrapDualListbox();
	let name = $("input[name='name']").val();
	if(name.trim() == '')
	{
		$("#validation_message").text("Route name is required");
		$("#validation_message").show();
		$("input[name='name']").focus();
		return;
	} 
	items = dualBox.find(":selected");
	if(items.length <2)
	{
		alert
		$("#validation_message").text("select atleast 2 checkpoints");
		$("#validation_message").show();
		return;
	}
  
  $("#validation_message").hide();
   $("#page_1").removeClass("active current");
   $("#page_2").addClass("active current");
    goToSection(i+1);
  }
}
 
$("li").on("click", function(e){
return;
  var i = $(this).index();
  if(i == 1)
  {
  calculatCheckPoints();
   nextSection();
  }
  else {
   goToSection(i);
      $("#page_2").removeClass("active current");
   $("#page_1").addClass("active current");
  }
  
  // 
    
 
});
 
 
 let validateRoute = ()=> {
 	let routeName = $("#route_name").val()
 	let routeType =$("input[name='routeType']:checked").val();
 
 }
 
 
 
