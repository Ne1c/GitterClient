package com.ne1c.developerstalk.presenters;

import android.content.Context;

import com.ne1c.developerstalk.MockRxSchedulersFactory;
import com.ne1c.developerstalk.models.MessageModel;
import com.ne1c.developerstalk.services.DataManger;
import com.ne1c.developerstalk.ui.views.ChatView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import retrofit.client.Response;
import retrofit.mime.TypedInput;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatRoomPresenterTest {
    private static final String ROOM_ID = "jf9w4j3fmn389f394n";
    private static final String MESSAGE_TEXT = "message";
    private static final String ERROR = "text_with_error";
    @Mock
    ChatView view;
    @Mock
    DataManger dataManger;
    private ChatRoomPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new ChatRoomPresenter(new MockRxSchedulersFactory(), dataManger);
        presenter.bindView(view);

        when(view.getAppContext()).thenReturn(mock(Context.class));
    }

    @Test
    public void successLoadCachedMessages() {
        when(dataManger.getCachedMessages(anyString()))
                .thenReturn(Observable.just(mock(ArrayList.class)));

        presenter.loadCachedMessages(ROOM_ID);

        verify(view, times(1)).showMessages(any(ArrayList.class));
        verify(view, never()).showError(anyString());
    }

    @Test
    public void failLoadCachedMessages() {
        when(dataManger.getCachedMessages(anyString()))
                .thenReturn(Observable.error(new Throwable(ERROR)));

        presenter.loadCachedMessages(anyString());

        verify(view, times(1)).showError(anyString());
    }

    @Test
    public void successSendMessage() {
        when(dataManger.sendMessage(anyString(), anyString()))
                .thenReturn(Observable.just(mock(MessageModel.class)));

        presenter.sendMessage(ROOM_ID, MESSAGE_TEXT);

        verify(view, times(1)).deliveredMessage(any(MessageModel.class));
        verify(view, never()).showError(anyString());
    }

    @Test
    public void failSendMessage() {
        when(dataManger.sendMessage(anyString(), anyString()))
                .thenReturn(Observable.error(new Throwable(ERROR)));

        presenter.sendMessage(ROOM_ID, MESSAGE_TEXT);

        verify(view, times(1)).errorDeliveredMessage();
    }

    @Test
    public void successLoadMessagesBeforeId() {
        when(dataManger.getMessagesBeforeId(anyString(), anyInt(), anyString()))
                .thenReturn(Observable.just(mock(ArrayList.class)));

        presenter.loadMessagesBeforeId(ROOM_ID, 100500, ROOM_ID);

        verify(view, times(1)).successLoadBeforeId(any(ArrayList.class));
        verify(view, never()).showError(anyString());
    }

    @Test
    public void failLoadMessagesBeforeId() {
        when(dataManger.getMessagesBeforeId(anyString(), anyInt(), anyString()))
                .thenReturn(Observable.error(new Throwable(ERROR)));

        presenter.loadMessagesBeforeId(ROOM_ID, 100500, ROOM_ID);

        verify(view, never()).successLoadBeforeId(any(ArrayList.class));
        verify(view, times(1)).showError(anyString());
    }

    @Test
    public void successLoadMessages() {
        when(dataManger.getMessages(anyString(), anyInt()))
                .thenReturn(Observable.just(mock(ArrayList.class)));

        presenter.loadMessages(ROOM_ID, 100500);

        verify(view, times(1)).showMessages(any(ArrayList.class));
        verify(view, never()).showError(anyString());
    }

    @Test
    public void failLoadMessages() {
        when(dataManger.getMessages(anyString(), anyInt()))
                .thenReturn(Observable.error(new Throwable(ERROR)));

        presenter.loadMessages(ROOM_ID, 100500);

        verify(view, never()).showMessages(any(ArrayList.class));
        verify(view, times(1)).showError(anyString());
    }

    @Test
    public void successUploadMessages() {
        MessageModel message = mock(MessageModel.class);

        when(dataManger.updateMessage(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(message));

        presenter.updateMessages(ROOM_ID, ROOM_ID, MESSAGE_TEXT);

        verify(dataManger, times(1)).insertMessageToDb(message, ROOM_ID);
        verify(view, times(1)).successUpdate(message);
        verify(view, never()).showError(anyString());
    }

    @Test
    public void failUploadMessages() {
        MessageModel message = mock(MessageModel.class);

        when(dataManger.updateMessage(anyString(), anyString(), anyString()))
                .thenReturn(Observable.error(new Throwable(ERROR)));

        presenter.updateMessages(ROOM_ID, ROOM_ID, MESSAGE_TEXT);

        verify(dataManger, never()).insertMessageToDb(message, ROOM_ID);
        verify(view, never()).successUpdate(message);
        verify(view, times(1)).showError(anyString());
    }

    @Test
    public void successMarkMessageAsRead() {
        Response resp = new Response("", 200, "", Collections.emptyList(), mock(TypedInput.class));
        String[] ids = new String[5];

        when(dataManger.readMessages(ROOM_ID, ids)).thenReturn(Observable.just(resp));

        presenter.markMessageAsRead(100500, 100500, ROOM_ID, ids);

        verify(view, times(1)).successRead(anyInt(), anyInt(), anyString(), anyInt());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void failMarkMessageAsRead() {
        String[] ids = new String[5];

        when(dataManger.readMessages(ROOM_ID, ids)).thenReturn(Observable.error(new Throwable(ERROR)));

        presenter.markMessageAsRead(100500, 100500, ROOM_ID, ids);

        verify(view, never()).successRead(anyInt(), anyInt(), anyString(), anyInt());
        verify(view, times(1)).showError(anyString());
    }

    @After
    public void end() {
        presenter.unbindView();
    }
}
