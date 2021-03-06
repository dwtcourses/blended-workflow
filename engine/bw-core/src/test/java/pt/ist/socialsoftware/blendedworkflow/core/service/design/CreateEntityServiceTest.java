package pt.ist.socialsoftware.blendedworkflow.core.service.design;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import pt.ist.socialsoftware.blendedworkflow.core.TeardownRollbackTest;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.EntityDto;
import pt.ist.socialsoftware.blendedworkflow.core.utils.ModulesFactory;

public class CreateEntityServiceTest extends TeardownRollbackTest {
	private static final String SPEC_ID = "Spec ID";
	private static final String ENTITY_NAME = "Entity Name";
	private static final String NON_EXIST = "No Name";
	private static final String DUP_NAME = "Exists Name";
	private static final String EMPTY_NAME = "";

	private final ModulesFactory factory = new ModulesFactory();

	Specification spec;

	@Override
	public void populate4Test() throws BWException {
		this.spec = new Specification(SPEC_ID, "name");
		new Entity(this.spec.getDataModel(), DUP_NAME, false);
	}

	@Test
	public void success() throws BWException {
		this.factory.createDesignInterface().createEntity(new EntityDto(SPEC_ID, ENTITY_NAME, false));

		Specification spec = getBlendedWorkflow().getSpecById(SPEC_ID).get();
		Entity entity = spec.getDataModel().getEntityByName(ENTITY_NAME).get();
		assertNotNull(entity);
		assertEquals(ENTITY_NAME, entity.getName());
		assertFalse(entity.getExists());
	}

	@Test
	public void nonExistSpecId() throws BWException {
		try {
			this.factory.createDesignInterface().createEntity(new EntityDto(NON_EXIST, ENTITY_NAME, false));
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_ID, bwe.getError());
			assertEquals(NON_EXIST, bwe.getMessage());
		}
	}

	@Test
	public void emptySpecId() throws BWException {
		try {
			this.factory.createDesignInterface().createEntity(new EntityDto(EMPTY_NAME, ENTITY_NAME, true));
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_ID, bwe.getError());
			assertEquals(EMPTY_NAME, bwe.getMessage());
		}
	}

	@Test
	public void nullSpecIdId() throws BWException {
		try {
			this.factory.createDesignInterface().createEntity(new EntityDto(null, ENTITY_NAME, false));
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_ID, bwe.getError());
			assertEquals(null, bwe.getMessage());
		}
	}

	@Test
	public void entityExists() throws BWException {
		try {
			this.factory.createDesignInterface().createEntity(new EntityDto(SPEC_ID, DUP_NAME, false));
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_ENTITY_NAME, bwe.getError());
			assertEquals(DUP_NAME, bwe.getMessage());
		}
	}

}
