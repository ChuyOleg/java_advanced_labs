package com.oleh.chui.controller.validator;

import com.oleh.chui.controller.exception.AuthenticationException;
import com.oleh.chui.controller.exception.UserIsBlockedException;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.service.PersonService;
import com.oleh.chui.model.service.ServiceFactory;

public class LoginValidator {

    private static final PersonService personService = ServiceFactory.getInstance().createPersonService();

    public static void checkForCorrectAuthentication(String login, char[] password) throws AuthenticationException {
        Person person = personService.findByLoginAndPassword(login, password);

        if (person.getId() == 0) {
            throw new AuthenticationException(Message.LOGIN_PASSWORD_DONT_MATCH);
        }
    }

    public static void checkForUserIsNotBlocked(Person person) throws UserIsBlockedException {
        if (person.getBlocked()) {
            throw new UserIsBlockedException(Message.USER_IS_BLOCKED);
        }
    }

}
