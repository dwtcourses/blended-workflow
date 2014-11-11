package pt.ist.socialsoftware.blendedworkflow.presentation.MedicalAppointment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import jvstm.Transaction;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.DataModel.DataState;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.DataModelInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.Entity;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.EntityInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.TaskWorkItem;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.User;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.WorkItemArgument;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class BookingForm extends VerticalLayout {
	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);

	private final String taskWorkItemOID;
	HorizontalLayout footer = new HorizontalLayout();

	Label episodeLabel;
	TextField numberTF;
	PopupDateField reserveDatePDF;
	NativeSelect patientNS;

	public BookingForm(final String workItemOID) {
		setMargin(true);
		setSpacing(true);

		this.taskWorkItemOID = workItemOID;

		episodeLabel = new Label("Episode");
		episodeLabel.addStyleName("h2");
		addComponent(episodeLabel);

		numberTF = new TextField("Number");
		addComponent(numberTF);

		reserveDatePDF = new PopupDateField("Reserve Date");
		reserveDatePDF.setValue(new java.util.Date());
		reserveDatePDF.setResolution(PopupDateField.RESOLUTION_DAY);
		reserveDatePDF.setImmediate(true);
		addComponent(reserveDatePDF);

		patientNS = new NativeSelect("Patient");
		patientNS.setNullSelectionAllowed(false);
		patientNS.setImmediate(true);
		addComponent(patientNS);
		patientNS.addStyleName("h2");
		getPatients();

		footer.setMargin(true);
		footer.setSpacing(true);

		Button submitButton = new Button("Submit");
		submitButton.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				processData(workItemOID);
				getApplication().getMainWindow().removeWindow(
						BookingForm.this.getWindow());
			}
		});
		footer.addComponent(submitButton);

		Button cancelButton = new Button("Cancel");
		cancelButton.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getApplication().getMainWindow().removeWindow(
						BookingForm.this.getWindow());
			}
		});
		footer.addComponent(cancelButton);

		addComponent(footer);
		setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
	}

	private void processData(final String workItemOID) {
		String episodeNumber = numberTF.getValue().toString();
		String episodeReserveDate = dateFormatter.format(reserveDatePDF
				.getValue());
		String patientOID = (String) patientNS.getValue();

		// Create RelationInstance
		Transaction.begin();
		TaskWorkItem taskWorkItem = FenixFramework.getDomainObject(workItemOID);
		String bwInstanceOID = taskWorkItem.getBwInstance().getExternalId();
		String episodeOID = taskWorkItem.getBwInstance().getDataModelInstance()
				.getEntity("Episode").getEntityInstance("Episode.1")
				.getExternalId();
		Transaction.commit();
		Transaction.begin();
		BlendedWorkflow.getInstance().getBwManager()
				.addRelationInstance(bwInstanceOID, episodeOID, patientOID);
		Transaction.commit();

		// Set WorkItemArguments
		Transaction.begin();
		List<WorkItemArgument> arguments = new ArrayList<WorkItemArgument>(
				taskWorkItem.getOutputWorkItemArgumentsSet());
		arguments.get(0).setValue(episodeNumber);
		arguments.get(0).setState(DataState.DEFINED);
		arguments.get(1).setValue(episodeReserveDate);
		arguments.get(1).setState(DataState.DEFINED);
		Transaction.commit();

		Transaction.begin();
		User activeUser = BlendedWorkflow.getInstance()
				.getOrganizationalManager().getActiveUser();
		taskWorkItem.setUser(activeUser);
		Transaction.commit();
		Transaction.begin();
		BlendedWorkflow.getInstance().getWorkListManager()
				.checkInWorkItem(taskWorkItemOID);
		Transaction.commit();
	}

	protected void getPatients() {
		Transaction.begin();
		TaskWorkItem taskWorkItem = FenixFramework
				.getDomainObject(taskWorkItemOID);
		BWInstance bwInstance = taskWorkItem.getBwInstance();
		DataModelInstance dataModelInstance = bwInstance.getDataModelInstance();
		Entity patient = dataModelInstance.getEntity("Patient");
		for (EntityInstance entityInstance : patient.getEntityInstancesSet()) {
			patientNS.addItem(entityInstance.getExternalId());
			patientNS.setItemCaption(entityInstance.getExternalId(),
					entityInstance.getAttributeInstance("Name.1").getValue());
		}
		Transaction.commit();
	}
}
