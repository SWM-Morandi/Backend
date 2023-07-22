package swm_nm.morandi.auth.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuthServiceFactory {

    private final Map<String, OAuthService> serviceMap;

    @Autowired
    public OAuthServiceFactory(List<OAuthService> oAuthServices) {
        this.serviceMap = oAuthServices.stream()
                .collect(Collectors.toMap(OAuthService::getType, Function.identity()));
    }

    public OAuthService getServiceByType(String type) {
        return serviceMap.get(type);
    }
}
