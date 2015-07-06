package pt.ist.socialsoftware.blendedworkflow.domain.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import pt.ist.socialsoftware.blendedworkflow.BWDomainAndServiceTest;
import pt.ist.socialsoftware.blendedworkflow.domain.Attribute;
import pt.ist.socialsoftware.blendedworkflow.domain.Attribute.AttributeType;
import pt.ist.socialsoftware.blendedworkflow.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.domain.Specification;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.service.BWException.BlendedWorkflowError;

public class CreateAttributeMethodTest extends BWDomainAndServiceTest {
    private static String ATT_NAME_ONE = "Attribute name one";
    private static String ATT_NAME_TWO = "Attribute name two";
    private static String ATT_NAME_THREE = "Attribute name three";
    private static String EXISTS_NAME = "Attribute name exist";
    private static String EMPTY_NAME = "";

    private Entity entity = null;

    @Override
    public void populate4Test() throws BWException {
        Specification spec = new Specification("My spec", "author",
                "description", "version", "UID");
        entity = new Entity(spec.getDataModel(), "Entity name");
        new Attribute(spec.getDataModel(), EXISTS_NAME, entity,
                AttributeType.BOOLEAN, false, false);
    }

    @Test
    public void success() {
        Attribute att1 = entity.createAttribute(ATT_NAME_ONE,
                AttributeType.STRING);
        Attribute att2 = entity.createAttribute(ATT_NAME_TWO,
                AttributeType.BOOLEAN);
        Attribute att3 = entity.createAttribute(ATT_NAME_THREE,
                AttributeType.NUMBER);

        assertEquals(4, entity.getAttributesSet().size());
        assertEquals(ATT_NAME_ONE, att1.getName());
        assertEquals(att1, entity.getAttribute(ATT_NAME_ONE));
        assertEquals(att2, entity.getAttribute(ATT_NAME_TWO));
        assertEquals(att3, entity.getAttribute(ATT_NAME_THREE));
        assertEquals(AttributeType.STRING, att1.getType());
        assertEquals(AttributeType.BOOLEAN, att2.getType());
        assertEquals(AttributeType.NUMBER, att3.getType());

    }

    @Test
    public void existsName() {
        try {
            entity.createAttribute(EXISTS_NAME, AttributeType.NUMBER);
            fail("Able to create an attribute with the same name");
        } catch (BWException bwe) {
            assertEquals(BlendedWorkflowError.INVALID_ATTRIBUTE_NAME,
                    bwe.getError());
        }
    }

    @Test
    public void emptyName() {
        try {
            entity.createAttribute(EMPTY_NAME, AttributeType.BOOLEAN);
            fail("Able to create an attribute with empty name");
        } catch (BWException bwe) {
            assertEquals(BlendedWorkflowError.INVALID_ATTRIBUTE_NAME,
                    bwe.getError());
        }
    }

    @Test
    public void nullName() {
        try {
            entity.createAttribute(null, AttributeType.STRING);
            fail("Able to create an attribute with null name");
        } catch (BWException bwe) {
            assertEquals(BlendedWorkflowError.INVALID_ATTRIBUTE_NAME,
                    bwe.getError());
        }
    }

}