# jQAssistant Camunda DMN Plugin

This is a **DMN** parser for [jQAssistant](https://jqassistant.org/). 
It enables jQAssistant to scan and to analyze **[Camunda DMN](https://camunda.com/de/dmn/)** files.

## Getting Started

Download the jQAssistant command line tool for your system: [jQAssistant - Get Started](https://jqassistant.org/get-started/).

Next download the latest version from the release tab. Put the `jqa-camunda-dmn-plugin-*.jar` into the plugins 
folder of the jQAssistant commandline tool.

Now scan your files and wait for the plugin to finish:

```bash
jqassistant.sh scan -f dish-decision.dmn
```

You can then start a local Neo4j server to start querying the database at [http://localhost:7474](http://localhost:7474):

```bash
jqassistant.sh server
```
