/*
 * Copyright 2019 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.datamodels.core.validation.rules.invalid.value;

import io.apicurio.datamodels.core.Constants;
import io.apicurio.datamodels.core.models.DocumentType;
import io.apicurio.datamodels.core.models.common.Parameter;
import io.apicurio.datamodels.core.validation.ValidationRuleMetaData;
import io.apicurio.datamodels.openapi.models.OasParameter;

/**
 * Implements the Unknown Parameter Location rule.
 * @author eric.wittmann@gmail.com
 */
public class OasUnknownParamLocationRule extends OasInvalidPropertyValueRule {

    /**
     * Constructor.
     * @param ruleInfo
     */
    public OasUnknownParamLocationRule(ValidationRuleMetaData ruleInfo) {
        super(ruleInfo);
    }
    
    /**
     * @see io.apicurio.datamodels.combined.visitors.CombinedAllNodeVisitor#visitParameter(io.apicurio.datamodels.core.models.common.Parameter)
     */
    @Override
    public void visitParameter(Parameter node) {
        OasParameter param = (OasParameter) node;
        if (hasValue(param.in)) {
            if (node.ownerDocument().getDocumentType() == DocumentType.openapi2) {
                this.reportIfInvalid(isValidEnumItem(param.in, array( "query", "header", "path", "formData", "body" )), 
                        node, Constants.PROP_IN, map("options", "query, header, path, formData, body"));
            } else {
                this.reportIfInvalid(isValidEnumItem(param.in, array( "query", "header", "path", "cookie" )), 
                        node, Constants.PROP_IN, map("options", "query, header, path, cookie"));
            }
        }
    }

}
