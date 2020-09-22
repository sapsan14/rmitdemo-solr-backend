package org.springframework.data.solr.rmitdemo.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.rmitdemo.application.SearchableApplicationDefinition;

@SolrDocument(solrCoreName = "collection1")
public class Application implements SearchableApplicationDefinition {

    private @Id
    @Indexed
    String id;

    private @Indexed(NAME_FIELD_NAME)
    String name;

	private @Indexed(GROUP_FIELD_NAME)
	String group;

	private @Indexed(TYPE_FIELD_NAME)
	String type;

	private @Indexed(DESCRIPTION_FIELD_NAME)
	String description;

//	private @Indexed List<String> services;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.name = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String type) {
		this.name = description;
	}

//	public List<String> getServices() {
//		return services;
//	}
//
//	public void setServices(List<String> services) {
//		this.sercices = services;
//	}

    @Override
    public String toString() {
        return "Application [id=" + id + ", name=" + name + "]";
    }

}
