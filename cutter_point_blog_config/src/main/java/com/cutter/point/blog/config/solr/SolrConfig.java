package com.cutter.point.blog.config.solr;

import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.SolrJConverter;
import org.springframework.data.solr.server.support.HttpSolrClientFactoryBean;

/**
 * @author limboy
 * @create 2018-09-29 16:09
 */
@Configuration
public class SolrConfig {
    @Value("${spring.data.solr.host}")
    private String solrHost;

    @Value("${spring.data.solr.core}")
    private String solrCore;

    /**
     * 配置SolrTemplate
     */
//    @Bean
//    public SolrTemplate solrTemplate() {
//        HttpSolrServer solrServer = new HttpSolrServer(solrHost);
//        SolrTemplate template = new SolrTemplate(solrServer);
//        template.setSolrCore(solrCore);
//        template.setSolrConverter(new SolrJConverter());
//        return template;
//    }

    @Bean
    public SolrTemplate solrTemplate() {
        HttpSolrClient httpSolrClient = new HttpSolrClient.Builder(solrHost).build();
        SolrTemplate template = new SolrTemplate(httpSolrClient);
        template.setSolrConverter(new SolrJConverter());
        return template;
    }

}
