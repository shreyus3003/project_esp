var app = angular.module('myApp', []);


 
 
app.controller('transactionCtrl', ['$scope', '$http', function ($scope, $http) {

    // List of user
    
   
    
   
	$scope.transaction = {};
	$scope.transactions = [];
	$scope.selectedIndex;
	
	
	    
    $scope.errors = {};
    
    
    $scope.gettransactions= function(){
        
        $http.get('/transaction/fetch')
        .then(function success(e) {
               console.log(e);
               $scope.transactions = e.data;
               
               
            }, function error(e) {
            	 console.log(e);
            	 $.notify("Error Fetching transactions", "danger");
            });
        
        
    }
    $scope.gettransactions();
    
 
 
    
    }]);
 

