/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.getstarted.dmn;

import java.util.List;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

@ProcessApplication("Dinner App DMN")
public class DinnerApplication extends ServletProcessApplication
{

    @PostDeploy
    public void evaluateDecisionTable(ProcessEngine processEngine) {

      DecisionService decisionService = processEngine.getDecisionService();

      VariableMap variables = Variables.createVariables()
        .putValue("season", "Spring")
        .putValue("guestCount", 10)
        .putValue("guestsWithChildren", false);

      DmnDecisionTableResult dishDecisionResult = decisionService.evaluateDecisionTableByKey("dish", variables);
      String desiredDish = dishDecisionResult.getSingleEntry();

      System.out.println("Desired dish: " + desiredDish);

      DmnDecisionTableResult beveragesDecisionResult = decisionService.evaluateDecisionTableByKey("beverages", variables);
      List<Object> beverages = beveragesDecisionResult.collectEntries("beverages");

      System.out.println("Desired beverages: " + beverages);
    }

}
