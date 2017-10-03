package pt.ist.socialsoftware.blendedworkflow.workflowinstance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import pt.ist.socialsoftware.blendedworkflow.TeardownRollbackTest;
import pt.ist.socialsoftware.blendedworkflow.domain.Attribute;
import pt.ist.socialsoftware.blendedworkflow.domain.Attribute.AttributeType;
import pt.ist.socialsoftware.blendedworkflow.domain.DataModel;
import pt.ist.socialsoftware.blendedworkflow.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.domain.Goal;
import pt.ist.socialsoftware.blendedworkflow.domain.GoalModel;
import pt.ist.socialsoftware.blendedworkflow.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.domain.WorkflowInstance;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;

public class GetEnabledGoalSetTest extends TeardownRollbackTest {
	private static final String ENT_ONE_NAME = "EntOne";
	private static final String ATT_ONE_NAME = "AttOne";
	private static final String ATT_TWO_NAME = "AttTwo";
	private static final String DESCRIPTION = "description";
	private static final String GOAL_ONE = "goalOne";
	private static final String NAME = "name";

	Specification spec;
	GoalModel goalModel;
	Entity entOne;
	Attribute attOne;
	Attribute attTwo;
	WorkflowInstance workflowInstance;

	@Override
	public void populate4Test() throws BWException {
		this.spec = new Specification("SpecId", "My spec", "author", DESCRIPTION, "version", "UID");
		DataModel dataModel = this.spec.getDataModel();
		this.goalModel = this.spec.getGoalModel();

		this.entOne = new Entity(dataModel, ENT_ONE_NAME, false);

		this.attOne = new Attribute(dataModel, this.entOne, ATT_ONE_NAME, AttributeType.NUMBER, true, false, false);
		this.attTwo = new Attribute(dataModel, this.entOne, ATT_TWO_NAME, AttributeType.NUMBER, false, false, false);

		this.workflowInstance = new WorkflowInstance(this.spec, NAME);
	}

	@Test
	public void testSingleGoal() {
		Goal goal = new Goal(this.goalModel, GOAL_ONE);
		goal.addSuccessCondition(this.entOne.getDefEntityCondition());
		goal.addSuccessCondition(this.attOne.getDefAttributeCondition());
		goal.addSuccessCondition(this.attTwo.getDefAttributeCondition());

		assertEquals(1, this.workflowInstance.getEnabledGoalSet().size());
		assertTrue(this.workflowInstance.getEnabledGoalSet().contains(goal));
	}

	@Test
	public void testTwoGoals() {
		Goal top = new Goal(this.goalModel, GOAL_ONE);
		top.addSuccessCondition(this.entOne.getDefEntityCondition());
		top.addSuccessCondition(this.attOne.getDefAttributeCondition());

		Goal sub = new Goal(this.goalModel, "goalTwo");
		sub.setParentGoal(top);
		sub.addSuccessCondition(this.attTwo.getDefAttributeCondition());

		Set<Goal> enabledGoals = this.workflowInstance.getEnabledGoalSet();
		assertEquals(2, enabledGoals.size());
		assertTrue(enabledGoals.contains(top));
		assertTrue(enabledGoals.contains(sub));
	}

}
