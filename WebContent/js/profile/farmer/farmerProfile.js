	
$( document ).ready(function() {
	
	// Get the element with id="defaultOpen" and click on it
	document.getElementById("defaultOpen").click();
	var acceptBidForm = $('#acceptBidForm');
    $( "#acceptBidButton" ).click(function() {
    	acceptBidForm.submit();
    });	
    var submitProductForm = $('#submitProductForm');
    submitProductForm.submit(function (e) 
	{	
		$.ajax(
		{
			type: submitProductForm.attr('method'),
			url: submitProductForm.attr('action'),
			data: submitProductForm.serialize(),
			success: function (data) 
			{
				alert(data.state);
			}
		});		 
		return false;
	});
    $( "#submitProductStock" ).click(function() {
    	jQuery('#undobutton').remove();
    	submitProductForm.submit();
    });	
    $( "#undoProductStock" ).click(function() {
    	input = jQuery('<input type="hidden" id="undobutton" name="undobutton" value="undobutton_clicked">');
    	submitProductForm.append(input);
    	submitProductForm.submit();
    });	
    doPoll();
    var submitCommandForm = $('#submitCommandForm');
    submitCommandForm.submit(function (e) 
	{	
		$.ajax(
		{
			type: submitCommandForm.attr('method'),
			url: submitCommandForm.attr('action'),
			data: submitCommandForm.serialize(),
			success: function (data) 
			{
				if(data == 'no_contracts')
				{
					$('#searchResults').html('');
					alert('No Contracts for this category..!');									
				}
				
				$('#searchResults').html('');
				$.each(data,function(key,value)
                {
					if( $('#searchResults').is(':empty') ) {
						$('#searchResults').append('<div id="result_'+key+'" style="border: 1px solid black;"><form id="dispatchid_'+key+'" name="dispatchid_'+key+'" method="post" action="../../../FarmerProfileLoader">'+'RetailerID : ['+value["retailerUserID"]+']<br>'+'Agreed Final Price : ['+value["agreedBid"]["agreedFinalPrice"]+']<br>'+'<br><input type="button" value="Dispatch" id="submitRetailerBid_'+key+'" class="input_class"/><input type="hidden" name="individualContractDispatch" value="individualContractDispatch"><input type="hidden" name="contractID" value="'+value["contractID"]+'"></form></div>');

					}
					else
					{
                        var div=$('<div id="result_'+key+'" style="border: 1px solid black;"><form id="dispatchid_'+key+'" name="dispatchid_'+key+'" method="post" action="../../../FarmerProfileLoader">'+'RetailerID : ['+value["retailerUserID"]+']<br>'+'Agreed Final Price : ['+value["agreedBid"]["agreedFinalPrice"]+']<br>'+'<br><input type="button" value="Dispatch" id="submitRetailerBid_'+key+'" class="input_class"/><input type="hidden" name="individualContractDispatch" value="individualContractDispatch"><input type="hidden" name="contractID" value="'+value["contractID"]+'"></form></div>');
						var sopra=$('#result_'+(key-1));
						$( sopra ).after( div );
					}
					var individualBidForm = $('#dispatchid_'+key);
					individualBidForm.submit(function (e) 
					{	
						$.ajax(
						{
							type: individualBidForm.attr('method'),
							url: individualBidForm.attr('action'),
							data: individualBidForm.serialize(),
							success: function (data) 
							{								
								alert(data.message);
							}
						});		 
						return false;
					});
				    $( "#submitRetailerBid_"+key ).click(function() {
				    	individualBidForm.submit();
				    });
                });
				if( $('#searchResults').is(':empty') )
				{
					$('#searchResults').html('');
				    alert('No contracts for this category');
				}
			}
		});		 
		return false;
	});
    $( "#showProductContracts" ).click(function() {
    	$('#searchResults').html('');
    	submitCommandForm.submit();
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
    				url: '../../../FarmerProfileLoaderRequest',
    				data: 'l',
    				success: function (data,message) 
    				{
    					//alert("You have logged in successfully!!!");
    					
    						
    						$('#username').val(data.page);
    						$('#ID').val(data.id);
    						$('#address').val(data.address);
    						$('#telephone').val(data.telephone);
    						$('#spam').val(data.spam);
    						
    					
    				}
    			});		
    	}
    else if(eventName == 'View Products')
	{
    	$.post('../../../FarmerProfileLoaderRequest',
    		    {
    		        tabEvent: eventName
    		    },
    		    function(data, status){
    		    	 $("#products_view").html('');
    		       $.each(data,function(key,value)
   		                {
   		        	
   		                    var option = $('<p>'+value+'</p>');
   		                    $("#products_view").append(option);
   		                });
					
    		        
    		    });
	}
    else if(eventName=="Add Products"){
    	
    	$.post('../../../FarmerProfileLoaderRequest',
    		    {
    		        tabEvent: eventName
    		    },
    		    function(data, status){
    		       
    		    	$("#product-dropdown").html('');
    		    	 var optionEmpty = $('<option />').val('-1').text('-- Select --');
    		    	$("#product-dropdown").append(optionEmpty);
    		        $.each(data,function(key,value)
    		                {
    		        	if(value.disabled)
    		        		return true;
    		                    var option = $('<option />').val(value.productID).text(value.name);
    		               $("#product-dropdown").append(option);
    		                });
    		        
    		    });
    }
    else if(eventName=="Contracts"){
    	
    	$.post('../../../FarmerProfileLoaderRequest',
    		    {
    		        tabEvent: eventName
    		    },
    		    function(data, status){
    		       
    		    	$("#contract-dropdown").html('');
    		    	 var optionEmpty = $('<option />').val('-1').text('-- Select --');
    		    	$("#contract-dropdown").append(optionEmpty);
    		        $.each(data,function(key,value)
    		                {
    		        	
    		                    var option = $('<option />').val(value.contractID).text(value.type);
    		               $("#contract-dropdown").append(option);
    		              });
    		        
    		    });
    }
    else if(eventName=="Bids")
	{
    	$.post('../../../BidsController',
    		    {
    		        tabEvent: eventName
    		    },
    		    function(data, status){
    		    	$("#bids-dropdown").html('');
    		        $.each(data,function(key,value)
    		                {
    		                    var option = $('<option />').val(value.bidID).text("[Product : "+value.productStock.product.name+"][Quantity : "+value.productStock.quantitiy+"][Agreed : "+value.agreedFinalPrice+"][Your Price : "+value.productStock.unitPrice+"]");
    		               $("#bids-dropdown").append(option);
    		                });
    		        
    		    });
	}
    else
	{
	
	}
    evt.currentTarget.className += " active";
}

function doPoll(){
    $.post('../../../NotificationRequest', function(data) {
    	if(data.state == "HasMessage")
		{
    		$('#notificationText').text(data.message);
    		//$("#farmerNotification").val(data.message);
    		$('.error').fadeIn(400).delay(8000).fadeOut(400); 
    		//$('.error').fadeIn(400); 
		}   	
        setTimeout(doPoll,5000);
    });
    
}


