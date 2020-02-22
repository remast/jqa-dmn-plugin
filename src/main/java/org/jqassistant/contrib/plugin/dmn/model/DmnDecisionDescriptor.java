package org.jqassistant.contrib.plugin.dmn.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Indexed;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

@Label("Decision")
public interface DmnDecisionDescriptor extends NamedDescriptor, DmnDescriptor {

    @Indexed
    @Property("key")
    String getKey();
    void setKey(String key);

    @Property("hitPolicy")
    String getHitPolicy();
    void setHitPolicy(String hitPolicy);

    @Property("decisionTable")
    Boolean getDecisionTable();
    void setDecisionTable(Boolean decisionTable);

    @Relation("DECLARES")
    List<DmnDecisionInput> getInputs();

    @Relation("DECLARES")
    List<DmnDecisionOutput> getOutputs();

}
