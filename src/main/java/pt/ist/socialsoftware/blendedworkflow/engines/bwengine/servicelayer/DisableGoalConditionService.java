package pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import pt.ist.fenixframework.pstm.Transaction;

import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.AchieveGoal;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.GoalWorkItem;

public class DisableGoalConditionService implements Callable<String> {

	private static Logger log = Logger.getLogger("CreateGoalInstanceService");
	
	private BWInstance bwInstance;
	private AchieveGoal goal;

	public DisableGoalConditionService (BWInstance bwInstance, AchieveGoal goal) {
		this.bwInstance = bwInstance;
		this.goal = goal;
	}
	
	@Override
	public String call() throws Exception {
		log.info("Start");
		Transaction.begin();
		generateGoalWorkItems(this.bwInstance, this.goal);
		Transaction.commit();
		log.info("END");
		return "CreateGoalInstanceService:Sucess";
	}
	
	private void generateGoalWorkItems(BWInstance bwInstance, AchieveGoal goal) {
		System.out.println("generateGoalWorkItems" + goal.getName());
		if (goal.getGoalWorkItem() == null) {
			new GoalWorkItem(bwInstance, goal);
			System.out.println("goal" + goal.getName());
		}
		
		for (AchieveGoal subGoal : goal.getSubGoals()) {
			generateGoalWorkItems(bwInstance, subGoal);
		}		
		
		
	}

}
