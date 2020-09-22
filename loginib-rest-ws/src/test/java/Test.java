
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import es.caib.loginib.rest.api.v1.RDatosAutenticacion;
import es.caib.loginib.rest.api.v1.RLoginParams;
import es.caib.loginib.rest.api.v1.RLogoutParams;

public class Test {

    private final static String urlBase = "http://localhost:8080/loginib/api/rest/v1";

    public static void main(String[] args) throws Exception {

        login();

        logout();

        // validarTicket();

    }

    private static String login() {

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final RLoginParams param = new RLoginParams();
        param.setEntidad("A04013511");
        param.setAplicacion("app1");
        param.setUrlCallback("http://www.google.es");
        param.setIdioma("es");
        param.setInicioClaveAutomatico(false);
        param.setForzarAutenticacion(false);
        param.setQaa(3);
        param.setMetodosAutenticacion(
                "ANONIMO;CLAVE_CERTIFICADO;CLAVE_PERMANENTE");

        final HttpEntity<RLoginParams> request = new HttpEntity<>(param,
                headers);

        final ResponseEntity<String> response = restTemplate
                .postForEntity(urlBase + "/login", request, String.class);

        final String res = response.getBody();

        System.out.println("Inicio sesion login: " + res);

        return res;
    }

    private static String logout() {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final RLogoutParams param = new RLogoutParams();
        param.setEntidad("A04003003");
        param.setUrlCallback("http://www.google.es");
        param.setIdioma("es");

        final HttpEntity<RLogoutParams> request = new HttpEntity<>(param,
                headers);

        final ResponseEntity<String> response = restTemplate
                .postForEntity(urlBase + "/logout", request, String.class);

        final String res = response.getBody();

        System.out.println("Inicio sesion logout: " + res);

        return res;
    }

    private static void validarTicket() {

        final RestTemplate restTemplate = new RestTemplate();

        final String ticket = "123";

        final RDatosAutenticacion datosAutenticacion = restTemplate
                .getForObject(urlBase + "/ticket/" + ticket,
                        RDatosAutenticacion.class);

        System.out.println(datosAutenticacion.getNif());

    }

}
