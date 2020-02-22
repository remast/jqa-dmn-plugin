package org.jqassistant.contrib.plugin.dmn.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Indexed;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

@Label("Input")
public interface DmnDecisionInput extends NamedDescriptor, DmnDescriptor {

    String getId();
    void setId(String id);

}
