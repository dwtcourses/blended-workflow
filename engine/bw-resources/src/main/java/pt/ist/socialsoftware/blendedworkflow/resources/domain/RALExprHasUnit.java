package pt.ist.socialsoftware.blendedworkflow.resources.domain;

import java.util.List;

public class RALExprHasUnit extends RALExprHasUnit_Base {
    
    public RALExprHasUnit(ResourceModel resourceModel, Unit unit) {
        setResourceModel(resourceModel);
        setUnit(unit);
    }

    @Override
    public void delete() {
        setUnit(null);
        super.delete();
    }

    @Override
    public List<Person> getEligibleResources() {
        return null;
    }

}
