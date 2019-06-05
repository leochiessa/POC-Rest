package LEO.Practica.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetSocketAddress;
import java.util.Map;

@RestController
@RequestMapping("")
public class Health {

    @GetMapping("")
    public String getHealth() {
        return "Todo OK.";
    }

    @GetMapping("/getBaseUrl")
    public ResponseEntity<String> getBaseUrl(@RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();
        String url = host.getHostName() + ":" + host.getPort();
        return new ResponseEntity<>(String.format("URL = %s", url), HttpStatus.OK);
    }

    @GetMapping("/listHeaders")
    public ResponseEntity<String> listHeaders(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
        return new ResponseEntity<>(String.format("Listed %d headers.", headers.size()), HttpStatus.OK);
    }
}