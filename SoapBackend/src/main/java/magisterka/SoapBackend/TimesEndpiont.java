package magisterka.SoapBackend;

import magisterka.SoapBackend.model.GetResponse;
import magisterka.SoapBackend.model.GetSoapTimes;
import magisterka.SoapBackend.model.Times;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class TimesEndpiont {

    @PayloadRoot(namespace = "http://www.dupa.pl/soapexample", localPart = "getSoapTimes")
    @ResponsePayload
    public GetResponse getSoapTimesBackend(@RequestPayload GetSoapTimes getSoapTimes){
        Times times = new Times();
        times.setDbDataFetchTime(100);
        times.setBackendResponseTime(100);
        times.setFrontToBackEndTime(100);
        times.setTextsSimilarity(0.5d);
        GetResponse getResponse = new GetResponse();
        getResponse.setTimes(times);
        return getResponse;
    }
}
