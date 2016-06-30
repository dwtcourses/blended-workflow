package pt.ist.socialsoftware.blendedworkflow.domain;

import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;

public class RelationInstance extends RelationInstance_Base {

	@Override
	public void setEntityInstanceOne(EntityInstance entityInstance) {
		checkConsistency(entityInstance, getEntityInstanceTwo(), getRelationType());
		super.setEntityInstanceOne(entityInstance);
	}

	@Override
	public void setEntityInstanceTwo(EntityInstance entityInstance) {
		checkConsistency(getEntityInstanceOne(), entityInstance, getRelationType());
		super.setEntityInstanceTwo(entityInstance);
	}

	@Override
	public void setRelationType(RelationBW relation) {
		super.setRelationType(relation);
		checkConsistency(getEntityInstanceOne(), getEntityInstanceTwo(), relation);
	}

	public RelationInstance(EntityInstance entityInstanceOne, EntityInstance entityInstanceTwo, RelationBW relation) {
		setEntityInstanceOne(entityInstanceOne);
		setEntityInstanceTwo(entityInstanceTwo);
		setRelationType(relation);
	}

	private void checkConsistency(EntityInstance entityInstanceOne, EntityInstance entityInstanceTwo,
			RelationBW relation) {
		if (entityInstanceOne != null && entityInstanceTwo != null && relation != null) {
			if (entityInstanceOne.getWorkflowInstance() != entityInstanceTwo.getWorkflowInstance()) {
				throw new BWException(BWErrorType.RELATIONINSTANCE_CONSISTENCY, "Different workflow instances "
						+ entityInstanceOne.getEntity().getName() + "-" + entityInstanceTwo.getEntity().getName());
			}

			if (entityInstanceOne.getEntity() != relation.getEntityOne()) {
				throw new BWException(BWErrorType.RELATIONINSTANCE_CONSISTENCY, "Entity One type does not match "
						+ entityInstanceOne.getEntity().getName() + "-" + relation.getEntityOne().getName());
			}

			if (entityInstanceTwo.getEntity() != relation.getEntityTwo()) {
				throw new BWException(BWErrorType.RELATIONINSTANCE_CONSISTENCY, "Entity Two type does not match "
						+ entityInstanceTwo.getEntity().getName() + "-" + relation.getEntityTwo().getName());
			}

			if (entityInstanceOne.numberOfInstances(relation.getRoleNameTwo()) > relation.getCardinalityTwo()
					.getMaxValue()) {
				throw new BWException(BWErrorType.RELATIONINSTANCE_CONSISTENCY,
						"Number of instances " + entityInstanceOne.numberOfInstances(relation.getRoleNameTwo()) + " > "
								+ relation.getCardinalityTwo().getMaxValue());
			}

			if (entityInstanceTwo.numberOfInstances(relation.getRoleNameOne()) > relation.getCardinalityOne()
					.getMaxValue()) {
				throw new BWException(BWErrorType.RELATIONINSTANCE_CONSISTENCY,
						"Number of instances " + entityInstanceOne.numberOfInstances(relation.getRoleNameOne()) + " > "
								+ relation.getCardinalityOne().getMaxValue());
			}
		}

	}

	public void delete() {
		setRelationType(null);
		setEntityInstanceOne(null);
		setEntityInstanceTwo(null);
	}

}
