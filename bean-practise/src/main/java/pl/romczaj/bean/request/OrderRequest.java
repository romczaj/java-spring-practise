package pl.romczaj.bean.request;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
class OrderRequest {
    Long carId;
}
