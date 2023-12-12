package pl.wyrazowka.server;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dictionary")
public class Config {
    private final String path;

    public String getPath() {
        return this.path;
    }

    public Config(String path) {
        this.path = path;
    }
}
