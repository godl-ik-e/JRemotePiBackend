function  DeviceListCtrl ($scope, $http, $location) {
	$scope.url = "http://localhost:8080/backend/resources/device";
	
	$scope.newDevice = function(){
		$location.path("/newDevice");
	};
	
	$scope.editDevice = function(device){
		$location.path("/device/"+device.id);
	};
	
	
	$scope.switchOn = function(device){
		$http.get($scope.url+"/"+device.id+"/1").
		  success(function(data, status, headers, config) {
		    $scope.result = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.result = "fehler";
		  });
		
	};
	
	$scope.switchOff = function(device){
		$http.get($scope.url+"/"+device.id+"/0").
		  success(function(data, status, headers, config) {
		    $scope.result = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.result = "fehler";
		  });
		
	};
	

		$http.get($scope.url).
	  success(function(data, status, headers, config) {
	    $scope.list = data.device;
	  }).
	  error(function(data, status, headers, config) {
		  $scope.result = "fehler";
	  });
	
}

function  DeviceDetailCtrl ($scope, $routeParams, $http, $location) {
	
	$scope.device = {};
	  $scope.deviceId = $routeParams.deviceId;
		$scope.url = "http://localhost:8080/backend/resources/device";
		
		$http.get($scope.url+"/"+$scope.deviceId).
		  success(function(data, status, headers, config) {
		    $scope.device = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.result = "fehler";
		  });
		
		$scope.save = function(device) {
			var postdata = "deviceName="+device.name+"&systemId="+device.systemId+"&deviceId="+device.deviceId;
			$http.post($scope.url+"/"+$scope.deviceId,postdata).
			  success(function(data, status, headers, config) {
				  $location.path("/device");
			  }).
			  error(function(data, status, headers, config) {
				  $scope.result = "fehler";
			  });
		};
		
		$scope.delete = function(device) {
			$http.delete($scope.url+"/"+$scope.deviceId).
			  success(function(data, status, headers, config) {
				  $location.path("/device");
			  }).
			  error(function(data, status, headers, config) {
				  $scope.result = "fehler";
			  });
		};
}

function  DeviceNewCtrl ($scope, $http, $location) {
	$scope.url = "http://localhost:8080/backend/resources/device";
	$scope.save = function(device) {
		var putdata = "deviceName="+device.name+"&systemId="+device.systemId+"&deviceId="+device.deviceId;
		$http.put($scope.url,putdata).
		  success(function(data, status, headers, config) {
			  $location.path("/device");
		  }).
		  error(function(data, status, headers, config) {
			  $scope.result = "fehler";
		  });
	};
	
}