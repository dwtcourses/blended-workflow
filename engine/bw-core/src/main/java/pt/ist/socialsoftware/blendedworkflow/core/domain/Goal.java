package pt.ist.socialsoftware.blendedworkflow.core.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.socialsoftware.blendedworkflow.core.domain.ProductInstance.ProductInstanceState;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.core.service.dto.domain.GoalDto;

public abstract class Goal extends Goal_Base {
	private static Logger logger = LoggerFactory.getLogger(Goal.class);

	@Override
	public void setName(String name) {
		checkUniqueGoalName(name);
		super.setName(name);
	}

	public Goal() {

	}

	public Goal(GoalModel goalModel, String name, String description) throws BWException {
		setGoalModel(goalModel);
		setName(name);
		setDescription(description);
	}

	private void checkUniqueGoalName(String name) throws BWException {
		for (Goal goal : this.getGoalModel().getGoalSet()) {
			if (goal.getName() != null && goal != this && goal.getName().equals(name)) {
				throw new BWException(BWErrorType.INVALID_GOAL_NAME, name);
			}
		}
	}

	/**
	 * Get the Goal condition data to use in the use interface.
	 * 
	 * @return a string with the condition data entities.
	 */
	public String getConstraintData() {
		Set<Entity> entities = getSuccessConditionSet().stream().findFirst().get().getEntities();
		Set<Attribute> attributes = getSuccessConditionSet().stream().findFirst().get().getAttributeSet();
		String dataString = "";

		// Add Attribute entities
		for (Attribute attribute : attributes) {
			entities.add(attribute.getEntity());
		}

		// Create String
		int count = 0;
		for (Entity entity : entities) {
			if (entities.size() == 1) {
				dataString += entity.getName();
			} else if (count < entities.size() - 1) {
				dataString += entity.getName() + ", ";
				;
			} else {
				dataString += entity.getName();
			}
			count++;
		}
		return dataString;
	}

	public String getPreConstraintData() {
		List<Condition> activateConditions = new ArrayList<>(getActivationConditionSet());
		Set<Entity> entities = activateConditions.get(0).getEntities(); // FIXME:
																		// Only
																		// First
																		// ActivateConditionData
		Set<Attribute> attributes = activateConditions.get(0).getAttributeSet();
		String dataString = "";

		// Add Attribute entities
		for (Attribute attribute : attributes) {
			entities.add(attribute.getEntity());
		}

		// Create String
		int count = 0;
		for (Entity entity : entities) {
			if (entities.size() == 1) {
				dataString += entity.getName();
			} else if (count < entities.size() - 1) {
				dataString += entity.getName() + ", ";
				;
			} else {
				dataString += entity.getName();
			}
			count++;
		}
		return dataString;
	}

	public void delete() {
		setGoalModel(null);

		if (getView() != null) getView().delete();

		getWorkItemSet().stream().forEach(wi -> wi.delete());

		getActivationConditionSet().stream().forEach(act -> removeActivationCondition(act));

		getSuccessConditionSet().stream().forEach(def -> removeSuccessCondition(def));

		deleteDomainObject();
	}

	protected void applyActivationConditionsForProductGoal() {
		Set<String> paths = getProducedProducts().stream().flatMap(p -> p.getDependenceSet().stream())
				.map(d -> d.getPath().getValue()).collect(Collectors.toSet());

		for (Attribute attribute : getProducedAttributes()) {
			paths.add(attribute.getEntity().getFullPath());
		}

		for (String path : paths) {
			if (!getProducedProducts().contains(getDataModel().getTargetOfPath(path))) {
				addActivationCondition(DefPathCondition.getDefPathCondition(getSpecification(), path));
			}
		}
	}

	protected void applyActivationConditionsForAssociationGoal() {
		for (Entity sourceEntity : getEntityInvariantConditionSet().stream().map(m -> m.getTargetEntity())
				.collect(Collectors.toSet())) {
			addActivationCondition(
					DefPathCondition.getDefPathCondition(getSpecification(), sourceEntity.getFullPath()));
		}
	}

	protected void applyRuleConditions() {
		getProducedEntities().stream().flatMap(e -> e.getRuleSet().stream())
				.forEach(rule -> addAttributeInvariantCondition(rule));
	}

	private DataModel getDataModel() {
		return getSpecification().getDataModel();
	}

	private Specification getSpecification() {
		return getGoalModel().getSpecification();
	}

	public boolean hasDefProductCondition(DefProductCondition defProductCondition) {
		return getSuccessConditionSet().contains(defProductCondition);
	}

	public boolean hasMulCondition(MulCondition mulCondition) {
		return getEntityInvariantConditionSet().contains(mulCondition);
	}

	public boolean hasRule(Rule rule) {
		return getAttributeInvariantConditionSet().contains(rule);
	}

