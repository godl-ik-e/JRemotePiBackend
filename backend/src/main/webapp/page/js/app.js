angular.module('GoRemotePi', []).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/device', {templateUrl: 'partials/device-list.html',   controller: DeviceListCtrl}).
      when('/device/:deviceId', {templateUrl: 'partials/device-detail.html', controller: DeviceDetailCtrl}).
      otherwise({redirectTo: '/device'});
}]);