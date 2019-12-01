var app = angular.module('myApp', []);


 
 
app.controller('adminTicketCtrl', ['$scope', '$http', function ($scope, $http) {

    // List of user
    
   
    
   
	$scope.ticket = {};
	$scope.tickets = [];
	$scope.selectedIndex;
	$scope.trackticket = [];
	
	    
    $scope.errors = {};
    
    var open=false;
    $scope.openForm= function(){
    	
    	if(!open){
    		$('#table').fadeOut();
    		$('#requestform').fadeIn();
        	$('#newReqBtn').fadeIn();
        	open=true;
    	}else{
    		$('#table').fadeIn();
    		$('#requestform').fadeOut();
        	$('#newReqBtn').fadeOut();
        	open=false;
    	}
    	
    }
    $('#newReqBtn').fadeOut();
    
    $scope.getTickets= function(){
        
        $http.get('/ticket')
        .then(function success(e) {
               console.log(e);
               $scope.tickets = e.data;
                
            }, function error(e) {
            	 console.log(e);
            	 $.notify("Error Fetching tickets", "danger");
            });
        
        
    }
    $scope.getTickets();
    
    let copiedItem = null; 
    $scope.edit = function(index){
    	console.log("Index:"+index);
    	copiedItem = Object.assign({}, copiedItem , $scope.tickets[index] );
    	$scope.ticket = copiedItem;
    	console.log($scope.ticket);
    	$scope.selectedIndex = index;

    	$scope.openForm();
    	
     }
    $scope.selectedIndex = "";
    $scope.approve = function(index){
    	
    	$scope.selectedIndex = index;
       var id = $scope.tickets[index].id;
    	
    	
    	
    	$http({
            url: '/admin/ticket/approve/'+id,
            method: "POST"
             
        }).then(function success(response) {
        	console.log(response);
        	if(response.data==id){
        		
        	
            location.reload();
        	
        	}else {
        		
        		$scope.errors = response.data.errors;
        		console.log($scope.errors);
        		console.log(angular.equals($scope.errors, {}));
        	}
        	
        }, 
        function(response) { // optional
                // failed
        	console.log("failed");
        	console.log(response);
        });
        
    }
    
    
 $scope.reject = function(index){
    	
    	$scope.selectedIndex = index;
       var id = $scope.tickets[index].id;
    	
    	
    	
    	$http({
            url: '/admin/ticket/reject/'+id,
            method: "POST"
             
        }).then(function success(response) {
        	console.log(response);
        	if(response.data==id){
        		
        	
            location.reload();
        	
        	}else {
        		
        		$scope.errors = response.data.errors;
        		console.log($scope.errors);
        		console.log(angular.equals($scope.errors, {}));
        	}
        	
        }, 
        function(response) { // optional
                // failed
        	console.log("failed");
        	console.log(response);
        });
        
    }
    
    
    function formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) 
            month = '0' + month;
        if (day.length < 2) 
            day = '0' + day;

        return new Date([day, month, year].join('-'));
    }
 
    
    }]);
 

