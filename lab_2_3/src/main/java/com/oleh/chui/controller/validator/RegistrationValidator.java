package com.oleh.chui.controller.validator;

import com.oleh.chui.controller.exception.PasswordsNotMatchException;
import com.oleh.chui.controller.exception.PersonAlreadyExistException;
import com.oleh.chui.model.service.PersonService;
import com.oleh.chui.model.service.ServiceFactory;

import java.util.Arrays;

public class RegistrationValidator {

    private static final PersonService personService = ServiceFactory.getInstance().createPersonService();

    public static void checkForPasswordsMatching(char[] password, char[] passwordCopy) throws PasswordsNotMatchException {
        if (!Arrays.equals(password, passwordCopy)) {
            throw new PasswordsNotMatchException(Message.PASSWORDS_DONT_MATCH);
        }
    }

    public static void checkForLoginIsFree(char[] login) throws PersonAlreadyExistException {
        if (personService.loginIsNotFree(login)) {
            throw new PersonAlreadyExistException(Message.PERSON_ALREADY_EXIST);
        }
    }

}
