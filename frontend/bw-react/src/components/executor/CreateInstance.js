import React from 'react';
import { connect } from 'react-redux';
import { setUnitOfWork } from '../../actions/setUnitOfWork';
import { createEntityInstance } from '../../actions/createEntityInstance';
import { RepositoryService } from '../../services/RepositoryService';
import DefineEntityInstance from './workitem/DefineEntityInstance';
import { CreateEntityInstance } from './CreateEntityInstance';

const mapStateToProps = state => {
    return {
        unitOfWork: state.unitOfWork,
        user: state.user,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        setUnitOfWork: unitOfWork => dispatch(setUnitOfWork(unitOfWork)),
        createEntityInstance: entityName => dispatch(createEntityInstance(entityName))
    };
};

class ConnectedCreateInstance extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            create: false,
            value: '',
            workItem: {}
        };

        this.createUnitOfWork = this.createUnitOfWork.bind(this);
        this.handleCreateEntityInstance = this.handleCreateEntityInstance.bind(this);
        this.handleCreate = this.handleCreate.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        const service = new RepositoryService(this.props.user);
        service.getInitWorkItem(this.props.specId).then(response => {
            this.props.setUnitOfWork(this.createUnitOfWork(response.data.entityInstancesToDefine));
            this.setState({
                workItem: response.data
            });
        });
    }

    createUnitOfWork(entityInstancesToDefine) {
        let counter = 0;
        return entityInstancesToDefine.map(ei => JSON.parse(JSON.stringify(ei)))
            .map(ei => {
                ei.id = --counter;
                return ei;
            });
    }

    handleCreate() {
        this.setState({
            create: !this.state.create
        });
    }

    handleCreateEntityInstance(entityInstance) {
        const entityInstanceToDefine = JSON.parse(JSON.stringify(
        this.state.workItem.entityInstancesToDefine.find(ei => ei.entity.name === entityInstance.entity.name)));
        const min = this.props.unitOfWork.map(ei => parseInt(ei.id)).reduce((id1, id2) => Math.min(id1, id2), 0) - 1;
        const newEntityInstance = { ...entityInstanceToDefine, id: min.toString()};
        this.props.createEntityInstance(newEntityInstance);
    }

    handleChange(event) {
        this.setState({
            value: event.target.value
        });
    }

    handleSubmit() {
        this.props.onSubmit(this.state.value, this.props.unitOfWork);
        this.setState({
            create: false,
            value: '',
            workItem: {}
        });
   }

    render() {
        return (
            <div>
                {this.state.create &&
                    <span>
                        <label> Instance Name:
                            <input type="text" value={this.state.value} onChange={this.handleChange} />
                        </label> <br />
                    </span>
                }
                {this.state.create && this.props.unitOfWork && 
                    this.props.unitOfWork.map(ei => <div>
                            <DefineEntityInstance key={ei.id} 
                                entityInstance={ei} />
                            <CreateEntityInstance key={ei.id} 
                                onCreateEntityInstance={this.handleCreateEntityInstance} 
                                entityInstance={ei} />
                        </div> 
                    )}
                <button onClick={this.handleCreate}>{this.state.create ? 'Close' : 'Create'}</button>
                {this.state.create &&
                    <span> <button onClick={this.handleSubmit}>Submit</button></span>
                }
            </div>
       )
    }
}

const CreateInstance = connect(mapStateToProps, mapDispatchToProps)(ConnectedCreateInstance);

export default CreateInstance;