package magisterka.SoapBackend;

import magisterka.SoapBackend.HelperClasses.DatabaseDataGetter;
import magisterka.SoapBackend.HelperClasses.PairOfTexts;
import magisterka.SoapBackend.HelperClasses.StringSimilarity;
import magisterka.SoapBackend.HelperClasses.TimeAndTexts;
import magisterka.SoapBackend.model.GetResponse;
import magisterka.SoapBackend.model.GetSoapTimes;
import magisterka.SoapBackend.model.Times;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Endpoint
public class TimesEndpiont {

    @PayloadRoot(namespace = "http://www.dupa.pl/soapexample", localPart = "getSoapTimes")
    @ResponsePayload
    public GetResponse getSoapTimesBackend(@RequestPayload GetSoapTimes getSoapTimes){
        GetSoapTimes getSoapTimes2 = getSoapTimes;
        Long czasPrzeslaniaRequestuZFrontuDoBackendu = policzCzasPrzesaniaRequestuZFrontenduDoBackendu(getSoapTimes.getCzasWyslaniaRequestuZFrontendu(), System.currentTimeMillis());
        TimeAndTexts czasOrazTekstyZBazyDanych = pobierzTekstyZBazyDanych(getSoapTimes.getTextLength(), getSoapTimes.getTextType(), getSoapTimes.getDbmsType());
        Double podobienstwoTextow = StringSimilarity.similarity(czasOrazTekstyZBazyDanych.getTexts().get(0),czasOrazTekstyZBazyDanych.getTexts().get(1));


        Times times = new Times();
        times.setDbDataFetchTime(czasOrazTekstyZBazyDanych.getTime());
        times.setBackendResponseTime(czasPrzeslaniaRequestuZFrontuDoBackendu);
        times.setFrontToBackEndTime(System.currentTimeMillis());
        times.setTextsSimilarity(podobienstwoTextow);
        GetResponse getResponse = new GetResponse();
        getResponse.setTimes(times);
        return getResponse;
    }

    private Long policzCzasPrzesaniaRequestuZFrontenduDoBackendu(Long czasWyslaniaRequestuZFrontendu, Long timeNow){
        return timeNow - czasWyslaniaRequestuZFrontendu;
    }

    private TimeAndTexts pobierzTekstyZBazyDanych (String textsLength, String textsType, String dbmsType){
        LinkedList<String> ll = new LinkedList<String>();
        Long timeBeforeDataRetrival = System.currentTimeMillis();
        PairOfTexts texts = DatabaseDataGetter.getTexts(textsLength, textsType, dbmsType);
        Long timeAfterDataRetrival = System.currentTimeMillis();
        Long elapsedTimeToGetData = timeAfterDataRetrival - timeBeforeDataRetrival;
        ll.add(texts.getText1());
        ll.add(texts.getText2());
        TimeAndTexts tnt = new TimeAndTexts(elapsedTimeToGetData,ll);
        return tnt;
    }
}
