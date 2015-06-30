/**
 */
package org.blended.blended;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.blended.blended.BlendedPackage
 * @generated
 */
public interface BlendedFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  BlendedFactory eINSTANCE = org.blended.blended.impl.BlendedFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Model</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Model</em>'.
   * @generated
   */
  BlendedModel createBlendedModel();

  /**
   * Returns a new object of class '<em>Entity Achieve Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Entity Achieve Condition</em>'.
   * @generated
   */
  EntityAchieveCondition createEntityAchieveCondition();

  /**
   * Returns a new object of class '<em>Entity Achieve Condition Exist</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Entity Achieve Condition Exist</em>'.
   * @generated
   */
  EntityAchieveConditionExist createEntityAchieveConditionExist();

  /**
   * Returns a new object of class '<em>Entity Invariant Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Entity Invariant Condition</em>'.
   * @generated
   */
  EntityInvariantCondition createEntityInvariantCondition();

  /**
   * Returns a new object of class '<em>Entity Dependence Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Entity Dependence Condition</em>'.
   * @generated
   */
  EntityDependenceCondition createEntityDependenceCondition();

  /**
   * Returns a new object of class '<em>Attribute Achieve Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Attribute Achieve Condition</em>'.
   * @generated
   */
  AttributeAchieveCondition createAttributeAchieveCondition();

  /**
   * Returns a new object of class '<em>Not Mandatory Attribute Achieve Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Not Mandatory Attribute Achieve Condition</em>'.
   * @generated
   */
  NotMandatoryAttributeAchieveCondition createNotMandatoryAttributeAchieveCondition();

  /**
   * Returns a new object of class '<em>Mandatory Attribute Achieve Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Mandatory Attribute Achieve Condition</em>'.
   * @generated
   */
  MandatoryAttributeAchieveCondition createMandatoryAttributeAchieveCondition();

  /**
   * Returns a new object of class '<em>Attribute Invariant Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Attribute Invariant Condition</em>'.
   * @generated
   */
  AttributeInvariantCondition createAttributeInvariantCondition();

  /**
   * Returns a new object of class '<em>Attribute Dependence Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Attribute Dependence Condition</em>'.
   * @generated
   */
  AttributeDependenceCondition createAttributeDependenceCondition();

  /**
   * Returns a new object of class '<em>Nothing</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Nothing</em>'.
   * @generated
   */
  Nothing createNothing();

  /**
   * Returns a new object of class '<em>Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression</em>'.
   * @generated
   */
  Expression createExpression();

  /**
   * Returns a new object of class '<em>Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Condition</em>'.
   * @generated
   */
  Condition createCondition();

  /**
   * Returns a new object of class '<em>Goal</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Goal</em>'.
   * @generated
   */
  Goal createGoal();

  /**
   * Returns a new object of class '<em>Activity</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Activity</em>'.
   * @generated
   */
  Activity createActivity();

  /**
   * Returns a new object of class '<em>Or</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Or</em>'.
   * @generated
   */
  Or createOr();

  /**
   * Returns a new object of class '<em>And</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>And</em>'.
   * @generated
   */
  And createAnd();

  /**
   * Returns a new object of class '<em>Not</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Not</em>'.
   * @generated
   */
  Not createNot();

  /**
   * Returns a new object of class '<em>Attribute Definition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Attribute Definition</em>'.
   * @generated
   */
  AttributeDefinition createAttributeDefinition();

  /**
   * Returns a new object of class '<em>Attribute Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Attribute Value</em>'.
   * @generated
   */
  AttributeValue createAttributeValue();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  BlendedPackage getBlendedPackage();

} //BlendedFactory