package com.makan.app.callback;

public interface WishListAddDeleteOperationCallback {

    void AddToWishListTaskSuccess();
    void AddToWishListTaskFailure(String errorMessage);
}
