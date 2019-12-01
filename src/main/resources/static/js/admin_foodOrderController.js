var app = angular.module('myApp', []);


 
 
app.controller('admin_foodOrderCtrl', ['$scope', '$http', function ($scope, $http) {

    // List of user
    
   
    
   
	$scope.foodOrder = {};
	$scope.foodOrders = [];
	$scope.selectedIndex;
	$scope.trackfoodOrder = [];
	
	    
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
    
    $scope.getfoodOrders= function(){
        
        $http.get('/foodOrder')
        .then(function success(e) {
               console.log(e);
               $scope.foodOrders = e.data;
               
               
            }, function error(e) {
            	 console.log(e);
            	 $.notify("Error Fetching foodOrders", "danger");
            });
        
        
    }
    
    $scope.getfoodOrderTrack= function(index){
    	console.log("Index:"+index);
    	console.log($scope.foodOrders[index].track);
        
    	$scope.trackfoodOrder = $scope.foodOrders[index].track;
    	$('#trackerModal').modal('toggle');
        
    }
    
    


    $scope.getfoodOrders();
    let copiedItem = null; 
    $scope.edit = function(index){
    	console.log("Index:"+index);
    	copiedItem = Object.assign({}, copiedItem , $scope.foodOrders[index] );
    	$scope.foodOrder = copiedItem;
    	console.log($scope.foodOrder);
    	$scope.selectedIndex = index;

    	$scope.openForm();
    	
     }
    $scope.selectedIndex = "";
    $scope.approve = function(index){
    	
    	$scope.selectedIndex = index;
       var id = $scope.foodOrders[index].id;
    	
    	
    	
    	$http({
            url: 'admin/foodOrder/approve/'+id,
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
       var id = $scope.foodOrders[index].id;
    	
    	
    	
    	$http({
            url: 'admin/foodOrder/reject/'+id,
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
    
    

 
    
    }]);
 

