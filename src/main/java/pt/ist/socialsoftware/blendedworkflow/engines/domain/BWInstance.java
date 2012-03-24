package pt.ist.socialsoftware.blendedworkflow.engines.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException.BlendedWorkflowError;

public class BWInstance extends BWInstance_Base {

	public BWInstance(BWSpecification bwSpecification, String name) throws BlendedWorkflowException {
		setBwSpecification(bwSpecification);
		setID(getBwSpecification().getName() + "." + getBwSpecification().getNewBWInstanceId()); //Id: BWSpecificationName.#
		setWorkItemCounter(0);
		setDataModelInstance(bwSpecification.getDataModel().cloneDataModel());
		
		GoalModelInstance goalModelInstance = new GoalModelInstance();
		setGoalModelInstance(goalModelInstance);
		bwSpecification.getGoalModel().cloneGoalModel(goalModelInstance);
		
		TaskModelInstance taskModelInstance = new TaskModelInstance();
		setTaskModelInstance(taskModelInstance);
		bwSpecification.getTaskModel().cloneTaskModel(taskModelInstance);
		
		setName(name);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		setCreationDate(dateFormat.format(Calendar.getInstance().getTime()));
		setLog(new Log());
	}
	
	public Integer getNewWorkItemID() {
		setWorkItemCounter(getWorkItemCounter()+1);
		return getWorkItemCounter();
	}

	public WorkItem getWorkItem(String id) throws BlendedWorkflowException {
		for (WorkItem workItem : getWorkItems())
			if (workItem.getID().equals(id))
				return workItem;
		throw new BlendedWorkflowException(BlendedWorkflowError.NON_EXISTENT_WORKITEM_ID, id);
	}

}