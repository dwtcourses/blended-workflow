/*
 * generated by Xtext
 */
grammar InternalData;

options {
	superClass=AbstractInternalAntlrParser;
	
}

@lexer::header {
package org.blended.data.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.blended.data.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.blended.data.services.DataGrammarAccess;

}

@parser::members {

 	private DataGrammarAccess grammarAccess;
 	
    public InternalDataParser(TokenStream input, DataGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "DataModel";	
   	}
   	
   	@Override
   	protected DataGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuleDataModel
entryRuleDataModel returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getDataModelRule()); }
	 iv_ruleDataModel=ruleDataModel 
	 { $current=$iv_ruleDataModel.current; } 
	 EOF 
;

// Rule DataModel
ruleDataModel returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{ 
	        newCompositeNode(grammarAccess.getDataModelAccess().getEntitiesEntityParserRuleCall_0_0()); 
	    }
		lv_entities_0_0=ruleEntity		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getDataModelRule());
	        }
       		add(
       			$current, 
       			"entities",
        		lv_entities_0_0, 
        		"Entity");
	        afterParserOrEnumRuleCall();
	    }

)
)*(
(
		{ 
	        newCompositeNode(grammarAccess.getDataModelAccess().getAssociatiosAssociationParserRuleCall_1_0()); 
	    }
		lv_associatios_1_0=ruleAssociation		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getDataModelRule());
	        }
       		add(
       			$current, 
       			"associatios",
        		lv_associatios_1_0, 
        		"Association");
	        afterParserOrEnumRuleCall();
	    }

)
)*(
(
		{ 
	        newCompositeNode(grammarAccess.getDataModelAccess().getConstraintConstraintParserRuleCall_2_0()); 
	    }
		lv_constraint_2_0=ruleConstraint		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getDataModelRule());
	        }
       		add(
       			$current, 
       			"constraint",
        		lv_constraint_2_0, 
        		"Constraint");
	        afterParserOrEnumRuleCall();
	    }

)
)*)
;





// Entry rule entryRuleEntity
entryRuleEntity returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEntityRule()); }
	 iv_ruleEntity=ruleEntity 
	 { $current=$iv_ruleEntity.current; } 
	 EOF 
;

// Rule Entity
ruleEntity returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='Entity' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getEntityAccess().getEntityKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEntityRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)(
(
		lv_exists_2_0=	'exists' 
    {
        newLeafNode(lv_exists_2_0, grammarAccess.getEntityAccess().getExistsExistsKeyword_2_0());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEntityRule());
	        }
       		setWithLastConsumed($current, "exists", true, "exists");
	    }

)
)?(	otherlv_3='dependsOn' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getEntityAccess().getDependsOnKeyword_3_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getEntityRule());
	        }
        }
	otherlv_4=RULE_ID
	{
		newLeafNode(otherlv_4, grammarAccess.getEntityAccess().getDependsOnEntityCrossReference_3_1_0()); 
	}

)
))?	otherlv_5='{' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getEntityAccess().getLeftCurlyBracketKeyword_4());
    }
(
(
(
		{ 
	        newCompositeNode(grammarAccess.getEntityAccess().getAttributesAttributeGroupParserRuleCall_5_0_0()); 
	    }
		lv_attributes_6_1=ruleAttributeGroup		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getEntityRule());
	        }
       		add(
       			$current, 
       			"attributes",
        		lv_attributes_6_1, 
        		"AttributeGroup");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getEntityAccess().getAttributesAttributeParserRuleCall_5_0_1()); 
	    }
		lv_attributes_6_2=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getEntityRule());
	        }
       		add(
       			$current, 
       			"attributes",
        		lv_attributes_6_2, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)

)
)*	otherlv_7='}' 
    {
    	newLeafNode(otherlv_7, grammarAccess.getEntityAccess().getRightCurlyBracketKeyword_6());
    }
)
;





// Entry rule entryRuleAttribute
entryRuleAttribute returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAttributeRule()); }
	 iv_ruleAttribute=ruleAttribute 
	 { $current=$iv_ruleAttribute.current; } 
	 EOF 
;

// Rule Attribute
ruleAttribute returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{ 
	        newCompositeNode(grammarAccess.getAttributeAccess().getNameQualifiedNameParserRuleCall_0_0()); 
	    }
		lv_name_0_0=ruleQualifiedName		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAttributeRule());
	        }
       		set(
       			$current, 
       			"name",
        		lv_name_0_0, 
        		"QualifiedName");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_1=':' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getAttributeAccess().getColonKeyword_1());
    }
