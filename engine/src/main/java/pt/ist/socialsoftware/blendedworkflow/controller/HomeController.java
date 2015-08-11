package pt.ist.socialsoftware.blendedworkflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ist.socialsoftware.blendedworkflow.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.service.design.AtomicDesignInterface;
import pt.ist.socialsoftware.blendedworkflow.service.dto.SpecDTO;

@Controller
public class HomeController {
    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHome(Model model) {
        log.debug("showHome()");

        AtomicDesignInterface di = AtomicDesignInterface.getInstance();

        BWSpecification spec;
        try {
            spec = di.getSpecBySpecId("novo");
            log.debug("showHome  getSpec {}:", spec);

        } catch (BWException be) {
            spec = di.createSpecification(new SpecDTO("novo", "nome"));
            log.debug("showHome  createSpec {}", spec);
        }

        di.cleanDataModel(spec.getDataModel().getExternalId());

        return "home";
    }

}