package org.example.project_manager_dashboard.views.screens.productDetailStrategies;

import org.example.project_manager_dashboard.models.Book;
import org.example.project_manager_dashboard.models.CD;
import org.example.project_manager_dashboard.models.DVD;
import org.example.project_manager_dashboard.models.LP;
import org.example.project_manager_dashboard.models.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailStrategyFactory {
    private static final Map<Class<? extends Product>, ProductDetailStrategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put(Book.class, new BookDetailStrategy());
        strategyMap.put(CD.class, new CDDetailStrategy());
        strategyMap.put(LP.class, new LPDetailStrategy());
        strategyMap.put(DVD.class, new DVDDetailStrategy());
    }

    public static ProductDetailStrategy getStrategy(Class<? extends Product> productClass) {
        return strategyMap.get(productClass);
    }
}
