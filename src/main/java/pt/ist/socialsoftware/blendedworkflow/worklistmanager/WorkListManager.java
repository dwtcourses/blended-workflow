package pt.ist.socialsoftware.blendedworkflow.worklistmanager;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.vaadin.ui.Window.Notification;

import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CheckInWorkItemService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CreateGoalInstanceService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CreateNewGoalService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.ManageGoalConditionService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.EnableGoalWorkItemsService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.RedoGoalWorkItemService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.SkipWorkItemService;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.AchieveGoal;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.GoalWorkItem;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.MaintainGoal.GoalState;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.WorkItem;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.WorkItem.WorkItemState;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException.BlendedWorkflowError;
import pt.ist.socialsoftware.blendedworkflow.presentation.BWPresentation;
import pt.ist.socialsoftware.blendedworkflow.shared.BWExecutorService;

public class WorkListManager {

	private Logger log = Logger.getLogger("WorklistManager");
	protected BWPresentation bwPresentation = null;

	public BWPresentation getBwPresentation() {
		return bwPresentation;
	}

	public void setBwPresentation(BWPresentation bwPresentation) {
		this.bwPresentation = bwPresentation;
	}

	/**
	 * Notify the BWPresentation of enabled WorkItems.
	 * @param workItem The enabled WorkItem (i.e. State = ENABLED or PRE_TASK).
	 */
	public void notifyEnabledWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now enabled. with OID" + workItem.getOID());
		String bwInstanceName = workItem.getBwInstance().getName() + ".";
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().addGoalWorkItem(workItem.getOID(), bwInstanceName + workItem.getID());
		}
		else {
			getBwPresentation().addTaskWorkItem(workItem.getOID(), bwInstanceName + workItem.getID());
		}
	}

	/**
	 * Notify the BWPresentation of skipped WorkItems.
	 * @param workItem The skipped WorkItem (i.e. State = SKIPPED).
	 */
	public void notifySkippedWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now skipped.");
		String bwInstanceName = workItem.getBwInstance().getName() + ".";
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().removeGoalWorkItem(workItem.getOID(), bwInstanceName + workItem.getID(), workItem.getState().toString());
		}
		else {
			getBwPresentation().removeTaskWorkItem(workItem.getOID(), bwInstanceName + workItem.getID(), workItem.getState().toString());
		}
	}

	/**
	 * Notify the BWPresentation of pending WorkItems.
	 * @param workItem The pending WorkItem (i.e. State = PENDING).
	 */
	public void notifyPendingWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now pending.");
		String bwInstanceName = workItem.getBwInstance().getName() + ".";
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().removeGoalWorkItem(workItem.getOID(), bwInstanceName + workItem.getID(), workItem.getState().toString());
		}
		else {
			getBwPresentation().removeTaskWorkItem(workItem.getOID(), bwInstanceName + workItem.getID(), workItem.getState().toString());
		}
	}

	/**
	 * Notify the BWPresentation of completed WorkItems.
	 * @param workItem The pending WorkItem (i.e. State = COMPLETED).
	 */
	public void notifyCompletedWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now completed.");
		String bwInstanceName = workItem.getBwInstance().getName() + ".";
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().removeGoalWorkItem(workItem.getOID(), bwInstanceName + workItem.getID(), workItem.getState().toString());
		}
		else {
			getBwPresentation().removeTaskWorkItem(workItem.getOID(), bwInstanceName + workItem.getID(), workItem.getState().toString());
		}
	}
	

	public void notifyWorkItemState(WorkItem workItem) {
		String message = "WorkItem: " + workItem.getID() + " activate condition failed.";
		getBwPresentation().getMainWindow().showNotification(message, Notification.TYPE_ERROR_MESSAGE);
	}
	
	public void notifyNewGoalCreated(AchieveGoal goal) {
		getBwPresentation().getMainWindow().showNotification("New Goal " + goal.getName() + " created successfully", Notification.TYPE_TRAY_NOTIFICATION);
	}
	
	public void notifyException(BlendedWorkflowError bwe) {
		getBwPresentation().getMainWindow().showNotification(bwe.toString(), Notification.TYPE_ERROR_MESSAGE);
	}

	public void notifyReEnabledWorkItem(GoalWorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now re-enabled. with OID" + workItem.getOID());
		String bwInstanceName = workItem.getBwInstance().getName() + ".";
		getBwPresentation().addGoalWorkItem(workItem.getOID(), bwInstanceName + workItem.getID() + "(ReEnabled)");
	}

	/**
	 * Update the BWPresentation with all the active WorkItems.
	 */
	public void updateBWPresentation() {
		for (BWSpecification bwSpecification : BlendedWorkflow.getInstance().getBwSpecifications()) {
			for (BWInstance bwInstance : bwSpecification.getBwInstances()) {
				for (WorkItem workItem : bwInstance.getWorkItems()) {
					if (workItem.getState().equals(WorkItemState.ENABLED) || workItem.getState().equals(WorkItemState.PRE_TASK) || workItem.getState().equals(WorkItemState.PRE_GOAL))
						notifyEnabledWorkItem(workItem);
				}
			}
		}
	}
	
	/********************
	 * Services.
	 ********************/
	public void checkInWorkItem(long workItemOID){
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		CheckInWorkItemService service = new CheckInWorkItemService(workItemOID);
		bwExecutorService.runTask(service);
	}
	
	public void skipWorkItem(long workItemOID){
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		SkipWorkItemService service = new SkipWorkItemService(workItemOID);
		bwExecutorService.runTask(service);
	}
	
	public void createGoal(long bwInstanceOID, String goalName, String goalDescription, long parentGoalID, String goalCondition, ArrayList<String> activateConditions, long entityOID, String userID){
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		CreateNewGoalService service = new CreateNewGoalService(bwInstanceOID, goalName, goalDescription, parentGoalID, goalCondition, activateConditions, entityOID, userID);
		bwExecutorService.runTask(service);
	}
	
	

	public void createGoalInstance(long bwInstanceOID, long parentGoalID, Long context) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		CreateGoalInstanceService service = new CreateGoalInstanceService(bwInstanceOID, parentGoalID, context);
		bwExecutorService.runTask(service);
	}

	public void redoGoal(long workItemOID, String userID) {
		log.info("Redo GoalWorkItem: " + workItemOID);
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		RedoGoalWorkItemService service = new RedoGoalWorkItemService(workItemOID, userID);
		bwExecutorService.runTask(service);
	}
	
	public void manageGoalCondition(long workitemOID, long conditionOID) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		ManageGoalConditionService service = new ManageGoalConditionService(workitemOID, conditionOID);
		bwExecutorService.runTask(service);
	}
	
	public void manageGoalCondition(long maintainGoalOID, GoalState state) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		ManageGoalConditionService service = new ManageGoalConditionService(maintainGoalOID, state);
		bwExecutorService.runTask(service);
	}

	public void enableGoalWorkItemsService(long bwInstanceOID) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		EnableGoalWorkItemsService service = new EnableGoalWorkItemsService(bwInstanceOID);
		bwExecutorService.runTask(service);
	}

}