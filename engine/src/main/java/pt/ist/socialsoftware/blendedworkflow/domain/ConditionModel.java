package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import pt.ist.socialsoftware.blendedworkflow.domain.Product.ProductType;

public class ConditionModel extends ConditionModel_Base {

	public void clean() {
		if (getSpecification().getGoalModel() != null)
			getSpecification().getGoalModel().clean();
		if (getSpecification().getTaskModel() != null)
			getSpecification().getTaskModel().clean();
		getEntityAchieveConditionSet().stream().forEach(defEnt -> removeEntityAchieveCondition(defEnt));
		getEntityDependenceConditionSet().stream().forEach(depEnt -> removeEntityDependenceCondition(depEnt));
		getEntityInvariantConditionSet().stream().forEach(mul -> removeEntityInvariantCondition(mul));
		getAttributeAchieveConditionSet().stream().forEach(defAtt -> removeAttributeAchieveCondition(defAtt));
		getAttributeDependenceConditionSet().stream().forEach(depAtt -> removeAttributeDependenceCondition(depAtt));
		getAttributeInvariantConditionSet().stream().forEach(rule -> removeAttributeInvariantCondition(rule));
	}

	public void delete() {
		clean();
		setSpecification(null);

		deleteDomainObject();
	}

	public void generateConditions() {
		DataModel dataModel = getSpecification().getDataModel();

		dataModel.getEntitySet().stream().forEach(e -> DefEntityCondition.getDefEntity(e));

		dataModel.getAttributeSet().stream().filter(Attribute.class::isInstance).map(Attribute.class::cast)
				.filter(a -> !a.getEntity().getExists()).forEach(a -> DefAttributeCondition.getDefAttribute(a));

		dataModel.getRelationBWSet().stream().forEach(r -> MulCondition.createMUlConditions(r));

		dataModel.getEntitySet().stream().flatMap(e -> e.getRuleSet().stream())
				.forEach(r -> this.addAttributeInvariantCondition(r));

		dataModel.getDependenceSet().stream().filter(d -> d.getProduct().getProductType().equals(ProductType.ENTITY))
				.forEach(d -> this.addEntityDependenceCondition(d));

		dataModel.getDependenceSet().stream().filter(d -> !d.getProduct().getProductType().equals(ProductType.ENTITY))
				.forEach(d -> this.addAttributeDependenceCondition(d));

	}

	public Set<DefProductCondition> getAllProductionConditions() {
		Set<DefProductCondition> allDefConditions = new HashSet<DefProductCondition>(getEntityAchieveConditionSet()
				.stream().filter(d -> !d.getEntity().getExists()).collect(Collectors.toSet()));

		allDefConditions.addAll(getAttributeAchieveConditionSet().stream()
				.filter(d -> !d.getAttributeOfDef().getEntity().getExists()).collect(Collectors.toSet()));

		return allDefConditions;
	}

	static public Set<Product> getProductsOfDefConditions(Set<? extends DefProductCondition> defConditions) {
		return defConditions.stream().map(d -> d.getTargetOfPath()).collect(Collectors.toSet());
	}

	static public Set<Entity> getEntitiesOfDefConditionSet(Set<? extends DefProductCondition> defEntities) {
		Set<Entity> entities = defEntities.stream().map(d -> d.getTargetOfPath()).filter(Entity.class::isInstance)
				.map(Entity.class::cast).collect(Collectors.toSet());
		return entities;
	}

	static public Set<DefEntityCondition> getDefEntityConditions(Set<? extends DefProductCondition> conditions) {
		return conditions.stream().filter(DefEntityCondition.class::isInstance).map(DefEntityCondition.class::cast)
				.collect(Collectors.toSet());
	}

	static public Set<DefAttributeCondition> getDefAttributeConditions(Set<? extends DefProductCondition> conditions) {
		return conditions.stream().filter(DefAttributeCondition.class::isInstance)
				.map(DefAttributeCondition.class::cast).collect(Collectors.toSet());
	}

}
