package pt.ist.socialsoftware.blendedworkflow.adapters;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.util.JDOMUtil;
import org.yawlfoundation.yawl.worklet.rdr.RdrNode;
import org.yawlfoundation.yawl.worklet.rdr.RuleType;

import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;

public class ProcessItemLevelExceptionEvent implements Callable<String> {

	private static Logger log = Logger.getLogger("ItemLevelExceptionEventTask");

	private WorkItemRecord wir;
//	private Element caseData;
	private RdrNode rdrNode;
	private RuleType ruleType;

	public ProcessItemLevelExceptionEvent(WorkItemRecord wir, Element caseData, RdrNode rdrNode, RuleType ruleType) {
		this.wir = wir;
//		this.caseData = caseData;
		this.rdrNode = rdrNode;
		this.ruleType = ruleType;			
	}

	@Override
	public String call() throws Exception {
		log.info("Start for WorkItemRecord: " + wir + " RT:" + ruleType + " Result:" + parseConclusion(rdrNode));

		Transaction.begin();
		if (ruleType.equals(RuleType.ItemPreconstraint)) {
			if (parseConclusion(rdrNode).equals("TRUE")) {
				BlendedWorkflow.getInstance().getWorkletAdapter().notifyNewTaskWorkItem(wir, "TRUE");
			} else if (parseConclusion(rdrNode).equals("FALSE")) {
				BlendedWorkflow.getInstance().getWorkletAdapter().notifyNewTaskWorkItem(wir, "FALSE");
			} else if (parseConclusion(rdrNode).equals("SKIPPED")) {
				BlendedWorkflow.getInstance().getWorkletAdapter().notifyNewTaskWorkItem(wir, "SKIPPED");
			} else {
				log.error(ruleType + " for wir: " + wir + " failed.");
			}
		} else if (ruleType.equals(RuleType.ItemConstraintViolation)) {
			if (parseConclusion(rdrNode).equals("TRUE")) {
				BlendedWorkflow.getInstance().getWorkletAdapter().notifyConstraintViolationResult(wir, null, "TRUE");
			} else if (parseConclusion(rdrNode).equals("FALSE")) {
				BlendedWorkflow.getInstance().getWorkletAdapter().notifyConstraintViolationResult(wir, null, "FALSE");
			} else if (parseConclusion(rdrNode).equals("SKIPPED")) {
				BlendedWorkflow.getInstance().getWorkletAdapter().notifyConstraintViolationResult(wir, null, "SKIPPED");
			} else {
				log.error(ruleType + " for wir: " + wir + " failed.");
			}
		}
		Transaction.commit();

		log.info("End for WorkItemRecord: " + wir);
		return "ItemLevelExceptionEventTask:Sucess";
	}

	/**
	 * Parse the RdrConclusion.
	 * @param rdrNode the enabled RdrNode.
	 * @return a string with the result.
	 */
	public String parseConclusion(RdrNode rdrNode) {
		String conclusion = JDOMUtil.elementToString(rdrNode.getConclusion());
		if (conclusion.contains("SKIPPED"))
			return "SKIPPED";
		else if (conclusion.contains("FALSE") || conclusion.contains("UNDEFINED"))
			return "FALSE";
		else if (conclusion.contains("complete") || conclusion.contains("TRUE"))
			return "TRUE";
		else
			return "FAIL";
	}

}