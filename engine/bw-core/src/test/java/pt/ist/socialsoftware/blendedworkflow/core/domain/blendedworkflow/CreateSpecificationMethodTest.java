package pt.ist.socialsoftware.blendedworkflow.core.domain.blendedworkflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import pt.ist.socialsoftware.blendedworkflow.core.TeardownRollbackTest;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWException;

public class CreateSpecificationMethodTest extends TeardownRollbackTest {
	private static final String SPEC_ID = "SpecId";
	private static final String DUP_ID = "SpecIdDouble";
	private static final String SPEC_NAME = "Spec Name";
	private static final String DUP_NAME = "Exists Name";
	private static final String EMPTY_NAME = "";

	@Override
	public void populate4Test() throws BWException {
		new Specification(DUP_ID, DUP_NAME);
	}

	@Test
	public void success() throws BWException {
		getBlendedWorkflow().createSpecification(SPEC_ID, SPEC_NAME);

		Specification spec = getBlendedWorkflow().getSpecById(SPEC_ID).orElse(null);
		assertNotNull(spec);
		assertEquals(SPEC_ID, spec.getSpecId());
		assertEquals(SPEC_NAME, spec.getName());
	}

	@Test
	public void duplicateName() throws BWException {
		getBlendedWorkflow().createSpecification(SPEC_ID, DUP_NAME);

		assertEquals(2, getBlendedWorkflow().getSpecByName(DUP_NAME).size());
	}

	@Test
	public void emptyName() throws BWException {
		try {
			getBlendedWorkflow().createSpecification(SPEC_ID, EMPTY_NAME);
			fail("emptyName");
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_NAME, bwe.getError());
		}
	}

	@Test
	public void nullName() throws BWException {
		try {
			getBlendedWorkflow().createSpecification(SPEC_ID, null);
			fail("nullName");
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_NAME, bwe.getError());
		}
	}

	@Test
	public void duplicateSpecId() throws BWException {
		try {
			getBlendedWorkflow().createSpecification(DUP_ID, SPEC_NAME);
			fail("duplicateId");
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_ID, bwe.getError());
		}
	}

	@Test
	public void emptyId() throws BWException {
		try {
			getBlendedWorkflow().createSpecification(EMPTY_NAME, SPEC_NAME);
			fail("emptyId");
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_ID, bwe.getError());
		}
	}

	@Test
	public void nullId() throws BWException {
		try {
			getBlendedWorkflow().createSpecification(null, SPEC_NAME);
			fail("nullId");
		} catch (BWException bwe) {
			assertEquals(BWErrorType.INVALID_SPECIFICATION_ID, bwe.getError());
		}
	}

}
