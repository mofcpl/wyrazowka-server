package pl.wyrazowka.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    private final String dictionaryPath;

    Config(@Value("${app.dictionary.path}") String path) {
        this.dictionaryPath = path;
    }

    String getPath() {
        return dictionaryPath;
    }
}
