# Simple Search Service - Assignment

Simple full-text search service using inverted index with simple In-Ram storage

## Getting Started

### Project structure

### Running

Using IDE or building standalone application

```
mvn clean install -DskipTests
java -jar -Dserver.port=58080 target/simple-search-engine-1.0-SNAPSHOT.jar
```

### Api

* Search using BM25 similarity
```
localhost:58080/search/bm25?query=bluetooth loa jbl
```
* Search using keyword
```
localhost:58080/search/keyword?query=bluetooth loa jbl
``` 

## Compare 2 search algorithms


## Built With

* Spring Boot
* Maven
* Intellij

## Versioning

 Git 

## Authors

* **Tuyen Luong**
