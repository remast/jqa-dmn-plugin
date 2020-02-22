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
import org.jqassistant.contrib.plugin.dmn.model.DmnDecisionDescriptor;
import org.jqassistant.contrib.plugin.dmn.model.DmnXmlDescriptor;

/**
 * A scanner for JPA model units.
 */
@Requires(FileDescriptor.class)
public class DmnXmlScannerPlugin extends AbstractXmlFileScannerPlugin<DmnXmlDescriptor> {

    private DmnEngine dmnEngine;

    @Override
    public void initialize() {
        dmnEngine  = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
    }

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) throws IOException {
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
            dmnXmlDescriptor.getContains().add(dmnDecisionDescriptor);
        }

        return dmnXmlDescriptor;
    }
}
