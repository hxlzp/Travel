package com.example.hxl.travel.model.net;

import com.example.hxl.travel.model.bean.FootprintMessageList;
import com.example.hxl.travel.model.bean.GroupAdd;
import com.example.hxl.travel.model.bean.GroupBuild;
import com.example.hxl.travel.model.bean.GroupList;
import com.example.hxl.travel.model.bean.GroupMember;
import com.example.hxl.travel.model.bean.Login;
import com.example.hxl.travel.model.bean.MapMessage;
import com.example.hxl.travel.model.bean.Register;
import com.example.hxl.travel.model.bean.ServicePointMessage;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hxl on 2017/6/14 at haiChou.
 */
public interface RetrofitApi {
    //注册112.12.36.86:8008/travel_app/user/userRegister.do
    @FormUrlEncoded
    @POST("travel_app/user/userRegister.do")
    Observable<Register> register(@Field("userName") String userName, @Field("password") String password);
    //登陆http://112.12.36.86:8008/travel_app/user/userLogin.do
    @FormUrlEncoded
    @POST("travel_app/user/userLogin.do")
    Observable<Login> login(@Field("userName") String userName, @Field("password") String password);

    //创建群组url：travel_app/html/visitorGroup/createVisitorGroup.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/createVisitorGroup.do")
    Observable<GroupBuild> groupBuild(@Field("user_id") String user_id,@Field("group_nickname") String group_nickname);

    //群列表url:travel_app/html/visitorGroup/findAllVisitorGroupByUserId.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/findAllVisitorGroupByUserId.do")
    Observable<GroupList> observableGroupList(@Field("user_id") String user_id );

    //加入群组url:travel_app/html/visitorGroup/addVisitorGroupMember.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/addVisitorGroupMember.do")
    Observable<GroupAdd> observableGroupAdd(@Field("user_id") String user_id,@Field("group_nickname") String group_nickname);

    //邀请入群url:travel_app/html/visitorGroup/inviteVisitorGroupMember.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/inviteVisitorGroupMember.do")
    Observable<GroupAdd> observableGroupInvite(@Field("group_id") String group_id,@Field("user_name") String user_name);

    //退出群组url:travel_app/html/visitorGroup/delete_visitorGroupByGroupIdAndUserId.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/delete_visitorGroupByGroupIdAndUserId.do")
    Observable<GroupAdd> observableQuitGroup(@Field("group_id") String group_id,@Field("user_id") String user_id);

    //解散群组url: travel_app/html/visitorGroup/dismissVisitorGroup.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/dismissVisitorGroup.do")
    Observable<GroupAdd> observableGroupDissolve(@Field("group_id") String group_id);

    //群组好友列表url:travel_app/html/visitorGroup/findAllMemberByGroupId.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/findAllMemberByGroupId.do")
    Observable<GroupMember> observableGroupMember(@Field("group_id") String group_id);

    //踢成员url:travel_app/html/visitorGroup/deleteVisitorGroupMemberByGroupIdAndUserIds.do
    @FormUrlEncoded
    @POST("travel_app/html/visitorGroup/deleteVisitorGroupMemberByGroupIdAndUserIds.do")
    Observable<GroupAdd> observableGroupDelete(@Field("group_id") String group_id,@Field("user_ids") String user_ids);


    //当前坐标信息http://112.12.36.86:8008/travel_app/html/imitate/saveLocus.do
    //http://112.12.36.86:8008/travel_app/html/imitate/saveLocus.do?user_id=5&group_id=222
    // &user_name=guide1&longitude=120253507&latitude=30244982&user_type=2&interval_time=3&view_time=123455677777
    @FormUrlEncoded
    @POST("travel_app/html/imitate/saveLocus.do")
    Observable<MapMessage> observableMapMessage(@Field("user_id") String user_id,@Field("group_id") String group_id,@Field("user_name") String user_name,
                                                @Field("longitude") String longitude,@Field("latitude") String latitude,@Field("user_type") String user_type,
                                                @Field("interval_time") String interval_time,@Field("view_time") String view_time);

    //获取当前服务点的文本、图片、音频、视频信息
    //url:travel_app/html/scenic/getServicePointResource.do
    @FormUrlEncoded
    @POST("travel_app/html/scenic/getServicePointResource.do")
    Observable<ServicePointMessage> observableServicePointMessage(@Field("servicePointId") String servicePointId);

    //足迹列表
    //travel_app/html/scenic/getScenicMessage.do
    @FormUrlEncoded
    @POST("travel_app/html/scenic/getScenicMessage.do")
    Observable<FootprintMessageList> observableFootprintMessageList(@Field("scenicName" )String scenicName);

}
