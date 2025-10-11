# Bear CRUD API
Simple CRUD API for Bear Objects with JPA (Hibernate)

### Version
1.0.0

## Installation
- Get the project
    - clone
        ```
      git clone <your-repository-url>
        ```
    - OR download zip.
- Open the project in VS Code.
- This project is built to run with jdk 21.
- Dependencies to JPA and Postgres in addition to the usual Spring Web. JPA handles the persistence, PostgreSQL is the database to be used.
- `/src/main/resources/application.properties` This file has the configuration for the PostgreSQL database to use for the API.
  - You MUST have the database up and running before running the project!
    - Login to your neon.tech account.
    - Locate your database project.
    - On the project dashboard, click on "Connect" and select Java.
    - Copy the connection string provided.
    - Paste it as a value for the property `spring.datasource.url`. No quotation marks.
- Build and run the main class. You should see a new table created in the Neon database.

## Demo
You can test the API endpoints using the following demo link:
- **Video Demo**: [Loom Video](https://www.loom.com/share/e7d4dc7ccc384c3aa43643b305664f32?sid=3946d651-7917-4d11-95b0-80b53e0cb118)

Or use tools like Postman, curl, or any REST client to interact with the API endpoints listed below.

## Notes
### Java - [Spring ORM with JPA and Hibernate](https://medium.com/@burakkocakeu/jpa-hibernate-and-spring-data-jpa-efa71feb82ac)
- We are using ORM (Object-Relational Mapping) to deal with databases. This is a technique that allows us to interact with a relational database using object-oriented programming principles.
- JPA (Jakarta Persistence, formerly Java Persistence API) is a specification that defines ORM standards in Java. It provides an abstraction layer for ORM frameworks to make concrete implementations.
- Hibernate: Hibernate is a popular ORM framework that implements JPA. It simplifies database operations by mapping Java objects to database tables and handling queries efficiently.
Spring ORM allows seamless integration of Hibernate and JPA, making database interactions more manageable and reducing boilerplate code.
### Bear Java classes have different purposes: Separation of concerns!
- [Entity](https://github.com/RamonSaturninoM/crud_api_app_csc340/blob/main/src/main/java/com/example/demo/bear/Bear.java)
  - The Bear class is annotated as an `@Entity`. This is used to map class attributes to database tables and SQL types.
  - We also annotated with `@Table` to give Hibernate directions to use this specific table name. This is optional but it helps with naming conventions.
  - Any Entity must have at least one attribute that is annotated as an `@Id`. In our case it's conveniently the `bearId` attribute.
    - We are also using an autogeneration strategy for the ID. This way we are not manually assigning IDs to our bears. This is optional.
       - For this reason, we also added a constructor to make a Bear without an ID.
  - An Entity must have a no-argument constructor.
- [Repository](https://github.com/RamonSaturninoM/crud_api_app_csc340/blob/main/src/main/java/com/example/demo/bear/BearRepository.java)
  - We are using an extension of the JPA Repository that comes with prebuilt database operations such as select all, select by id, select by any other reference, insert, delete, etc.
  - Annotate it as a `@Repository`.
  - We parametrize this using our object and its ID type.
    - `public interface BearRepository extends JpaRepository<Bear, Long>` => We want to apply the JPA repository operations on the `Bear` type. The `Bear` has an ID of type `Long`.
  - If we need special database queries that are not the standard ones mentioned above, we can create custom methods with special purpose queries as shown. This is an interface so no implementation body.
- [Service](https://github.com/RamonSaturninoM/crud_api_app_csc340/blob/main/src/main/java/com/example/demo/bear/BearService.java)
  - Annotated as a `@Service`.
  - It is the go-between from controller to database. In here we define what functions we need from the repository. A lot of the functions are default functions that our repository inherits from JPA (save, delete, findAll, findByX), some of them are custom made (getBearsByHabitat, getBearsByName).
  - It asks the repository to perform SQL queries.
  - The Repository class is `@Autowired`. This is for managing the dependency to the repository. Do not use a constructor to make a Repository object, you will get errors.
- [Rest Controller](https://github.com/RamonSaturninoM/crud_api_app_csc340/blob/main/src/main/java/com/example/demo/bear/BearController.java)
  - Annotated as a `@RestController`.
  - It asks the Service class to perform data access functions.
  - The Service class is `@Autowired` here as well :)

## API Endpoints
Base URL: [`http://localhost:8080/bears`](http://localhost:8080/bears)

1. ### [`/`](http://localhost:8080/bears) (GET)
Gets a list of all Bears in the database.

#### Response - A JSON array of Bear objects.

```
[
  {
    "bearId": 1,
    "bearName": "Grizzly",
    "bearDescription": "Large brown bear found in North America",
    "age": 15,
    "habitat": "Forest"
  },
  {
    "bearId": 2,
    "bearName": "Polar",
    "bearDescription": "White bear adapted to Arctic conditions",
    "age": 12,
    "habitat": "Arctic"
  }
]
```

2. ### [`/{bearId}`](http://localhost:8080/bears/1) (GET)
Gets an individual Bear in the system. Each Bear is identified by a numeric `bearId`

#### Parameters
- Path Variable: `bearId` &lt;Long&gt; - REQUIRED

#### Response - A single Bear

```
{
  "bearId": 1,
  "bearName": "Grizzly",
  "bearDescription": "Large brown bear found in North America",
  "age": 15,
  "habitat": "Forest"
}
```

3. ### [`/habitat/{habitat}`](http://localhost:8080/bears/habitat/Forest) (GET)
Gets a list of bears for a specific habitat.

#### Parameters
- Path Variable: `habitat` &lt;String&gt; - REQUIRED

#### Response - A JSON array of Bear objects.

```
[
  {
    "bearId": 1,
    "bearName": "Grizzly",
    "bearDescription": "Large brown bear found in North America",
    "age": 15,
    "habitat": "Forest"
  }
]
```

4. ### [`/search/{name}`](http://localhost:8080/bears/search/Grizzly) (GET)
Gets a list of bears with a name that matches the given string.

#### Parameters
- Path Variable: `name` &lt;String&gt; - REQUIRED

#### Response - A JSON array of Bear objects.

```
[
  {
    "bearId": 1,
    "bearName": "Grizzly",
    "bearDescription": "Large brown bear found in North America",
    "age": 15,
    "habitat": "Forest"
  }
]
```

5. ### [`/`](http://localhost:8080/bears) (POST)
Create a new Bear entry

#### Request Body
A bear object. Note the object does not include an ID as this is autogenerated.
```
{
  "bearName": "Black Bear",
  "bearDescription": "Smaller bear species found in North America",
  "age": 8,
  "habitat": "Mountain"
}
```

#### Response - The newly created Bear.

```
{
  "bearId": 3,
  "bearName": "Black Bear",
  "bearDescription": "Smaller bear species found in North America",
  "age": 8,
  "habitat": "Mountain"
}
```

6. ### [`/{bearId}`](http://localhost:8080/bears/3) (PUT)
Update an existing Bear.

#### Parameters
- Path Variable: `bearId` &lt;Long&gt; - REQUIRED

#### Request Body
A bear object with the updates.
```
{
  "bearId": 3,
  "bearName": "Updated Black Bear",
  "bearDescription": "Updated description for the black bear",
  "age": 9,
  "habitat": "Mountain"
}
```

#### Response - The updated Bear object.

```
{
  "bearId": 3,
  "bearName": "Updated Black Bear",
  "bearDescription": "Updated description for the black bear",
  "age": 9,
  "habitat": "Mountain"
}
```

7. ### [`/{bearId}`](http://localhost:8080/bears/3) (DELETE)
Delete an existing Bear.

#### Parameters
- Path Variable: `bearId` &lt;Long&gt; - REQUIRED

#### Response - No content (HTTP 204)