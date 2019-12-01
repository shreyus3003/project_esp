var app = angular.module('myApp', []);


 
 
app.controller('loginCtrl', ['$scope', '$http', function ($scope, $http) {

    // List of user
    
   
    
   
    $scope.user = {};
    $scope.login= function(){
        console.log(JSON.stringify($scope.user));
        $http.post('authenticate',
           JSON.stringify($scope.user)
        ).then(function success(e) {
               console.log(e.data.jwt);
                document.cookie= "foodys-temp="+e.data.jwt;
                sessionStorage.setItem("jwt",e.data.jwt);
                window.location.href="/home";
                
            }, function error(e) {
            	 console.log(e);
            	
            });
        
        
    }
    

 
    
    }]);
 

