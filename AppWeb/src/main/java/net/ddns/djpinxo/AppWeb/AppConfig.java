package net.ddns.djpinxo.AppWeb;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
    	RestTemplate restTemplate = new RestTemplate();
        
        return restTemplate;
    }
}
/*static class BasicAuthInterceptor implements ClientHttpRequestInterceptor {
    private final String username;
    private final String password;

    public BasicAuthInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Codificar las credenciales en base64
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        // Agregar el encabezado de autorización a la solicitud
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Basic " + encodedCredentials);

        // Continuar con la ejecución de la solicitud
        return execution.execute(request, body);
    }
}*/