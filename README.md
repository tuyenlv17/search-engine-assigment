# Simple Search Service - Assignment

Simple full-text search service using inverted index with simple In-Ram storage using 2 algorithms:
* BM25 similarity using recommended config with k1 = 1.2 and b = 0.75
* keyword matching (no ranking)

## Getting Started

### Project structure

### Running

Using IDE or building standalone application

```
mvn clean install -DskipTests
java -jar -Dserver.port=58080 target/simple-search-engine-1.0-SNAPSHOT.jar
```

### Api
Api only returns top 10 most relevant product names
* Search using BM25 similarity
```
localhost:58080/search/bm25?query=bluetooth loa jbl
```
* Search using keyword
```
localhost:58080/search/keyword?query=bluetooth loa jbl
``` 

## Compare 2 search algorithms

* Keyword search gives seemingly random results since a is document matched when 
a single token is matched (order of results may be random or sorted on inserted time)
* BM25 gives more relevant results using modified TF-IDF, priorities short-length results, 
field with tokens that matched multiple time and less-common terms

## Built With

* Spring Boot
* Maven
* Intellij

## Versioning

 Git 

## Authors

* **Tuyen Luong**
