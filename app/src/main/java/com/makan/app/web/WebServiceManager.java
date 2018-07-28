package com.makan.app.web;

import com.makan.app.app.AppLog;
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

import retrofit2.Response;

public class WebServiceManager {

    private RestApi service;
    private static WebServiceManager webServiceManager=null;

    private WebServiceManager(){

        service = Connector.getInstance().getConnector().create(RestApi.class);
    }

    public static WebServiceManager getInstance(){

        if(webServiceManager==null){
            webServiceManager=new WebServiceManager();
        }

        return webServiceManager;

    }

    public Response<SignUpResponse> signUp(SignUpRequest signUpRequest) {

        try {
            return service.callSignUpWebService(signUpRequest)
                    .execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<SignInResponse> signIn(SignInRequest signInRequest) {

        try {
            return service.callSignInWebService(signInRequest)
                    .execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<HomeResponse> homeData(HomeRequest homeRequest) {

        try {
            return service.callHomeWebService(homeRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<DealerResponse> getDealers() {

        try {
            return service.callDealerService().execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<NewsResponse> getNews() {

        try {
            return service.callNewsWebService().execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<GetCategoryResponse> getCategories() {

        try {
            return service.callGetCategoryService().execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }


    public Response<WishListResponse> getWishList(WishListRequest wishListRequest) {

        try {
            return service.callWishList(wishListRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<GetCategoryPropertyResponse> getPropertiesByCategory(GetCategoryPropertyRequest getCategoryPropertyRequest) {

        try {
            return service.callGetPropertiesByCategory(getCategoryPropertyRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<WishListOperationResponse> addToWishList(WishListOperationRequest wishListOperationRequest) {

        try {
            return service.callAddToWishList(wishListOperationRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<WishListOperationResponse> deleteFromWishList(WishListOperationRequest wishListOperationRequest){

        try {
            return service.callDeleteFromWishList(wishListOperationRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<PropertyDetailResponse> getPropertyDetail(PropertyDetailRequest propertyDetailRequest) {

        try {
            return service.callPropertyDetail(propertyDetailRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<FeedbackResponse> sendFeedback(FeedbackRequest feedbackRequest) {

        try {
            return service.sendFeedback(feedbackRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<DealerDetailResponse> getDealerDetail(DealerDetailRequest dealerDetailRequest) {

        try {
            return service.dealerDetail(dealerDetailRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<GetPropertiesByTypeResponse> getPropertiesByType(GetPropertyByTypeRequest getPropertyByTypeRequest) {

        try {
            return service.getPropertyByType(getPropertyByTypeRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<FindDealsResponse> findDeals() {

        try {
            return service.findDeals().execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }


    public Response<GetPropertyByPlaceResponse> getPropertyByPlace(GetPropertyByPlaceRequest getPropertyByPlaceRequest) {

        try {
            return service.getPropertiesByPlace(getPropertyByPlaceRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<AdvertisementResponse> getAdds() {

        try {
            return service.callAdds().execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<FilterSearchResponse> getFilterSearchResult(FilterSearchRequest filterSearchRequest) {

        try {
            return service.getSearchResultByFilter(filterSearchRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<SocialMediaResponse> socialMediaSignUp(SocialMediaRequest socialMediaRequest) {

        try {
            return service.socialMediaSignUp(socialMediaRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<SubscribeBusinessResponse> subscribeBusiness(SubscribeBusinessRequest subscribeBusinessRequest) {

        try {
            return service.subscribeForMakanBusiness(subscribeBusinessRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<BookPropertyResponse> bookProperty(BookPropertyRequest bookPropertyRequest) {

        try {
            return service.callBookProperty(bookPropertyRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<SearchByNameResponse> searchByName(SearchByNameRequest searchByNameRequest) {

        try {
            return service.callSearchByName(searchByNameRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

        try {
            return service.requestPassword(forgotPasswordRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }

    public Response<UploadImageResponse> uploadImage(UploadImageRequest uploadImageRequest) {

        try {
            return service.uploadImage(uploadImageRequest).execute();

        } catch (Exception e) {
            AppLog.showErrorMessage(e.toString());
        }
        return null;
    }


}
