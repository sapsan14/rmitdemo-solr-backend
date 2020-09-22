package org.springframework.data.solr.rmitdemo.application.web;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.rmitdemo.application.ApplicationService;
import org.springframework.data.solr.rmitdemo.application.model.Application;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
@Scope("prototype")
public class SearchController {

	private ApplicationService applicationService;

	@RequestMapping("/search")
	public String search(Model model, @RequestParam(value = "q", required = false) String query, @PageableDefault(
			page = 0, size = ApplicationService.DEFAULT_PAGE_SIZE) Pageable pageable, HttpServletRequest request) {

		model.addAttribute("page", applicationService.findByName(query, pageable));
		model.addAttribute("pageable", pageable);
		model.addAttribute("query", query);
		return "search";
	}

	@ResponseBody
	@RequestMapping(value = "/autocomplete", produces = "application/json")
	public Set<String> autoComplete(Model model, @RequestParam("term") String query,
			@PageableDefault(page = 0, size = 1) Pageable pageable) {
		if (!StringUtils.hasText(query)) {
			return Collections.emptySet();
		}

		FacetPage<Application> result = applicationService.autocompleteNameFragment(query, pageable);

		Set<String> titles = new LinkedHashSet<String>();
		for (Page<FacetFieldEntry> page : result.getFacetResultPages()) {
			for (FacetFieldEntry entry : page) {
				if (entry.getValue().contains(query)) { // we have to do this as we do not use terms vector or a string field
					titles.add(entry.getValue());
				}
			}
		}
		return titles;
	}

	@Autowired
	public void setProductService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

}
