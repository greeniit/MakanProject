package com.makan.app.web;

import com.makan.app.app.WebConstant;
import com.makan.app.web.pojo.AdvertisementResponse;
import com.makan.app.web.pojo.BookPropertyRequest;
import com.makan.app.web.pojo.BookPropertyResponse;
import com.makan.app.web.pojo.DealerDetailRequest;
import com.makan.app.web.pojo.DealerDetailResponse;
import com.makan.app.web.pojo.DealerResponse;
import com.makan.app.web.pojo.FeedbackRequest;
import com.makan.app.web.pojo.FeedbackResponse;
import com.makan.app.web.pojo.FilterSearchRequest;
import com.makan.app.web.pojo.FilterSearchResponse;
import com.makan.app.web.pojo.FindDealsResponse;
import com.makan.app.web.pojo.ForgotPasswordRequest;
import com.makan.app.web.pojo.ForgotPasswordResponse;
import com.makan.app.web.pojo.GetCategoryPropertyRequest;
import com.makan.app.web.pojo.GetCategoryPropertyResponse;
import com.makan.app.web.pojo.GetCategoryResponse;
import com.makan.app.web.pojo.GetPropertiesByTypeResponse;
import com.makan.app.web.pojo.GetPropertyByPlaceRequest;
import com.makan.app.web.pojo.GetPropertyByPlaceResponse;
import com.makan.app.web.pojo.GetPropertyByTypeRequest;
import com.makan.app.web.pojo.HomeRequest;
import com.makan.app.web.pojo.HomeResponse;
import com.makan.app.web.pojo.NewsResponse;
import com.makan.app.web.pojo.PropertyDetailRequest;
import com.makan.app.web.pojo.PropertyDetailResponse;
import com.makan.app.web.pojo.SearchByNameRequest;
import com.makan.app.web.pojo.SearchByNameResponse;
import com.makan.app.web.pojo.SignInRequest;
import com.makan.app.web.pojo.SignInResponse;
import com.makan.app.web.pojo.SignUpRequest;
import com.makan.app.web.pojo.SignUpResponse;
import com.makan.app.web.pojo.SocialMediaRequest;
import com.makan.app.web.pojo.SocialMediaResponse;
import com.makan.app.web.pojo.SubscribeBusinessRequest;
import com.makan.app.web.pojo.SubscribeBusinessResponse;
import com.makan.app.web.pojo.UploadImageRequest;
import com.makan.app.web.pojo.UploadImageResponse;
import com.makan.app.web.pojo.WishListOperationRequest;
import com.makan.app.web.pojo.WishListOperationResponse;
import com.makan.app.web.pojo.WishListRequest;
import com.makan.app.web.pojo.WishListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @POST(WebConstant.SIGN_UP)
    Call<SignUpResponse> callSignUpWebService(@Body SignUpRequest signUpRequest);

    @POST(WebConstant.SIGN_IN)
    Call<SignInResponse> callSignInWebService(@Body SignInRequest signInRequest);

    @POST(WebConstant.HOME)
    Call<HomeResponse> callHomeWebService(@Body HomeRequest homeRequest);

    @POST(WebConstant.AGENCY)
    Call<DealerResponse> callDealerService();

    @POST(WebConstant.CATEGORY)
    Call<GetCategoryResponse> callGetCategoryService();

    @POST(WebConstant.NEWS)
    Call<NewsResponse> callNewsWebService();

    @POST(WebConstant.PROPERTY_DETAIL)
    Call<PropertyDetailResponse> callPropertyDetail(@Body PropertyDetailRequest propertyDetailRequest);

    @POST(WebConstant.WISH_LIST)
    Call<WishListResponse> callWishList(@Body WishListRequest wishListRequest);

    @POST(WebConstant.WISH_LIST_ADD)
    Call<WishListOperationResponse> callAddToWishList(@Body WishListOperationRequest wishListOperationRequest);

    @POST(WebConstant.WISH_LIST_DELETE)
    Call<WishListOperationResponse> callDeleteFromWishList(@Body WishListOperationRequest wishListOperationRequest);

    @POST(WebConstant.CATEGORY_PROPERTY_LIST)
    Call<GetCategoryPropertyResponse> callGetPropertiesByCategory(@Body GetCategoryPropertyRequest getCategoryPropertyRequest);

    @POST(WebConstant.SEND_FEEDBACK)
    Call<FeedbackResponse> sendFeedback(@Body FeedbackRequest feedbackRequest);

    @POST(WebConstant.DEALER_DETAIL)
    Call<DealerDetailResponse> dealerDetail(@Body DealerDetailRequest dealerDetailRequest);

    @POST(WebConstant.PROPERTY_BY_TYPE)
    Call<GetPropertiesByTypeResponse> getPropertyByType(@Body GetPropertyByTypeRequest getPropertyByTypeRequest);

    @GET(WebConstant.FIND_DEALS)
    Call<FindDealsResponse> findDeals();

    @POST(WebConstant.GET_PROPERTY_BY_PLACE)
    Call<GetPropertyByPlaceResponse> getPropertiesByPlace(@Body GetPropertyByPlaceRequest getPropertyByPlaceRequest);

    @GET(WebConstant.GET_ADVERTISEMENTS)
    Call<AdvertisementResponse> callAdds();

    @POST(WebConstant.GET_SEARCH_RESULT_BY_FILTER)
    Call<FilterSearchResponse> getSearchResultByFilter(@Body FilterSearchRequest filterSearchRequest);

    @POST(WebConstant.SOCIAL_MEDIA_SIGN_UP)
    Call<SocialMediaResponse> socialMediaSignUp(@Body SocialMediaRequest socialMediaRequest);

    @POST(WebConstant.BUSINESS)
    Call<SubscribeBusinessResponse> subscribeForMakanBusiness(@Body SubscribeBusinessRequest subscribeBusinessRequest);

    @POST(WebConstant.BOOK_PROPERTY)
    Call<BookPropertyResponse> callBookProperty(@Body BookPropertyRequest bookPropertyRequest);

    @POST(WebConstant.SEARCH_BY_NAME)
    Call<SearchByNameResponse> callSearchByName(@Body SearchByNameRequest searchByNameRequest);

    @POST(WebConstant.FORGOT_PASSWORD)
    Call<ForgotPasswordResponse> requestPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST(WebConstant.UPLOAD_IMAGE)

    Call<UploadImageResponse> uploadImage(@Body UploadImageRequest uploadImageRequest);

}
