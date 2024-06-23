package org.example.project_manager_dashboard.productFactories;

public class ProductFactoryProvider {
    public static ProductFactory getProductFactory(String category) {
        switch (category) {
            case "book":
                return new BookFactory();
            case "cd":
                return new CDFactory();
            case "dvd":
                return new DVDFactory();
            case "lp":
                return new LPFactory();
            default:
                throw new IllegalArgumentException("Invalid product category: " + category);
        }
    }
}
