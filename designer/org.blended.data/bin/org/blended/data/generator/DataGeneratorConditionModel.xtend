/*
 * generated by Xtext
 */
package org.blended.data.generator

import org.blended.data.data.And
import org.blended.data.data.Association
import org.blended.data.data.Attribute
import org.blended.data.data.AttributeValue
import org.blended.data.data.Constraint
import org.blended.data.data.Entity
import org.blended.data.data.Expression
import org.blended.data.data.Not
import org.blended.data.data.Or
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.blended.data.data.AttributeGroup
import org.blended.data.data.AttributeDefinition

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class DataGeneratorConditionModel {
	Resource resource
	IFileSystemAccess fsa
	StringBuilder sb
	
	new (Resource resource, IFileSystemAccess fsa) {
		this.resource = resource
		this.fsa = fsa
		this.sb = new StringBuilder
	}
	
	def doGenerate() {
		sb.append("ENTITY_ACHIEVE_CONDITIONS\r\n")
		for (r : resource.allContents.toIterable.filter(typeof(Entity))) {
			if (!r.exists) {
				var entity = new StringBuilder(r.entityAchieveCondition)
				sb.append("\t" + entity)	
			}
			else {
				var entity = new StringBuilder(r.entityAchieveConditionExists)
				sb.append("\t" + entity)	
			}
		}
		
		sb.append("\r\nENTITY_INVARIANT_CONDITIONS\r\n")	
		for (r : resource.allContents.toIterable.filter(typeof(Association))) {
			if (!r.entity1.exists) {
				var association = new StringBuilder(r.entityInvariantCondition1)
				sb.append("\t" + association)		
			}
			if (!r.entity2.exists) {
				var association = new StringBuilder(r.entityInvariantCondition2)
				sb.append("\t" + association)		
			}
		}
		
		sb.append("\r\nENTITY_DEPENDENCE_CONDITIONS\r\n")
		for (r : resource.allContents.toIterable.filter(typeof(Entity))) {
			if ((!r.exists) && (r.dependsOn != null)) {
				var entity = new StringBuilder(r.entityDependenceCondition)
				sb.append("\t" + entity)	
			}
		}	
		
		sb.append("\r\nATTRIBUTE_ACHIEVE_CONDITIONS\r\n")
		for (r : resource.allContents.toIterable.filter(typeof(Entity))) {
			if (!r.exists) {
				for (a : r.attributes) {
					if (a instanceof Attribute) {
						var attribute = new StringBuilder(r.attributeAchieveCondition(a))
						sb.append("\t" + attribute)		
					}	
				}
			}
		}
		for (r : resource.allContents.toIterable.filter(typeof(Entity))) { //grouped
			if (!r.exists) {
				for (ag : r.attributes) {
					if (ag instanceof AttributeGroup) {
						var s = new StringBuilder()
						var i = 1
						for (a : ag.attributes) {
							if (i==1) {
								s.append((ag.eContainer as Entity).name + "." + a.name)
								i++
							}
							else {
								s.append(", " + (ag.eContainer as Entity).name + "." + a.name)
							}
						}
						var attribute = new StringBuilder(s.toString.attributeAchieveCondition(ag.mandatory))
						sb.append("\t" + attribute)		
					}	
				}
			}
		}
		
		sb.append("\r\nATTRIBUTE_INVARIANT_CONDITIONS\r\n")
		for (r : resource.allContents.toIterable.filter(typeof(Constraint))) {
			var attribute = new StringBuilder(r.attributeInvariantCondition)
			sb.append("\t" + attribute)			
		}
		
		sb.append("\r\nATTRIBUTE_DEPENDENCE_CONDITIONS\r\n")
		for (e : resource.allContents.toIterable.filter(typeof(Entity))) {
			if (!e.exists) {
				for (ab : e.attributes) {
					if (ab instanceof Attribute) {
						var b = new StringBuilder()
						var i = 1
						if (ab.dependsOn.size > 0) {
							for (d : ab.dependsOn) {
								if (i==1) {
									b.append((d.eContainer as Entity).name + "." + d.name)
									i++
								}
								else b.append(", " + (d.eContainer as Entity).name + "." + d.name)
							}
							var entity = new StringBuilder(attributeDependenceCondition(e.name + "." + ab.name, b.toString))
							sb.append("\t" + entity)	
						}					
					}
					if (ab instanceof AttributeGroup) {
						var a = new StringBuilder()
						var b = new StringBuilder()
						if (ab.dependsOn.size > 0) {
							var i = 1
							for (att : ab.attributes) {
								if (i==1) {
									a.append(e.name + "." + att.name)
									i++
								} else a.append(", " + e.name + "." + att.name)
							}
							i = 1
							for (d : ab.dependsOn) {
								if (i==1) {
									b.append((d.eContainer as Entity).name + "." + d.name)
									i++
								}
								else b.append(", " + (d.eContainer as Entity).name + "." + d.name)
							}
							var entity = new StringBuilder(attributeDependenceCondition(a.toString(), b.toString))
							sb.append("\t" + entity)	
						}
					}
				}
			}
		}	
		
		fsa.generateFile(resource.normalizedURI.lastSegment.replace(".dm", ".cm"), CustomOutputConfigurationProvider.SRC_OUTPUT, sb.toString)
	}
	
	def entityAchieveCondition(Entity e)'''
	DEF(«e.name»)	
	'''
	
	def entityAchieveConditionExists(Entity e)'''
	EXISTS(DEF(«e.name»))
	'''
	
	def entityInvariantCondition1(Association a)'''
	MUL(«a.entity1.name».«a.name2», «a.cardinality2»)
	'''	
	
	def entityInvariantCondition2(Association a)'''
	MUL(«a.entity2.name».«a.name1», «a.cardinality1»)
	'''	
	
	def entityDependenceCondition(Entity e)'''
	DEP(DEF(«e.name»), DEF(«e.dependsOn.name»))	
	'''

	def attributeAchieveCondition(Entity e, Attribute a)'''
	«IF a.mandatory»
	MAN(DEF(«e.name».«a.name»))
	«ELSE»
	DEF(«e.name».«a.name»)
	«ENDIF»
	'''
	
	def attributeAchieveCondition(String s, Boolean mandatory)'''
	«IF mandatory»
	MAN(DEF(«s»))
	«ELSE»
	DEF(«s»)
	«ENDIF»
	'''
	
	def attributeAchieveConditionGrouped(String s, Boolean mandatory)'''
	«IF mandatory»
	MAN(DEF(«s»))
	«ELSE»
	DEF(«s»)
	«ENDIF»
	'''
	
	def attributeInvariantCondition(Constraint c)'''
	RUL(«c.constraint.completeExpression»)
	'''
	
	def attributeDependenceCondition(String a, String b)'''
	DEP(DEF(«a»), DEF(«b»))
	'''

	static def getCompleteExpression(Expression e) {
		var sb = new StringBuilder()
		e.getCompleteExpression(sb)		
		return sb.toString
	}
	
	static def Object getCompleteExpression(Expression e, StringBuilder sb) {	
		switch (e) {
			AttributeDefinition: {
				sb.append('DEF(' + e.name + ')')
			}
			AttributeValue: {
				sb.append(e.name)				
			}
			Not: {
				sb.append('NOT (')
				e.expression.getCompleteExpression(sb)
				sb.append(')')
			}
			And: {
				sb.append('(')
				e.left.getCompleteExpression(sb)
				sb.append(') AND (') 
				e.right.getCompleteExpression(sb)
				sb.append(')')
			}
			Or: {
				sb.append('(')
				e.left.getCompleteExpression(sb)
				sb.append(') OR (') 
				e.right.getCompleteExpression(sb)
				sb.append(')')
			}
		}		
	}


}
