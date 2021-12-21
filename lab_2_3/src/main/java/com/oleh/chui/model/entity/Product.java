package com.oleh.chui.model.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int id;

    private String name;

    private BigDecimal price;

    private String category;

    private LocalDate startDate;

    private Size size;

    public enum Size {
        SMALL,
        MEDIUM,
        LARGE;

        public String getValue() {
            return this.name();
        }
    }

    public Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.category = builder.category;
        this.startDate = builder.startDate;
        this.size = builder.size;
    }

    public static class Builder {
        private int id;

        private String name;

        private BigDecimal price;

        private String category;

        private LocalDate startDate;

        private Size size;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setSize(Size size) {
            this.size = size;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }

}
