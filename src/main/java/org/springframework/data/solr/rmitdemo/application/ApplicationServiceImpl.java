package org.springframework.data.solr.rmitdemo.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.rmitdemo.application.model.Application;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
class ApplicationServiceImpl implements ApplicationService {

	private static final Pattern IGNORED_CHARS_PATTERN = Pattern.compile("\\p{Punct}");

	private ApplicationRepository applicationRepository;

	@Override
	public Page<Application> findByName(String searchTerm, Pageable pageable) {
		if (StringUtils.isBlank(searchTerm)) {
			return applicationRepository.findAll(pageable);
		}

		return applicationRepository.findByNameIn(splitSearchTermAndRemoveIgnoredCharacters(searchTerm), pageable);
	}

	@Override
	public Application findById(String id) {
		return applicationRepository.findOne(id);
	}

	@Override
	public FacetPage<Application> autocompleteNameFragment(String fragment, Pageable pageable) {
		if (StringUtils.isBlank(fragment)) {
			return new SolrResultPage<Application>(Collections.<Application> emptyList());
		}
		return applicationRepository.findByNameStartsWith(splitSearchTermAndRemoveIgnoredCharacters(fragment), pageable);
	}

	private Collection<String> splitSearchTermAndRemoveIgnoredCharacters(String searchTerm) {
		String[] searchTerms = StringUtils.split(searchTerm, " ");
		List<String> result = new ArrayList<String>(searchTerms.length);
		for (String term : searchTerms) {
			if (StringUtils.isNotEmpty(term)) {
				result.add(IGNORED_CHARS_PATTERN.matcher(term).replaceAll(" "));
			}
		}
		return result;
	}

	@Autowired
	public void setApplicationRepository(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}

}
