package com.oleh.chui.controller.validator;

import com.oleh.chui.controller.exception.PriceIsNotNumberException;

public class PriceValidator {

    public static void checkForCorrectPrice(String priceString) throws PriceIsNotNumberException {
        try {
            double price = Double.parseDouble(priceString);
            if (price < 0) {
                throw new PriceIsNotNumberException(Message.PRICE_IS_NEGATIVE);
            }
        } catch (NumberFormatException e) {
            throw new PriceIsNotNumberException(Message.PRICE_IS_NOT_NUMBER);
        }
    }

}
