/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nixmash.blog.solr.repository.custom;

import com.nixmash.blog.solr.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.UncategorizedSolrException;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.util.List;

public interface CustomBaseProductRepository {

	void updateProductCategory(String productId, List<String> categories);

	void updateProductName(Product product);

	Page<Product> findTestCategoryRecords();

	List<Product> searchWithCriteria(String searchTerm);

	List<Product> findProductsBySimpleQuery(String userQuery) throws UncategorizedSolrException;

	HighlightPage<Product> searchProductsWithHighlights(String term);

}
