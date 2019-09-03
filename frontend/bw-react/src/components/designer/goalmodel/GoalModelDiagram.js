import React from 'react';
import { RepositoryService } from '../../../services/RepositoryService';
import { OperationsMenu, operations } from './OperationsMenu';
import { VisNetwork } from '../../util/VisNetwork';
import { ModalMessage } from '../../util/ModalMessage';
import { Tooltip, OverlayTrigger } from 'react-bootstrap';

const tooltip = (
    <Tooltip id="tooltip">
      Select node for conditions and<br /> double click them apply an operation
    </Tooltip>
);

const options = {
    height: "700",
    layout: {
        hierarchical: false,
    },
    edges: {
        smooth: false,
        color: '#000000',
        width: 0.5,
        arrows: {
          from: {
            enabled: true,
            scaleFactor: 0.5
          }
        }
    },
    nodes: {
        shape: 'ellipse',
    },
    interaction: {
        hover: true
    },
    physics: {
        enabled: false,
    }
};

export class GoalModelDiagram extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            graph: {},
            goalModel: [],
            showMenu: false,
            selectedGoal: {},
            mergeWithGoal: {},
            goalConditions: [],
            error: false,
            errorMessage: '',
            operation: operations.NONE
        };

        this.networkRef = React.createRef();

        this.loadModel = this.loadModel.bind(this);
        this.setGoalConditions = this.setGoalConditions.bind(this);
        this.handleSelectOperation = this.handleSelectOperation.bind(this);
        this.handleSelectGoal = this.handleSelectGoal.bind(this);
        this.handleSelectCondition = this.handleSelectCondition.bind(this);
        this.handleOperationSubmit = this.handleOperationSubmit.bind(this);
        this.handleOperationCancel = this.handleOperationCancel.bind(this);
        this.closeErrorMessageModal = this.closeErrorMessageModal.bind(this);
        this.storeGraph = this.storeGraph.bind( this );
    }

    loadModel() {
        const service = new RepositoryService();
        service.getGoalModelGraphVis(this.props.spec.specId).then(response => {
            const graph = response.data;
            service.getGoalModel(this.props.spec.specId).then(response => {
                const goalModel = response.data;

                goalModel.forEach( goal => {
                    const node = graph.nodes.find( node => node.id === goal.extId );
                    const goalPosition = goal.position;

                    if ( goalPosition ) {
                        node.x = goalPosition.x;
                        node.y = goalPosition.y;
                    }
                } );

                this.setState({
                    graph: graph,
                    goalModel: goalModel,
                    showMenu: false,
                    selectedGoal: {},
                    mergeWithGoal: {},
                    goalCondtions: [],
                    operation: operations.NONE
                });
            });
        });
    }

    setGoalConditions(selectedGoal) {
        const service = new RepositoryService();
        if (selectedGoal.type === 'ProductGoal') {
            service.getGoalEntitySuccessConditions(this.props.spec.specId, selectedGoal.name).then(response => {
                const entConditions = response.data.map(c => ({...c, active: false}));
                service.getGoalAttributeSuccessConditions(this.props.spec.specId, selectedGoal.name).then(response => {
                    this.setState({
                        selectedGoal: selectedGoal,
                        mergeWithGoal: {},
                        goalConditions: entConditions.concat(response.data.map(c => ({...c, active: false}))),
                        operation: operations.SPLIT
                    });
                });
            });
        } else if (selectedGoal.type === 'AssociationGoal') {
            service.getGoalRelations(this.props.spec.specId, selectedGoal.name).then(response => {
                this.setState({
                    selectedGoal: selectedGoal,
                    mergeWithGoal: {},
                    goalConditions: response.data.map(c => ({...c, active: false})),
                    operation: operations.SPLIT
                });
            });
        }
    }

    componentDidMount() {
        this.loadModel();
    }

    componentWillUnmount() {
        this.storeGraph();
    }

    handleSelectOperation(operation) {
        if (operation === operations.SPLIT) {
            this.setGoalConditions(this.state.selectedGoal);
        } else {
            this.setState({
                mergeWithGoal: {},
                goalConditions: [],
                operation: operation
            });
        }
    }

    handleSelectGoal(externalId) {
        if ( externalId === null || externalId === undefined ) {
            return;
        }

        if (this.state.operation === operations.NONE ||
            this.state.operation === operations.RENAME) {
            this.setState({
                showMenu: true,
                selectedGoal: this.state.goalModel.find(goal => goal.extId === externalId)
            });
        }

        if (this.state.operation === operations.MERGE) {
            const mergeWithGoal = this.state.goalModel.find(goal => goal.extId === externalId);
            if (this.state.selectedGoal.type !== mergeWithGoal.type ||
                this.state.selectedGoal === mergeWithGoal) {
                this.setState({
                    error: true,
                    errorMessage: 'The goals are not of the same type or are equal: ' + this.state.selectedGoal.type + " <> " + mergeWithGoal.type
                });
            } else {
                this.setState({
                    mergeWithGoal: mergeWithGoal
                });
            }
        }

        if (this.state.operation === operations.SPLIT) {
            this.setGoalConditions(this.state.goalModel.find(goal => goal.extId === externalId));
        }
    }

    handleSelectCondition(conditionKey) {
        const goalConditions = this.state.goalConditions.map(c => {
            if (c.path === conditionKey || c.name === conditionKey) {
                return {...c, active: !c.active};
            } else {
                return c;
            }
        });
        this.setState({
            goalConditions: goalConditions
        });
    }

    async handleOperationSubmit(operation, inputValue) {
        const service = new RepositoryService();
        
        await this.storeGraph();

        try {
            switch (operation) {
                case operations.RENAME:
                    await service.renameGoal(
                        this.props.spec.specId,
                        this.state.selectedGoal.name,
                        inputValue,
                    );

                    break;
                case operations.MERGE:
                    await service.mergeGoals(
                        this.props.spec.specId,
                        this.state.selectedGoal,
                        this.state.mergeWithGoal,
                        inputValue,
                    );

                    break;
                case operations.SPLIT:
                    const goalConditions = this.state.goalConditions.filter( c => c.active );
                    const sucConditions = this.state.selectedGoal.type === 'ProductGoal' ? goalConditions : null;
                    const relations = this.state.selectedGoal.type === 'AssociationGoal' ? goalConditions : null;
                    
                    await service.splitGoal(
                        this.props.spec.specId,
                        this.state.selectedGoal,
                        sucConditions,
                        relations,
                        inputValue,
                    );

                    break;
                default:
                    return;
            }
            
            this.loadModel();
        } catch ( err ) {
            this.setState( {
                error: true,
                errorMessage: `ERROR: ${err.response.data.type} - ${err.response.data.value}`,
            } );
        }
    }

    handleOperationCancel() {
        this.setState({
            showMenu: false,
            selectedGoal: {},
            mergeWithGoal: {},
            goalConditions: [],
            operation: operations.NONE
        });
    }

    closeErrorMessageModal() {
        this.setState({
            error: false,
            errrorMessage: ''
        });
    }

    storeGraph() {
        const graph = this.state.graph;
        const network = this.networkRef.current.network;

        if ( !graph.nodes ) {
            // For when there's no graph available, for whatever reason.
            return;
        }

        const goals = graph.nodes.map( node => {
            const position = network.getPositions( node.id );

            const goal = this.state.goalModel.find( goal => goal.extId === node.id );

            goal.position = {
                x: position[ node.id ].x,
                y: position[ node.id ].y,
            };

            return goal;
        } );

        const service = new RepositoryService();
        return service.storeView( this.props.spec.specId, goals );
    }

    render() {
        return (
            <div>
                <OverlayTrigger placement="bottom" overlay={tooltip}>
                    <h3>{this.props.spec.name}: Goal Model Diagram</h3>
                </OverlayTrigger><br /><br />

                {this.state.error &&
                <ModalMessage
                    title='Error Message'
                    message={this.state.errorMessage}
                    onClose={this.closeErrorMessageModal} />}

                {this.state.showMenu &&
                <OperationsMenu
                    selectedGoal={this.state.selectedGoal}
                    mergeWithGoal={this.state.mergeWithGoal}
                    goalConditions={this.state.goalConditions}
                    goalConditionKeys={this.state.goalConditionKeys}
                    handleSelectOperation={this.handleSelectOperation}
                    handleSelectCondition={this.handleSelectCondition}
                    handleSubmit={this.handleOperationSubmit}
                    handleCancel={this.handleOperationCancel}
                    goalModel={this.state.goalModel}/>}

                <div style={{width:'1000px' , height: '700px'}}>
                    <VisNetwork
                        ref={this.networkRef}
                        graph={this.state.graph}
                        options={options}
                        onSelection={this.handleSelectGoal}
                    />
                </div>
            </div>
        );
    }
}