package edu.byu.cs.tweeter.client.presenter;

import android.widget.EditText;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter {

    public interface View {
        void successfulLogin(User user);
        void displayErrorMessage(String message);
    }

    private View view;
    private UserService userService;

    public LoginPresenter(View view) {
        this.view = view;
        this.userService = new UserService();
    }

    public void login(String userAlias, String password) {
        userService.login(userAlias, password, new LoginObserver());
    }

    public class LoginObserver implements UserService.LoginObserver {

        @Override
        public void handleSuccess(User user) {
            view.successfulLogin(user);
        }

        @Override
        public void handleFailure(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(String message) {
            view.displayErrorMessage(message);
        }
    }

    public void validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }


}
