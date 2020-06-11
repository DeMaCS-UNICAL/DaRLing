## Features

*DaRLing* is a *Da*talog Rewriter for OWL 2 *RL* Ontological Reason*ing* built on top of the OWL API.

*DaRLing* supports:  
  - translation of (the RL fragment of) OWL 2 ontologies,
  - loading of RDF/XML dataset,
  - translation of SPARQL queries,
  - `owl:sameAs` handling,
  - loading of datatypes, such as `xsd:string` and `xsd:integer`,
  - OWL API input formats.

## Installation

*DaRLing* is available for download [here](https://github.com/DeMaCS-UNICAL/DaRLing/releases).

It can be executed as:

```sh
$ unzip darling.zip 
$ perl darling.pl [options]
```

As **input**, it takes:
  - the *ontology* (i.e. the TBox) contained in a single file or in a folder containing multiple files,
  - the *dataset* (i.e. the ABox) contained in a single file or in a folder containing multiple files,
  - one or more *queries* contained in a file with the *.SPARQL* extension.

As **output**, it produces in the same folder from which it is executed:
  - a *.asp* file for the *dataset* (if given as input),
  - a *.asp* file for the *ontology* (if given as input without any query),
  - a *.asp* file for each input *query* (if given as input, containing both the ontology and the query rewritings).

Outputted .asp files contain the respective Datalog translation.

*DaRLing* produces suitable rewritings also if some inputs are missing: e.g., in case the *dataset* is missing, then the generated program is simply equivalent to the pair *ontology* plus *query*. 

### Command Line Options

```sh
 -a,--abox <arg>    ABox input file(s) 
 -h,--help          Print usage and exit
 -q,--query <arg>   Query input file(s)
 -s,--sameas        Enable owl:sameAs management
 -t,--tbox <arg>    TBox input file(s)
```

### License

*DaRLing* is licensed under the Apache License, Version 2.0. 

For further information, contact fiorentino_AT_mat.unical.it, manna_AT_mat.unical.it and zangari_AT_mat.unical.it.

### ICLP 2020

Benchmarks relative to ICLP 2020 submission can be downloaded [here](https://gofile.io/d/1bRMSL).
