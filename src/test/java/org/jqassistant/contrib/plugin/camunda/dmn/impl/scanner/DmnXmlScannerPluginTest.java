package org.jqassistant.contrib.plugin.camunda.dmn.impl.scanner;

import java.io.File;
import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import org.hamcrest.CoreMatchers;
import org.jqassistant.contrib.plugin.camunda.dmn.model.DmnXmlDescriptor;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class DmnXmlScannerPluginTest extends AbstractPluginIT {

    /**
     * TRICKY: For JUnit Jupiter we need to override the standard method to get the classes directory.
     * @see <a href="https://www.baeldung.com/junit-src-test-resources-directory-path">Get the Path of the /src/test/resources Directory in JUnit</a>"
     */
    protected File getClassesDirectory(Class<?> rootClass) {
        String resourceName = ".";
        ClassLoader classLoader = rootClass.getClassLoader();
        File directory = new File(classLoader.getResource(resourceName).getFile());
        return directory;
    }

    @Test
    public void scanExampleDmn() {
        store.beginTransaction();

        File testFile = new File(getClassesDirectory(DmnXmlScannerPluginTest.class), "dish-decision.dmn");

        assertThat(getScanner().scan(testFile, "dish-decision.dmn", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(DmnXmlDescriptor.class));

        // Check files
        TestResult testResult = query("MATCH (file:Dmn:File) RETURN file");
        List<DmnXmlDescriptor> files = testResult.getColumn("file");
        assertThat(files.size(), CoreMatchers.equalTo(1));
        DmnXmlDescriptor file = files.get(0);
        assertThat(file.getFileName(), CoreMatchers.equalTo("dish-decision.dmn"));

        store.commitTransaction();
    }

}
