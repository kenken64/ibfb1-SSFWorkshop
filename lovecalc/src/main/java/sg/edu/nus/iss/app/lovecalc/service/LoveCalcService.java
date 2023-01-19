package sg.edu.nus.iss.app.lovecalc.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.redis.core.RedisTemplate;

import sg.edu.nus.iss.app.lovecalc.model.LoverResult;

@Service
public class LoveCalcService {
    private static final String LOVE_CALC_API_URL 
                        = "https://love-calculator.p.rapidapi.com/getPercentage";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    public Optional<LoverResult> calcCompatibility(String person1Name, String personName2) 
        throws IOException{
        String finalLoveCalculatorUrl = UriComponentsBuilder
                                    .fromUriString(LOVE_CALC_API_URL)
                                    .queryParam("fname", person1Name)
                                    .queryParam("sname", personName2)
                                    .toUriString();
        System.out.println(finalLoveCalculatorUrl);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        HttpHeaders headers = new HttpHeaders();
        String loverApiKey = System.getenv("LOVER_API_KEY");
        String loverApiHost = System.getenv("LOVER_API_HOST");
        
        headers.set("X-RapidAPI-Key", loverApiKey);
        headers.set("X-RapidAPI-Host", loverApiHost);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        resp = template.exchange(finalLoveCalculatorUrl,HttpMethod.GET, 
                    requestEntity, String.class);
        System.out.println(resp);
        LoverResult w = LoverResult.create(resp.getBody());
        System.out.println(w);
        if(w != null){
            redisTemplate.opsForValue().set(w.getId(), w);
            return Optional.of(w); 
        }
                                   
        return Optional.empty();
    }


}
