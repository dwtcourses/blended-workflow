/*
 * generated by Xtext
 */
package org.blended.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.blended.blended.Activity;
import org.blended.blended.And;
import org.blended.blended.AttributeDefinition;
import org.blended.blended.AttributeDependenceCondition;
import org.blended.blended.AttributeInvariantCondition;
import org.blended.blended.AttributeValue;
import org.blended.blended.BlendedPackage;
import org.blended.blended.Condition;
import org.blended.blended.EntityAchieveCondition;
import org.blended.blended.EntityAchieveConditionExist;
import org.blended.blended.EntityDependenceCondition;
import org.blended.blended.EntityInvariantCondition;
import org.blended.blended.Goal;
import org.blended.blended.MandatoryAttributeAchieveCondition;
import org.blended.blended.Not;
import org.blended.blended.NotMandatoryAttributeAchieveCondition;
import org.blended.blended.Nothing;
import org.blended.blended.Or;
import org.blended.services.BlendedGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class BlendedSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private BlendedGrammarAccess grammarAccess;
	
	@Override
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == BlendedPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case BlendedPackage.ACTIVITY:
				sequence_Activity(context, (Activity) semanticObject); 
				return; 
			case BlendedPackage.AND:
				sequence_And(context, (And) semanticObject); 
				return; 
			case BlendedPackage.ATTRIBUTE_DEFINITION:
				sequence_Atomic(context, (AttributeDefinition) semanticObject); 
				return; 
			case BlendedPackage.ATTRIBUTE_DEPENDENCE_CONDITION:
				sequence_AttributeDependenceCondition(context, (AttributeDependenceCondition) semanticObject); 
				return; 
			case BlendedPackage.ATTRIBUTE_INVARIANT_CONDITION:
				sequence_AttributeInvariantCondition(context, (AttributeInvariantCondition) semanticObject); 
				return; 
			case BlendedPackage.ATTRIBUTE_VALUE:
				sequence_Atomic(context, (AttributeValue) semanticObject); 
				return; 
			case BlendedPackage.CONDITION:
				sequence_Condition(context, (Condition) semanticObject); 
				return; 
			case BlendedPackage.ENTITY_ACHIEVE_CONDITION:
				sequence_EntityAchieveCondition(context, (EntityAchieveCondition) semanticObject); 
				return; 
			case BlendedPackage.ENTITY_ACHIEVE_CONDITION_EXIST:
				sequence_EntityAchieveConditionExist(context, (EntityAchieveConditionExist) semanticObject); 
				return; 
			case BlendedPackage.ENTITY_DEPENDENCE_CONDITION:
				sequence_EntityDependenceCondition(context, (EntityDependenceCondition) semanticObject); 
				return; 
			case BlendedPackage.ENTITY_INVARIANT_CONDITION:
				sequence_EntityInvariantCondition(context, (EntityInvariantCondition) semanticObject); 
				return; 
			case BlendedPackage.GOAL:
				sequence_Goal(context, (Goal) semanticObject); 
				return; 
			case BlendedPackage.MANDATORY_ATTRIBUTE_ACHIEVE_CONDITION:
				sequence_MandatoryAttributeAchieveCondition(context, (MandatoryAttributeAchieveCondition) semanticObject); 
				return; 
			case BlendedPackage.NOT:
				sequence_Primary(context, (Not) semanticObject); 
				return; 
			case BlendedPackage.NOT_MANDATORY_ATTRIBUTE_ACHIEVE_CONDITION:
				sequence_NotMandatoryAttributeAchieveCondition(context, (NotMandatoryAttributeAchieveCondition) semanticObject); 
				return; 
			case BlendedPackage.NOTHING:
				sequence_Nothing(context, (Nothing) semanticObject); 
				return; 
			case BlendedPackage.OR:
				sequence_Or(context, (Or) semanticObject); 
				return; 
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         description=STRING 
	 *         (
	 *             (pre+=EntityAchieveCondition | pre+=NotMandatoryAttributeAchieveCondition) 
	 *             (pre+=EntityAchieveCondition | pre+=NotMandatoryAttributeAchieveCondition)*
	 *         )? 
	 *         (
	 *             (post+=EntityAchieveCondition | post+=NotMandatoryAttributeAchieveCondition | post+=EntityInvariantCondition | post+=AttributeInvariantCondition) 
	 *             (post+=EntityAchieveCondition | post+=NotMandatoryAttributeAchieveCondition | post+=EntityInvariantCondition | post+=AttributeInvariantCondition)*
	 *         )?
	 *     )
	 */
	protected void sequence_Activity(EObject context, Activity semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=And_And_1_0 right=Primary)
	 */
	protected void sequence_And(EObject context, And semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.AND__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.AND__LEFT));
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.AND__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.AND__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAndAccess().getAndLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getAndAccess().getRightPrimaryParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     name=Attribute
	 */
	protected void sequence_Atomic(EObject context, AttributeDefinition semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ATTRIBUTE_DEFINITION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ATTRIBUTE_DEFINITION__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAtomicAccess().getNameAttributeParserRuleCall_0_3_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     name=Attribute
	 */
	protected void sequence_Atomic(EObject context, AttributeValue semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ATTRIBUTE_VALUE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ATTRIBUTE_VALUE__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAtomicAccess().getNameAttributeParserRuleCall_1_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (attributes1+=Attribute attributes1+=Attribute* attribute2=Attribute)
	 */
	protected void sequence_AttributeDependenceCondition(EObject context, AttributeDependenceCondition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expression=Expression
	 */
	protected void sequence_AttributeInvariantCondition(EObject context, AttributeInvariantCondition semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ATTRIBUTE_INVARIANT_CONDITION__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ATTRIBUTE_INVARIANT_CONDITION__EXPRESSION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAttributeInvariantConditionAccess().getExpressionExpressionParserRuleCall_2_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (entityAchieveConditions+=EntityAchieveCondition | entityAchieveConditions+=EntityAchieveConditionExist)* 
	 *         entityInvariantConditions+=EntityInvariantCondition* 
	 *         entityDependenceConditions+=EntityDependenceCondition* 
	 *         attributeAchieveConditions+=AttributeAchieveCondition* 
	 *         attributeInvariantConditions+=AttributeInvariantCondition* 
	 *         attributeDependenceConditions+=AttributeDependenceCondition*
	 *     )
	 */
	protected void sequence_Condition(EObject context, Condition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_EntityAchieveConditionExist(EObject context, EntityAchieveConditionExist semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ENTITY_ACHIEVE_CONDITION_EXIST__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ENTITY_ACHIEVE_CONDITION_EXIST__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getEntityAchieveConditionExistAccess().getNameIDTerminalRuleCall_4_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_EntityAchieveCondition(EObject context, EntityAchieveCondition semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ENTITY_ACHIEVE_CONDITION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ENTITY_ACHIEVE_CONDITION__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getEntityAchieveConditionAccess().getNameIDTerminalRuleCall_2_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (entity1=ID entity2=ID)
	 */
	protected void sequence_EntityDependenceCondition(EObject context, EntityDependenceCondition semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ENTITY_DEPENDENCE_CONDITION__ENTITY1) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ENTITY_DEPENDENCE_CONDITION__ENTITY1));
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ENTITY_DEPENDENCE_CONDITION__ENTITY2) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ENTITY_DEPENDENCE_CONDITION__ENTITY2));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getEntityDependenceConditionAccess().getEntity1IDTerminalRuleCall_4_0(), semanticObject.getEntity1());
		feeder.accept(grammarAccess.getEntityDependenceConditionAccess().getEntity2IDTerminalRuleCall_9_0(), semanticObject.getEntity2());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (name=Attribute cardinality=Cardinality)
	 */
	protected void sequence_EntityInvariantCondition(EObject context, EntityInvariantCondition semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ENTITY_INVARIANT_CONDITION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ENTITY_INVARIANT_CONDITION__NAME));
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.ENTITY_INVARIANT_CONDITION__CARDINALITY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.ENTITY_INVARIANT_CONDITION__CARDINALITY));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getEntityInvariantConditionAccess().getNameAttributeParserRuleCall_2_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getEntityInvariantConditionAccess().getCardinalityCardinalityParserRuleCall_4_0(), semanticObject.getCardinality());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (sucessCondition=EntityAchieveCondition | sucessCondition=AttributeAchieveCondition | sucessCondition=Nothing) 
	 *         (activationCondition=EntityAchieveCondition | activationCondition=AttributeAchieveCondition)? 
	 *         (
	 *             (invariantConditions+=EntityInvariantCondition | invariantConditions+=AttributeInvariantCondition) 
	 *             (invariantConditions+=EntityInvariantCondition | invariantConditions+=AttributeInvariantCondition)*
	 *         )? 
	 *         (childrenGoals+=[Goal|ID] childrenGoals+=[Goal|ID]*)?
	 *     )
	 */
	protected void sequence_Goal(EObject context, Goal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (conditions+=Attribute conditions+=Attribute*)
	 */
	protected void sequence_MandatoryAttributeAchieveCondition(EObject context, MandatoryAttributeAchieveCondition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (conditions+=Attribute conditions+=Attribute*)
	 */
	protected void sequence_NotMandatoryAttributeAchieveCondition(EObject context, NotMandatoryAttributeAchieveCondition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name='nothing'
	 */
	protected void sequence_Nothing(EObject context, Nothing semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.NOTHING__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.NOTHING__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNothingAccess().getNameNothingKeyword_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=Or_Or_1_0 right=And)
	 */
	protected void sequence_Or(EObject context, Or semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.OR__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.OR__LEFT));
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.OR__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.OR__RIGHT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getOrAccess().getOrLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     expression=Primary
	 */
	protected void sequence_Primary(EObject context, Not semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, BlendedPackage.Literals.NOT__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, BlendedPackage.Literals.NOT__EXPRESSION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPrimaryAccess().getExpressionPrimaryParserRuleCall_1_2_0(), semanticObject.getExpression());
		feeder.finish();
	}
}