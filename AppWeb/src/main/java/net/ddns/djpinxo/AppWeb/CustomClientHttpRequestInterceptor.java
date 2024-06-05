package net.ddns.djpinxo.AppWeb;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;

import java.io.IOException;

public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private String email;
    private String password;
    
    public CustomClientHttpRequestInterceptor() {
        super();
    }

    public CustomClientHttpRequestInterceptor(String email, String password) {
    	super();
        this.email = email;
        this.password = password;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String auth = email + ":" + password;
        byte[] encodedAuth = Base64Utils.encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);

        request.getHeaders().add("Authorization", authHeader);
        return execution.execute(request, body);
    }

    public void setCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
