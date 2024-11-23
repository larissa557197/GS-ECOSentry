package org.example;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Permite requisições OPTIONS para pré-verificação
        if ("OPTIONS".equalsIgnoreCase(requestContext.getRequest().getMethod())) {
            Response.ResponseBuilder response = Response.ok();
            setCorsHeaders(response);
            requestContext.abortWith(response.build());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Garante que os cabeçalhos CORS sejam adicionados
        if (!responseContext.getHeaders().containsKey("Access-Control-Allow-Origin")) {
            setCorsHeaders(responseContext);
        }
    }

    private void setCorsHeaders(Response.ResponseBuilder response) {
        response.header("Access-Control-Allow-Origin", "*"); // Para testes, substitua por URL específica no ambiente de produção
        response.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.header("Access-Control-Allow-Credentials", "true");
        response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.header("Access-Control-Max-Age", "1209600"); // Cache do preflight
    }

    private void setCorsHeaders(ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*"); // Substitua por URL específica no ambiente de produção
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}
