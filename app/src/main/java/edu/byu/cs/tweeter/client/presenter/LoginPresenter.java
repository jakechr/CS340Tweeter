package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;
import edu.byu.cs.tweeter.client.presenter.View.AuthenticationView;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticationPresenter{

    private UserService userService;

    public LoginPresenter(AuthenticationView view) {
        super(view);
        this.userService = new UserService();
    }

    @Override
    String getDescription() {
        return "Failed to login ";
    }

    public void login(String userAlias, String password) {
        userService.login(userAlias, password, new AuthenticationObserver());
    }


}
