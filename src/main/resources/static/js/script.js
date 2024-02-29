function toggleState(bookId) {
            var issueButton = document.getElementById('issueButton' + bookId);
         
          
            

            if (issueButton.classList.contains('issued') ) {
                // Change to Returned state
                issueButton.classList.remove('issued');
                issueButton.classList.add('btn-primary');
                issueButton.innerText = 'Issue'
                issueButton.disabled = false;

 			

             
				
               // messageElement.innerText = 'Book ID ' + bookId + ' has been returned.';
            } else {
                // Change to Issued state
            	
            	
                issueButton.classList.add('issued');
                issueButton.classList.remove('btn-primary');
                issueButton.innerText = 'Issued';
        
                issueButton.disabled = true;
               
              
			
                 

                //messageElement.innerText = 'Book ID ' + bookId + ' has been issued.';
            }
 }
  function issueBook(bookId) {
        // Toggle the state if needed
        toggleState(bookId);

        // Build the URL dynamically
        var url = '/library/userLogIn/issue/' + bookId;

        // Make an AJAX request
        $.post(url, function(response) {
            // Handle the response if needed
            console.log(response);
        });
    }
    
    
    
    function returnBook(bookId) {
        // Toggle the state if needed
       // toggleState(bookId);

		
		console.log(bookId);
		console.log("Button is Clicked");
        // Build the URL dynamically
       var url = '/library/bookview/return/' + bookId;

        // Make an AJAX request
        $.post(url, function(response) {
            // Handle the response if needed
            console.log(response);
        });
   }



 /*
 function issueBook(bookId) {
    // Extracting the bookId from the HTML element with id 'issueButton' + bookId
    var bookId = $('#issueButton' + bookId).data('book-id');
    
    // Add AJAX logic to submit the book ID to the /issue endpoint
    $.get("/login/userLogIn/issue", { "bookId": bookId }, function(response) {
        // Check if the response contains a new endpoint
        if (response && response.newEndpoint) {
            // Use the new endpoint for subsequent requests
            $.get(response.newEndpoint, { "bookId": bookId }, function(newResponse) {
                // Handle the new response
                console.log(newResponse);
            });
        } else {
            // Handle the original response
            console.log(response);
        }
    });
}*/
