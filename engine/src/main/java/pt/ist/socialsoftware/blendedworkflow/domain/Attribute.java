package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.Set;

import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;

public abstract class Attribute extends Attribute_Base {

    @Override
    public void setName(String name) {
        checkName(name);
        super.setName(name);
    }

    private void checkName(String name) {
        if ((name == null) || name.equals("")) {
            throw new BWException(BWErrorType.INVALID_ATTRIBUTE_NAME, name);
        }

        if (getEntity().getAttributeSet().stream()
                .anyMatch(att -> (att != this) && att.getName().equals(name)))
            throw new BWException(BWErrorType.INVALID_ATTRIBUTE_NAME, name);
    }

    public abstract Set<AttributeBasic> getAttributeBasicSet();

}