/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.server.remote.rest.casemgmt;

import static org.kie.server.api.rest.RestURI.*;
import static org.kie.server.remote.rest.common.util.RestUtils.*;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

import org.kie.server.api.model.cases.CaseInstanceList;
import org.kie.server.remote.rest.common.Header;
import org.kie.server.services.api.KieServerRegistry;
import org.kie.server.services.casemgmt.CaseManagementRuntimeDataServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.Api;

@Api(value="case-admin")
@Path("server/" + ADMIN_CASE_URI)
public class CaseAdminResource extends AbstractCaseResource {

    private static final Logger logger = LoggerFactory.getLogger(CaseAdminResource.class);

    public CaseAdminResource() {

    }

    public CaseAdminResource(
            final CaseManagementRuntimeDataServiceBase caseManagementRuntimeDataServiceBase,
            final KieServerRegistry context) {
        super(caseManagementRuntimeDataServiceBase,
              context);
    }

    @GET
    @Path(CASE_ALL_INSTANCES_GET_URI)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCaseInstances(@javax.ws.rs.core.Context HttpHeaders headers,
                                     @QueryParam("status") List<String> status,
                                     @QueryParam("page") @DefaultValue("0") Integer page,
                                     @QueryParam("pageSize") @DefaultValue("10") Integer pageSize,
                                     @QueryParam("sort") String sort,
                                     @QueryParam("sortOrder") @DefaultValue("true") boolean sortOrder) {

        return invokeCaseOperation(headers,
                                   "",
                                   null,
                                   (Variant v, String type, Header... customHeaders) -> {

                                       CaseInstanceList responseObject = null;

                                       logger.debug("About to look for case instances with status {}",
                                                    status);
                                       responseObject = this.caseManagementRuntimeDataServiceBase.getCaseInstances(status,
                                                                                                                   page,
                                                                                                                   pageSize,
                                                                                                                   sort,
                                                                                                                   sortOrder);

                                       logger.debug("Returning OK response with content '{}'",
                                                    responseObject);
                                       return createCorrectVariant(responseObject,
                                                                   headers,
                                                                   Response.Status.OK,
                                                                   customHeaders);
                                   });
    }
}