	public boolean hasDependence(Dependence dependence) {
		if (!getProducedProducts().contains(dependence.getProduct())) {
			return false;
		} else if (getProducedProducts().contains(dependence.getProduct())
				&& getProducedProducts().contains(dependence.getTarget())) {
			return true;
		}
		return activationConditionImplementsDependence(dependence);
	}

	private boolean activationConditionImplementsDependence(Dependence dependence) {
		return getActivationConditionSet().contains(dependence.getPath().getDefPathCondition());
	}

	public abstract void checkType();

	public void checkActivationCondition() {
		checkAttributeEntityDependence();
		checkDependencies();
	}

	private void checkAttributeEntityDependence() {
		for (Attribute attribute : getSuccessAttributes()) {
			if (!getSuccessEntities().contains(attribute.getEntity()) && !getActivationConditionSet().contains(
					DefPathCondition.getDefPathCondition(getSpecification(), attribute.getEntity().getFullPath()))) {
				throw new BWException(BWErrorType.INCONSISTENT_GOALMODEL,
						"not implemented atribute entity dependence: " + attribute.getFullPath());
			}
		}
	}

	private void checkDependencies() {
		for (Product product : getSuccessProducts()) {
			for (Dependence dependence : product.getDependenceSet()) {
				if (!hasDependence(dependence)) {
					throw new BWException(BWErrorType.INCONSISTENT_GOALMODEL,
							"not implemented dependence: " + dependence.getPath().getValue());
				}
			}
		}

	}

	public void checkDefProductConditionsExistSucc(Set<DefProductCondition> successConditions) {
		Optional<DefProductCondition> oCond = successConditions.stream()
				.filter((def) -> !getSuccessConditionSet().contains(def)).findFirst();

		if (oCond.isPresent()) {
			throw new BWException(BWErrorType.CANNOT_EXTRACT_GOAL,
					"checkConditionsExistInSource:" + oCond.get().getSubPath());
		}
	}

	public void checkMulConditionsExistInv(Set<MulCondition> mulConditions) {
		Optional<MulCondition> oCond = mulConditions.stream()
				.filter((mul) -> !getEntityInvariantConditionSet().contains(mul)).findFirst();

		if (oCond.isPresent()) {
			throw new BWException(BWErrorType.CANNOT_EXTRACT_GOAL,
					"checkConditionsExistInSource:" + oCond.get().getSubPath());
		}
	}

	public void checkCycles(Map<Goal, Set<Goal>> goalDependencies) {
		goThroughAcyclicPath(this, goalDependencies, new HashSet<Goal>());
	}

	private void goThroughAcyclicPath(Goal goal, Map<Goal, Set<Goal>> goalDependencies, Set<Goal> visitedGoals) {
		Set<Goal> nextGoals = goalDependencies.get(this);
		visitedGoals.add(this);

		if (nextGoals.contains(goal)) {
			throw new BWException(BWErrorType.DEPENDENCE_CIRCULARITY, getName());
		} else {
			for (Goal nextGoal : nextGoals) {
				if (!visitedGoals.contains(nextGoal)) {
					nextGoal.goThroughAcyclicPath(goal, goalDependencies, visitedGoals);
				}
			}
		}
	}

	public void shrinkProductGoal(Set<DefProductCondition> successConditions) {
		successConditions.stream().forEach(def -> removeSuccessCondition(def));
		getActivationConditionSet().stream().forEach(def -> removeActivationCondition(def));
		getAttributeInvariantConditionSet().stream().forEach(rul -> removeAttributeInvariantCondition(rul));
	}

	public void shrinkAssociationGoal(Set<MulCondition> mulConditionSet) {
		mulConditionSet.stream().forEach(mul -> removeEntityInvariantCondition(mul));
		getActivationConditionSet().stream().forEach(def -> removeActivationCondition(def));
	}

	protected Set<Product> getProducedProducts() {
		return getSuccessConditionSet().stream().map(d -> d.getPath().getTarget()).collect(Collectors.toSet());
	}

	private Set<Entity> getProducedEntities() {
		return getSuccessConditionSet().stream().map(d -> d.getPath().getTarget()).filter(Entity.class::isInstance)
				.map(Entity.class::cast).collect(Collectors.toSet());
	}

	private Set<Attribute> getProducedAttributes() {
		return getSuccessConditionSet().stream().map(d -> d.getPath().getTarget()).filter(Attribute.class::isInstance)
				.map(Attribute.class::cast).collect(Collectors.toSet());
	}

