package edu.byu.cs.tweeter.client.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.View.MainView;
import edu.byu.cs.tweeter.model.domain.Status;

public class MainPresenterUnitTest {

    private MainView mockView;
    private StatusService mockStatusService;
    private Cache mockCache;
    private Status mockStatus;

    private MainPresenter mainPresenterSpy;

    @Before
    public void setup() {
        // Create Mocks
        mockView = Mockito.mock(MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);
        mockStatus = Mockito.mock(Status.class);

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);

        Cache.setInstance(mockCache);
    }

    @Test
    public void testPostStaus_PostStatusSuccessful() {
        mainPresenterSpy.postStatus(mockStatus);
        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
    }

    @Test
    public void testPostStaus_PostStatusFailedWithMessage() {

    }

    @Test
    public void testPostStaus_PostStatusFailedWithException() {

    }

}
