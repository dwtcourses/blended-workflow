package pt.ist.socialsoftware.blendedworkflow.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.service.dto.DependenceDTO;

public class Dependence extends Dependence_Base {
	private static Logger log = LoggerFactory.getLogger(Dependence.class);

	@Override
	public void setPath(Path path) {
		checkPathPrefixLocal(path);
		super.setPath(path);
	}

	public Dependence(DataModel dataModel, Product product, String value) {
		setDataModel(dataModel);
		setProduct(product);
		Path path = new Path(dataModel, value);
		setPath(path);
	}

	private void checkPathPrefixLocal(Path path) {
		List<String> elements = Arrays.stream(path.getValue().split("\\.")).collect(Collectors.toList());
		if (elements.size() == 1) {
			throw new BWException(BWErrorType.INVALID_PATH, path.getValue());
		}

		if (!elements.get(0).equals(getProduct().getEntity().getName())) {
			throw new BWException(BWErrorType.INVALID_PATH, path.getValue()
					+ " requires to have the Entity name as prefix: " + getProduct().getEntity().getName());
		}
	}

	public void checkPathPrefix() {
		checkPathPrefixLocal(getPath());
	}

	public boolean checkPath() {
		checkPathPrefix();
		return getPath().check();
	}

	public void delete() {
		setDataModel(null);
		setProduct(null);
		getPath().delete();

		deleteDomainObject();
	}

	public DependenceDTO getDTO() {
		DependenceDTO depDTO = new DependenceDTO();
		depDTO.setSpecId(getDataModel().getSpecification().getSpecId());
		depDTO.setExtId(getExternalId());
		depDTO.setProduct(getProduct().getFullPath());
		depDTO.setProductExtId(getProduct().getExternalId());
		depDTO.setPath(getPath().getValue());

		return depDTO;
	}

	public Product getTarget() {
		return getPath().getTargetOfPath();
	}

}
