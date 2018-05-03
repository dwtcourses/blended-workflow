package pt.ist.socialsoftware.blendedworkflow.resources.domain;

import pt.ist.socialsoftware.blendedworkflow.core.domain.Product;

import java.util.List;

public class RALExprHistoryExecuting extends RALExprHistoryExecuting_Base {

    public RALExprHistoryExecuting(ResourceModel resourceModel, Quantifier quantifier, Product dataField) {
        setResourceModel(resourceModel);
        setQuantifier(quantifier);
        setDataField(dataField);
    }

    @Override
    public List<Person> getEligibleResources() {
        return null;
    }

}