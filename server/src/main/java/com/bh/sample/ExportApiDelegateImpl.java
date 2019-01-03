package com.bh.sample;

import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ExportApiDelegateImpl implements ExportApiDelegate {
    @Value("classpath:contracts/sample/ui/exit101.zip")
    private Resource resource;

    @Override
    public ResponseEntity<Resource> exportConfiguration() {
        try (final InputStream is = resource.getInputStream()) {
            final byte[] bytes = ByteStreams.toByteArray(is);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "configuration.zip")
                    .body(new ByteArrayResource(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
