var app = angular.module('App', []);

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
 
 
app.controller('fileCtrl', ['$scope', '$http', function ($scope, $http) {

    // List of user
    
    $scope.image={};
    $scope.images=[];
    $scope.rfromserver = [];
    ip = "127.0.0.1:8080";
   
    $scope.CSRF_TOKEN = "";
    
    $scope.uploadFileToClient = function (file, uploadUrl, progressCB) {
    	
    	$scope.CSRF_TOKEN = $("meta[name='_csrf']").attr("content");
    	console.log(angular.toJson($scope.reviewer));
    	console.log("CSRF_Token:"+ $scope.CSRF_TOKEN);
    
    $http.post(uploadUrl, file, {
        transformRequest: angular.identity,
        headers: { 
        	'Content-Type': undefined, 
        	'X-CSRF-TOKEN': $scope.CSRF_TOKEN
        	},
        uploadEventHandlers: {
            progress: progressCB
        }
    })
    .then(function success(e){
        console.log("Success from Client");
      console.log(e.data);
      if(e.data.type="success"){
         
//          prediction = e.data.prediction;
//          duration = e.data.duration;
         
           $scope.image.fileName = e.data.fileName;
           $scope.image.fileDownloadUri = e.data.fileDownloadUri;

             
          $scope.images.push($scope.image);
          
          
      }
      if(e.data.type="error"){
          
          console.log(e);
           $scope.errors.push(e.data);
      }
     
    });
    }
    
   
    
    
    
   $scope.closePB = function(){
       $('#uploadStatus').fadeOut();
       $scope.errors = [];
   }
   
   
    $scope.addImage = function () {
         
         $scope.image.uid=0;
         $scope.image.prediction="";
         $scope.duration = 0;
         
            if($scope.myFile){
            var fd = new FormData();
            angular.forEach($scope.myFile,function(file1){
            fd.append('file',file1);
       });
      
            
     
                           var uploadUrl = "/uploadFile";
                         
                        $scope.uploadFileToClient(fd,uploadUrl,  function (e) {
                                if (e.lengthComputable) {
                                    var progressBar = (e.loaded / e.total) * 100;
                                    $('#uploadStatus').fadeIn();
                                    $scope.progress=   Math.round(progressBar) ;

                                }
                            });
 
            }
        
    };
    
    
    $scope.AddImagetoClient= function(){
        
        $http.post('addimage',{
            image : JSON.stringify($scope.image),
            duration: $scope.duration
        }).then(function success(e) {
                var type = e.data.type;
                if(type=="success"){
                   $scope.image={};
                   $scope.rfromserver[0] = e.data.id;
                   $scope.rfromserver[1] = e.data.machine;
                   $scope.rfromserver[2] = e.data.text;
                   $scope.rfromserver[3] = e.data.prediction;
                   $scope.rfromserver[4] = e.data.duration;
                   $scope.images.push($scope.rfromserver);
                   $scope.rfromserver = [];
                }else{
                    
                }
                
            }, function error(e) {

            });
        
        
    }
    

    $scope.getImages = function () {
        
        $http.get('getimages')
            .then(function success(e) {
                   $scope.images = e.data;
                
            }, function error(e) {

            });
    };
//    $scope.getImages();
    
    
    $scope.deleteImage = function (index) {
        var id = $scope.images[index][0];
        console.log(id);
        var conf = confirm("Do you really want to delete the image?");
        if(conf){
        $http.get('deleteimage?id='+id)
            .then(function success(e) {
                   $scope.images.splice(index,1);
                
            }, function error(e) {

            });
        }
    };
    
    
    
    $scope.paper = {};
    $scope.papers = [];
    
    $scope.submitPaper = function () {
    	
    	$scope.CSRF_TOKEN = $("meta[name='_csrf']").attr("content");
        console.log( $scope.paper);
        
        if($scope.myFile){
        	
        
        
        $http({
            url: '/paper/save',
            method: 'post',
            data: angular.toJson($scope.paper),
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': $scope.CSRF_TOKEN,
            }
            	 
        })
        .then(function(response) {
        	console.log(response);    
        	
        	console.log(response.data.obj.id);
        	
                 var fd = new FormData();
                 angular.forEach($scope.myFile,function(file1){
                 fd.append('file',file1);
                 fd.append('pid',response.data.obj.id);
            });
           
                 
          
                                var uploadUrl = "/uploadFile";
                              
                             $scope.uploadFileToClient(fd,uploadUrl,  function (e) {
                                     if (e.lengthComputable) {
                                         var progressBar = (e.loaded / e.total) * 100;
                                         $('#uploadStatus').fadeIn();
                                         $scope.progress=   Math.round(progressBar) ;

                                     }
                                 });
                                
        	
                
            }, function error(e) {
            	console.log(e);  
            });
        
        }else{
        console.log("File is required");	
        }
        
        
    };
    
    }]);
 

