package es.caib.loginib.rest.v1;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.loginib.rest.api.util.JsonException;
import es.caib.loginib.rest.api.util.JsonUtil;
import es.caib.loginib.rest.api.v1.RLoginParams;
import es.caib.loginib.rest.api.v1.RLogoutParams;

public class Test {

    private final static String urlBase = "http://localhost:8080/loginib/api/rest/v1";

    public static void main(String[] args) throws Exception {

        login();

        // logout();

    }

    private static String login() throws JsonException {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final RLoginParams param = new RLoginParams();
        param.setEntidad("A04013511");
        // param.setEntidad("SP");
        param.setUrlCallback("http://www.google.es");
        param.setIdioma("es");
        param.setForzarAutenticacion(false);
        param.setQaa(3);
        param.setMetodosAutenticacion("ANONIMO;CERTIFICADO;CLAVE_PERMANENTE");

        final String parametrosJSON = JsonUtil.toJson(param);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("parametros", parametrosJSON);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                map, headers);

        final ResponseEntity<String> response = restTemplate
                .postForEntity(urlBase + "/login", request, String.class);

        final String res = response.getBody();

        System.out.println("Inicio sesion: " + res);

        return res;
    }

    private static String logout() throws JsonException {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final RLogoutParams param = new RLogoutParams();
        param.setEntidad("A04003003");
        param.setUrlCallback("http://www.google.es");
        param.setIdioma("es");

        final String parametrosJSON = JsonUtil.toJson(param);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("parametros", parametrosJSON);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                map, headers);

        final ResponseEntity<String> response = restTemplate
                .postForEntity(urlBase + "/logout", request, String.class);

        final String res = response.getBody();

        System.out.println("Inicio sesion: " + res);

        return res;
    }

}
