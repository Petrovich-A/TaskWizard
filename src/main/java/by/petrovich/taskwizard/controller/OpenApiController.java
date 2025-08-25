package by.petrovich.taskwizard.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class OpenApiController {
    @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
    public String getOpenApiYaml() throws IOException {
        Resource resource = new ClassPathResource("openapi.yaml");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
