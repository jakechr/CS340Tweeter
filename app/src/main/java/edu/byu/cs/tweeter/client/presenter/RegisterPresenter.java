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
import edu.byu.cs.tweeter.client.presenter.View.AuthenticationView;
import edu.byu.cs.tweeter.client.view.login.RegisterFragment;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AuthenticationPresenter {

    private UserService userService;

    public RegisterPresenter(AuthenticationView view) {
        super(view);
        this.userService = new UserService();
    }

    @Override
    String getDescription() {
        return "Failed to register user";
    }

    public void register(Bitmap image, String firstName, String lastName, String alias, String password) {
        userService.register(image, firstName, lastName, alias, password, new AuthenticationObserver());
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password, Drawable imageToUpload) {
        validateLoginInfo(alias, password);
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }

        if (imageToUpload == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }
}
