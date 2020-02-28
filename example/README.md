# camunda BPM - Getting Started Developing DMN Applications

This Repository contains the example process application for the guide at [docs.camunda.org](https://docs.camunda.org/get-started/dmn11/).

Every step of the tutorial was tagged in this repository. You can jump to the final state of each step
by the following command:

```
git checkout -f Step-X
```

If you want to follow the tutorial along please clone this repository and checkout the `Start` tag.

```
git clone https://github.com/camunda/camunda-get-started-dmn.git
git checkout -f Start
```

License: The source files in this repository are made available under the [Apache License Version 2.0](./LICENSE).

## JQAssistant

First step is to run the Maven build:
    
    mvn clean install

Second step is to run the JQAssistant server:

    mvn jqassistant:server
