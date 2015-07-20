/*
 * generated by Xtext
 */
package org.blended.goal.formatting

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import org.eclipse.xtext.formatting.impl.FormattingConfig
import org.blended.goal.services.GoalGrammarAccess
import org.eclipse.xtext.Keyword

// import com.google.inject.Inject;
// import org.blended.goal.services.GoalGrammarAccess

/**
 * This class contains custom formatting declarations.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#formatting
 * on how and when to use it.
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
class GoalFormatter extends AbstractDeclarativeFormatter {

//	@Inject extension GoalGrammarAccess
	
	override protected void configureFormatting(FormattingConfig c) {
		var GoalGrammarAccess f = getGrammarAccess() as GoalGrammarAccess;
		c.autoLinewrap = 200
			
		for (Keyword key : f.findKeywords("(")) {
			c.setNoSpace().before(key)
			c.setNoSpace().after(key)
		}
		
		for (Keyword key : f.findKeywords(")", ":")) {
			c.setNoSpace().before(key)
		}
			
		for (Keyword key : f.findKeywords(",")) {
			c.setNoSpace().before(key)
		}
		
		c.setLinewrap().after(f.goalRule)
		c.setLinewrap(2).after(f.specificationRule)
	}
}