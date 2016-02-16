package it.gilvegliach.learning.networkdexloading;

import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
public class DexController {

    @RequestMapping(value = "/{file:.+}", method = RequestMethod.GET, produces = "application/octet-stream")
    public byte[] getDex(@PathVariable String file) throws IOException {
        URL res = Resources.getResource(file);
        return ByteStreams.toByteArray(res.openStream()); // Content-Disposition is still wrong
    }
}
