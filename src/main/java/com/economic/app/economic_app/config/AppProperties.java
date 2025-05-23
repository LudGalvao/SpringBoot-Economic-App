package com.economic.app.economic_app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    
    private String frontendUrl;
    
    private Security security = new Security();
    
    @Data
    public static class Security {
        private Jwt jwt = new Jwt();
        
        @Data
        public static class Jwt {
            private Auth auth = new Auth();
            private RecuperacaoSenha recuperacaoSenha = new RecuperacaoSenha();
            
            @Data
            public static class Auth {
                private String chaveSecreta;
                private long expiracao;
            }
            
            @Data
            public static class RecuperacaoSenha {
                private String chaveSecreta;
                private long expiracao;
            }
        }
    }
} 