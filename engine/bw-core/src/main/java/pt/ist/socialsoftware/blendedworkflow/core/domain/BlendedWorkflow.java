package pt.ist.socialsoftware.blendedworkflow.core.domain;

import java.text.DateFormat;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.socialsoftware.blendedworkflow.core.service.BWException;

public class BlendedWorkflow extends BlendedWorkflow_Base {
	private static Logger log = LoggerFactory.getLogger(BlendedWorkflow.class);

	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
	private String today = this.dateFormatter.format(new java.util.Date());

	public static BlendedWorkflow getInstance() {
		if (FenixFramework.getDomainRoot().getBlendedWorkflow() == null) {
			writeTransactionToCreateBlendedWorkflow();
			log.debug("BlendedWorkflow instance created");
		}

		return FenixFramework.getDomainRoot().getBlendedWorkflow();
	}

	@Atomic(mode = TxMode.WRITE)
	private static BlendedWorkflow writeTransactionToCreateBlendedWorkflow() {
		return new BlendedWorkflow();
	}

	private BlendedWorkflow() {
		FenixFramework.getDomainRoot().setBlendedWorkflow(this);
	}

	public Specification createSpecification(String specId, String name) throws BWException {
		return new Specification(specId, name);
	}

	public Set<Specification> getSpecByName(String name) {
		return getSpecificationSet().stream().filter(spec -> spec.getName().equals(name)).collect(Collectors.toSet());
	}

	public Optional<Specification> getSpecById(String specId) {
		return getSpecificationSet().stream().filter(spec -> spec.getSpecId().equals(specId)).findFirst();
	}

	public String getToday() {
		return this.today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	// TODO: a in depth deletion of objects
	@Atomic(mode = TxMode.WRITE)
	public void delete() {
		getSpecificationSet().stream().forEach(spec -> spec.delete());

		setRoot(null);

		deleteDomainObject();
	}

}