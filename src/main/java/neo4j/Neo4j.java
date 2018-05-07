package neo4j;

import org.neo4j.driver.v1.*;
import static org.neo4j.driver.v1.Values.parameters;


public class Neo4j {
    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j","123456"));
        Session session = driver.session();

        //StatementResult result = session.run("MATCH (n:User) RETURN n.name AS name, n.sex AS sex");
        StatementResult result = session.run("MATCH (a:User)-[:Following]->(b) WHERE a.name = {name}" + "RETURN b.name AS name, b.sex AS sex",
                parameters( "name", "张三" ));

        while(result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("name").asString() + " " + record.get("sex"));
        }
        session.close();
        driver.close();
    }
}
