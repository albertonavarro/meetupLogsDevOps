import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws UnirestException {

       invocation("request-1");
       invocation("request-2");
    }

    private static void invocation(String rid) throws UnirestException {
        MDC.put("requestId", rid );
        logger.info("Receiving request from user to create account, name=user1");

        logger.info("Invoking AccountService");
        HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.post("http://localhost:8080/accounts")
                .header("rid", rid)
                .field("name", "user1")
                .asJson();

        logger.info("Invoked AccountService");

        if (jsonNodeHttpResponse.getStatus() == 200) {
            logger.info("Creation successful, new id: " + jsonNodeHttpResponse.getBody().getObject().get("name"));
        } else if (jsonNodeHttpResponse.getStatus() == 409) {
            logger.info("Error, user name already exist");
        }

        MDC.clear();
    }
}
