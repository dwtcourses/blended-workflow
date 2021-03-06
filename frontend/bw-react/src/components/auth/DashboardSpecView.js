import React from 'react';
import DashboardInstanceView from './DashboardInstanceView';

export class DashboardSpecView extends React.Component {
    render() {
        const instances = Object.keys(this.props.instances).map(instance => 
            <DashboardInstanceView 
                specName={this.props.specName}
                instanceName={instance} 
                activityWorkItems={this.props.instances[instance].activityWorkItems}
                goalWorkItems={this.props.instances[instance].goalWorkItems} />
        );

        return ( 
            <div>
                <h3>{this.props.specName}</h3>
                {instances}
            </div>
        )
    }
}