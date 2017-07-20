﻿(function(angular) {

	'use strict';

	angular.module(
			'governProject',
			[ 'ngRoute', 'simplePagination','ngTagsInput',
			// 'ngResource',
			'ngAnimate', 'ngProgress', 'governProject.home',
					'governProject.problem', 'governProject.sentiment',
					'governProject.poll', 'governProject.search',
					'governProject.elasticsearch', 'elasticsearch',
					'ngResource', 'ngAnimate', 'ui.bootstrap',
					'checklist-model', 'governProject.user',
					'governProject.fact', 'governProject.govtschemedata',
					'governProject.gaHeader', 'governProject.response',
					 'governProject.quickproblem',
					'governProject.trackPromise', 'governProject.contest',
					'governProject.createopinion', 'governProject.gpi',
					'governProject.localverdict', 'governProject.verdict',
					'governProject.category', 'governProject.header'

			]).config(Configure);

	Configure.$inject = [ '$routeProvider', '$httpProvider' ];

	function Configure($routeProvider, $httpProvider) {

		$routeProvider.when('/', {
			templateUrl : "client/app/home/home.html",
			controller : 'HomeController'
		}).when('/fact/:componentId', {
			templateUrl : "client/app/fact/fact.html",
			controller : 'FactController'
		}).when('/viewFact', {
			templateUrl : "client/app/fact/viewAllFacts.html",
			controller : 'viewFactController'
		}).when('/acceptedFact/:factId', {
			templateUrl : "client/app/fact/acceptedFact.html",
			controller : 'acceptedFactController'
		}).when('/response/:opinionId', {
			templateUrl : "client/app/response/response.html",
			controller : 'ResponseController'
		}).when('/contest', {
			templateUrl : "client/app/contest/contest.html",
			controller : 'ContestController'
		}).when('/contest/:contestId', {
			templateUrl : 'client/app/contest/contestfind.html',
			controller : 'ContestViewController'
		}).when('/quickproblem', {
			templateUrl : "client/app/quickproblem/quickproblem.html",
			controller : 'QuickProblemController'
		})
		.when('/verdict/:sentimentId', {
			templateUrl : 'client/app/verdict/localVerdict.html',
			controller : 'VerdictController'
		}).when('/globalVerdict', {
			templateUrl : 'client/app/localverdict/globalVerdict.html',
			controller : 'LocalVerdictController'
		}).when('/gaHeader', {
			templateUrl : "client/app/gaHeader/gapage.html",
			controller : 'gaHeaderController'
		}).when('/gaHeaderOld', {
			templateUrl : "client/app/gaHeader/gaHeader.html",
			controller : 'gaHeaderController'
		}).when('/trackPromise', {
			templateUrl : "client/app/trackPromise/trackPromise.html",
			controller : 'trackPromiseController'
		}).when('/problem', {
			templateUrl : "client/app/problem/problem.html",
			controller : 'ProblemController'
		}).when('/solveProblem', {
			templateUrl : "client/app/problem/solveProblem.html",
			controller : 'ProblemController'
		}).when('/problemLanding', {
			templateUrl : "client/app/problem/landingProblem.html",
			controller : 'ProblemLandingController'
		}).when('/problem/:problemId', {
			templateUrl : 'client/app/problem/problemDetail.html',
			controller : 'ProblemViewController'
		}).when('/task', {
			templateUrl : "client/app/problem/task.html",
			controller : 'HeaderController'
		}).when('/poll', {
			templateUrl : 'client/app/poll/poll.html',
			controller : 'PollController'
		}).when('/poll/:pollId', {
			templateUrl : 'client/app/poll/viewpoll1.html',
			controller : 'PollViewController'
		}).when('/login', {
			templateUrl : 'client/app/user/login.html',
			controller : 'userLoginController'
		}).when('/signup', {
			templateUrl : 'client/app/user/registration.html',
			controller : 'userRegistrationController'
		}).when('/user', {
			templateUrl : 'client/app/user/user.html',
			controller : 'HeaderController'
		}).when('/user/:userId', {
			templateUrl : 'client/app/user/profile.html',
			controller : 'UserViewController'
		}).when('/requesteduser/:userId', {
			templateUrl : 'client/app/user/userconnection.html',
			controller : 'UserController'
		}).when('/govtschemedata', {
			templateUrl : 'client/app/govtschemedata/govtschemedata.html',
			controller : 'GovtschemedataController'
		}).when('/govtschemedata/:govtId', {
			templateUrl : 'client/app/govtschemedata/govtschemedataview.html',
			controller : 'GovtschemedataviewController'
		}).when('/createopinion/:sentimentId1', {
			templateUrl : 'client/app/createopinion/createopinion.html',
			controller : 'CreateopinionController'
		}).when('/viewallopinion', {
			templateUrl : 'client/app/createopinion/viewallopinion.html',
			controller : 'ViewallopinionController'
		}).when('/permanentsentiment', {
			templateUrl : 'client/app/sentiment/permanentSentiment.html',
			controller : 'ViewSentimentController'
		}).when('/temporarysentiment', {
			templateUrl : 'client/app/sentiment/temporarySentiment.html',
			controller : 'ViewSentimentController'
		}).when('/sentiment', {
			templateUrl : 'client/app/sentiment/viewsentiment.html',
			controller : 'HeaderController'
		}).when('/sentiment/:sentimentId', {
			templateUrl : 'client/app/sentiment/viewNewsentiment.html',
			controller : 'ViewSentimentController1'
		}).when('/sentiments/:sentimentId', {
			templateUrl : 'client/app/sentiment/viewSentiment1.html',
			controller : 'ViewSentimentController1'
		}).when('/gpi', {
			templateUrl : 'client/app/gpi/gpi.html',
			controller : 'GpiController'
		}).when('/addsentiment', {
			templateUrl : 'client/app/sentiment/sentiment.html',
			controller : 'SentimentController'
		}).when('/aboutUs', {
			templateUrl : 'client/app/home/aboutus.html'
		}).when('/permanentVerdict/:sentimentId', {
			templateUrl : 'client/app/verdict/permanentVerdict.html',
			controller : 'PermanentVerdictController'
		});

	}

})(window.angular);