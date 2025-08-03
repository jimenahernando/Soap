# PROYECTO SOAP

## Detalle
En este proyecto trabajamos con SOAP para la comunicación entre nuestra aplicacion y una externa (http://www.dataaccess.com/webservicesserver/).
Esta aplicación externa es una Web que publica gratis llamadas SOAP para developers.

Generamos 3 servicios que llaman al mismo servicio SOAP: https://www.dataaccess.com/webservicesserver/NumberConversion.wso
con el mismo cuerpo, aunque varia el número que se le envia
````XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.dataaccess.com/webservicesserver/">
   <soapenv:Header/>
   <soapenv:Body>
      <web:NumberToWords>
         <web:ubiNum>123</web:ubiNum>
      </web:NumberToWords>
   </soapenv:Body>
</soapenv:Envelope>
````

- /api/number/toWords/manual/{number}: crea el body en XML directamente:
- /api/number/toWords-v2/manual/{number}: crea el body utilizando objetos SOAP de Jakarta
- /api/number//toWords/marshaller/{number}: utliza marshaller y crea Dtos de solicitud y respuesta

Para los dos primeros la respuesta tiene el siguiente formato:
````XML
<?xml version="1.0" encoding="UTF-8"?><m:NumberToWordsResponse xmlns:m="http://www.dataaccess.com/webservicesserver/">
      <m:NumberToWordsResult>forty four </m:NumberToWordsResult>
    </m:NumberToWordsResponse>
````

Para el ultimo, se corresponde con el objeto de respuesta que haya creado  
**eighty six**

> IMPORTANTE ⚠️!  En Spring WS no debes enviar el <<soapenv:Envelope>> ni el <<soapenv:Body>> completos, porque el WebServiceTemplate ya los genera. Si construyes todo el Envelope con SAAJ y luego lo envuelves en otro Envelope, el servidor devuelve "No such method: Envelope"


## Dependencias agregadas:
- spring-boot-starter-web
- spring-boot-starter-web-services
- spring-boot-starter-test
- lombok
- spring-boot-devtools (opcional)
- spring-boot-starter-actuator (opcional)