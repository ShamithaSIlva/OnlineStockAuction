
$( document ).ready(function() {
	
	// Get the element with id="defaultOpen" and click on it
	document.getElementById("defaultOpen").click();
	var acceptBidForm = $('#submitReportForm');
    
    acceptBidForm.submit(function (e) 
    		{	
    			$.ajax(
    			{
    				type: acceptBidForm.attr('method'),
    				url: acceptBidForm.attr('action'),
    				data: acceptBidForm.serialize(),
    				success: function (data) 
    				{
    					alert(data.message);
    				}
    			});		 
    			return false;
    		});
    $( "#generate_contract_report" ).click(function() {
    	acceptBidForm.submit();
    });
var chartForm = $('#submitChartForm');
    
chartForm.submit(function (e) 
    		{	
    			$.ajax(
    			{
    				type: chartForm.attr('method'),
    				url: chartForm.attr('action'),
    				data: chartForm.serialize(),
    				success: function (data) 
    				{
    					alert(data.message);
    				}
    			});		 
    			return false;
    		});
    $( "#generate_Chart" ).click(function() {
    	chartForm.submit();
    });
    generate_Chart
});
function openCity(evt, eventName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(eventName).style.display = "block";
    if(eventName == 'Generate Report')
	{
    	loadGenerateReportTab();
	}
    evt.currentTarget.className += " active";



document.getElementById(eventName).style.display = "block";
	if(eventName == 'Product')
	{
		
		$.post('../../../AdminProfileLoaderRequest',
	    {
	        tabEvent: eventName
	    },
	    function(data, status)
	    {
	    	$("#product_table tbody").html('');
	       $.each(data,function(key,value)
	        {
	    	    var buttonText = (value["disabled"])? 'Enable':'Disable';
	        	var newRow = '<tr><td>'+value["productID"]+'</td><td>'+value["name"]+'</td><td><button class="tablinks" id="action_button_'+value["productID"]+'" onclick="disableProduct(\''+value["productID"]+'\')">'+buttonText+'</button></td></tr>';
	            $("#product_table tbody").append(newRow);  
	        });    		        
	    });
	}
	else if(eventName == 'Spams')
	{
		$.post('../../../AdminProfileLoaderRequest',
			    {
			        tabEvent: eventName
			    },
			    function(data, status)
			    {
			    	$("#user_table tbody").html('');
			       $.each(data,function(key,value)
			        {
			    	   var newRow = '<tr><td>'+value["userID"]+'</td><td>'+value["username"]+'</td><td>'+value["profileType"]+'</td><td><button class="tablinks" id="action_button_'+value["productID"]+'" onclick="removeUser(\''+value["productID"]+'\')">Remove</button></td></tr>';
			            $("#user_table tbody").append(newRow);  
			        });    		        
			    });
	}
}

function disableProduct(productID)
{
	$.post('../../../AdminProfileLoaderRequest',
		    {
		        disableProduct: productID
		    },
		    function(data, status)
		    {
		       alert(data.message);	  
		       $("#action_button_"+productID).html(data.changedto);
		    });
}
function loadGenerateReportTab()
{
	
	$.ajax(
			{
				type: 'POST',
				
				url: '../../../AdminProfileLoaderRequest',
				data: 'l',
				success: function (data) 
				{
					alert(data.message);	   
				}
			});	

		}






