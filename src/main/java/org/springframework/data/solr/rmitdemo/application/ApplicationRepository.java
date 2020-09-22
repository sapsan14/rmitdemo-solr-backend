package org.springframework.data.solr.rmitdemo.application;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.Query.Operator;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.solr.rmitdemo.application.model.Application;

interface ApplicationRepository extends SolrCrudRepository<Application, String> {

	@Highlight(prefix = "<b>", postfix = "</b>")
	@Query(fields = { SearchableApplicationDefinition.ID_FIELD_NAME, SearchableApplicationDefinition.NAME_FIELD_NAME,
			SearchableApplicationDefinition.GROUP_FIELD_NAME, SearchableApplicationDefinition.TYPE_FIELD_NAME,
			SearchableApplicationDefinition.DESCRIPTION_FIELD_NAME }, defaultOperator = Operator.AND)
	HighlightPage<Application> findByNameIn(Collection<String> names, Pageable page);

	@Facet(fields = { SearchableApplicationDefinition.NAME_FIELD_NAME })
	FacetPage<Application> findByNameStartsWith(Collection<String> nameFragments, Pageable pagebale);

}
