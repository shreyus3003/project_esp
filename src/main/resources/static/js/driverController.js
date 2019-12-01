var app = angular.module('myApp', []);


 
 
app.controller('driverCtrl', ['$scope', '$http', function ($scope, $http) {

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
        	$('#newReqBtn').html("<i class='fa fa-times'></i> close");
        	open=true;
    	}else{
    		$('#table').fadeIn();
    		$('#requestform').fadeOut();
        	$('#newReqBtn').html("<i class='fa fa-plus'></i> New Request");
        	open=false;
    	}
    	
    }
    
    $scope.gettickets= function(){
        
        $http.get('/ticket')
        .then(function success(e) {
               console.log(e);
               $scope.tickets = e.data;
                
            }, function error(e) {
            	 console.log(e);
            	 $.notify("Error Fetching tickets", "danger");
            });
        
        
    }
    
    $scope.getticketTrack= function(index){
    	console.log("Index:"+index);
    	console.log($scope.tickets[index].track);
        
    	$scope.trackticket = $scope.tickets[index].track;
    	$('#trackerModal').modal('toggle');
        
    }
    
    


    $scope.gettickets();
    let copiedItem = null; 
    $scope.edit = function(index){
    	console.log("Index:"+index);
    	copiedItem = Object.assign({}, copiedItem , $scope.tickets[index] );
    	
    	copiedItem.arrival_date = formatDate(copiedItem.arrival_date);
    	copiedItem.departure_date = formatDate(copiedItem.departure_date);
    	
    	$scope.ticket = copiedItem;
    	
    	console.log($scope.ticket);
    	
    	$scope.selectedIndex = index;

    	$scope.openForm();
    	
     }
   
    $scope.submitTicket = function(){
    	
        console.log(JSON.stringify($scope.ticket));
        var method = "POST";
    	
    	if($scope.ticket.id>0){
    		console.log("Reached put");
    		method = "PUT";
    	}
    	
    	$http({
            url: '/ticket',
            method: method,
            data: JSON.stringify($scope.ticket),
            headers: {
                'Content-Type': 'application/json',
            }
            	 
        }).then(function success(response) {
        	console.log(response);
        	if(response.data.type=="success"){
        		$scope.errors = {};
        	if(method=="POST"){
        		console.log(response.data.id);
        		$scope.ticket.id = response.data.id;
        	 $scope.tickets.push($scope.ticket);   //insert
        	}else{
             $scope.tickets[$scope.selectedIndex] = $scope.ticket;  //update
        	}
        	
        	$scope.ticket = {};
        	
        	  
        	
        	$scope.openForm();
        	
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
    
    $scope.foodOrders = []; 
    $scope.foodOrderErrorMsg = "";
    $scope.viewfoodOrders = function(index){
    	
    	$scope.selectedIndex = index;
        var id = $scope.tickets[index].id;
    	
    	
    	
    	$http({
            url: '/driver/fetch/foodOrder/ticket/'+id,
            method: "GET"
             
        }).then(function success(response) {
        	console.log(response);
        	
        	$scope.foodOrders = response.data;
        	$('#foodOrdersModal').modal('toggle');
            
        }, 
        function(response) { // optional
                // failed
        	console.log("failed");
        	console.log(response);
        });
        
    }
    
    $scope.selectedIndex = "";
$scope.acceptfoodOrder = function(index){
    	
    	$scope.selectedIndex = index;
       var id = $scope.foodOrders[index].id;
    	
    	
    	
    	$http({
            url: '/driver/foodOrder/accept/'+id,
            method: "GET"
             
        }).then(function success(response) {
        	console.log(response);
        	
        	$('#foodOrdersModal').modal('toggle');
        	$.notify("foodOrder has been accepted. please pickup the foodOrder on time.", "success");
        
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
 

