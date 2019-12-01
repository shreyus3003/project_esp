var app = angular.module('myApp', []);


app.directive('ngFile', ['$parse', function ($parse) {
	  return {
	   restrict: 'A',
	   link: function(scope, element, attrs) {
	     element.bind('change', function(){

	     $parse(attrs.ngFile).assign(scope,element[0].files)
	     scope.$apply();
	   });
	  }
	 };
	 }]);
 
app.controller('driver_foodOrderCtrl', ['$scope', '$http', function ($scope, $http) {

    // List of user
    
   
    
   
	$scope.foodOrder = {};
	$scope.foodOrders = [];
	$scope.selectedIndex;
	$scope.trackfoodOrder = [];
	
	    
    $scope.errors = {};
    
    var open=false;
    
    $scope.closeuploadStatus = function(){
//        $('#uploadStatus').fadeOut();
//        $scope.errors = [];
        location.reload();
    }
    
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
    
    $scope.collected = function(index){
    	
    	$scope.selectedIndex = index;
       var id = $scope.foodOrders[index].id;
    	
    	
    	
    	$http({
            url: '/driver/foodOrder/collected/'+id,
            method: "GET"
             
        }).then(function success(response) {
        	console.log(response);
        	
        		
        	
            location.reload();
        	
        	
        	
        }, 
        function(response) { // optional
                // failed
        	console.log("failed");
        	console.log(response);
        });
        
    }
    
$scope.transit = function(index){
    	
    	$scope.selectedIndex = index;
       var id = $scope.foodOrders[index].id;
    	
    	
    	
    	$http({
            url: '/driver/foodOrder/transit/'+id,
            method: "GET"
             
        }).then(function success(response) {
        	console.log(response);
        	
        		
        	
            location.reload();
        	
        	
        	
        }, 
        function(response) { // optional
                // failed
        	console.log("failed");
        	console.log(response);
        });
        
    }
    
   $scope.deliverId = ""; 
   
   $scope.getDeliveryImage= function(index){
   	
	$scope.selectedIndex = index;
	$scope.deliverId = $scope.foodOrders[index].id;
   	
   	$('#deliveryModal').modal('toggle');
       
   }
   
 $scope.delivered = function(){
    	
    	
    	 
    	
    	console.log("Delivery foodOrder id :"+$scope.deliverId);
    	
    	var uploadUrl = "/uploadFile";
    	var fd = new FormData();
        angular.forEach($scope.myFile,function(file1){
        fd.append('file',file1);
        fd.append('id',$scope.deliverId);
        });
        
        $scope.uploadFileToClient(fd,uploadUrl,  function (e) {
                if (e.lengthComputable) {
                    var progressBar = (e.loaded / e.total) * 100;
                    $('#uploadStatus').fadeIn();
                    $scope.progress=   Math.round(progressBar) ;

                }
            });
    	
//    	$http({
//            url: '/driver/foodOrder/delivered/'+$scope.deliverId,
//            method: "GET"
//             
//        }).then(function success(response) {
//        	console.log(response);
//        	
//        	
//            location.reload();
//        	
//        	
//        }, 
//        function(response) { // optional
//                // failed
//        	console.log("failed");
//        	console.log(response);
//        });
        
    }
    
    

 $scope.returned = function(index){
 	
 	$scope.selectedIndex = index;
    var id = $scope.foodOrders[index].id;
 	
 	
 	
 	$http({
         url: '/driver/foodOrder/returned/'+id,
         method: "GET"
          
     }).then(function success(response) {
     	console.log(response);
     	location.reload();
     	
     	
     	
     }, 
     function(response) { // optional
             // failed
     	console.log("failed");
     	console.log(response);
     });
     
 }
 
 
 $scope.uploadFileToClient = function (file, uploadUrl, progressCB) {
 	
 	
 
 $http.post(uploadUrl, file, {
     transformRequest: angular.identity,
     headers: { 
     	'Content-Type': undefined 
     	
     	},
     uploadEventHandlers: {
         progress: progressCB
     }
 })
 .then(function success(e){
     console.log("Success from Client");
   console.log(e.data);
   if(e.data.type="success"){
	   console.log(e.data.fileDownloadUri);
       
	   $scope.foodOrders[index].customer_image = e.data.fileDownloadUri;
	   
      
       
       
   }
   if(e.data.type="error"){
       
       console.log(e);
        $scope.errors.push(e.data);
   }
  
 });
 }
 
    
    }]);
 

