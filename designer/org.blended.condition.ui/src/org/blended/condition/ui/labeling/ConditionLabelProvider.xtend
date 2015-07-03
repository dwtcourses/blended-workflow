/*
 * generated by Xtext
 */
package org.blended.condition.ui.labeling

import com.google.inject.Inject
import org.blended.condition.condition.And
import org.blended.condition.condition.AttributeAchieveCondition
import org.blended.condition.condition.AttributeDependenceCondition
import org.blended.condition.condition.AttributeInvariantCondition
import org.blended.condition.condition.Condition
import org.blended.condition.condition.ConditionModel
import org.blended.condition.condition.EntityAchieveCondition
import org.blended.condition.condition.EntityDependenceCondition
import org.blended.condition.condition.EntityInvariantCondition
import org.blended.condition.condition.Not
import org.blended.condition.condition.Or
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class ConditionLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// Labels and icons can be computed like this:

	def image(ConditionModel cm) {
		'condition.gif'
	}
	
	def text(ConditionModel cm) {
		'Conditions'
	}
	
	def image(Condition c) {
		'condition.gif'
	}
	
	def text(Condition c) {
		'For Entities and Attributes'
	}
	
	//********ENTITIES
	def image(EntityAchieveCondition eac) {
		'LetterABlue.png'
	}
	
	def text(EntityAchieveCondition eac) {
		'Achieve: [' + eac.name + ']'
	}
	
	def image(EntityInvariantCondition eic) {
		'LetterIBlue.png'
	}
	
	def text(EntityInvariantCondition eic) {
		'Invariant for: [' + eic.name + ']'
	}
	
	def image(EntityDependenceCondition edc) {
		'LetterDBlue.png'
	}
	
	def text(EntityDependenceCondition edc) {
		'Dependence on: [' + edc.entity2 + ']'
	}
	
	//********ATTRIBUTES
	
	def image(AttributeAchieveCondition aac) {
		'LetterAGreen.png'
	}
	
	def text(AttributeAchieveCondition aac) {
		'Achieve: ' + aac.conditions
	}
	
	def image(AttributeInvariantCondition aic) {
		'LetterIGreen.png'
	}
	
	def text(AttributeInvariantCondition aic) {
		'Invariant for attribute'
	}
	
	def text(And and) {
		'AND'
	}
	
	def text(Or or) {
		'OR'
	}
	
	def text(Not not) {
		'NOT'
	}
	
	def image(AttributeDependenceCondition adc) {
		'LetterDGreen.png'
	}
	
	def text(AttributeDependenceCondition edc) {
		'Dependence on: [' + edc.attribute2 + ']'
	}
}
