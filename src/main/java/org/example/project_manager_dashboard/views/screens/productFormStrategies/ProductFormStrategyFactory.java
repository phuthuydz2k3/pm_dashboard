package org.example.project_manager_dashboard.views.screens.productFormStrategies;

import java.util.HashMap;
import java.util.Map;

public class ProductFormStrategyFactory {
    private static final Map<String, ExtraFieldsStrategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put("book", new BookFieldsStrategy());
        strategyMap.put("cd", new CDFieldsStrategy());
        strategyMap.put("dvd", new DVDFieldsStrategy());
        strategyMap.put("lp", new LPFieldsStrategy());
    }

    public static ExtraFieldsStrategy getStrategy(String product) {
        return strategyMap.get(product);
    }
}
