package com.example.gestionimmobilier.configuration;//package com.projet.gestionimmobilier.configuration;
//
//import com.projet.gestionimmobilier.Dtos.AppartementDto;
//import io.swagger.v3.oas.models.media.*;
//import io.swagger.v3.oas.models.parameters.RequestBody;
//import org.springdoc.core.customizers.OperationCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.Arrays;
//
//@Configuration
//public class MultipartSwaggerConfig {
//
//    @Bean
//    public OperationCustomizer multipartOperationCustomizer() {
//        return (operation, handlerMethod) -> {
//            if (handlerMethod.getMethodAnnotation(PostMapping.class) != null
//                    && Arrays.asList(handlerMethod.getMethodAnnotation(PostMapping.class).consumes())
//                    .contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
//
//                // Create complete schema for AppartementDto
//                Schema appartementSchema = new Schema()
//                        .type("object")
//                        .addProperty("id", new IntegerSchema().example(1))
//                        .addProperty("numero", new NumberSchema().example(12))
//                        .addProperty("etage", new NumberSchema().example(3))
//                        .addProperty("nombrePieces", new NumberSchema().example(2))
//                        .addProperty("superficie", new NumberSchema().example(75))
//                        .addProperty("prix", new NumberSchema().example(1200))
//                        .addProperty("meuble", new BooleanSchema().example(true))
//                        .addProperty("aVendre", new BooleanSchema().example(false))
//                        .addProperty("aLouer", new BooleanSchema().example(true))
//                        .addProperty("description", new StringSchema().example("Bel appartement lumineux"))
//                        .addProperty("immeuble", new Schema()
//                                .type("object")
//                                .addProperty("id", new IntegerSchema().example(1)));
//
//                // Create media type for multipart
//                io.swagger.v3.oas.models.media.MediaType mediaType = new io.swagger.v3.oas.models.media.MediaType();
//                mediaType.schema(new Schema()
//                        .type("object")
//                        .addProperty("appartement", appartementSchema)
//                        .addProperty("images", new ArraySchema()
//                                .items(new Schema()
//                                        .type("string")
//                                        .format("binary")
//                                        .description("Fichier image"))));
//
//                operation.requestBody(new RequestBody()
//                        .content(new Content()
//                                .addMediaType(MediaType.MULTIPART_FORM_DATA_VALUE, mediaType)));
//            }
//            return operation;
//        };
//    }
//}