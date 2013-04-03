function  DeviceListCtrl ($scope, $http) {
	
	this.url = "http://localhost:8080/backend/resources/device/";

		$http.get(this.url).
	  success(function(data, status, headers, config) {
	    $scope.list = data.device;
	  }).
	  error(function(data, status, headers, config) {
		  $scope.result = "fehler";
	  });
	
}

function  DeviceDetailCtrl ($scope, $http) {
	
	return
	
}
