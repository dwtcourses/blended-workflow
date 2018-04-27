package pt.ist.socialsoftware.blendedworkflow.designer.remote.resourcemodel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RALExprReportedByPositionExprDTO extends RALExpressionDTO {
	private boolean directly;
	private String position;

	public RALExprReportedByPositionExprDTO() {
		
	}

	public RALExprReportedByPositionExprDTO(String position, boolean directly) {
		super();
		this.position = position;
		this.directly = directly;
	}

	public boolean isDirectly() {
		return directly;
	}

	public void setDirectly(boolean directly) {
		this.directly = directly;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}