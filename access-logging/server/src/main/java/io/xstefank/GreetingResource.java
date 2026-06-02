package io.xstefank;

import java.time.Duration;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataOutput;

@Path("/")
public class GreetingResource {

    @POST
    @Path("/single")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(String body) {
        return "echo: " + body;
    }

    @GET
    @Path("/single")
    @Produces(MediaType.TEXT_PLAIN)
    public String single() {
        return """
            lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.
            Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. Donec et mollis dolor. Praesent et
            diam eget libero egestas mattis sit amet vitae augue. Nam tincidunt congue enim, ut porta lorem lacinia
            consectetur. Donec ut libero sed arcu vehicula ultricies a non tortor. Lorem ipsum dolor sit amet,
            consectetur adipiscing elit. Aenean ut gravida lorem.
            """;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String upload(@RestForm String name, @RestForm String description, @RestForm FileUpload file) {
        String filePart = file != null
                ? " file=" + file.fileName() + " (" + file.size() + " bytes)"
                : " no-file";
        return "received: name=" + name + " description=" + description + filePart;
    }

    /**
     * Multipart response using the explicit {@link MultipartFormDataOutput} API.
     * Covers a short text part, a long text part (truncated in the log), and a binary part.
     */
    @GET
    @Path("/multipart-output")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public MultipartFormDataOutput multipartOutput() {
        MultipartFormDataOutput output = new MultipartFormDataOutput();
        output.addFormData("greeting", "Hello, multipart!", MediaType.TEXT_PLAIN_TYPE);
        output.addFormData("description",
                "This is a rather long description that should be truncated in the log output because it exceeds the configured body limit.",
                MediaType.TEXT_PLAIN_TYPE);
        output.addFormData("data", new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 }, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        return output;
    }

    /**
     * Multipart response using a {@code @RestForm}-annotated POJO.
     * The build-time generated mapper converts it to {@link MultipartFormDataOutput} for logging.
     */
    @GET
    @Path("/multipart-pojo")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public MultipartPojoResponse multipartPojo() {
        MultipartPojoResponse response = new MultipartPojoResponse();
        response.name = "Alice";
        response.message = "This is a rather long message that should be truncated in the log output because it exceeds the configured body limit.";
        response.data = new byte[] { 10, 20, 30, 40, 50 };
        return response;
    }

    @GET
    @Path("/streaming")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> streaming() {
        return Multi.createFrom().ticks().every(Duration.ofMillis(5000)).map(i -> "tick " + i);
    }

    public static class MultipartPojoResponse {

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        public String name;

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        public String message;

        @RestForm
        @PartType(MediaType.APPLICATION_OCTET_STREAM)
        public byte[] data;
    }
}
