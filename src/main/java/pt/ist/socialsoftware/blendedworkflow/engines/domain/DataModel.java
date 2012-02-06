package pt.ist.socialsoftware.blendedworkflow.engines.domain;

public class DataModel extends DataModel_Base {

	public enum DataState {DEFINED, UNDEFINED, SKIPPED};

	public DataModel () {
		super();
	}

	public DataModel(String dataModelURI) {
		setDataModelURI(dataModelURI);
	}

}
