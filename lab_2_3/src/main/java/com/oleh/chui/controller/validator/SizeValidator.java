package com.oleh.chui.controller.validator;

import com.oleh.chui.controller.exception.NonExistentSizeException;
import com.oleh.chui.model.entity.Product;

public class SizeValidator {

    public static void checkForCorrectSize(String size) throws NonExistentSizeException {
        try {
            Product.Size.valueOf(size);
        } catch (RuntimeException e) {
            throw new NonExistentSizeException(Message.INCORRECT_SIZE);
        }
    }

}
