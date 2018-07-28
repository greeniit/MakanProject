package com.makan.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.activity.FilterActivity;
import com.makan.app.activity.PropertyListActivity;
import com.makan.app.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment{

    @BindView(R.id.btnFilter)
    Button btnFilter;

    @BindView(R.id.btnSearch)
    Button btnSearch;

    @BindView(R.id.etKeyWord)
    EditText etKeyWord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,rootView);
        setListeners();
        return rootView;
    }

    @OnClick(R.id.btnFilter)
    void onFilterClicked() {

        new Utility().moveToActivity(getActivity(), FilterActivity.class, null);
    }

    @OnClick(R.id.btnSearch)
    void onSearchClicked() {

        performSearch();
    }

    private void performSearch() {
        if (etKeyWord.getText()!=null&&etKeyWord.getText().length()>0){

            Bundle bundle = new Bundle();
            bundle.putString("search_key",etKeyWord.getText().toString());
            new Utility().moveToActivity(getActivity(), PropertyListActivity.class,bundle);

        }else{

            new Utility().showMessageAlertDialog(getActivity(),"Enter Property name/id/locality.");
        }
    }

    private void setListeners(){

        etKeyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    try{
                        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(etKeyWord.getWindowToken(), 0);
                    }catch (Exception e){

                    }

                    performSearch();
                    return true; // Focus will do whatever you put in the logic.
                }
                return false;  // Focus will change according to the actionId
            }
        });
    }
}
