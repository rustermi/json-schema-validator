package com.networknt.schema.spi.providers.draftv4;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.spi.JsonSchemaValidatorNode;
import com.networknt.schema.spi.ValidatorNode;
import com.networknt.schema.spi.ValidatorNodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static com.networknt.schema.ValidatorTypeCode.MAX_PROPERTIES;

public class MaxPropertiesValidatorNode extends JsonSchemaValidatorNode {

    public static final String PROPERTY_NAME_MAXPROPERTIES = "maxProperties";

    private static final Logger logger = LoggerFactory.getLogger(MaxPropertiesValidatorNode.class);

    private final int max;

    private MaxPropertiesValidatorNode(String schemaPath, JsonNode jsonNode, ValidatorNode parent, ValidatorNode root) {
        super(PROPERTY_NAME_MAXPROPERTIES, MAX_PROPERTIES, schemaPath, jsonNode, parent, root);
        max = jsonNode.isIntegralNumber() ? jsonNode.intValue() : 0;
    }

    @Override
    public List<ValidationMessage> validate(JsonNode node, JsonNode rootNode, String at) {
        debug(logger, node, rootNode, at);

        List<ValidationMessage> errors = new LinkedList<>();

        if (node.isObject()) {
            if (node.size() > max) {
                errors.add(buildValidationMessage(at, "" + max));
            }
        }

        return errors;
    }

    public static final class Factory implements ValidatorNodeFactory<MaxPropertiesValidatorNode> {
        @Override
        public MaxPropertiesValidatorNode newInstance(String schemaPath, JsonNode jsonNode, ValidatorNode parent,
                ValidatorNode root) {
            return new MaxPropertiesValidatorNode(schemaPath, jsonNode, parent, root);
        }
    }
}