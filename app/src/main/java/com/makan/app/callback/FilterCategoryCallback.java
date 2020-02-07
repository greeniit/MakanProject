package com.makan.app.callback;

import com.makan.app.web.pojo.FillterCategoryResponse;

import java.util.List;

public interface FilterCategoryCallback {
    void filterCategorySelect(List<FillterCategoryResponse.SubCategoryList> subCategoryList);

    void selectedData(List<String> selected);
}
