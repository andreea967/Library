package pos.labs.lab5;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pos.labs.lab5.models.RoleResponse;
import pos.labs.lab5.models.UserAuthResponse;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

@Endpoint
public class LoginEndpoint {
    public static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private static final String BACKEND_USERS_URI = "http://localhost:7766/api/auth/";
    private static final String BACKEND_ROLES_URI = "http://localhost:7766/api/roles/";


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse login(@RequestPayload GetUserRequest request) throws IOException {
        GetUserResponse response = new GetUserResponse();

        Assert.notNull(request.getUsername(), "Username must not be null");
        Assert.notNull(request.getPassword(), "Password must not be null");

        // request user auth
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BACKEND_USERS_URI)
                .queryParam("username", request.getUsername())
                .queryParam("password", request.getPassword());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<UserAuthResponse> responseFromBackend = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                UserAuthResponse.class);

        UserAuthResponse authUser = responseFromBackend.getBody();
        assert authUser != null;

        //response.setCountry(countryRepository.findCountry(request.getName()));

        //scoatem rolul
        builder = UriComponentsBuilder.fromHttpUrl(BACKEND_ROLES_URI + authUser.roleId);
        HttpEntity<RoleResponse> responseRoleFromBackend = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                RoleResponse.class);

        RoleResponse role = responseRoleFromBackend.getBody();
        assert role != null;

        String filename = String.format("%s_%s.txt", authUser.uid, System.currentTimeMillis());

        // GENERAM JWT-ul
        String secretKey = "secretulstaindoamnedoamnefbseighseigkdfnnkglrsglkinsrhlinfingldknlgdl";
        SecretKey secretKeyType = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000);

        String jwt = Jwts.builder()                .setIssuer("http://localhost:2020")
                .setSubject(authUser.uid.toString())
                .setExpiration(expiration)
                .setId(filename)
                .claim("role", role.role)
                .signWith(secretKeyType)
                .compact();

        FileWriter writer = new FileWriter(filename);
        writer.write(String.format("%s %s %s", authUser.uid, role.role, jwt));
        writer.close();

        response.setCookieValue(filename);

        String encoded_jwt = Base64.getEncoder().encodeToString(jwt.getBytes());
        response.setTokenValue(encoded_jwt);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRoleRequest")
    @ResponsePayload
    public GetUserRoleResponse getRoles(@RequestPayload GetUserRoleRequest request) throws Exception {
        GetUserRoleResponse getUserRoleResponse = new GetUserRoleResponse();
        Assert.notNull(request.getCookieValue(), "The user's filename must not be null");
        Assert.notNull(request.getTokenValue(), "The user's token must not be null");

        String content = new Scanner(new File(request.getCookieValue())).nextLine();
        String[] words = content.split(" ");
        String decoded_jwt = new String(Base64.getDecoder().decode(request.getTokenValue()), StandardCharsets.UTF_8);
        if (!words[2].equals(decoded_jwt)){
            throw new Exception("Unauthorized");
        }

        getUserRoleResponse.setRole(words[1]);

        return getUserRoleResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLogoutRequest")
    @ResponsePayload
    public GetLogoutResponse logout(@RequestPayload GetLogoutRequest request) throws Exception {
        GetLogoutResponse getLogoutResponse = new GetLogoutResponse();

        Assert.notNull(request.getCookieValue(), "Filename must not be null");
        Assert.notNull(request.getTokenValue(), "Token must not be null");

        String content = new Scanner(new File(request.getCookieValue())).nextLine();
        String[] words = content.split(" ");
        String decoded_jwt = new String(Base64.getDecoder().decode(request.getTokenValue()), StandardCharsets.UTF_8);
        if (!words[2].equals(decoded_jwt)){
            throw new Exception("Unauthorized");
        }

        File myFile = new File(request.getCookieValue());

        if (myFile.delete()){
            getLogoutResponse.setMessage("Success");
        } else {
            getLogoutResponse.setMessage("Can't find file");
        }

        return getLogoutResponse;
    }

}
