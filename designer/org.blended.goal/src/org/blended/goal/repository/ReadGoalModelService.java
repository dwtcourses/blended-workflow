package org.blended.goal.repository;

import java.util.Optional;
import java.util.Set;

import org.blended.common.common.AttributeAchieveCondition;
import org.blended.common.common.AttributeInvariantCondition;
import org.blended.common.common.CommonFactory;
import org.blended.common.common.EntityAchieveCondition;
import org.blended.common.common.EntityInvariantCondition;
import org.blended.common.common.Expression;
import org.blended.common.common.Nothing;
import org.blended.common.repository.CommonInterface;
import org.blended.common.repository.resttemplate.dto.DefAttributeConditionDTO;
import org.blended.common.repository.resttemplate.dto.DefEntityConditionDTO;
import org.blended.common.repository.resttemplate.dto.ExpressionDTO;
import org.blended.common.repository.resttemplate.dto.GoalDTO;
import org.blended.common.repository.resttemplate.dto.MulConditionDTO;
import org.blended.common.repository.resttemplate.dto.RuleDTO;
import org.blended.goal.goal.Goal;
import org.blended.goal.goal.GoalFactory;
import org.blended.goal.goal.GoalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadGoalModelService {
	private static Logger logger = LoggerFactory.getLogger(ReadGoalModelService.class);

	private static ReadGoalModelService instance = null;

	public static ReadGoalModelService getInstance() {
		logger.debug("getInstance");
		if (instance == null) {
			instance = new ReadGoalModelService();
		}
		return instance;
	}

	private CommonInterface ci = null;
	private CommonFactory factory = null;

	private ReadGoalModelService() {
		ci = CommonInterface.getInstance();
		factory = CommonFactory.eINSTANCE;
	}

	public void read(String specId, GoalModel model) {
		Set<GoalDTO> goals = ci.getGoalSet(specId);

		for (GoalDTO goalDTO : goals) {
			Goal newGoal = createNewGoal(model, goalDTO.getName());

			Set<ExpressionDTO> paths = ci.getGoalActivationDefPathConditionSet(specId, goalDTO.getName());
			addActivationDefPathConditions(newGoal, paths);

			addSuccessCondition(specId, goalDTO, newGoal);

			Set<MulConditionDTO> mulsDTO = ci.getGoalMulInvSet(specId, goalDTO.getName());
			addEntityInvariantConditions(newGoal, mulsDTO);

			Set<RuleDTO> rulesDTO = ci.getGoalRuleInvSet(specId, goalDTO.getName());
			for (RuleDTO ruleDTO : rulesDTO) {
				AttributeInvariantCondition attributeInvariantCondition = ruleDTO
						.createAttributeInvariantCondition(factory);
				newGoal.getInvariantConditions().add(attributeInvariantCondition);
			}
		}

		for (GoalDTO goalDTO : goals) {
			Goal newGoal = getGoalByName(model, goalDTO.getName()).get();
			GoalDTO parentGoalDTO = ci.getParentGoal(specId, goalDTO.getName());
			model.getGoals().stream().filter(g -> getClass().getName().equals(parentGoalDTO.getName()))
					.forEach(g -> g.getChildrenGoals().add(newGoal));

			Set<GoalDTO> subGoals = ci.getSubGoals(specId, goalDTO.getName());
			subGoals.stream().map(g -> getGoalByName(model, g.getName()).get())
					.forEach(g -> newGoal.getChildrenGoals().add(g));
		}

	}

	private void addSuccessCondition(String specId, GoalDTO goalDTO, Goal goal) {
		Set<DefEntityConditionDTO> defsEnt;
		Set<DefAttributeConditionDTO> defsAtt;
		defsEnt = ci.getGoalSuccessEntitySet(specId, goalDTO.getName());
		addSuccessEntityAchieveConditions(goal, defsEnt);

		defsAtt = ci.getGoalSuccessAttributeSet(specId, goalDTO.getName());
		addSuccessAttributeAchieveConditions(goal, defsAtt);

		if (goal.getSuccessConditions().isEmpty()) {
			Nothing nothing = factory.createNothing();
			nothing.setName("nothing");
			goal.getSuccessConditions().add(nothing);
		}
	}

	private void addEntityInvariantConditions(Goal newGoal, Set<MulConditionDTO> mulsDTO) {
		for (MulConditionDTO mul : mulsDTO) {
			EntityInvariantCondition entityInvariantCondition = mul.createEntityInvariantCondition(factory);
			newGoal.getInvariantConditions().add(entityInvariantCondition);
		}
	}

	private void addActivationDefPathConditions(Goal newGoal, Set<ExpressionDTO> paths) {
		for (ExpressionDTO path : paths) {
			Expression pathDefinition = path.buildExpression();
			newGoal.getActivationConditions().add(pathDefinition);
		}
	}

	private void addSuccessEntityAchieveConditions(Goal newGoal, Set<DefEntityConditionDTO> defsEnt) {
		for (DefEntityConditionDTO defEnt : defsEnt) {
			if (!defEnt.isExists()) {
				EntityAchieveCondition entityAchieveCondition = defEnt.createEntityAchieveCondition(factory);
				newGoal.getSuccessConditions().add(entityAchieveCondition);
			}
		}
	}

	private void addSuccessAttributeAchieveConditions(Goal newGoal, Set<DefAttributeConditionDTO> defsAtt) {
		for (DefAttributeConditionDTO defAtt : defsAtt) {
			AttributeAchieveCondition achieveCondition = defAtt.createAttributeAchieveCondition(factory);
			newGoal.getSuccessConditions().add(achieveCondition);
		}
	}

	private static Optional<Goal> getGoalByName(GoalModel model, String name) {
		Optional<Goal> goal = model.getGoals().stream().filter(e -> e.getName().equals(name)).findFirst();
		return goal;
	}

	private Goal createNewGoal(GoalModel model, String name) {
		GoalFactory factory = GoalFactory.eINSTANCE;
		Goal newGoal = factory.createGoal();
		newGoal.setName(name);
		model.getGoals().add(newGoal);
		return newGoal;
	}

}
