# Yaml2JsonTransformer
* Transforms a given file of type YAML into json representation (using Jackson and snakeyaml)
<Description will follow>

# Usage 
* ``java -jar yaml2json.jar`` from a directory with an existing yaml file ``sample.yml``
* ``java -jar yaml2json.jar <complete_path_to_yml_file_with_yml_suffix>`` to create json with same name of file type ``json`` in same path
* ``java -jar yaml2json.jar <complete_path_to_yml_file_with_yml_suffix> <name>`` to create json with name <name> of file type ``json`` in same path

# Unit Tests
in root directory, call ``mvn clean test`` to execute unit tests
  
  
