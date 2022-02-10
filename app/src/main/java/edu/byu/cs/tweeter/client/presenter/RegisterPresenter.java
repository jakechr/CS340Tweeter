package edu.byu.cs.tweeter.client.presenter;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;
import edu.byu.cs.tweeter.client.view.login.RegisterFragment;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {

    public interface View {
        void loginNewUser(User user, String message);
        void displayErrorMessage(String message);
    }

    private RegisterPresenter.View view;
    private UserService userService;

    public RegisterPresenter(View view) {
        this.view = view;
        this.userService = new UserService();
    }

    public void register(Bitmap image, String firstName, String lastName, String alias, String password) {
        userService.register(image, firstName, lastName, alias, password, new RegisterObserver());
    }

    public class RegisterObserver implements SimpleItemObserver<User> {

        @Override
        public void handleSuccess(User user) {
            view.loginNewUser(user, "Hello " + Cache.getInstance().getCurrUser().getName());
        }

        @Override
        public void handleError(String message) {
            view.displayErrorMessage(message);
        }
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password, Drawable imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (imageToUpload == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }
}
