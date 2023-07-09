package vn.com.hust.admin.config;

import lombok.Data;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.com.hust.admin.utilities.Constants;

import javax.annotation.PostConstruct;

@Data
@Component
public class KeyCloakMasterConfig {
    @Value("${url.base}")
    private String urlBase;

    @Value("${client.id.master}")
    private String clientIdMaster;

    @Value("${client.secret.master}")
    private String clientSecretMaster;

    @Value("${url.token_master_endpoint}")
    private String tokenMasterEndpoint;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        keycloak = KeycloakBuilder.builder() //
                .serverUrl(urlBase) //
                .realm(Constants.Realm.MASTER) //
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
                .clientId(clientIdMaster) //
                .clientSecret(clientSecretMaster) //
                .build();
    }

}
