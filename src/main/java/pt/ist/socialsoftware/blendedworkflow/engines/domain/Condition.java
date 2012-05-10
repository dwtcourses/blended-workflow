package pt.ist.socialsoftware.blendedworkflow.engines.domain;

import java.util.HashMap;
import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.shared.TripleStateBool;

public abstract class Condition extends Condition_Base {

	public enum ConditionType {PRE_CONSTRAINT, CONSTRAINT_VIOLATION};
	public enum ConditionResult {TRUE, FALSE, SKIPPED};
	
	public abstract TripleStateBool evaluate(GoalWorkItem goalWorkItem);
	
	public Condition and(Condition one, Condition other) {
		return new AndCondition(one, other);
	}

	public Condition or(Condition one, Condition other) {
		return new OrCondition(one, other);
	}

	public Condition not(Condition condition) {
		return new NotCondition(condition);
	}
	
	abstract Condition cloneCondition(GoalModelInstance goalModelInstance);
	
	abstract Condition cloneCondition(TaskModelInstance taskModelInstance);

	abstract void assignAttributeInstances(GoalWorkItem goalWorkItem);
	
	abstract void assignAttributeInstances(TaskWorkItem taskWorkItem, String conditionType);

	public abstract Set<Entity> getEntities();

	public abstract Set<Attribute> getAttributes();
	
	public abstract HashMap<Attribute,String> getcompareConditionValues();
	
	/**
	 * Parse an Blended Workflow condition to a Worklet Service one.
	 * @param type the type of the condition (DEFINED, UNDEFINED or SKIPPED)
	 * @return a string with the condition
	 */
//	public abstract String getRdrCondition(String type);
	
	/**
	 * 
	 * @return
	 */
	public abstract String getRdrTrueCondition();
	public abstract String getRdrFalseCondition();
	public abstract String getRdrSkippedCondition();
	
	/**
	 * 
	 * NEW
	 */
	public abstract String getRdrUndefinedConditionNEW();
	public abstract String getRdrSkippedConditionNEW();
	public abstract String getRdrTrueConditionNEW();
	public abstract String getRdrFalseConditionNEW();
}
