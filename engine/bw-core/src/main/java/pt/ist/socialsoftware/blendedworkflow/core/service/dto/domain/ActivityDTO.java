package pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDTO {
	private String specId;
	private String name;
	private String description;

	public ActivityDTO() {
	}

	public ActivityDTO(String specId, String name, String description) {
		this.specId = specId;
		this.name = name;
		this.description = description;
	}

	public ActivityDTO(String specId, String name) {
		this(specId, name, "");
	}

	public String getSpecId() {
		return this.specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}