(
(
		lv_type_2_0=RULE_ID
		{
			newLeafNode(lv_type_2_0, grammarAccess.getAttributeAccess().getTypeIDTerminalRuleCall_2_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getAttributeRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"type",
        		lv_type_2_0, 
        		"ID");
	    }

)
)(
(
		lv_mandatory_3_0=	'mandatory' 
    {
        newLeafNode(lv_mandatory_3_0, grammarAccess.getAttributeAccess().getMandatoryMandatoryKeyword_3_0());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getAttributeRule());
	        }
       		setWithLastConsumed($current, "mandatory", true, "mandatory");
	    }

)
)?(	otherlv_4='dependsOn' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getAttributeAccess().getDependsOnKeyword_4_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getAttributeRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getAttributeAccess().getDependsOnAttributeCrossReference_4_1_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
)(	otherlv_6=',' 
    {
    	newLeafNode(otherlv_6, grammarAccess.getAttributeAccess().getCommaKeyword_4_2_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getAttributeRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getAttributeAccess().getDependsOnAttributeCrossReference_4_2_1_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
))*)?)
;





// Entry rule entryRuleAttributeGroup
entryRuleAttributeGroup returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAttributeGroupRule()); }
	 iv_ruleAttributeGroup=ruleAttributeGroup 
	 { $current=$iv_ruleAttributeGroup.current; } 
	 EOF 
;

// Rule AttributeGroup
ruleAttributeGroup returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='group' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getAttributeGroupAccess().getGroupKeyword_0());
    }
(
(
		lv_mandatory_1_0=	'mandatory' 
    {
        newLeafNode(lv_mandatory_1_0, grammarAccess.getAttributeGroupAccess().getMandatoryMandatoryKeyword_1_0());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getAttributeGroupRule());
	        }
       		setWithLastConsumed($current, "mandatory", true, "mandatory");
	    }

)
)?(	otherlv_2='dependsOn' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getAttributeGroupAccess().getDependsOnKeyword_2_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getAttributeGroupRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getAttributeGroupAccess().getDependsOnAttributeCrossReference_2_1_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
)(	otherlv_4=',' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getAttributeGroupAccess().getCommaKeyword_2_2_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getAttributeGroupRule());
	        }
        }
		{ 
	        newCompositeNode(grammarAccess.getAttributeGroupAccess().getDependsOnAttributeCrossReference_2_2_1_0()); 
	    }
		ruleQualifiedName		{ 
	        afterParserOrEnumRuleCall();
	    }

)
))*)?	otherlv_6='{' 
    {
    	newLeafNode(otherlv_6, grammarAccess.getAttributeGroupAccess().getLeftCurlyBracketKeyword_3());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAttributeGroupAccess().getAttributesAttributeParserRuleCall_4_0()); 
	    }
		lv_attributes_7_0=ruleAttribute		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAttributeGroupRule());
	        }
       		add(
       			$current, 
       			"attributes",
        		lv_attributes_7_0, 
        		"Attribute");
	        afterParserOrEnumRuleCall();
	    }

)
)+	otherlv_8='}' 
    {
    	newLeafNode(otherlv_8, grammarAccess.getAttributeGroupAccess().getRightCurlyBracketKeyword_5());
    }
)
;





// Entry rule entryRuleQualifiedName
entryRuleQualifiedName returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getQualifiedNameRule()); } 
	 iv_ruleQualifiedName=ruleQualifiedName 
	 { $current=$iv_ruleQualifiedName.current.getText(); }  
	 EOF 
;

// Rule QualifiedName
ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(    this_ID_0=RULE_ID    {
		$current.merge(this_ID_0);
    }

    { 
    newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
    }
(
	kw='.' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
    }
    this_ID_2=RULE_ID    {
		$current.merge(this_ID_2);
    }

    { 
    newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
    }
)*)
    ;





// Entry rule entryRuleAssociation
entryRuleAssociation returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAssociationRule()); }
	 iv_ruleAssociation=ruleAssociation 
	 { $current=$iv_ruleAssociation.current; } 
	 EOF 
;

// Rule Association
ruleAssociation returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='Association' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getAssociationAccess().getAssociationKeyword_0());
    }
	otherlv_1='{' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getAssociationAccess().getLeftCurlyBracketKeyword_1());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getAssociationRule());
	        }
        }
	otherlv_2=RULE_ID
	{
		newLeafNode(otherlv_2, grammarAccess.getAssociationAccess().getEntity1EntityCrossReference_2_0()); 
	}

)
)	otherlv_3='with' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getAssociationAccess().getWithKeyword_3());
    }
