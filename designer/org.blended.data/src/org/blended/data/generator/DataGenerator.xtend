/*
 * generated by Xtext
 */
package org.blended.data.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IFileSystemAccess
//import org.blended.data.terminal.ConsoleManagement
//import org.blended.data.terminal.DataListener
//import static extension org.eclipse.xtext.EcoreUtil2.*
/*import org.blended.condition.ConditionStandaloneSetup
import org.eclipse.xtext.util.StringInputStream
import org.blended.condition.condition.ConditionModel
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.SaveOptions
*/

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class DataGenerator implements IGenerator {
	
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {			
		var conditionModel = new DataGeneratorConditionModel(resource, fsa)
		conditionModel.doGenerate	
		
	}
}
