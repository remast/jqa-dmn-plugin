package org.jqassistant.contrib.plugin.dmn.scanner;

import java.io.IOException;
import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.jqassistant.plugin.xml.api.scanner.AbstractXmlFileScannerPlugin;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableInputImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableOutputImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableRuleImpl;
import org.jqassistant.contrib.plugin.dmn.model.*;

/**
 * A scanner for DMN decision models.
 */
@Requires(FileDescriptor.class)
public class DmnXmlScannerPlugin extends AbstractXmlFileScannerPlugin<DmnXmlDescriptor> {

    private DmnEngine dmnEngine;

    @Override
    public void initialize() {
        dmnEngine  = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
    }

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) {
        return path.toLowerCase().endsWith(".dmn11.xml") || path.toLowerCase().endsWith(".dmn");
    }

    @Override
    public DmnXmlDescriptor scan(FileResource item, DmnXmlDescriptor dmnXmlDescriptor, String path, Scope scope, Scanner scanner) throws IOException {
        Store store = scanner.getContext().getStore();

        List<DmnDecision> dmnDecisions = dmnEngine.parseDecisions(item.createStream());
        for (DmnDecision dmnDecision : dmnDecisions) {
            DmnDecisionDescriptor dmnDecisionDescriptor = store.create(DmnDecisionDescriptor.class);
            dmnDecisionDescriptor.setName(dmnDecision.getName());
            dmnDecisionDescriptor.setKey(dmnDecision.getKey());
            dmnDecisionDescriptor.setDecisionTable(dmnDecision.isDecisionTable());
            dmnXmlDescriptor.getContains().add(dmnDecisionDescriptor);

            if (dmnDecision.isDecisionTable()) {
                DmnDecisionTableImpl decisionTable = (DmnDecisionTableImpl) dmnDecision.getDecisionLogic();
                String hitPolicy = decisionTable.getHitPolicyHandler().getHitPolicyEntry().getHitPolicy().name();
                dmnDecisionDescriptor.setHitPolicy(hitPolicy);

                for (DmnDecisionTableInputImpl input : decisionTable.getInputs()) {
                    DmnDecisionInput dmnDecisionInput = store.create(DmnDecisionInput.class);
                    dmnDecisionInput.setId(input.getId());
                    dmnDecisionInput.setName(input.getName());
                    dmnDecisionDescriptor.getInputs().add(dmnDecisionInput);
                }

                for (DmnDecisionTableOutputImpl output : decisionTable.getOutputs()) {
                    DmnDecisionOutput dmnDecisionOutput = store.create(DmnDecisionOutput.class);
                    dmnDecisionOutput.setId(output.getId());
                    dmnDecisionOutput.setName(output.getName());
                    dmnDecisionDescriptor.getOutputs().add(dmnDecisionOutput);
                }

                for (DmnDecisionTableRuleImpl rule : decisionTable.getRules()) {
                    DmnDecisionRule dmnDecisionRule = store.create(DmnDecisionRule.class);
                    dmnDecisionRule.setId(rule.getId());
                    dmnDecisionRule.setName(rule.getName());
                    dmnDecisionDescriptor.getRules().add(dmnDecisionRule);
                }
            }
        }

        return dmnXmlDescriptor;
    }
}