(
(
		lv_name1_4_0=RULE_ID
		{
			newLeafNode(lv_name1_4_0, grammarAccess.getAssociationAccess().getName1IDTerminalRuleCall_4_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getAssociationRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name1",
        		lv_name1_4_0, 
        		"ID");
	    }

)
)	otherlv_5='(' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getAssociationAccess().getLeftParenthesisKeyword_5());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAssociationAccess().getCardinality1CardinalityParserRuleCall_6_0()); 
	    }
		lv_cardinality1_6_0=ruleCardinality		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAssociationRule());
	        }
       		set(
       			$current, 
       			"cardinality1",
        		lv_cardinality1_6_0, 
        		"Cardinality");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_7=')' 
    {
    	newLeafNode(otherlv_7, grammarAccess.getAssociationAccess().getRightParenthesisKeyword_7());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getAssociationRule());
	        }
        }
	otherlv_8=RULE_ID
	{
		newLeafNode(otherlv_8, grammarAccess.getAssociationAccess().getEntity2EntityCrossReference_8_0()); 
	}

)
)	otherlv_9='with' 
    {
    	newLeafNode(otherlv_9, grammarAccess.getAssociationAccess().getWithKeyword_9());
    }
(
(
		lv_name2_10_0=RULE_ID
		{
			newLeafNode(lv_name2_10_0, grammarAccess.getAssociationAccess().getName2IDTerminalRuleCall_10_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getAssociationRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name2",
        		lv_name2_10_0, 
        		"ID");
	    }

)
)	otherlv_11='(' 
    {
    	newLeafNode(otherlv_11, grammarAccess.getAssociationAccess().getLeftParenthesisKeyword_11());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAssociationAccess().getCardinality2CardinalityParserRuleCall_12_0()); 
	    }
		lv_cardinality2_12_0=ruleCardinality		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAssociationRule());
	        }
       		set(
       			$current, 
       			"cardinality2",
        		lv_cardinality2_12_0, 
        		"Cardinality");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_13=')' 
    {
    	newLeafNode(otherlv_13, grammarAccess.getAssociationAccess().getRightParenthesisKeyword_13());
    }
	otherlv_14='}' 
    {
    	newLeafNode(otherlv_14, grammarAccess.getAssociationAccess().getRightCurlyBracketKeyword_14());
    }
)
;





// Entry rule entryRuleConstraint
entryRuleConstraint returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getConstraintRule()); }
	 iv_ruleConstraint=ruleConstraint 
	 { $current=$iv_ruleConstraint.current; } 
	 EOF 
;

// Rule Constraint
ruleConstraint returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='Constraint' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getConstraintAccess().getConstraintKeyword_0());
    }
	otherlv_1=':' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getConstraintAccess().getColonKeyword_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getConstraintAccess().getConstraintExpressionParserRuleCall_2_0()); 
	    }
		lv_constraint_2_0=ruleExpression		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getConstraintRule());
	        }
       		set(
       			$current, 
       			"constraint",
        		lv_constraint_2_0, 
        		"Expression");
	        afterParserOrEnumRuleCall();
	    }

)
))
;





// Entry rule entryRuleCardinality
entryRuleCardinality returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getCardinalityRule()); } 
	 iv_ruleCardinality=ruleCardinality 
	 { $current=$iv_ruleCardinality.current.getText(); }  
	 EOF 
;

// Rule Cardinality
ruleCardinality returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(    this_INT_0=RULE_INT    {
		$current.merge(this_INT_0);
    }

    { 
    newLeafNode(this_INT_0, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_0()); 
    }

    |(    this_INT_1=RULE_INT    {
		$current.merge(this_INT_1);
    }

    { 
    newLeafNode(this_INT_1, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_1_0()); 
    }

	kw='..' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getFullStopFullStopKeyword_1_1()); 
    }
    this_INT_3=RULE_INT    {
		$current.merge(this_INT_3);
    }

    { 
    newLeafNode(this_INT_3, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_1_2()); 
    }
)
    |(    this_INT_4=RULE_INT    {
		$current.merge(this_INT_4);
    }

    { 
    newLeafNode(this_INT_4, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_2_0()); 
    }

	kw='..' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getFullStopFullStopKeyword_2_1()); 
    }

	kw='*' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getAsteriskKeyword_2_2()); 
    }
)
    |(    this_INT_7=RULE_INT    {
		$current.merge(this_INT_7);
    }

    { 
    newLeafNode(this_INT_7, grammarAccess.getCardinalityAccess().getINTTerminalRuleCall_3_0()); 
    }

	kw='..' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getFullStopFullStopKeyword_3_1()); 
    }

	kw='+' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getCardinalityAccess().getPlusSignKeyword_3_2()); 
    }
))
    ;





// Entry rule entryRuleExpression
entryRuleExpression returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getExpressionRule()); }
	 iv_ruleExpression=ruleExpression 
	 { $current=$iv_ruleExpression.current; } 
	 EOF 
;

// Rule Expression
ruleExpression returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:

    { 
        newCompositeNode(grammarAccess.getExpressionAccess().getOrParserRuleCall()); 
    }
    this_Or_0=ruleOr
    { 
        $current = $this_Or_0.current; 
        afterParserOrEnumRuleCall();
    }

