package edu.byu.cs.tweeter.client.model.service;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.AuthenticationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends BaseService {

    public void login(String userAlias, String password, SimpleItemObserver<User> loginObserver) {
        // Send the login request.
        LoginTask loginTask = new LoginTask(userAlias,
                password, new AuthenticationHandler(loginObserver));
        executeTask(loginTask);
    }

    public void register(Bitmap image, String firstName, String lastName, String alias, String password,
                         SimpleItemObserver<User> registerObserver) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();

        // Intentionally, Use the java Base64 encoder so it is compatible with M4.
        String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

        // Send register request.
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new AuthenticationHandler(registerObserver));

        executeTask(registerTask);
    }

    public void logout(AuthToken currUserAuthToken, SimpleNotificationObserver logoutObserver) {
        LogoutTask logoutTask = new LogoutTask(currUserAuthToken, new SimpleNotificationHandler(logoutObserver));
        executeTask(logoutTask);
    }
}
