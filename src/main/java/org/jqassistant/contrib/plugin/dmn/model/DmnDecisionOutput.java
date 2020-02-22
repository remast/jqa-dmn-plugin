package org.jqassistant.contrib.plugin.dmn.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Output")
public interface DmnDecisionOutput extends NamedDescriptor, DmnDescriptor {

    String getId();
    void setId(String id);

}
