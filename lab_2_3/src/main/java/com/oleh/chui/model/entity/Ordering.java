package com.oleh.chui.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ordering {

    private int id;

    private int productId;

    private int personId;

    private Status status;

    public enum Status {
        REGISTERED,
        PAID,
        CANCELED;

        public String getValue() {
            return this.name();
        }
    }

}
