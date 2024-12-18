package de.telran.onlineshop.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private Long productID;
    private String name;
    private String description;
    private double price;
    private CategoryDto categoryDto;
    private String imageURL;
    private double discountPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto productDto = (ProductDto) o;
        return productID == productDto.productID && Double.compare(price, productDto.price) == 0 && Double.compare(discountPrice, productDto.discountPrice) == 0 && Objects.equals(name, productDto.name) && Objects.equals(description, productDto.description) && Objects.equals(imageURL, productDto.imageURL) && Objects.equals(createdAt, productDto.createdAt) && Objects.equals(updatedAt, productDto.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, name, description, price, imageURL, discountPrice, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                ", discountPrice=" + discountPrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}