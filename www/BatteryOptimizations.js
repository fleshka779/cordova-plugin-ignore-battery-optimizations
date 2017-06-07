function BatteryOptimizations () {
};

BatteryOptimizations.prototype.run = function (successCallback, failureCallback) {
	cordova.exec(successCallback, failureCallback, 'BatteryOptimizations', 'run', [true]);
}
BatteryOptimizations.prototype.check = function (successCallback, failureCallback) {
	cordova.exec(successCallback, failureCallback, 'BatteryOptimizations', 'check', [true]);
}

module.exports = BatteryOptimizations;