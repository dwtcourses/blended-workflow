/*
 * generated by Xtext
 */
package org.blended.goal.services;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.util.List;

import org.eclipse.xtext.*;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.service.AbstractElementFinder.*;

import org.blended.services.BlendedGrammarAccess;

@Singleton
public class GoalGrammarAccess extends AbstractGrammarElementFinder {
	
	
	public class GoalModelElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "GoalModel");
		private final Assignment cGoalsAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cGoalsGoalParserRuleCall_0 = (RuleCall)cGoalsAssignment.eContents().get(0);
		
		////import "http://www.blended.org/condition/Condition" as def
		//GoalModel:
		//	goals+=Goal+;
		@Override public ParserRule getRule() { return rule; }

		//goals+=Goal+
		public Assignment getGoalsAssignment() { return cGoalsAssignment; }

		//Goal
		public RuleCall getGoalsGoalParserRuleCall_0() { return cGoalsGoalParserRuleCall_0; }
	}
	
	
	private final GoalModelElements pGoalModel;
	
	private final Grammar grammar;

	private final BlendedGrammarAccess gaBlended;

	@Inject
	public GoalGrammarAccess(GrammarProvider grammarProvider,
		BlendedGrammarAccess gaBlended) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaBlended = gaBlended;
		this.pGoalModel = new GoalModelElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.blended.goal.Goal".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	

	public BlendedGrammarAccess getBlendedGrammarAccess() {
		return gaBlended;
	}

	
	////import "http://www.blended.org/condition/Condition" as def
	//GoalModel:
	//	goals+=Goal+;
	public GoalModelElements getGoalModelAccess() {
		return pGoalModel;
	}
	
	public ParserRule getGoalModelRule() {
		return getGoalModelAccess().getRule();
	}

	//BlendedModel:
	//	Condition | Goal | Activity;
	public BlendedGrammarAccess.BlendedModelElements getBlendedModelAccess() {
		return gaBlended.getBlendedModelAccess();
	}
	
	public ParserRule getBlendedModelRule() {
		return getBlendedModelAccess().getRule();
	}

	//EntityAchieveCondition:
	//	"DEF" "(" name=ID ")";
	public BlendedGrammarAccess.EntityAchieveConditionElements getEntityAchieveConditionAccess() {
		return gaBlended.getEntityAchieveConditionAccess();
	}
	
	public ParserRule getEntityAchieveConditionRule() {
		return getEntityAchieveConditionAccess().getRule();
	}

	//EntityAchieveConditionExist:
	//	"EXISTS" "(" "DEF" "(" name=ID ")" ")";
	public BlendedGrammarAccess.EntityAchieveConditionExistElements getEntityAchieveConditionExistAccess() {
		return gaBlended.getEntityAchieveConditionExistAccess();
	}
	
	public ParserRule getEntityAchieveConditionExistRule() {
		return getEntityAchieveConditionExistAccess().getRule();
	}

	//EntityInvariantCondition:
	//	"MUL" "(" name=Attribute "," cardinality=Cardinality ")";
	public BlendedGrammarAccess.EntityInvariantConditionElements getEntityInvariantConditionAccess() {
		return gaBlended.getEntityInvariantConditionAccess();
	}
	
	public ParserRule getEntityInvariantConditionRule() {
		return getEntityInvariantConditionAccess().getRule();
	}

	//EntityDependenceCondition:
	//	"DEP" "(" "DEF" "(" entity1=ID ")" "," "DEF" "(" entity2=ID ")" ")";
	public BlendedGrammarAccess.EntityDependenceConditionElements getEntityDependenceConditionAccess() {
		return gaBlended.getEntityDependenceConditionAccess();
	}
	
	public ParserRule getEntityDependenceConditionRule() {
		return getEntityDependenceConditionAccess().getRule();
	}

	//AttributeAchieveCondition:
	//	MandatoryAttributeAchieveCondition | NotMandatoryAttributeAchieveCondition;
	public BlendedGrammarAccess.AttributeAchieveConditionElements getAttributeAchieveConditionAccess() {
		return gaBlended.getAttributeAchieveConditionAccess();
	}
	
	public ParserRule getAttributeAchieveConditionRule() {
		return getAttributeAchieveConditionAccess().getRule();
	}

	//NotMandatoryAttributeAchieveCondition:
	//	"DEF" "(" conditions+=Attribute ("," conditions+=Attribute)* ")";
	public BlendedGrammarAccess.NotMandatoryAttributeAchieveConditionElements getNotMandatoryAttributeAchieveConditionAccess() {
		return gaBlended.getNotMandatoryAttributeAchieveConditionAccess();
	}
	
	public ParserRule getNotMandatoryAttributeAchieveConditionRule() {
		return getNotMandatoryAttributeAchieveConditionAccess().getRule();
	}

	//MandatoryAttributeAchieveCondition:
	//	"MAN" "(" "DEF" "(" conditions+=Attribute ("," conditions+=Attribute)* ")" ")";
	public BlendedGrammarAccess.MandatoryAttributeAchieveConditionElements getMandatoryAttributeAchieveConditionAccess() {
		return gaBlended.getMandatoryAttributeAchieveConditionAccess();
	}
	
	public ParserRule getMandatoryAttributeAchieveConditionRule() {
		return getMandatoryAttributeAchieveConditionAccess().getRule();
	}

	//AttributeInvariantCondition:
	//	"RUL" "(" expression=Expression ")";
	public BlendedGrammarAccess.AttributeInvariantConditionElements getAttributeInvariantConditionAccess() {
		return gaBlended.getAttributeInvariantConditionAccess();
	}
	
	public ParserRule getAttributeInvariantConditionRule() {
		return getAttributeInvariantConditionAccess().getRule();
	}

	//AttributeDependenceCondition:
	//	"DEP" "(" "DEF" "(" attributes1+=Attribute ("," attributes1+=Attribute)* ")" "," "DEF" "(" attribute2=Attribute ")"
	//	")";
	public BlendedGrammarAccess.AttributeDependenceConditionElements getAttributeDependenceConditionAccess() {
		return gaBlended.getAttributeDependenceConditionAccess();
	}
	
	public ParserRule getAttributeDependenceConditionRule() {
		return getAttributeDependenceConditionAccess().getRule();
	}

	//Cardinality:
	//	INT | INT ".." INT | INT ".." "*" | INT ".." "+";
	public BlendedGrammarAccess.CardinalityElements getCardinalityAccess() {
		return gaBlended.getCardinalityAccess();
	}
	
	public ParserRule getCardinalityRule() {
		return getCardinalityAccess().getRule();
	}

	//Attribute:
	//	ID "." ID ("." ID)*;
	public BlendedGrammarAccess.AttributeElements getAttributeAccess() {
		return gaBlended.getAttributeAccess();
	}
	
	public ParserRule getAttributeRule() {
		return getAttributeAccess().getRule();
	}

	//Nothing:
	//	name="nothing";
	public BlendedGrammarAccess.NothingElements getNothingAccess() {
		return gaBlended.getNothingAccess();
	}
	
	public ParserRule getNothingRule() {
		return getNothingAccess().getRule();
	}

	//Expression:
	//	Or;
	public BlendedGrammarAccess.ExpressionElements getExpressionAccess() {
		return gaBlended.getExpressionAccess();
	}
	
	public ParserRule getExpressionRule() {
		return getExpressionAccess().getRule();
	}

	//Or returns Expression:
	//	And ({Or.left=current} "OR" right=And)*;
	public BlendedGrammarAccess.OrElements getOrAccess() {
		return gaBlended.getOrAccess();
	}
	
	public ParserRule getOrRule() {
		return getOrAccess().getRule();
	}

	//And returns Expression:
	//	Primary ({And.left=current} "AND" right=Primary)*;
	public BlendedGrammarAccess.AndElements getAndAccess() {
		return gaBlended.getAndAccess();
	}
	
	public ParserRule getAndRule() {
		return getAndAccess().getRule();
	}

	//Primary returns Expression:
	//	"(" Expression ")" | {Not} "NOT" expression=Primary | Atomic;
	public BlendedGrammarAccess.PrimaryElements getPrimaryAccess() {
		return gaBlended.getPrimaryAccess();
	}
	
	public ParserRule getPrimaryRule() {
		return getPrimaryAccess().getRule();
	}

	//Atomic returns Expression:
	//	{AttributeDefinition} "DEF" "(" name=Attribute ")" | {AttributeValue} name=Attribute;
	public BlendedGrammarAccess.AtomicElements getAtomicAccess() {
		return gaBlended.getAtomicAccess();
	}
	
	public ParserRule getAtomicRule() {
		return getAtomicAccess().getRule();
	}

	//// ********CONDITION MODEL
	//Condition:
	//	{Condition} ("ENTITY_ACHIEVE_CONDITIONS" entityAchieveConditions+=(EntityAchieveCondition |
	//	EntityAchieveConditionExist)*)? ("ENTITY_INVARIANT_CONDITIONS" entityInvariantConditions+=EntityInvariantCondition*)?
	//	("ENTITY_DEPENDENCE_CONDITIONS" entityDependenceConditions+=EntityDependenceCondition*)?
	//	("ATTRIBUTE_ACHIEVE_CONDITIONS" attributeAchieveConditions+=AttributeAchieveCondition*)?
	//	("ATTRIBUTE_INVARIANT_CONDITIONS" attributeInvariantConditions+=AttributeInvariantCondition*)?
	//	("ATTRIBUTE_DEPENDENCE_CONDITIONS" attributeDependenceConditions+=AttributeDependenceCondition*)?;
	public BlendedGrammarAccess.ConditionElements getConditionAccess() {
		return gaBlended.getConditionAccess();
	}
	
	public ParserRule getConditionRule() {
		return getConditionAccess().getRule();
	}

	//// ********GOAL MODEL
	//Goal:
	//	name=ID ":" "SUC" "(" sucessCondition=(EntityAchieveCondition | AttributeAchieveCondition | Nothing) ")" ("," "ACT"
	//	"(" activationCondition=(EntityAchieveCondition | AttributeAchieveCondition) ")")? ("," "INV" "("
	//	invariantConditions+=(EntityInvariantCondition | AttributeInvariantCondition) (","
	//	invariantConditions+=(EntityInvariantCondition | AttributeInvariantCondition))* ")")? ("," "SUB" "("
	//	childrenGoals+=[Goal] ("," childrenGoals+=[Goal])* ")")?;
	public BlendedGrammarAccess.GoalElements getGoalAccess() {
		return gaBlended.getGoalAccess();
	}
	
	public ParserRule getGoalRule() {
		return getGoalAccess().getRule();
	}

	//// ********ACTIVITY MODEL
	//Activity:
	//	name=ID ":" description=STRING "PRE" "(" (pre+=(EntityAchieveCondition | NotMandatoryAttributeAchieveCondition) (","
	//	pre+=(EntityAchieveCondition | NotMandatoryAttributeAchieveCondition))*)? ")" "," "POST" "("
	//	(post+=(EntityAchieveCondition | NotMandatoryAttributeAchieveCondition | EntityInvariantCondition |
	//	AttributeInvariantCondition) ("," post+=(EntityAchieveCondition | NotMandatoryAttributeAchieveCondition |
	//	EntityInvariantCondition | AttributeInvariantCondition))*)? ")";
	public BlendedGrammarAccess.ActivityElements getActivityAccess() {
		return gaBlended.getActivityAccess();
	}
	
	public ParserRule getActivityRule() {
		return getActivityAccess().getRule();
	}

	//terminal ID:
	//	"^"? ("a".."z" | "A".."Z" | "_") ("a".."z" | "A".."Z" | "_" | "0".."9")*;
	public TerminalRule getIDRule() {
		return gaBlended.getIDRule();
	} 

	//terminal INT returns ecore::EInt:
	//	"0".."9"+;
	public TerminalRule getINTRule() {
		return gaBlended.getINTRule();
	} 

	//terminal STRING:
	//	"\"" ("\\" . / * 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' * / | !("\\" | "\""))* "\"" | "\'" ("\\" .
	//	/ * 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' * / | !("\\" | "\'"))* "\'";
	public TerminalRule getSTRINGRule() {
		return gaBlended.getSTRINGRule();
	} 

	//terminal ML_COMMENT:
	//	"/ *"->"* /";
	public TerminalRule getML_COMMENTRule() {
		return gaBlended.getML_COMMENTRule();
	} 

	//terminal SL_COMMENT:
	//	"//" !("\n" | "\r")* ("\r"? "\n")?;
	public TerminalRule getSL_COMMENTRule() {
		return gaBlended.getSL_COMMENTRule();
	} 

	//terminal WS:
	//	(" " | "\t" | "\r" | "\n")+;
	public TerminalRule getWSRule() {
		return gaBlended.getWSRule();
	} 

	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaBlended.getANY_OTHERRule();
	} 
}