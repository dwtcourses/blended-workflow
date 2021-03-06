package pt.ist.socialsoftware.blendedworkflow.core.service.execution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Activity;
import pt.ist.socialsoftware.blendedworkflow.core.domain.ActivityWorkItem;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Attribute;
import pt.ist.socialsoftware.blendedworkflow.core.domain.AttributeInstance;
import pt.ist.socialsoftware.blendedworkflow.core.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.core.domain.EntityInstance;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Goal;
import pt.ist.socialsoftware.blendedworkflow.core.domain.GoalWorkItem;
import pt.ist.socialsoftware.blendedworkflow.core.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.core.domain.WorkItem;
import pt.ist.socialsoftware.blendedworkflow.core.domain.WorkflowInstance;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.ActivityWorkItemDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.AttributeInstanceDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.AttributeInstanceToDefineDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.AttributeInstanceWithEntityInstanceIdDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.EntityInstanceDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.EntityInstanceDto.Depth;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.EntityInstanceToDefineDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.GoalWorkItemDto;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.WorkItemDto;

public class ExecutionInterface {
	private static Logger logger = LoggerFactory.getLogger(ExecutionInterface.class);

	private static ExecutionInterface instance;

	public static ExecutionInterface getInstance() {
		if (instance == null) {
			instance = new ExecutionInterface();
		}
		return instance;
	}

	protected ExecutionInterface() {
	}

	private BlendedWorkflow getBlendedWorkflow() {
		return BlendedWorkflow.getInstance();
	}

	private Specification getSpecification(String specId) {
		return getBlendedWorkflow().getSpecById(specId)
				.orElseThrow(() -> new BWException(BWErrorType.UNKNOWN_SPECIFICATION, specId));
	}

	public Set<WorkflowInstance> getWorkflowInstances(String specId) {
		return getSpecification(specId).getWorkflowInstanceSet();
	}

	public WorkflowInstance getWorkflowInstance(String specId, String name) {
		return getSpecification(specId).getWorkflowInstance(name);
	}

	public Activity getActivity(String specId, String activityName) {
		return getSpecification(specId).getActivityModel().getActivity(activityName);
	}

	public Goal getGoal(String specId, String goalName) {
		return getSpecification(specId).getGoalModel().getGoal(goalName);
	}

	public WorkItemDto getInitWorkItem(String specId) {
		Specification specification = getSpecification(specId);

		WorkItemDto workItemDto = new WorkItemDto();

		workItemDto.setSpecId(specification.getSpecId());
		workItemDto.setSpecName(specification.getName());
		workItemDto.setName("Initialize workflow instance");

		List<EntityInstanceToDefineDto> entityInstanceToDefineDtos = specification.getDataModel().getEntitySet()
				.stream().filter(e -> e.getExists()).sorted(Comparator.comparing(Entity::getName))
				.map(e -> new EntityInstanceToDefineDto(e)).collect(Collectors.toList());

		for (EntityInstanceToDefineDto entityInstanceToDefineDto : entityInstanceToDefineDtos) {
			Entity entity = specification.getDataModel()
					.getEntityByName(entityInstanceToDefineDto.getEntity().getName()).get();
			for (Attribute attribute : entity.getAttributeSet()) {
				new AttributeInstanceToDefineDto(entityInstanceToDefineDto, attribute);
			}
			entityInstanceToDefineDto.fillUndefLinks(entity);
		}

		workItemDto.setEntityInstancesToDefine(entityInstanceToDefineDtos);

		return workItemDto;
	}

	@Atomic(mode = TxMode.WRITE)
	public WorkflowInstance createWorkflowInstance(WorkItemDto workItemDto) {
		WorkflowInstance workflowInstance = new WorkflowInstance(getSpecification(workItemDto.getSpecId()),
				workItemDto.getWorkflowInstanceName());

		workItemDto.executeWorkItem(workflowInstance, null);

		return workflowInstance;
	}

	@Atomic(mode = TxMode.WRITE)
	public void deleteWorkflowInstance(String specId, String name) {
		getSpecification(specId).getWorkflowInstance(name).delete();

	}

	public Set<EntityInstance> getEntityInstances(String specId, String name) {
		return getSpecification(specId).getWorkflowInstance(name).getEntityInstanceSet();
	}

	public Set<EntityInstanceDto> getEntityInstancesDto(String specId, String name) {
		return getEntityInstances(specId, name).stream().map(i -> new EntityInstanceDto(i, Depth.DEEP))
				.collect(Collectors.toSet());
	}

