package org.jqassistant.contrib.plugin.camunda.dmn.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Indexed;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Decision")
public interface DmnDecisionDescriptor extends NamedDescriptor, DmnDescriptor {

    @Indexed
    String getKey();
    void setKey(String key);
}
