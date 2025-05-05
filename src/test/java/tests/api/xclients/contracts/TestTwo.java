package tests.api.xclients.contracts;

import config.urls.UrlsConfig;
import utils.auth.Auth;
import utils.auth.AuthRequest;
import jupiter.annotations.api.WithTestCompanyApi;
import jupiter.annotations.api.WithTestUserApi;
import jupiter.extensions.api.CompanyCreateExtension;
import jupiter.extensions.api.DatabaseConnectionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith({DatabaseConnectionExtension.class, CompanyCreateExtension.class})
public class TestTwo {
    private static Auth auth = new Auth();
    private static final UrlsConfig URL = UrlsConfig.getInstance();
    @Test
    @WithTestUserApi(type = WithTestUserApi.Type.ADMIN)
    @WithTestCompanyApi
    public void testTwo(AuthRequest authRequest, Integer companyId){
        String token = auth.authAndGetToken(URL.UrlApiXclient(), authRequest);
        assertNotNull(token);
        assertNotNull(companyId);
    }
}
