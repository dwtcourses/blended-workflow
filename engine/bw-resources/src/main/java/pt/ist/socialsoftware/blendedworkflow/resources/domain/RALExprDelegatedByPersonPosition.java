package pt.ist.socialsoftware.blendedworkflow.resources.domain;

import pt.ist.socialsoftware.blendedworkflow.core.domain.Product;
import pt.ist.socialsoftware.blendedworkflow.core.domain.WorkflowInstance;
import pt.ist.socialsoftware.blendedworkflow.resources.service.dto.domain.PositionDto;
import pt.ist.socialsoftware.blendedworkflow.resources.service.dto.domain.SetOfRequiredResources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RALExprDelegatedByPersonPosition extends RALExprDelegatedByPersonPosition_Base {

    public RALExprDelegatedByPersonPosition(ResourceModel resourceModel, RALPersonExpression personExpression) {
        setPersonExpr(personExpression);
        setResourceModel(resourceModel);
    }

    @Override
    public void delete() {
        setPersonExpr(null);
        super.delete();
    }

    @Override
    public List<Person> getEligibleResources(WorkflowInstance history, Set<Product> defProducts) {
        List<Position> positions = new ArrayList();
        getPersonExpr().getEligibleResources(history, defProducts).stream()
                .forEach(person -> person.getPositionSet().stream().forEach(position -> {
                    positions.addAll(position.getWorkDelegatedBySet());
                }));

        return getPersonSet().stream()
                .filter(person -> person.getPositionSet().stream().anyMatch(position -> positions.contains(position)))
                .collect(Collectors.toList());
    }

    @Override
    public SetOfRequiredResources getSetOfRequiredResources() {
        List<PositionDto> positions = null;
        if (getPersonExpr() instanceof RALExprIsPerson) {
            Person person = ((RALExprIsPerson) getPersonExpr()).getPerson();

            positions = person.getPositionSet().stream()
                    .map(Position::getWorkDelegatedBySet)
                    .flatMap(Collection::stream)
                    .map(Position::getDTO)
                    .collect(Collectors.toList());
        }

        return getPersonExpr().getSetOfRequiredResources().addPositions(positions);
    }

    @Override
    public void isMergable(RALExpression expression) {
        getPersonExpr().isMergable(expression);
    }

}
