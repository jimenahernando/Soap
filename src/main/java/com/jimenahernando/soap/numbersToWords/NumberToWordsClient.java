package com.jimenahernando.soap.numbersToWords;

import com.jimenahernando.soap.numbersToWords.Dtos.NumberToWordsRequest;
import com.jimenahernando.soap.numbersToWords.Dtos.NumberToWordsResponse;
import jakarta.annotation.PostConstruct;
import jakarta.xml.soap.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class NumberToWordsClient {

    private final WebServiceTemplate webServiceTemplate;

    private static final String ENDPOINT = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso";
    private static final String SOAP_ACTION = "http://www.dataaccess.com/webservicesserver/NumberToWords";


    @PostConstruct
    public void init() {
        log.info("✔ WebServiceTemplate in NumberToWordsClient: " + webServiceTemplate);
    }

    public String convertNumberToWords(int number) {
        try {
            String requestPayload = """
                  <web:NumberToWords xmlns:web="http://www.dataaccess.com/webservicesserver/">
                     <web:ubiNum>%d</web:ubiNum>
                  </web:NumberToWords>
                    """.formatted(number);

            StreamSource source = new StreamSource(new ByteArrayInputStream(requestPayload.getBytes(StandardCharsets.UTF_8)));
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(responseStream);

            webServiceTemplate.sendSourceAndReceiveToResult(
                    ENDPOINT,
                    source,
                    message -> {
                        SoapMessage soapMessage = (SoapMessage) message;
                        // Importante: SOAPAction vacío como en SoapUI
                        soapMessage.setSoapAction("");
                    },
                    result
            );

            return responseStream.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error consumiendo SOAP: " + e.getMessage(), e);
        }
    }

    public String convertNumberToWordscreatingRequestSOAP(int number) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPEnvelope env = soapMessage.getSOAPPart().getEnvelope();
        SOAPBody body = env.getBody();

        // ✅ Agregar elementos manualmente
        SOAPElement numberToWordsElem = body.addChildElement("NumberToWords", "web",
                "http://www.dataaccess.com/webservicesserver/");
        SOAPElement ubiNumElem = numberToWordsElem.addChildElement("ubiNum", "web");
        ubiNumElem.addTextNode(String.valueOf(number));

        soapMessage.saveChanges();

        // ✅ Convertir solo el contenido del Body en DOMSource
        org.w3c.dom.Node firstChild = body.getFirstChild();
        DOMSource bodySource = new DOMSource(firstChild);

        // ✅ Enviar la petición con Spring WebServiceTemplate
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(responseStream);

        webServiceTemplate.sendSourceAndReceiveToResult(
                ENDPOINT,
                bodySource,
                message -> ((SoapMessage) message).setSoapAction(""),
                result
        );

        // ✅ Retornar respuesta
        return responseStream.toString(StandardCharsets.UTF_8);
    }

    public String convertWithMarshaller(int number) {
        // ✅ Crear objeto request
        NumberToWordsRequest request = new NumberToWordsRequest(number);

        // ✅ Llamar servicio SOAP usando marshaller
        NumberToWordsResponse response = (NumberToWordsResponse) webServiceTemplate.marshalSendAndReceive(
                ENDPOINT,
                request,
                message -> {}
        );

        return response.getResult();
    }

}
