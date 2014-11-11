package pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import jvstm.Transaction;

import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.ist.socialsoftware.blendedworkflow.AbstractServiceTest;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.LoadBWSpecificationService;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.DataModel;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.GoalModel;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.TaskModel;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException;
import pt.ist.socialsoftware.blendedworkflow.shared.StringUtils;

@RunWith(JMock.class)
public class LoadBWSpecificationServiceTest extends AbstractServiceTest {

	@Test
	public void loadBWSpecification() throws Exception {

		final String bwSpecificationString = StringUtils
				.fileToString(BWSPECIFICATION_FILENAME);
		new LoadBWSpecificationService(bwSpecificationString).call();

		boolean committed = false;
		try {
			Transaction.begin();

			assertLoadSpecificationResults();

			Transaction.commit();
			committed = true;
		} catch (final BlendedWorkflowException e) {
			fail(e.getMessage());
		} finally {
			if (!committed)
				Transaction.abort();
		}

	}

	private void assertLoadSpecificationResults()
			throws BlendedWorkflowException {
		final BlendedWorkflow blendedWorkflow = BlendedWorkflow.getInstance();
		final BWSpecification bwSpecification = blendedWorkflow
				.getBWSpecification(BWSPECIFICATION_NAME);
		final DataModel dataModel = bwSpecification.getDataModel();
		final GoalModel goalModel = bwSpecification.getGoalModel();
		final TaskModel taskModel = bwSpecification.getTaskModel();

		assertEquals(6, dataModel.getEntitiesSet().size());
		assertEquals(2, dataModel.getEntity("Patient").getEntityInstancesSet()
				.size());
		assertEquals(17, dataModel.getAttributesSet().size());
		assertEquals(5, dataModel.getRelationsSet().size());
		assertEquals(11, goalModel.getAchieveGoalsSet().size());
		assertEquals(1, goalModel.getMaintainGoalsSet().size());
		assertEquals(6, taskModel.getTasksSet().size());

		assertEquals(3, BlendedWorkflow.getInstance().getOrganizationalModel()
				.getRolesSet().size());
		assertEquals(4, BlendedWorkflow.getInstance().getOrganizationalModel()
				.getUsersSet().size());
	}

}