# Building environment
The following software need to be installed in your machine in order to run ASIA-backend:

- Java v. 1.8
- Maven

# Build the library
```
mvn package
```


# Usage

Use `ASIA4JFactory` builder to get a `GrafterizerHashtableClient` object. 


```
  ASIA4J hClient = ASIA4JFactory.getClient(asiaEndpoint, GrafterizerHashtableClient.class);
```

You can now use your client to **reconcile** or **extend**.

## Reconciliation

To reconcile used the `reconcile` provided by your client.  

In the following example the string "Berlin" is reconciled with a threshold of 0.1 using the `geonames` service. 
The reconciliation service in ASIA-Backend is suggested to reconcile "Berlin" against the type "A.ADM1"; such a suggestion, is optional. 
The result of the reconciliation process is an id with the most suitable entity identifier (in this case `6554818`). 

```
String geoID =  hClient.reconcile(
                new Annotation(
                        new SingleColumnReconciliation(
                                "Berlin",
                                0.1,
                                Collections.singletonList("A.ADM1")
                        ),
                        "geonames"));

```

### (Deprecated) Reconcile a single column
The GrafterizerClient exposes a method that allows you to achieve the above result without
providing the Annotation object (as in the old ASIA4J interface).
This method is deprecated and then removed from the interface, but it's still implemented 
in the GrafterizerClient.
```
String geoID = ((GrafterizerHashtableClient) hClient)
                .reconcileSingleColumn("Berlin", "A.ADM1", .1, "geonames");

```

## Extension
The identified retrieved using the reconciliation functionality can be used to extract from the reference knowledge base a
data related to that entity. 
The entity's properties can be queried using the `extendFromConciliator` method. 
In the following code snippet used the Berlin ID to obtain the `parentADM1` (that is the id of the region) using the geonames service.

```java
String property = client.extendFromConciliator("6554818", "parentADM1", "geonames");
```
 
 


