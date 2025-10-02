package testfunctionapp;

import java.util.Optional;

import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.ExecutionContext;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Test {

    /**
     * This function listens at endpoint "/api/test". Two ways to invoke it using "curl":
     * 1. curl -d "HTTP Body" {your host}/api/test
     * 2. curl {your host}/api/test?name=HTTP%20Query
     *
     * @param request The incoming HTTP request
     * @param context The execution context
     * @return HttpResponseMessage with a greeting or error message
     */
    @FunctionName("testfunctionapp")
    public HttpResponseMessage run(
            final @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a name on the query string or in the request body")
                    .build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK)
                    .body("Hello, " + name)
                    .build();
        }
    }
}
