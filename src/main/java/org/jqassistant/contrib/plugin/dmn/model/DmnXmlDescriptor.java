package org.jqassistant.contrib.plugin.dmn.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.jqassistant.plugin.xml.api.model.XmlFileDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

import java.util.List;

/**
 * A descriptor for DMN model descriptors.
 */
@Label("Definition")
public interface DmnXmlDescriptor extends XmlFileDescriptor, NamedDescriptor, DmnDescriptor {

    @Property("contains")
    List<DmnDecisionDescriptor> getContains();

}
