package com.jimenahernando.soap.numbersToWords.Dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "NumberToWordsResponse", namespace = "http://www.dataaccess.com/webservicesserver/")
@XmlAccessorType(XmlAccessType.FIELD)
public class NumberToWordsResponse {
    @XmlElement(name = "NumberToWordsResult", namespace = "http://www.dataaccess.com/webservicesserver/")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
