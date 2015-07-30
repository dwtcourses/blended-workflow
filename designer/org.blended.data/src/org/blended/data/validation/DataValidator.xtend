/*
 * generated by Xtext
 */
package org.blended.data.validation

import org.blended.common.common.Attribute
import org.blended.common.common.CommonPackage
import org.blended.data.data.DataModel
import org.blended.data.data.DataPackage
import org.blended.data.repository.DataInterface
import org.eclipse.xtext.validation.Check

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.eclipse.xtext.validation.CheckType

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class DataValidator extends AbstractDataValidator {
	public static val INVALID_NAME = 'invalidName'

	@Check
	def checkModel(Attribute att) {
		if ((!att.type.equals("String")) && (!att.type.equals("Boolean")) && (!att.type.equals("Number")) &&
			(!att.type.equals("Date"))) {
			error("Invalid Data Type", CommonPackage.Literals.ATTRIBUTE__TYPE)
		}
	}

	@Check(CheckType.NORMAL)
	def checkModel(DataModel model) {
				info('everything OK 0', DataPackage.Literals.DATA_MODEL__SPECIFICATION)
		var instance = DataInterface.getInstance
				info('everything OK 2', DataPackage.Literals.DATA_MODEL__SPECIFICATION)
		var specId = model.eResource.normalizedURI.lastSegment.split("\\.").get(0)
				info('everything OK 3', DataPackage.Literals.DATA_MODEL__SPECIFICATION)
		var notification = instance.loadDataModel(specId, model)
				info('everything OK 4', DataPackage.Literals.DATA_MODEL__SPECIFICATION)
		if (notification.hasErrors)
			for (error : notification.error)
				error(error.type.toString + "-" + error.value, DataPackage.Literals.DATA_MODEL__SPECIFICATION,
					INVALID_NAME)
		else
			info('everything OK 2', DataPackage.Literals.DATA_MODEL__SPECIFICATION)
	}

}
