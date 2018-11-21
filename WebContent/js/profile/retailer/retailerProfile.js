
$( document ).ready(function() {
	
	// Get the element with id="defaultOpen" and click on it
	document.getElementById("defaultOpen").click();
	var contractView = $('#contractView');
	contractView.submit(function (e) 
			{	
				$.ajax(
				{
					type: form.attr('method'),
					url: form.attr('action'),
					data: form.serialize(),
					success: function (data) 
					{
						alert('ggg');
					}
				});		 
				return false;
			});
		    $( "#submitViewContract" ).click(function() {
		    	contractView.submit();
		    });	
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
    if(eventName == 'Account Details')
    	{
    	$.ajax(
    			{
    				type: 'POST',
    				url: '../../../RetailerProfileLoaderRequest',
    				data: 'l',
    				success: function (data) 
    				{
    					alert("You have logged in successfully!!!");
    					if(data.page=="sulthan")   {
   						
    						$('#username').val(data.page);
    						$('#ID').val(data.id);
    						$('#address').val(data.address);
    						$('#tel').val(data.tel);
    						$('#spam').val(data.spam);
    					}
    				}
    			});		
    	}
    else if(eventName=="View Product Catalogue")
	{
    	$.post('../../../RetailerProfileLoaderRequest',
    		    {
    		        tabEvent: eventName
    		    },
    		    function(data, status){
    		       // alert("Data: " + data + "\nStatus: " + status);
    		        $.each(data,function(key,value)
    		                {
    		                    var option = $('<option />').val(value.productID).text(value.name);
    		               $("#product-dropdown").append(option);
    		                });
    		        
    		    });
	}
    else if(eventName=="Manage Contracts")
	{
		// try to print a contract
    	$.post('../../../RetailerProfileLoaderRequest',
    		    {
    		        tabEvent: eventName
    		    },
    		    function(data, status){
    		        $.each(data,function(key,value)
    		                {
    		                    var option = $('<option />').val(value.farmerUserID).text(value.farmerUserID);
    		               $("#contract-dropdown").append(option);
    		                });
    		        
    		    });	
	}
    else if(eventName=="Change Password")
	{
	
	}
    else
    	{
    	//z
    	}
    evt.currentTarget.className += " active";
}


