package org.blended.common.repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.blended.common.repository.resttemplate.BWError;
import org.blended.common.repository.resttemplate.RepositoryException;
import org.blended.common.repository.resttemplate.RestUtil;
import org.blended.common.repository.resttemplate.dto.AttributeDTO;
import org.blended.common.repository.resttemplate.dto.AttributeGroupDTO;
import org.blended.common.repository.resttemplate.dto.DefAttributeConditionDTO;
import org.blended.common.repository.resttemplate.dto.DefEntityConditionDTO;
import org.blended.common.repository.resttemplate.dto.DependenceDTO;
import org.blended.common.repository.resttemplate.dto.EntityDTO;
import org.blended.common.repository.resttemplate.dto.GoalDTO;
import org.blended.common.repository.resttemplate.dto.MulConditionDTO;
import org.blended.common.repository.resttemplate.dto.ProductDTO;
import org.blended.common.repository.resttemplate.dto.RelationDTO;
import org.blended.common.repository.resttemplate.dto.RuleDTO;
import org.blended.common.repository.resttemplate.dto.SpecDTO;
import org.blended.common.repository.resttemplate.dto.SuccessConditionDTO;
import org.blended.common.repository.resttemplate.req.ExtractGoalReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonInterface {
    private static Logger log = LoggerFactory.getLogger(CommonInterface.class);

    final static String BASE_URL = "http://localhost:8080";

    private static CommonInterface instance = null;

    public static CommonInterface getInstance() {
        if (instance == null) {
            instance = new CommonInterface();
        }
        return instance;
    }

    public void deleteSpecification(String specId) {
        log.debug("deleteSpecification: {}", specId);

        final String uri = BASE_URL + "/specs/{specId}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.delete(uri, params);

    }

    public SpecDTO getSpecBySpecId(String specId) {
        log.debug("getSpecBySpecId: {}", specId);

        final String uri = BASE_URL + "/specs/{specId}";

        RestTemplate restTemplate = RestUtil.getRestTemplate();

        Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("specId", specId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.GET, entity, String.class, uriVariables);

        ObjectMapper mapper = new ObjectMapper();
        String responseBody = response.getBody();
        log.debug("getSpecBySpecId responseBody: {}", responseBody);
        try {
            if (RestUtil.isError(response.getStatusCode())) {
                BWError error = mapper.readValue(responseBody, BWError.class);
                throw new RepositoryException(error);
            } else {
                SpecDTO specVO = mapper.readValue(responseBody, SpecDTO.class);
                return specVO;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public SpecDTO createSpec(SpecDTO specVO) {
        log.debug("createSpec: {}, {}", specVO.getSpecId(), specVO.getName());

        final String uri = BASE_URL + "/specs";

        RestTemplate restTemplate = RestUtil.getRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<SpecDTO> entity = new HttpEntity<SpecDTO>(specVO, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.POST, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        String responseBody = response.getBody();
        log.debug("createSpec responseBody: {}", responseBody);
        try {
            if (RestUtil.isError(response.getStatusCode())) {
                BWError error = mapper.readValue(responseBody, BWError.class);
                throw new RepositoryException(error);
            } else {
                SpecDTO newSpecVO = mapper.readValue(responseBody,
                        SpecDTO.class);
                return newSpecVO;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanDataModel(String specId) {
        log.debug("cleanDataModel: {}", specId);

        final String uri = BASE_URL + "/specs/{specId}/datamodel";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.put(uri, null, params);
    }

    public EntityDTO createEntity(EntityDTO entityVO) {
        log.debug("createEntity: {}, {}, {}", entityVO.getSpecId(),
                entityVO.getName(), entityVO.getExists());

        final String uri = BASE_URL + "/specs/{specId}/datamodel/entities";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", entityVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        EntityDTO result = restTemplate.postForObject(uri, entityVO,
                EntityDTO.class, params);

        return result;
    }

    public DependenceDTO createDependence(DependenceDTO dependenceVO) {
        log.debug("createDependence: {}, {}", dependenceVO.getProductExtId(),
                dependenceVO.getPath());

        final String uri = BASE_URL + "/specs/{specId}/datamodel/dependencies";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", dependenceVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DependenceDTO result = restTemplate.postForObject(uri, dependenceVO,
                DependenceDTO.class, params);

        return result;
    }

    public Set<DependenceDTO> getDependencies(String specId) {
        log.debug("getDependencies: {}", specId);

        final String uri = BASE_URL + "specs/{specId}/datamodel/dependencies";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DependenceDTO[] result = restTemplate.getForObject(uri,
                DependenceDTO[].class, params);

        return new HashSet<DependenceDTO>(Arrays.asList(result));
    }

    public boolean checkDependence(String specId, String extId) {
        log.debug("checkDependence: {}", extId);

        final String uri = BASE_URL
                + "/specs/{specId}/datamodel/dependencies/{depExtId}/check";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("depExtId", extId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        Boolean response = restTemplate.getForObject(uri, Boolean.class,
                params);

        return response;
    }

    public void deleteDependence(String specId, String extId) {
        log.debug("deleteDependence: {}", extId);

        final String uri = BASE_URL
                + "/specs/{specId}/datamodel/dependencies/{depExtId}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("depExtId", extId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.delete(uri, params);
    }

    public AttributeDTO getAttribute(String specId, String extId) {
        log.debug("getAttribute specId:{} extId:{}", specId, extId);

        final String uri = BASE_URL
                + "specs/{specId}/datamodel/attributes/{extId}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("extId", extId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        AttributeDTO result = restTemplate.getForObject(uri, AttributeDTO.class,
                params);

        return result;
    }

    public AttributeDTO createAttribute(AttributeDTO attribueVO) {
        log.debug(
                "createAttribute: entitityExtId:{}, groupExtId:{}, {}, {}, {}",
                attribueVO.getEntityExtId(), attribueVO.getGroupExtId(),
                attribueVO.getName(), attribueVO.getType(),
                attribueVO.getIsMandatory());

        final String uri = BASE_URL + "/specs/{specId}/datamodel/attributes";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", attribueVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        AttributeDTO result = restTemplate.postForObject(uri, attribueVO,
                AttributeDTO.class, params);

        return result;
    }

    public AttributeGroupDTO createAttributeGroup(
            AttributeGroupDTO attributeGroupVO) {
        log.debug("createAttributeGroup: entitityExtId:{},  {}, {}",
                attributeGroupVO.getEntityExtId(), attributeGroupVO.getName(),
                attributeGroupVO.isMandatory());

        final String uri = BASE_URL
                + "/specs/{specId}/datamodel/attributegroups";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", attributeGroupVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        AttributeGroupDTO result = restTemplate.postForObject(uri,
                attributeGroupVO, AttributeGroupDTO.class, params);

        return result;
    }

    public RelationDTO createRelation(RelationDTO relationVO) {
        log.debug("createRelation: {}, {}, {}", relationVO.getName(),
                relationVO.getEntOneName(), relationVO.getEntTwoName());

        final String uri = BASE_URL + "/specs/{specId}/datamodel/relations";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", relationVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        RelationDTO result = restTemplate.postForObject(uri, relationVO,
                RelationDTO.class, params);

        return result;
    }

    public RuleDTO createRule(RuleDTO ruleVO) {
        log.debug("createRule: {}, {}, {}", ruleVO.getSpecId(),
                ruleVO.getName(), ruleVO.getExpression());

        final String uri = BASE_URL + "/specs/{specId}/datamodel/rules";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", ruleVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        RuleDTO result = restTemplate.postForObject(uri, ruleVO, RuleDTO.class,
                params);

        return result;
    }

    public void printSpecificationModels(String specId) {
        log.debug("printSpecificationModels: {}", specId);

        final String uri = BASE_URL + "/specs/{specId}/print";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.getForObject(uri, Boolean.class, params);
    }

    public void cleanConditionModel(String specId) {
        log.debug("cleanConditionModel: {}", specId);

        final String uri = BASE_URL + "/specs/{specId}/conditionmodel";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.put(uri, null, params);
    }

    public DefEntityConditionDTO createEntityAchieveCondition(
            DefEntityConditionDTO entityAchieveConditionVO) {
        log.debug("createEntityAchieveCondition: {}, {}, {}",
                entityAchieveConditionVO.getSpecId(),
                entityAchieveConditionVO.getEntityName(),
                entityAchieveConditionVO.isExists());

        final String uri = BASE_URL
                + "/specs/{specId}/conditionmodel/entityachieveconditions";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", entityAchieveConditionVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefEntityConditionDTO result = restTemplate.postForObject(uri,
                entityAchieveConditionVO, DefEntityConditionDTO.class, params);

        return result;
    }

    public DependenceDTO createEntityDependenceCondition(
            DependenceDTO dependenceVO) {
        log.debug("createEntityDependenceCondition entityExtId:{}, path:{}",
                dependenceVO.getProductExtId(), dependenceVO.getPath());

        final String uri = BASE_URL
                + "/specs/{specId}/conditionmodel/entitydependenceconditions";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", dependenceVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DependenceDTO result = restTemplate.postForObject(uri, dependenceVO,
                DependenceDTO.class, params);

        return result;
    }

    public MulConditionDTO createEntityInvariantCondition(
            MulConditionDTO mulConditionVO) {
        log.debug("createEntityInvariantCondition rolePath:{}, cardinality:{}",
                mulConditionVO.getRolePath(), mulConditionVO.getCardinality());

        final String uri = BASE_URL
                + "/specs/{specId}/conditionmodel/entityinvariantconditions";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", mulConditionVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        MulConditionDTO result = restTemplate.postForObject(uri, mulConditionVO,
                MulConditionDTO.class, params);

        return result;
    }

    public DefAttributeConditionDTO createAttributeAchieveCondition(
            DefAttributeConditionDTO defAttributeConditionVO) {
        log.debug("createAttributeAchieveCondition paths:{}, mandatory:{}",
                defAttributeConditionVO.getPaths().toString(),
                defAttributeConditionVO.isMandatory());

        final String uri = BASE_URL
                + "/specs/{specId}/conditionmodel/attributeachieveconditions";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", defAttributeConditionVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefAttributeConditionDTO result = restTemplate.postForObject(uri,
                defAttributeConditionVO, DefAttributeConditionDTO.class,
                params);

        return result;
    }

    public DependenceDTO createAttributeDependenceCondition(
            DependenceDTO dependenceVO) {
        log.debug("createDependenceCondition productExtId:{}, path:{}",
                dependenceVO.getProductExtId(), dependenceVO.getPath());

        final String uri = BASE_URL
                + "/specs/{specId}/conditionmodel/attributedependenceconditions";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", dependenceVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DependenceDTO result = restTemplate.postForObject(uri, dependenceVO,
                DependenceDTO.class, params);

        return result;
    }

    public RuleDTO createAttributeInvariantCondition(RuleDTO ruleVO) {
        log.debug(
                "createAttributeInvariantCondition conditionModelExtId:{}, name:{}",
                ruleVO.getSpecId(), ruleVO.getName());

        final String uri = BASE_URL
                + "/specs/{specId}/conditionmodel/attributeinvariantconditions";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", ruleVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        RuleDTO result = restTemplate.postForObject(uri, ruleVO, RuleDTO.class,
                params);

        return result;
    }

    public ProductDTO getProduct(String specId, Set<String> sourceAtts) {
        log.debug("getProduct: {}", sourceAtts.toString());

        final String uri = BASE_URL
                + "/specs/{specId}/datamodel/products/{atts}/";

        String atts = sourceAtts.stream().collect(Collectors.joining(","));

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("atts", atts);

        log.debug("getProduct: {}", atts);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        ProductDTO productVO = restTemplate.getForObject(uri, ProductDTO.class,
                params);

        return productVO;
    }

    public EntityDTO getEntityByName(String specId, String entityName) {
        log.debug("getEntityByName: {}, {}", specId, entityName);

        final String uri = BASE_URL
                + "/specs/{specId}/datamodel/entities/{entityName}/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("entityName", entityName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        EntityDTO entityVO = restTemplate.getForObject(uri, EntityDTO.class,
                params);

        return entityVO;
    }

    public void cleanGoalModel(String specId) {
        log.debug("cleanGoalModel: {}", specId);

        final String uri = BASE_URL + "/specs/{specId}/goalmodel";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.put(uri, null, params);
    }

    public GoalDTO getGoalByName(String specId, String goalName) {
        log.debug("getGoalByName: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        GoalDTO goalVO = restTemplate.getForObject(uri, GoalDTO.class, params);

        return goalVO;
    }

    public GoalDTO createGoal(GoalDTO goalVO) {
        log.debug("createGoal specId:{}, name:{}", goalVO.getSpecId(),
                goalVO.getName());

        final String uri = BASE_URL + "/specs/{specId}/goalmodel/goals";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", goalVO.getSpecId());

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        GoalDTO result = restTemplate.postForObject(uri, goalVO, GoalDTO.class,
                params);

        return result;
    }

    public Set<GoalDTO> getSubGoals(String specId, String goalName) {
        log.debug("getsubGoals: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/sub";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        GoalDTO[] goalsVO = restTemplate.getForObject(uri, GoalDTO[].class,
                params);

        return new HashSet<GoalDTO>(Arrays.asList(goalsVO));
    }

    public GoalDTO getParentGoal(String specId, String goalName) {
        log.debug("getParentGoal: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/sup";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        GoalDTO goalVO = restTemplate.getForObject(uri, GoalDTO.class, params);

        return goalVO;
    }

    public GoalDTO addSubGoal(String parentName, GoalDTO goalVO) {
        log.debug("addSubGoal specId:{}, parentName:{}, childName:{}",
                goalVO.getSpecId(), parentName, goalVO.getName());

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/sub";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", goalVO.getSpecId());
        params.put("goalName", parentName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        GoalDTO result = restTemplate.postForObject(uri, goalVO, GoalDTO.class,
                params);

        return result;
    }

    public Set<DefEntityConditionDTO> getGoalSuccessEntitySet(String specId,
            String goalName) {
        log.debug("getGoalSucEntityAchieveCondition: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/sucent";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefEntityConditionDTO[] defsVO = restTemplate.getForObject(uri,
                DefEntityConditionDTO[].class, params);

        return new HashSet<DefEntityConditionDTO>(Arrays.asList(defsVO));
    }

    public DefEntityConditionDTO associateEntityToGoalSuccess(String specId,
            String goalName, String path) {
        log.debug(
                "associatedSucConditionToGoal specId:{}, goalName:{}, path:{}",
                specId, goalName, path);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/sucent/{path}/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);
        params.put("path", path);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefEntityConditionDTO result = restTemplate.postForObject(uri, null,
                DefEntityConditionDTO.class, params);

        return result;
    }

    public Set<DefAttributeConditionDTO> getGoalSuccessAttributeSet(
            String specId, String goalName) {
        log.debug("getGoalSuccessAttributeSet: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/sucatt";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefAttributeConditionDTO[] defsVO = restTemplate.getForObject(uri,
                DefAttributeConditionDTO[].class, params);

        return new HashSet<DefAttributeConditionDTO>(Arrays.asList(defsVO));
    }

    public void associateAttributeToGoalSuccess(String specId, String goalName,
            Set<String> paths) {
        log.debug(
                "associateAttributeAchieveConditionToGoalActCondition specId:{}, goalName:{}, paths:{}",
                specId, goalName, paths);

        String pathsParam = paths.stream().collect(Collectors.joining(","));

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/sucatt/{paths}/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);
        params.put("paths", pathsParam);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.postForObject(uri, null, String.class, params);
    }

    public Set<DefEntityConditionDTO> getGoalActivationEntitySet(String specId,
            String goalName) {
        log.debug("getGoalActivationEntitySet: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/actent";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefEntityConditionDTO[] defsVO = restTemplate.getForObject(uri,
                DefEntityConditionDTO[].class, params);

        return new HashSet<DefEntityConditionDTO>(Arrays.asList(defsVO));
    }

    public DefEntityConditionDTO associateEntityToGoalActivation(String specId,
            String goalName, String path) {
        log.debug(
                "associatedActConditionToGoal specId:{}, goalName:{}, path:{}",
                specId, goalName, path);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/actent/{path}/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);
        params.put("path", path);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefEntityConditionDTO result = restTemplate.postForObject(uri, null,
                DefEntityConditionDTO.class, params);

        return result;
    }

    public Set<DefAttributeConditionDTO> getGoalActivationAttributeSet(
            String specId, String goalName) {
        log.debug("getGoalActivationAttributeSet: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/actatt";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        DefAttributeConditionDTO[] defsVO = restTemplate.getForObject(uri,
                DefAttributeConditionDTO[].class, params);

        return new HashSet<DefAttributeConditionDTO>(Arrays.asList(defsVO));
    }

    public void associateAttributeToGoalActivation(String specId,
            String goalName, Set<String> paths) {
        log.debug(
                "associateAttributeAchieveConditionToGoalActCondition specId:{}, goalName:{}, paths:{}",
                specId, goalName, paths);

        String pathsParam = paths.stream().collect(Collectors.joining(","));

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/actatt/{paths}/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);
        params.put("paths", pathsParam);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.postForObject(uri, null, String.class, params);
    }

    public Set<MulConditionDTO> getGoalMulInvSet(String specId,
            String goalName) {
        log.debug("getGoalMulInvSet: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/invent";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        MulConditionDTO[] mulsVO = restTemplate.getForObject(uri,
                MulConditionDTO[].class, params);

        return new HashSet<MulConditionDTO>(Arrays.asList(mulsVO));
    }

    public void associateMulToGoalInvariant(String specId, String goalName,
            MulConditionDTO mulConditionVO) {
        log.debug(
                "associateMulConditionToGoalEntityInvariantCondition specId:{}, goalName:{}, path:{}, cardinality:{}",
                specId, goalName, mulConditionVO.getRolePath(),
                mulConditionVO.getCardinality());

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/invent";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.postForObject(uri, mulConditionVO, MulConditionDTO.class,
                params);
    }

    public Set<RuleDTO> getGoalRuleInvSet(String specId, String goalName) {
        log.debug("getGoalRuleInvSet: {}, {}", specId, goalName);

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/invatt";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        RuleDTO[] rulesVO = restTemplate.getForObject(uri, RuleDTO[].class,
                params);

        return new HashSet<RuleDTO>(Arrays.asList(rulesVO));
    }

    public void associateRuleToGoalInvariant(String specId, String goalName,
            RuleDTO ruleVO) {
        log.debug(
                "associateRuleConditionToGoalAttributeInvariantCondition specId:{}, goalName:{}, rule:{}",
                specId, goalName, ruleVO.getName());

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/{goalName}/invatt";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);
        params.put("goalName", goalName);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        restTemplate.postForObject(uri, ruleVO, RuleDTO.class, params);
    }

    public GoalDTO mergeGoals(String specId, String newGoalName,
            String goalNameOne, String goalNameTwo) {
        log.debug(
                "mergeGoals specId:{}, newGoalName:{} goalNameOne:{}, goalNameTwo:{}",
                specId, newGoalName, goalNameOne, goalNameTwo);

        final String uri = BASE_URL + "/specs/{specId}/goalmodel/goals/merge";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        MultiValueMap<String, Object> variablesMap = new LinkedMultiValueMap<String, Object>();
        variablesMap.add("newGoalName", newGoalName);
        variablesMap.add("goalNameOne", goalNameOne);
        variablesMap.add("goalNameTwo", goalNameTwo);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        return restTemplate.postForObject(uri, variablesMap, GoalDTO.class,
                params);
    }

    public GoalDTO extractChildGoal(String specId, String newGoalName,
            String sourceGoalName, SuccessConditionDTO successCondition) {
        log.debug(
                "extractChildGoal specId:{}, newGoalName:{}, sourceGoalName:{}, entDefs:{}, attDefs:{}",
                specId, newGoalName, sourceGoalName,
                successCondition.getDefEnts().stream()
                        .map((def) -> def.getEntityName()).collect(
                                Collectors.joining(",")),
                successCondition.getDefAtts().stream()
                        .map((def) -> def.getPaths().stream()
                                .collect(Collectors.joining(",")))
                        .collect(Collectors.joining("|")));

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/extractchild";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        ExtractGoalReq req = new ExtractGoalReq();
        req.setNewGoalName(newGoalName);
        req.setSourceGoalName(sourceGoalName);
        req.setSuccessCondition(successCondition);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        return restTemplate.postForObject(uri, req, GoalDTO.class, params);
    }

    public GoalDTO extractSiblingGoal(String specId, String newGoalName,
            String sourceGoalName, SuccessConditionDTO successCondition) {
        log.debug(
                "extractSiblingGoal specId:{}, newGoalName:{}, sourceGoalName:{}, entDefs:{}, attDefs:{}",
                specId, newGoalName, sourceGoalName,
                successCondition.getDefEnts().stream()
                        .map((def) -> def.getEntityName()).collect(
                                Collectors.joining(",")),
                successCondition.getDefAtts().stream()
                        .map((def) -> def.getPaths().stream()
                                .collect(Collectors.joining(",")))
                        .collect(Collectors.joining("|")));

        final String uri = BASE_URL
                + "/specs/{specId}/goalmodel/goals/extractsibling";

        Map<String, String> params = new HashMap<String, String>();
        params.put("specId", specId);

        ExtractGoalReq req = new ExtractGoalReq();
        req.setNewGoalName(newGoalName);
        req.setSourceGoalName(sourceGoalName);
        req.setSuccessCondition(successCondition);

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        return restTemplate.postForObject(uri, req, GoalDTO.class, params);
    }

    public ProductDTO getSourceOfPath(String specId, String path) {
        log.debug("getSourceOfPath specId:{} path:{}", specId, path);

        final String uri = BASE_URL + "/specs/{specId}/pathsource?path={path}";

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        ProductDTO productVO = restTemplate.getForObject(uri, ProductDTO.class,
                specId, path);

        return productVO;
    }

    public ProductDTO getTargetOfPath(String specId, String path) {
        log.debug("getTargetOfPath specId:{} path:{}", specId, path);

        final String uri = BASE_URL + "/specs/{specId}/pathtarget?path={path}";

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        ProductDTO productVO = restTemplate.getForObject(uri, ProductDTO.class,
                specId, path);

        return productVO;
    }

    public Set<String> getDependencePaths(String specId, Set<String> paths) {
        log.debug("getDependencePaths paths:{}", paths);

        final String uri = BASE_URL + "/specs/{specId}/pathdep?paths={paths}";

        RestTemplate restTemplate = RestUtil.getRestTemplate();
        String[] result = restTemplate.getForObject(uri, String[].class, specId,
                paths.stream().collect(Collectors.joining(",")));

        return Arrays.stream(result).collect(Collectors.toSet());
    }

}
