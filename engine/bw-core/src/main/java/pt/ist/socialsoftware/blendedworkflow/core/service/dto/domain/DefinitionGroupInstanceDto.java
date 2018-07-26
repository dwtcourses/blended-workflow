package pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain;

import java.util.Set;

public class DefinitionGroupInstanceDto {
	private Set<EntityInstanceContextDto> entityInstanceContextSet;
	private Set<ProductInstanceDto> productInstanceSet;
	private Set<InnerRelationInstanceDto> innerRelationInstanceSet;

	public DefinitionGroupInstanceDto() {
	}

	public Set<EntityInstanceContextDto> getEntityInstanceContextSet() {
		return this.entityInstanceContextSet;
	}

	public void setEntityInstanceContextSet(Set<EntityInstanceContextDto> entityInstanceContextSet) {
		this.entityInstanceContextSet = entityInstanceContextSet;
	}

	public Set<ProductInstanceDto> getProductInstanceSet() {
		return this.productInstanceSet;
	}

	public void setProductInstanceSet(Set<ProductInstanceDto> productInstanceSet) {
		this.productInstanceSet = productInstanceSet;
	}

	public Set<InnerRelationInstanceDto> getInnerRelationInstanceSet() {
		return this.innerRelationInstanceSet;
	}

	public void setInnerRelationInstanceSet(Set<InnerRelationInstanceDto> innerRelationInstanceSet) {
		this.innerRelationInstanceSet = innerRelationInstanceSet;
	}

}