package com.jimenahernando.soap.numbersToWords;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/number")
@RequiredArgsConstructor
public class NumberToWordsController {

    private final NumberToWordsClient numberToWordsClient;

    @GetMapping("/toWords/manual/{number}")
    public ResponseEntity<String> convertNumberToWords(@PathVariable int number) {
        try {
            String soapResponse = numberToWordsClient.convertNumberToWords(number);
            return ResponseEntity.ok(soapResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consumir servicio SOAP: " + e.getMessage());
        }
    }

    @GetMapping("/toWords-v2/manual/{number}")
    public ResponseEntity<String> convertNumberToWordscreatingRequestSOAP(@PathVariable int number) {
        try {
            String soapResponse = numberToWordsClient.convertNumberToWordscreatingRequestSOAP(number);
            return ResponseEntity.ok(soapResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consumir servicio SOAP: " + e.getMessage());
        }
    }

    @GetMapping("/toWords/marshaller/{number}")
    public ResponseEntity<String> convertWithMarshaller(@PathVariable int number) {
        try {
            String soapResponse = numberToWordsClient.convertWithMarshaller(number);
            return ResponseEntity.ok(soapResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consumir servicio SOAP: " + e.getMessage());
        }
    }
}
