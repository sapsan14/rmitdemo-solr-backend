package org.springframework.data.solr.rmitdemo.application.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.solr.rmitdemo.application.ApplicationService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
@Scope("prototype")
public class ProductController {

	private ApplicationService applicationService;

	@RequestMapping("/application/{id}")
	public String search(Model model, @PathVariable("id") String id, HttpServletRequest request) {
		model.addAttribute("application", applicationService.findById(id));
		return "application";
	}

	@Autowired
	public void setProductService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

}
