/**
 * Trifecta Dashboard Service
 * @author Lawrence Daniels <lawrence.daniels@gmail.com>
 */
(function () {
    angular.module('trifecta')
        .factory('DashboardSvc', function ($http) {
            var service = {};

            service.executeQuery = function (queryString) {
                return $http.get("/rest/executeQuery/" + encodeURI(queryString))
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.findOne = function (topic, decoderURL, criteria) {
                return $http.get("/rest/findOne/" + topic + "/" + decoderURL + "/" + encodeURI(criteria))
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.getConsumers = function () {
                return $http.get("/rest/getConsumers")
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.getConsumerMapping = function () {
                return $http.get("/rest/getConsumerSet")
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.getDecoders = function () {
                return $http.get("/rest/getDecoders")
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.getMessage = function (topic, partition, offset, decoder) {
                return $http.get("/rest/getMessage/" + topic + "/" + partition + "/" + offset + "/" + decoder)
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.getTopics = function () {
                return $http.get("/rest/getTopicSummaries")
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.getTopicsByName = function (topic) {
                return $http.get("/rest/getTopicsByName/" + topic)
                    .then(function (response) {
                        return response.data;
                    });
            };

            service.getTopicDetails = function (topic) {
                return $http.get("/rest/getTopicDetails/" + topic)
                    .then(function (response) {
                        return response.data;
                    });
            };

            return service;
        });

})();