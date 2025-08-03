package com.jimenahernando.soap.numbersToWords.Dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "NumberToWords", namespace = "http://www.dataaccess.com/webservicesserver/")
@XmlAccessorType(XmlAccessType.FIELD)
public class NumberToWordsRequest {

    @XmlElement(name = "ubiNum", namespace = "http://www.dataaccess.com/webservicesserver/")
    private int ubiNum;

    public NumberToWordsRequest() {}

    public NumberToWordsRequest(int ubiNum) {
        this.ubiNum = ubiNum;
    }

    public int getUbiNum() {
        return ubiNum;
    }

    public void setUbiNum(int ubiNum) {
        this.ubiNum = ubiNum;
    }
}
