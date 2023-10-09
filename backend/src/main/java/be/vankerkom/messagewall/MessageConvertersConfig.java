package be.vankerkom.messagewall;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MessageConvertersConfig implements WebMvcConfigurer {

    HttpMessageConverter messagePackMessageConverter() {
        MediaType msgPackMediaType = new MediaType("application", "x-msgpack"); //
        return new AbstractJackson2HttpMessageConverter(new ObjectMapper(new MessagePackFactory()), msgPackMediaType) {
        };
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(messagePackMessageConverter());
    }

}
