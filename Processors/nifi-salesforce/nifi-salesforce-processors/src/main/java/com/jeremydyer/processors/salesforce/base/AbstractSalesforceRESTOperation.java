/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jeremydyer.processors.salesforce.base;

import com.mashape.unirest.http.Unirest;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;

import com.jeremydyer.processors.salesforce.SalesforceUserPassAuthentication;

/**
 * Created by jdyer on 8/4/16.
 */
public class AbstractSalesforceRESTOperation
        extends AbstractProcessor {

    protected static final PropertyDescriptor SALESFORCE_AUTH_SERVICE = new PropertyDescriptor
            .Builder().name("Salesforce.com Authentication Controller Service")
            .description("Your Salesforce.com authentication service for authenticating against Salesforce.com")
            .required(true)
            .identifiesControllerService(SalesforceUserPassAuthentication.class)
            .build();

    public static final Relationship REL_SUCCESS = new Relationship.Builder()
            .name("success")
            .description("Operation completed successfully")
            .build();

    public static final Relationship REL_FAILURE = new Relationship.Builder()
            .name("failure")
            .description("Operation failed")
            .build();

    private static final String SALESFORCE_URL_BASE = "https://persgroep.my.salesforce.com";
    private static final String SALESFORCE_API_PATH = "/services/data/";
    private static final String SALESFORCE_VERSION = "v43.0";

    @Override
    public void onTrigger(ProcessContext processContext, ProcessSession processSession) throws ProcessException {

    }

    // HTTP GET request
    protected String sendGet(String accessToken, String url) throws Exception {
        return Unirest.get(url)
                .header("Authorization ", "Bearer " + accessToken)
                .asString().getBody();
    }

    protected String generateSalesforceURL(String apiEndpoint) {
        return SALESFORCE_URL_BASE + SALESFORCE_API_PATH + SALESFORCE_VERSION + "/" + apiEndpoint;
    }
}