	public Boolean isEnabledForExecution(WorkflowInstance workflowInstance) {
		// get entity context
		Set<Entity> entityContext = getEntityContext();
		
		if (entityContext.isEmpty()) {
			return true;
		}
		
		// for each entity, in entity context, get instance context
		for (Entity entity : entityContext) {
			if (getEntityInstanceContext(workflowInstance, entity).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public Set<Entity> getEntityContext() {
		Set<Entity> entityContext = new HashSet<>();

		for (DefPathCondition defPathCondition : getActivationConditionSet()) {
			if (getProducedEntities().contains(defPathCondition.getSourceOfPath())) {
				// entity in the source of path is going to be defined
				entityContext.add(defPathCondition.getPath().getAdjacent());
			} else {
				// entity in the source of path is already defined
				entityContext.add(defPathCondition.getSourceOfPath());
			}
		}
		
		return entityContext;
	}
	
	public Set<Entity> getInitialSkippedEntityInstancesContext(WorkflowInstance workflowInstance, Set<Entity> entityContext) {		
		if (entityContext.isEmpty() && getActivationConditionSet().isEmpty()) {
			for (Entity entity : getProducedEntities()) {
				for (EntityInstance entityInstance : workflowInstance.getEntityInstanceSet(entity)) {
					if (entityInstance.getState().equals(ProductInstanceState.SKIPPED)) 
						entityContext.add(entity);
				}
			}
		}

		return entityContext;
	}

	public Set<Entity> getEntityContextForDefinitionGroup(Entity entity, WorkflowInstance workflowInstance) {
		Set<Entity> entityContext = new HashSet<>();

		if (!getProducedEntities().contains(entity)) {
			// entity is defined, some of its attributes are going to be defined
			entityContext.add(entity);
		} else {
			// entity is going to be defined
			for (DefPathCondition defPathCondition : getActivationConditionSet()) {
				// it depends on a path
				if (defPathCondition.getSourceOfPath() == entity) {
					entityContext.add(defPathCondition.getPath().getAdjacent());
				}
			}
			
			entityContext = getInitialSkippedEntityInstancesContext(workflowInstance, entityContext);		
		}

		return entityContext;
	}

	public Map<Entity, Set<EntityInstance>> getInstanceContext(WorkflowInstance workflowInstance) {
		Map<Entity, Set<EntityInstance>> instanceContext = new HashMap<>();

		for (Entity entity : getEntityContext()) {
			instanceContext.put(entity, getEntityInstanceContext(workflowInstance, entity));
		}

		return instanceContext;
	}

	public abstract Set<EntityInstance> getEntityInstanceContext(WorkflowInstance workflowInstance,
			Entity contextEntity);
	
	public Set<EntityInstance> getEntityInstanceContextForNewEntityGoal(WorkflowInstance workflowInstance, Entity contextEntity) {
		// instances where activation conditions hold
		Set<EntityInstance> instanceContext = workflowInstance.getEntityInstanceSet(contextEntity).stream()
				.filter(ei -> ei.holdsDefPathConditions(getActivationConditionSetForContextEntity(contextEntity)))
				.collect(Collectors.toSet());

		instanceContext = instanceContext.stream().filter(ei -> ei.isSkipped())
				.collect(Collectors.toSet());
		
		return instanceContext;
	}

	public Set<MulCondition> getMulConditionsThatShouldHold(Product product) {
		return getEntityInvariantConditionSet().stream().filter(m -> m.getSourceEntity() == product)
				.collect(Collectors.toSet());
	}

	protected Set<Entity> getSuccessEntities() {
		return getSuccessConditionSet().stream().map(d -> d.getTargetOfPath()).filter(Entity.class::isInstance)
				.map(Entity.class::cast).collect(Collectors.toSet());
	}

	protected Set<Attribute> getSuccessAttributes() {
		return getSuccessConditionSet().stream().map(d -> d.getTargetOfPath()).filter(Attribute.class::isInstance)
				.map(Attribute.class::cast).collect(Collectors.toSet());
	}

	protected Set<Product> getSuccessProducts() {
		return getSuccessConditionSet().stream().map(d -> d.getTargetOfPath()).collect(Collectors.toSet());
	}

	public boolean successProductsContainSomeOf(Set<Product> products) {
		for (Product product : getSuccessProducts()) {
			if (products.contains(product)) {
				return true;
			}
		}
		return false;
	}

	public Set<MulCondition> getMulConditionFromEntityToEntity(Entity fromEntity, Entity toEntity) {
		return getEntityInvariantConditionSet().stream()
				.filter(m -> m.getSourceEntity() == fromEntity && m.getTargetEntity() == toEntity)
				.collect(Collectors.toSet());
	}

	public Set<MulCondition> getInnerMulConditions(Entity entity) {
		return getEntityInvariantConditionSet().stream().filter(m -> m.getSourceEntity() == entity)
				.collect(Collectors.toSet());
	}

	public Set<DefPathCondition> getActivationConditionSetForContextEntity(Entity contextEntity) {
		return getActivationConditionSet().stream().filter(
				def -> def.getPath().getSource() == contextEntity || def.getPath().getAdjacent() == contextEntity)
				.collect(Collectors.toSet());
	}

}