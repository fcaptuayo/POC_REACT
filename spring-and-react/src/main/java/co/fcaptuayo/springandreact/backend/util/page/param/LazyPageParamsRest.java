package co.fcaptuayo.springandreact.backend.util.page.param;

import co.fcaptuayo.springandreact.backend.util.page.filter.LazyPageFilter;
import lombok.Data;

import java.util.Map;

@Data
public class LazyPageParamsRest {
    Integer first;
    Integer rows;
    String sortField;
    Integer sortOrder;
    Map<String, LazyPageFilter> filters;
}
