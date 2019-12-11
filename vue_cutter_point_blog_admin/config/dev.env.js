'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  //开发环境
  ADMIN_API: '"http://192.168.0.105:9527/cutter-point-blog-admin"',
  PICTURE_API: '"http://localhost:8602"',
  WEB_API: '"http://localhost:8603"',
  SOLR_API: '"http://localhost:8080/solr"',
  BASE_IMAGE_URL: '"http://localhost:8600"',
  BLOG_WEB_URL: '"http://localhost:9527"',  
})
