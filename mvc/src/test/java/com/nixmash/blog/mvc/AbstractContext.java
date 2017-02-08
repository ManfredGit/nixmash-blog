package com.nixmash.blog.mvc;

import com.nixmash.blog.jpa.enums.DataConfigProfile;
import com.nixmash.blog.solr.enums.SolrConfigProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = Application.class)
@Transactional
@ActiveProfiles({ DataConfigProfile.H2, SolrConfigProfile.DEV })
@TestPropertySource("classpath:/test.properties")
public class AbstractContext {

	@Autowired
	protected WebApplicationContext context;

}
