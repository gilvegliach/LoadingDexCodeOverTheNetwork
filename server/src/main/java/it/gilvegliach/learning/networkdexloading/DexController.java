package it.gilvegliach.learning.networkdexloading;

import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
public class DexController {

    @RequestMapping(value = "/{file:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDex(@PathVariable String file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file);
        URL res = Resources.getResource(file);
        byte[] data = ByteStreams.toByteArray(res.openStream());
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
