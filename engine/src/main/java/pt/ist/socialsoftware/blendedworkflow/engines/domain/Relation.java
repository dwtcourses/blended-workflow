package pt.ist.socialsoftware.blendedworkflow.engines.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException.BlendedWorkflowError;

//TODO: Create two separate Relations in DML for EntityOne and EntityTwo.
public class Relation extends Relation_Base {

	public enum Cardinality {
		MANY, ZERO_OR_ONE, ONE
	}

	public Relation(DataModel dataModel, String name, Entity entityOne,
			Entity entityTwo, Cardinality cardinalityOne,
			Cardinality cardinalityTwo, boolean isOneKeyEntity,
			boolean isTwoKeyEntity) throws BlendedWorkflowException {
		checkUniqueRelationName(entityOne, entityTwo, name);
		setDataModel(dataModel);
		setName(name);
		setEntityOne(entityOne);
		setEntityTwo(entityTwo);
		setCardinalityOne(cardinalityOne);
		setCardinalityTwo(cardinalityTwo);
		setIsOneKeyEntity(isOneKeyEntity);
		setIsTwoKeyEntity(isTwoKeyEntity);
	}

	private void checkUniqueRelationName(Entity entityOne, Entity entityTwo,
			String name) throws BlendedWorkflowException {
		for (Relation relation : entityOne.getRelationsSet()) {
			if (isInRelation(entityTwo, relation)
					&& relation.getName().equals(name)) {
				throw new BlendedWorkflowException(
						BlendedWorkflowError.INVALID_RELATION_NAME, name);
			}
		}
	}

	public void cloneRelation(DataModelInstance dataModelInstance,
			Entity entityOne, Entity entityTwo) throws BlendedWorkflowException {
		new Relation(dataModelInstance, getName(), entityOne, entityTwo,
				getCardinalityOne(), getCardinalityTwo(), getIsOneKeyEntity(),
				getIsTwoKeyEntity());
	}

	private boolean isInRelation(Entity entityTwo, Relation relation) {
		return relation.getEntityOne().equals(entityTwo)
				|| relation.getEntityTwo().equals(entityTwo);
	}

	public Entity getEntity(Entity entity) {
		if (entity.equals(getEntityOne())) {
			return getEntityOne();
		} else {
			return getEntityTwo();
		}
	}

	public Set<Entity> getEntitiesSet() {
		Set<Entity> entities = new HashSet<Entity>();
		entities.add(this.getEntityOne());
		entities.add(this.getEntityTwo());
		return entities;
	}

}