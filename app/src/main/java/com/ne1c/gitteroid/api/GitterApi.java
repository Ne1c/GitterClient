package com.ne1c.gitteroid.api;

import com.ne1c.gitteroid.api.responses.JoinRoomResponse;
import com.ne1c.gitteroid.api.responses.StatusResponse;
import com.ne1c.gitteroid.models.data.AuthResponseModel;
import com.ne1c.gitteroid.models.data.MessageModel;
import com.ne1c.gitteroid.models.data.RoomModel;
import com.ne1c.gitteroid.models.data.SearchRoomsResponse;
import com.ne1c.gitteroid.models.data.UserModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface GitterApi {

    @GET("/v1/rooms")
    Observable<ArrayList<RoomModel>> getCurrentUserRooms(@Header("Authorization") String access_token);

    @GET("/v1/rooms/{roomId}/chatMessages")
    Observable<ArrayList<MessageModel>> getMessagesRoom(@Header("Authorization") String access_token,
                                                        @Path("roomId") String roomId,
                                                        @Query("limit") int limit);

    @FormUrlEncoded
    @POST("/v1/rooms/{roomId}/chatMessages")
    Observable<MessageModel> sendMessage(@Header("Authorization") String access_token,
                                         @Path("roomId") String roomId,
                                         @Field("text") String text);

    @FormUrlEncoded
    @PUT("/v1/rooms/{roomId}/chatMessages/{chatMessageId}")
    Observable<MessageModel> updateMessage(@Header("Authorization") String access_token,
                                           @Path("roomId") String roomId,
                                           @Path("chatMessageId") String chatMessageId,
                                           @Field("text") String messageText);

    @GET("/v1/user")
    Observable<ArrayList<UserModel>> getCurrentUser(@Header("Authorization") String access_token);

    @GET("/v1/rooms/{roomId}/chatMessages")
    Observable<ArrayList<MessageModel>> getMessagesBeforeId(@Header("Authorization") String access_token,
                                                            @Path("roomId") String roomId,
                                                            @Query("limit") int limit,
                                                            @Query("beforeId") String beforeId);

    @FormUrlEncoded
    @POST
    Observable<AuthResponseModel> authorization(@Url String authUrl,
                                                @Field("client_id") String client_id,
                                                @Field("client_secret") String client_secret,
                                                @Field("code") String code,
                                                @Field("grant_type") String grant_type,
                                                @Field("redirect_uri") String redirect_uri);

    @FormUrlEncoded
    @POST("/v1/user/{userId}/rooms/{roomId}/unreadItems")
    Observable<StatusResponse> readMessages(@Header("Authorization") String access_token,
                                            @Path("userId") String userId,
                                            @Path("roomId") String roomId,
                                            @Field("chat") String[] chat);

    @DELETE("/v1/rooms/{roomId}/users/{userId}")
    Observable<StatusResponse> leaveRoom(@Header("Authorization") String access_token,
                                         @Path("roomId") String roomId,
                                         @Path("userId") String userId);

    @POST("/v1/rooms")
    @FormUrlEncoded
    Observable<JoinRoomResponse> joinRoom(@Header("Authorization") String access_token,
                                          @Field("uri") String roomUri);

    @GET("/v1/user")
    Observable<ResponseBody> searchUsers(@Header("Authorization") String access_token,
                                         @Query("q") String searchTerm);

    @GET("/v1/rooms")
    Observable<SearchRoomsResponse> searchRooms(@Header("Authorization") String access_token,
                                                @Query("q") String query,
                                                @Query("limit") int limit,
                                                @Query("offset") int offset); // Offset not working
}