;





// Entry rule entryRuleOr
entryRuleOr returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getOrRule()); }
	 iv_ruleOr=ruleOr 
	 { $current=$iv_ruleOr.current; } 
	 EOF 
;

// Rule Or
ruleOr returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
    }
    this_And_0=ruleAnd
    { 
        $current = $this_And_0.current; 
        afterParserOrEnumRuleCall();
    }
((
    {
        $current = forceCreateModelElementAndSet(
            grammarAccess.getOrAccess().getOrLeftAction_1_0(),
            $current);
    }
)	otherlv_2='OR' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getOrAccess().getORKeyword_1_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
	    }
		lv_right_3_0=ruleAnd		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getOrRule());
	        }
       		set(
       			$current, 
       			"right",
        		lv_right_3_0, 
        		"And");
	        afterParserOrEnumRuleCall();
	    }

)
))*)
;





// Entry rule entryRuleAnd
entryRuleAnd returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAndRule()); }
	 iv_ruleAnd=ruleAnd 
	 { $current=$iv_ruleAnd.current; } 
	 EOF 
;

// Rule And
ruleAnd returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getAndAccess().getPrimaryParserRuleCall_0()); 
    }
    this_Primary_0=rulePrimary
    { 
        $current = $this_Primary_0.current; 
        afterParserOrEnumRuleCall();
    }
((
    {
        $current = forceCreateModelElementAndSet(
            grammarAccess.getAndAccess().getAndLeftAction_1_0(),
            $current);
    }
)	otherlv_2='AND' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getAndAccess().getANDKeyword_1_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAndAccess().getRightPrimaryParserRuleCall_1_2_0()); 
	    }
		lv_right_3_0=rulePrimary		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAndRule());
	        }
       		set(
       			$current, 
       			"right",
        		lv_right_3_0, 
        		"Primary");
	        afterParserOrEnumRuleCall();
	    }

)
))*)
;





// Entry rule entryRulePrimary
entryRulePrimary returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getPrimaryRule()); }
	 iv_rulePrimary=rulePrimary 
	 { $current=$iv_rulePrimary.current; } 
	 EOF 
;

// Rule Primary
rulePrimary returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((	otherlv_0='(' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
    }

    { 
        newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1()); 
    }
    this_Expression_1=ruleExpression
    { 
        $current = $this_Expression_1.current; 
        afterParserOrEnumRuleCall();
    }
	otherlv_2=')' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_2());
    }
)
    |((
    {
        $current = forceCreateModelElement(
            grammarAccess.getPrimaryAccess().getNotAction_1_0(),
            $current);
    }
)	otherlv_4='NOT' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getNOTKeyword_1_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionPrimaryParserRuleCall_1_2_0()); 
	    }
		lv_expression_5_0=rulePrimary		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getPrimaryRule());
	        }
       		set(
       			$current, 
       			"expression",
        		lv_expression_5_0, 
        		"Primary");
	        afterParserOrEnumRuleCall();
	    }

)
))
    |
    { 
        newCompositeNode(grammarAccess.getPrimaryAccess().getAtomicParserRuleCall_2()); 
    }
    this_Atomic_6=ruleAtomic
    { 
        $current = $this_Atomic_6.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleAtomic
entryRuleAtomic returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAtomicRule()); }
	 iv_ruleAtomic=ruleAtomic 
	 { $current=$iv_ruleAtomic.current; } 
	 EOF 
;

// Rule Atomic
ruleAtomic returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(((
    {
        $current = forceCreateModelElement(
            grammarAccess.getAtomicAccess().getAttributeDefinitionAction_0_0(),
            $current);
    }
)	otherlv_1='DEF' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getAtomicAccess().getDEFKeyword_0_1());
    }
	otherlv_2='(' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getAtomicAccess().getLeftParenthesisKeyword_0_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAtomicAccess().getNameQualifiedNameParserRuleCall_0_3_0()); 
	    }
		lv_name_3_0=ruleQualifiedName		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAtomicRule());
	        }
       		set(
       			$current, 
       			"name",
        		lv_name_3_0, 
        		"QualifiedName");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_4=')' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getAtomicAccess().getRightParenthesisKeyword_0_4());
    }
)
    |((
    {
        $current = forceCreateModelElement(
            grammarAccess.getAtomicAccess().getAttributeValueAction_1_0(),
            $current);
    }
)(
(
		{ 
	        newCompositeNode(grammarAccess.getAtomicAccess().getNameQualifiedNameParserRuleCall_1_1_0()); 
	    }
		lv_name_6_0=ruleQualifiedName		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAtomicRule());
	        }
       		set(
       			$current, 
       			"name",
        		lv_name_6_0, 
        		"QualifiedName");
	        afterParserOrEnumRuleCall();
	    }

)
)))
;





RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;

