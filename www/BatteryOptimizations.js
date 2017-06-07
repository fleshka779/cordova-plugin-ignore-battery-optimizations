var BatteryOptimizations = {
	run: function (successCallback, failureCallback) {
		cordova.exec(successCallback, failureCallback, 'BatteryOptimizations', 'run', [true]);
	},
	check: function (successCallback, failureCallback) {
		cordova.exec(successCallback, failureCallback, 'BatteryOptimizations', 'check', [true]);
	}
};


module.exports = BatteryOptimizations;
