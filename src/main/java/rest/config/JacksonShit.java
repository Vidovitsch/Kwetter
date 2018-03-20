package rest.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Jackson JSON processor could be controlled via providing a custom Jackson ObjectMapper instance.
 * This could be handy if you need to redefine the default Jackson behavior and to fine-tune how
 * your JSON data structures look like (copied from Jersey web site). *
 *
 * @see https://jersey.java.net/documentation/latest/media.html#d0e4799
 */

@Provider
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class JacksonShit implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        MAPPER.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
    }

    public JacksonShit() {
        Logger.getAnonymousLogger().log(Level.INFO, "Instantiate MyJacksonJsonProvider");
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        Logger.getAnonymousLogger().log(Level.INFO, "MyJacksonProvider.getContext() called with type: {0]", type);
        return MAPPER;
    }
}

