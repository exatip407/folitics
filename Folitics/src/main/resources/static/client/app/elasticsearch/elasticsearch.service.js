(function(angular) {

	'use strict';

	function ElasticsearchService(esFactory) {

		return esFactory({
			host : 'localhost:9200',
		});
	}

	ElasticsearchService.$inject = [ 'esFactory' ];

	angular.module('governProject.elasticsearch').service('ElasticsearchService',
	ElasticsearchService);

})(window.angular);