	public EntityInstance getMandatoryEntityInstance(String specId, String name) {
		return getSpecification(specId).getWorkflowInstance(name).getMandatoryEntityInstance();
	}

	public List<WorkItemDto> getLogWorkItemDtoList(String specId, String instanceName) {
		return getLogWorkItemList(specId, instanceName).stream().map(WorkItem::getDto).collect(Collectors.toList());
	}

	protected List<WorkItem> getLogWorkItemList(String specId, String instanceName) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		return workflowInstance.getLogWorkItemList();
	}

	public Set<ActivityWorkItemDto> getPendingActivityWorkItemSet(String specId, String instanceName) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		Set<ActivityWorkItemDto> activityWorkItemDTOs = new HashSet<>();

		for (Activity activity : getPendingActivitySet(workflowInstance)) {
			activityWorkItemDTOs.add(ActivityWorkItemDto.createActivityWorkItemDto(workflowInstance, activity));
		}

		return activityWorkItemDTOs;
	}

	// Hook for extension in other modules
	protected Set<Activity> getPendingActivitySet(WorkflowInstance workflowInstance) {
		return workflowInstance.getEnabledActivitySet();
	}

	public List<ActivityWorkItem> getLogActivityWorkItemSet(String specId, String instanceName) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		return workflowInstance.getLogActivityWorkItemList();
	}

	public List<ActivityWorkItemDto> getLogActivityWorkItemDtoSet(String specId, String instanceName) {
		return getLogActivityWorkItemSet(specId, instanceName).stream().map(ActivityWorkItem::getDto)
				.collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.WRITE)
	public ActivityWorkItem executeActivityWorkItem(ActivityWorkItemDto activityWorkItemDTO) {
		WorkflowInstance workflowInstance = getWorkflowInstance(activityWorkItemDTO.getSpecId(),
				activityWorkItemDTO.getWorkflowInstanceName());
		Activity activity = getActivity(activityWorkItemDTO.getSpecId(), activityWorkItemDTO.getName());

		ActivityWorkItem activityWorkItem = activityWorkItemDTO.executeActivity(workflowInstance, activity);

		activityWorkItem.holds(activity.getPreConditionSet(), activity.getPostConditionSet());

		return activityWorkItem;
	}

	public Set<GoalWorkItemDto> getPendingGoalWorkItemSet(String specId, String instanceName) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		Set<GoalWorkItemDto> goalWorkItemDTOs = new HashSet<>();

		for (Goal goal : getPendingGoalSet(workflowInstance)) {
			goalWorkItemDTOs.add(GoalWorkItemDto.createGoalWorkItemDto(workflowInstance, goal));
		}

		return goalWorkItemDTOs;
	}

	// Hook for extension in other modules
	protected Set<Goal> getPendingGoalSet(WorkflowInstance workflowInstance) {
		return workflowInstance.getEnabledGoalSet();
	}

	public List<GoalWorkItem> getLogGoalWorkItemSet(String specId, String instanceName) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		return workflowInstance.getLogGoalWorkItemList();
	}

	public List<GoalWorkItemDto> getLogGoalWorkItemDTOSet(String specId, String instanceName) {
		return getLogGoalWorkItemSet(specId, instanceName).stream().map(GoalWorkItem::getDto)
				.collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.WRITE)
	public GoalWorkItem executeGoalWorkItem(GoalWorkItemDto goalWorkItemDTO) {
		WorkflowInstance workflowInstance = getWorkflowInstance(goalWorkItemDTO.getSpecId(),
				goalWorkItemDTO.getWorkflowInstanceName());
		Goal goal = getGoal(goalWorkItemDTO.getSpecId(), goalWorkItemDTO.getName());

		GoalWorkItem goalWorkItem = goalWorkItemDTO.executeGoal(workflowInstance, goal);

		goalWorkItem.holds(goal.getActivationConditionSet(), goal.getSuccessConditionSet());

		return goalWorkItem;
	}

	public void checkDependentAttributeInstances(String specId, String instanceName, List<List<AttributeInstanceDto>> dependencyTree) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		dependencyTree.stream()
			.forEach(row -> row.stream()
					.forEach(node -> 
						node.getAttributeInstance(workflowInstance, node.getExternalId()).get().checkSkippedAttributeInstance(node)
					)
			);
	}
	
	@Atomic(mode = TxMode.WRITE)
	public void defineDependentAttributeInstances(String specId, String instanceName, List<List<AttributeInstanceDto>> dependencyTree) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		dependencyTree.stream()
			.forEach(row -> row.stream()
					.forEach(node -> 
						node.getAttributeInstance(workflowInstance, node.getExternalId()).get().defineSkippedAttributeInstance(node)
					)
			);
	}
	
	public List<List<AttributeInstanceDto>> getDependencyTreeDto(List<List<AttributeInstance>> dependencyTree) {
		return dependencyTree.stream()
				.map(row -> row.stream()
						.map(ai -> new AttributeInstanceDto(ai)).collect(Collectors.toList())
				).collect(Collectors.toList());
	}
	
	public List<List<AttributeInstance>> removeDuplicates(List<List<AttributeInstance>> dependencyTree) {
		for (int i = 0; i < dependencyTree.size(); i++) {
			List<AttributeInstance> row = dependencyTree.get(i);
			List<List<AttributeInstance>> restOfTree = dependencyTree.subList(i + 1, dependencyTree.size());
			List<AttributeInstance> restOfTreeFlat = restOfTree.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
			List<AttributeInstance> rowWithUniqueElements = row.stream().filter(ai -> !restOfTreeFlat.contains(ai))
					.collect(Collectors.toList());
			
			dependencyTree.set(i, rowWithUniqueElements);
		}
		
		return dependencyTree;
	}
	
	@Atomic(mode = TxMode.WRITE)
	public List<List<AttributeInstanceDto>> getDependencyTree(String specId, String instanceName, 
			AttributeInstanceWithEntityInstanceIdDto attributeInstanceWithEntityInstanceIdDto) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);
		
		AttributeInstanceDto attributeInstanceDto = attributeInstanceWithEntityInstanceIdDto.getAttributeInstance();
		EntityInstance entityInstance = workflowInstance.getEntityInstanceById(attributeInstanceWithEntityInstanceIdDto.getEntityInstanceId());
		Attribute attribute = Attribute.getAttributeByName(attributeInstanceDto.getAttribute().getName(), 
				attributeInstanceDto.getAttribute().getEntityName(), workflowInstance).get();
		AttributeInstance attributeInstance = new AttributeInstance(entityInstance, attribute, attributeInstanceDto.getValue(), 
				attributeInstanceDto.getState());
		
		List<AttributeInstance> dependentAttributeInstances = attributeInstance.getDependentAttributeInstances(workflowInstance);
		List<List<AttributeInstance>> dependencyTree = new ArrayList<List<AttributeInstance>>();
		
		while (dependentAttributeInstances.size() != 0) {
			dependencyTree.add(dependentAttributeInstances.stream().distinct().collect(Collectors.toList()));
			dependentAttributeInstances = attributeInstance.getNextDependentAttributeInstances(workflowInstance, dependentAttributeInstances);
		}
		
		attributeInstance.delete();

		return getDependencyTreeDto(removeDuplicates(dependencyTree));
	}
	
	@Atomic(mode = TxMode.WRITE)
	public List<AttributeInstanceDto> getDependentAttributeInstances(String specId, String instanceName, 
			AttributeInstanceWithEntityInstanceIdDto attributeInstanceWithEntityInstanceIdDto) {
		WorkflowInstance workflowInstance = getWorkflowInstance(specId, instanceName);

		AttributeInstanceDto attributeInstanceDto = attributeInstanceWithEntityInstanceIdDto.getAttributeInstance();
		EntityInstance entityInstance = workflowInstance.getEntityInstanceById(attributeInstanceWithEntityInstanceIdDto.getEntityInstanceId());
		Attribute attribute = Attribute.getAttributeByName(attributeInstanceDto.getAttribute().getName(), 
				attributeInstanceDto.getAttribute().getEntityName(), workflowInstance).get();
		AttributeInstance attributeInstance = new AttributeInstance(entityInstance, attribute, attributeInstanceDto.getValue(), 
				attributeInstanceDto.getState());
		
		List<AttributeInstanceDto> dependentAttributeInstances = attributeInstance.getDependentAttributeInstances(workflowInstance)
				.stream().map(ai -> new AttributeInstanceDto(ai)).collect(Collectors.toList());
		
		attributeInstance.delete();
		
		return dependentAttributeInstances;
	}
}
