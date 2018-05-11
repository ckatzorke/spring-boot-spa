# Spring Boot SPA

A sample project how to use Spring Boot as your primary middleware serve, a.k.a. "backend for frontend".
Spring Boot acts as 
* web server for static resources
  * handling '', '/', '/index'
  * handling all spa routes, return the index.html with Statuscode 200
  * handling of bundled resources (js, css, assets)
* REST endpoint (or graphQL) - like handling '/api'
* gateway to other REST services that should be integrated in a more tightly way, this includes
  * handling sth like '/gw/*
  * handling CORS
  * handling OAuth2 (bearer token)
  * potential model transformation

**NOTE:** it is not intended to replace an API gateway!