package pl.romczaj.batch.batch;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("custom.batch")
public record CustomBatchProperties(
        int chunkSize,
        int defaultGenerateTransactionsSize

) {
    @ConstructorBinding
    public CustomBatchProperties(int chunkSize, int defaultGenerateTransactionsSize) {
        this.chunkSize = chunkSize;
        this.defaultGenerateTransactionsSize = defaultGenerateTransactionsSize;
    }
}
