/**
 * generated by Xtext
 */
package org.blended.condition.generator;

import org.blended.condition.generator.ConditionGeneratorActivityModel;
import org.blended.condition.generator.ConditionGeneratorGoalModel;
import org.blended.condition.utils.ConditionListener;
import org.blended.utils.ConsoleManagement;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class ConditionGenerator implements IGenerator {
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    URI _normalizedURI = EcoreUtil2.getNormalizedURI(resource);
    String _lastSegment = _normalizedURI.lastSegment();
    String _plus = ((ConsoleManagement.CONDITION_CONSOLE + " (") + _lastSegment);
    String consoleName = (_plus + ")");
    ConditionGeneratorGoalModel goalModel = new ConditionGeneratorGoalModel(resource, fsa);
    goalModel.doGenerate();
    ConditionGeneratorActivityModel activityModel = new ConditionGeneratorActivityModel(resource, fsa);
    activityModel.doGenerate();
    ConditionListener manager = new ConditionListener(consoleName, resource);
    Boolean _isRunning = manager.isRunning();
    boolean _not = (!(_isRunning).booleanValue());
    if (_not) {
      Thread thread = new Thread(manager);
      thread.start();
    }
    URI _normalizedURI_1 = EcoreUtil2.getNormalizedURI(resource);
    String _lastSegment_1 = _normalizedURI_1.lastSegment();
    String _plus_1 = ("CONDITION MODEL " + _lastSegment_1);
    String _plus_2 = (_plus_1 + " UPDATED. TYPE 0 TO SEE THE OPTIONS");
    ConsoleManagement.write(consoleName, _plus_2);
  }
}