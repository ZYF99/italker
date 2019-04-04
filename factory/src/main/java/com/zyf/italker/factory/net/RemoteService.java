package com.zyf.italker.factory.net;


import com.zyf.italker.factory.model.api.RspModel;
import com.zyf.italker.factory.model.api.account.AccountRspModel;
import com.zyf.italker.factory.model.api.account.LoginModel;
import com.zyf.italker.factory.model.api.account.RegisterModel;
import com.zyf.italker.factory.model.api.user.UserUpdateModel;
import com.zyf.italker.factory.model.card.UserCard;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 **/
public interface RemoteService {

    /**
     * 网络请求一个注册接口
     *
     * @param model 传入的是RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登陆接口
     *
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    /**
     * 用户更新的接口
     *
     * @param model UserUpdateMoedel
     * @return RspModel<UserCard>
     */
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    /**
     * 用户搜索的接口
     *
     * @param name String
     * @return RspModel<UserCard>
     */
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    /**
     * 用户关注的接口
     *
     * @param userId String
     * @return RspModel<UserCard>
     */
    @PUT("user/follow/{userId}")
    Call<RspModel<UserCard>> userFollow(@Path("userId") String userId);

    /**
     * 获取联系人列表
     *
     * @param
     * @return RspModel<UserCard>
     */
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> userContacts();

    /**
     * 获取一个人的信息
     *
     * @param
     * @return RspModel<UserCard>
     */
    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);



}
