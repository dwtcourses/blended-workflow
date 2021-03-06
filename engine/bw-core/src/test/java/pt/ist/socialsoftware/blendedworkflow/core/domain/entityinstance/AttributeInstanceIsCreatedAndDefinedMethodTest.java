package pt.ist.socialsoftware.blendedworkflow.core.domain.entityinstance;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import pt.ist.socialsoftware.blendedworkflow.core.TeardownRollbackTest;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Attribute;
import pt.ist.socialsoftware.blendedworkflow.core.domain.DataModel;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.core.domain.EntityInstance;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.core.domain.WorkflowInstance;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Attribute.AttributeType;
import pt.ist.socialsoftware.blendedworkflow.core.domain.AttributeInstance;
import pt.ist.socialsoftware.blendedworkflow.core.domain.ProductInstance.ProductInstanceState;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWException;

public class AttributeInstanceIsCreatedAndDefinedMethodTest extends TeardownRollbackTest {
	private static final String ENT_ONE_NAME = "EntOne";
	private static final String ATT_ONE_NAME = "AttOne";
	private static final String ATT_TWO_NAME = "AttTwo";

	Specification spec = null;
	Entity entOne;
	Attribute attOne;
	Attribute attTwo;

	WorkflowInstance workflowInstance = null;

	@Override
	public void populate4Test() throws BWException {
		this.spec = new Specification("SpecId", "My spec");
		DataModel dataModel = this.spec.getDataModel();
		this.workflowInstance = new WorkflowInstance(this.spec, "WorkflowInstanceName");

		this.entOne = new Entity(dataModel, ENT_ONE_NAME, false);

		this.attOne = new Attribute(dataModel, this.entOne, ATT_ONE_NAME, AttributeType.NUMBER, true);
		this.attTwo = new Attribute(dataModel, this.entOne, ATT_TWO_NAME, AttributeType.NUMBER, false);
	}

	@Test
	public void attributeIsCreatedAndDefined() {
		EntityInstance entityInstance = new EntityInstance(this.workflowInstance, this.entOne, ProductInstanceState.DEFINED);
		new AttributeInstance(entityInstance, attOne, "1",  ProductInstanceState.DEFINED);

		assertTrue(entityInstance.attributeInstanceIsCreatedAndDefined(attOne));
	}
	
	@Test
	public void attributeNotCreated() {
		EntityInstance entityInstance = new EntityInstance(this.workflowInstance, this.entOne, ProductInstanceState.DEFINED);

		assertFalse(entityInstance.attributeInstanceIsCreatedAndDefined(attTwo));
	}
	
	@Test
	public void attributeSkipped() {
		EntityInstance entityInstance = new EntityInstance(this.workflowInstance, this.entOne, ProductInstanceState.DEFINED);
		new AttributeInstance(entityInstance, attTwo, "2",  ProductInstanceState.SKIPPED);
		
		assertFalse(entityInstance.attributeInstanceIsCreatedAndDefined(attTwo));
	}
}
