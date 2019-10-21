'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  
 	BASE_BLOG_API: '"http://192.168.0.104:9527/cutter-point-blog-web"',
 	PICTURE_API: '"http://192.168.0.104:8602"',
	WEB_API: '"http://192.168.0.104:9527/cutter-point-blog-web"',

	// BASE_BLOG_API: '"http://localhost:8080/mogu_blog"',
	// PICTURE_API: '"http://localhost:8080/mogu_picture"',
	// WEB_API: '"http://localhost:8080/mogu_web"',

})
