/*
 * generated by Xtext
 */
package org.blended.condition.generator

import java.util.ArrayList
import java.util.List
import org.blended.blended.EntityAchieveCondition
import org.blended.generator.CustomOutputConfigurationProvider
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.blended.blended.EntityDependenceCondition
import org.blended.blended.EntityInvariantCondition
import org.blended.blended.AttributeAchieveCondition
import org.blended.blended.AttributeInvariantCondition
import org.blended.blended.AttributeDependenceCondition

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class ConditionGeneratorActivityModel {
	Resource resource
	IFileSystemAccess fsa
	StringBuilder sb
	List<StringBuilder> sbs
	//List<Integer> nPre
	//List<List<String>> postsEnt
	//List<List<String>> postsAtt
	Integer i
	
	new (Resource resource, IFileSystemAccess fsa) {
		this.resource = resource
		this.fsa = fsa
		this.sb = new StringBuilder
		this.sbs = new ArrayList<StringBuilder>
		//this.nPre = new ArrayList<Integer>()
		//this.postsEnt = new ArrayList<List<String>>()
		//this.postsAtt = new ArrayList<List<String>>()
		this.i = 0
	}
	
	def doGenerate() {	
		for (r : resource.allContents.toIterable.filter(typeof(EntityAchieveCondition))) {
			//this.nPre.add(i, 0)
			//this.postsEnt.add(i, new ArrayList<String>())
			//this.postsAtt.add(i, new ArrayList<String>())
			var sb = new StringBuilder(r.activity)
			sbs.add(sb)
			i++
		}
		
		for (part : sbs) {
			sb.append(part) //to put everything together
		}
		
		fsa.generateFile(resource.normalizedURI.lastSegment.replace(".cm", ".am"), CustomOutputConfigurationProvider::SRC_OUTPUT, sb.toString)
	}
	
	def activity(EntityAchieveCondition eac) {
		var sb = new StringBuilder()
		sb.append('a' + (i+1) + ':' + ' "Activity number ' + (i+1) + '"\r\n')
		sb.append('\tPRE(' + processPre(eac) + '),\r\n\tPOST(' + processPost(eac) + ')\r\n\r\n')
	}

	def processPre(EntityAchieveCondition eac) {
		var s2 = step2(eac)
		//var s3 = step3()
		
		//if (s2.length > 0 && s3.length > 0)
		//	s2+= ', ' + s3
		//else 
		//	s2+= s3
		return s2
	}
	
	def processPost(EntityAchieveCondition eac) {
		var s1 = step1(eac)
		var s4 = step4(eac)
		//var s5 = step5(eac)
		return s1+s4
	}
	
	//ENTITY_ACHIEVE_CONDITIONS & ATTRIBUTE_ACHIEVE_CONDITIONS
	def step1(EntityAchieveCondition eac) {
		var s = new StringBuilder()
		//addInPosts(eac.name, Type.Ent)
		
		s.append('DEF(' + eac.name + ')')
		for (aList : resource.allContents.toIterable.filter(typeof(AttributeAchieveCondition))) {
			var found = false
			var def = new StringBuilder()
			var j = 1
			for (a : aList.conditions) {
				var entity = a.substring(0, a.indexOf('.'))
				if (j==1) {
					def.append(a)
					j++
				}
				else def.append(', ' + a)
				if (eac.name == entity) {
					found = true					
				}
			}
			if (found) {
				//addInPosts(def.toString, Type.Att)
				//this.nPre.set(i, nPre.get(i)+1)
				s.append(', DEF(' + def + ')')
			}
		}
		s.toString
	}
	
	//ENTITY_DEPENDENCE_CONDITION & ATTRIBUTE_DEPENDENCE_CONDITION
	def step2(EntityAchieveCondition eac) {
		var s = new StringBuilder()
		var j = 1
		for (r : resource.allContents.toIterable.filter(typeof(EntityDependenceCondition))) {
			if (eac.name == r.entity1) {
				//this.nPre.set(i, nPre.get(i)+1)
				if (j==1) {
					s.append('DEF(' + r.entity2 + ')')
					j++
				}
				else s.append(', DEF(' + r.entity2 + ')')
			}
		}
		
		for (aList : resource.allContents.toIterable.filter(typeof(AttributeDependenceCondition))) {
			//var entity2 = aList.attribute2.substring(0, aList.attribute2.indexOf('.'))
			var founds = new ArrayList<String>()
			for (a : aList.attributes1) {
				var entity1 = a.substring(0, a.indexOf('.'))
				if (eac.name.equals(entity1)) {
					if (!founds.contains(entity1)) {
						founds.add(entity1)
						if (j==1) {
							s.append('DEF(' + aList.attribute2 + ')')
							j++
						}
						else {
							s.append(', DEF(' + aList.attribute2 +')')
						}	
					}	
				}
			}
		}
		return s.toString
	}
	
	/*def step3() {
		var s = new StringBuilder()
		var j = 1
		//if (i==1) addInPosts("Data.episode", Type.Att)
		for (a : postsAtt.get(i)) {
			var entity = a.substring(0, a.indexOf('.'))
			if (!isInPosts(entity, Type.Att)) {
				if (j==1) {
					s.append('PRE(' + entity  + ')')
					j++
				}
				else s.append(', PRE(' + entity  + ')')
			}
		}
		return s.toString
	}*/
	
	//ENTITY_INVARIANT_CONDITION & ATTRIBUTE_INVARIANT_CONDITION
	def step4(EntityAchieveCondition eac) {
		var s = new StringBuilder()		
		for (r : resource.allContents.toIterable.filter(typeof(EntityInvariantCondition))) {
			var entity = r.name.substring(0, r.name.indexOf('.'))
			if (eac.name == entity) {
				//addInPosts(r.name, Type.Att)
				s.append(', MUL(' + r.name + ', ' + r.cardinality + ')')
			}
		}
		
		for (a : resource.allContents.toIterable.filter(typeof(AttributeInvariantCondition))) {
			var founds = new ArrayList<String>()
			for (e : ConditionGeneratorGoalModel.getDecomposedExpression(a.expression)) {
				var entity1 = e.substring(0, e.indexOf('.'))
				if (eac.name.equals(entity1)) {
					if (!founds.contains(entity1)) {
						founds.add(entity1)
						s.append(', RUL(' + ConditionGeneratorGoalModel.getCompleteExpression(a.expression) + ')')	
					}	
				}
			}
		}
		return s.toString
	}
	
	
	/*def addInPosts(String s, Type type) {
		switch (type) {
			case Ent: {
				var t = postsEnt.get(i)
				t.add(s)
				postsEnt.set(i, t)		
			} 
			case Att: {
				var t = postsAtt.get(i)
				t.add(s)
				postsAtt.set(i, t)		
			}
		}
	}
	
	def isInPosts(String s, Type type) {
		switch (type) {
			case Ent: {
				var t = postsEnt.get(i)
				return t.contains(s)
			} 
			case Att: {
				var t = postsAtt.get(i)		
				return t.contains(s)
			}
		}
	}*/
	
	enum Type {
		Ent,
		Att
	}

}