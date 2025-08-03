package com.jimenahernando.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration
@Slf4j
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // 📌 Escanea el paquete donde están tus clases JAXB generadas/manuales
        marshaller.setPackagesToScan("com.jimenahernando.soap.numbersToWords");
        return marshaller;
    }

    @Bean
    @Primary
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) throws Exception {
        WebServiceTemplate template = new WebServiceTemplate();

        // Configurar un MessageSender que ignora SSL
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();

        // Configurar SSLContext para confiar en todos los certificados
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }}, new SecureRandom());

        // Asignar el contexto SSL y el verificador de host al sender
        sender.setHostnameVerifier((hostname, session) -> true);
        sender.setSslSocketFactory(sslContext.getSocketFactory());

        template.setMessageSender(sender);

        log.info("✔ WebServiceTemplate configurado con SSL ignorado: " + template);

        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        return template;
    }

}
