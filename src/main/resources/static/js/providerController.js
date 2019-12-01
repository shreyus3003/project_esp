var app = angular.module('myApp', []);


 
 
app.controller('providerCtrl', ['$scope', '$http', function ($scope, $http) {

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
        	$('#newReqBtn').html("<i class='fa fa-cross'></i> close");
        	open=true;
    	}else{
    		$('#table').fadeIn();
    		$('#requestform').fadeOut();
        	$('#newReqBtn').html("<i class='fa fa-plus'></i> New Request");
        	open=false;
    	}
    	
    }
    
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
    
    $scope.openedImage ="";
    $scope.openImage= function(index){
    	console.log("Index:"+index);
    	
        
    	$scope.openedImage = $scope.foodOrders[index].customer_image;
    	$('#imageModal').modal('toggle');
        
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
   
    $scope.submitRequest = function(){
    	
        console.log(JSON.stringify($scope.foodOrder));
        var method = "POST";
    	
    	if($scope.foodOrder.id>0){
    		method = "PUT";
    	}
    	
    	$http({
            url: '/foodOrder',
            method: method,
            data: JSON.stringify($scope.foodOrder),
            headers: {
                'Content-Type': 'application/json',
            }
            	 
        }).then(function success(response) {
        	console.log(response);
        	if(response.data.type=="success"){
        		$scope.errors = {};
        	if(method=="POST"){
        		console.log(response.data.id);
        		$scope.foodOrder.id = response.data.id;
        	 $scope.foodOrders.push($scope.foodOrder);   //insert
        	}else{
             $scope.foodOrders[$scope.selectedIndex] = response.data.obj;  //update
        	}
        	
        	$scope.foodOrder = {};
        	
        	  
        	
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
    
    
    

 
    
    }]);
 

