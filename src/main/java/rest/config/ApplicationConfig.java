package rest.config;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import rest.resources.*;
import rest.auth.AuthResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.phillipkruger.apiee.ApieeService;

import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;
import java.util.logging.Level;


@javax.ws.rs.ApplicationPath("/rest")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();

        Logger.getAnonymousLogger().log(Level.INFO, "REST configuration starting: getClasses()");
        resources.add(JacksonJaxbJsonProvider.class);

        //instead let's do it manually:
        resources.add(UserResource.class);
        resources.add(KweetResource.class);
        resources.add(HashtagResource.class);
        resources.add(ApieeService.class);
        resources.add(AuthResource.class);
        //==> we could also choose packages, see below getProperties()
        resources.add(CorsFilter.class);
        resources.add(DeclarativeLinkingFeature.class);
        Logger.getAnonymousLogger().log(Level.INFO, "REST configuration ended successfully.");

        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.emptySet();
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();

        //in Jersey WADL generation is enabled by default, but we don't
        //want to expose too much information about our apis.
        //therefore we want to disable wadl (http://localhost:8080/service/application.wadl should return http 404)
        //see https://jersey.java.net/nonav/documentation/latest/user-guide.html#d0e9020 for details
        properties.put("jersey.config.server.wadl.disableWadl", true);
        properties.put("rest.config.CorsFilter",true);
        //we could also use something like this instead of adding each of our resources
        //explicitly in getClasses():

        return properties;
    }
}