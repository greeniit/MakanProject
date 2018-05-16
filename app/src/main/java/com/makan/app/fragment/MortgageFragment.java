package com.makan.app.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.makan.R;
import com.makan.app.util.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MortgageFragment extends BaseFragment{

    @BindView(R.id.etScLoanAmount)
    EditText etScLoanAmount;

    @BindView(R.id.etScApr)
    EditText etScApr;

    @BindView(R.id.etScLoanTerm)
    EditText etScLoanTerm;

    @BindView(R.id.tvScMonthlyPayment)
    TextView tvScMonthlyPayment;

    @BindView(R.id.tvScNumberOfPayments)
    TextView tvScNumberOfPayments;

    @BindView(R.id.tvScTotalInterest)
    TextView tvScTotalInterest;

    @BindView(R.id.tvScTotalPayableAmount)
    TextView tvScTotalPayableAmount;

    @BindView(R.id.btnCalculateBasicLoan)
    Button btnCalculateBasicLoan;

    @BindView(R.id.etIscLoanAmount)
    EditText etIscLoanAmount;

    @BindView(R.id.etIscApr)
    EditText etIscApr;

    @BindView(R.id.etIscLoanTerm)
    EditText etIscLoanTerm;

    @BindView(R.id.etIscComparisonRate)
    EditText etIscComparisonRate;

    @BindView(R.id.tvIcsSavedInterestAmount)
    TextView tvIcsSavedInterestAmount;

    @BindView(R.id.btnCalculateInterestSaving)
    Button btnCalculateInterestSaving;

    @BindView(R.id.etAcLoanAmount)
    EditText etAcLoanAmount;

    @BindView(R.id.etAcRate)
    EditText etAcRate;

    @BindView(R.id.etAcLoanTerm)
    EditText etAcLoanTerm;

    @BindView(R.id.row1PaymentAmount)
    TextView row1PaymentAmount;

    @BindView(R.id.row1TotalInterest)
    TextView row1TotalInterest;

    @BindView(R.id.row1TotalPayment)
    TextView row1TotalPayment;

    @BindView(R.id.row1Balance)
    TextView row1Balance;

    @BindView(R.id.btnCalculateAmortization)
    Button btnCalculateAmortization;

    @BindView(R.id.tableLayout)
    TableLayout tableLayout;

    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mortgage, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }


    @OnClick(R.id.btnCalculateBasicLoan)
    void onCalculateBasicLoanClicked(){

        if(etScLoanAmount.getText().length()>0&&etScApr.getText().length()>0&&etScLoanTerm.getText().length()>0){

            showProgressDialog();

            calculateBasicLoanDetails();

            dismissProgressDialog();

            new Utility().hideSoftKeyBoard(getActivity());

        }else{

            new Utility().showMessageAlertDialog(getActivity(),"Enter all the details for basic loan calculation.");
        }
    }

    @OnClick(R.id.btnCalculateInterestSaving)
    void onCalculateInterestSavingLoanClicked(){

        if(etIscLoanAmount.getText().length()>0&&etIscApr.getText().length()>0&&etIscLoanTerm.getText().length()>0&&etIscComparisonRate.getText().length()>0){

            showProgressDialog();

            calculateInterestComparisonLoanDetails();

            dismissProgressDialog();

            new Utility().hideSoftKeyBoard(getActivity());

        }else{

            new Utility().showMessageAlertDialog(getActivity(),"Enter all the details for interest saving calculation.");
        }
    }

    @OnClick(R.id.btnCalculateAmortization)
    void onCalculateAmortizationClicked(){

        if(etAcLoanAmount.getText().length()>0&&etAcRate.getText().length()>0&&etAcLoanTerm.getText().length()>0){

            showProgressDialog();

            calculateAmortizationLoanDetails();

            dismissProgressDialog();

            new Utility().hideSoftKeyBoard(getActivity());

        }else{

            new Utility().showMessageAlertDialog(getActivity(),"Enter all the details for amortization calculation.");
        }
    }

    private void calculateBasicLoanDetails(){

        float interestAmount=0;

        float loanAmount=Float.valueOf(etScLoanAmount.getText().toString());
        float interestRate=Float.valueOf(etScApr.getText().toString())/(12*100);
        float duration=Float.valueOf(etScLoanTerm.getText().toString());;

        //[P x R x (1+R)^N]/[(1+R)^N-1]

        float emi = (loanAmount * interestRate * (float) Math.pow((1+interestRate),duration))/((float) Math.pow((1+interestRate),duration)-1);

        float totalPayableAmount = emi*duration;
        interestAmount = totalPayableAmount-loanAmount;

        tvScTotalPayableAmount.setText("OMR "+String.valueOf(String.format("%.2f", totalPayableAmount)));
        tvScMonthlyPayment.setText("OMR "+String.valueOf(String.format("%.2f", emi)));
        tvScNumberOfPayments.setText(String.valueOf((int)duration));
        tvScTotalInterest.setText("OMR "+String.valueOf(String.format("%.2f", interestAmount)));
    }

    private void calculateInterestComparisonLoanDetails(){

        float firstInterestAmount,secondInterestAmount;

        float loanAmount=Float.valueOf(etIscLoanAmount.getText().toString());
        float interestRate=Float.valueOf(etIscApr.getText().toString())/(12*100);
        float duration=Float.valueOf(etIscLoanTerm.getText().toString());;

        //[P x R x (1+R)^N]/[(1+R)^N-1]

        float emi = (loanAmount * interestRate * (float) Math.pow((1+interestRate),duration))/((float) Math.pow((1+interestRate),duration)-1);
        float totalPayableAmount = emi*duration;
        firstInterestAmount = totalPayableAmount-loanAmount;

        interestRate=Float.valueOf(etIscComparisonRate.getText().toString())/(12*100);

        emi = (loanAmount * interestRate * (float) Math.pow((1+interestRate),duration))/((float) Math.pow((1+interestRate),duration)-1);
        totalPayableAmount = emi*duration;

        secondInterestAmount = totalPayableAmount-loanAmount;

        float diff = firstInterestAmount - secondInterestAmount;

        tvIcsSavedInterestAmount.setText(" Save OMR "+String.valueOf(String.format("%.2f", diff)));

    }

    private void calculateAmortizationLoanDetails(){

        if(tableLayout.getChildCount()>2){

            int childCount=tableLayout.getChildCount();

            /*for (int i=2;i<=tableLayout.getChildCount();i++){
                tableLayout.removeView(tableLayout.getChildAt(i));
            }*/

            ArrayList<View> childList=new ArrayList<>();

            for (int i=2;i<tableLayout.getChildCount();i++){

                childList.add(tableLayout.getChildAt(i));
            }

            for (int i=0;i<childList.size();i++){

                tableLayout.removeView(childList.get(i));
            }


        }

        float loanAmount=Float.valueOf(etAcLoanAmount.getText().toString());
        final float interestRate=Float.valueOf(etAcRate.getText().toString())/(12*100);
        float duration=Float.valueOf(etAcLoanTerm.getText().toString());;

        //[P x R x (1+R)^N]/[(1+R)^N-1]

        final float emi = (loanAmount * interestRate * (float) Math.pow((1+interestRate),duration))/((float) Math.pow((1+interestRate),duration)-1);

        float interestAmount = loanAmount * (interestRate);
        float payment = emi-interestAmount;
        loanAmount = loanAmount-payment;

        row1PaymentAmount.setText(String.valueOf(String.format("%.2f", emi)));
        row1TotalInterest.setText(String.valueOf(String.format("%.2f", interestAmount)));
        row1TotalPayment.setText(String.valueOf(String.format("%.2f", payment)));
        row1Balance.setText(String.valueOf(String.format("%.2f", loanAmount)));

        for (int i=1;i<duration;i++){

            int serialNum=i+1;
            interestAmount = loanAmount * (interestRate);
            payment = emi-interestAmount;
            loanAmount = loanAmount-payment;

            String emiTobeDisplayed = String.format("%.2f",emi);

            addTableRow(String.valueOf(serialNum),emiTobeDisplayed,String.valueOf(String.valueOf(String.format("%.2f",interestAmount))),String.valueOf(String.format("%.2f",payment)),String.valueOf(String.format("%.2f", loanAmount)));
        }

    }

    private void addTableRow(String serialNumber,String emi,String totalInterest,String totalPayment,String balance){


        TableRow tr = new TableRow(getActivity());

        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tvFirst = new TextView(getActivity());
        tvFirst.setText(serialNumber);
        tvFirst.setTextColor(getResources().getColor(R.color.white));
        tvFirst.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tvFirst.setTypeface(boldTypeface);

        tr.addView(tvFirst);

        TextView tvSecond = new TextView(getActivity());
        tvSecond.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tvSecond.setText(emi);
        tvSecond.setTextColor(getResources().getColor(R.color.white));
        tvSecond.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tvSecond.setTypeface(boldTypeface);

        tr.addView(tvSecond,1);

        TextView tvThird = new TextView(getActivity());
        tvThird.setText(totalInterest);
        tvThird.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tvThird.setTextColor(getResources().getColor(R.color.white));
        tvThird.setTypeface(boldTypeface);
        tvThird.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


        tr.addView(tvThird);

        TextView tvFour = new TextView(getActivity());
        tvFour.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tvFour.setText(totalPayment);
        tvFour.setTextColor(getResources().getColor(R.color.white));
        tvFour.setTypeface(boldTypeface);
        tvFour.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


        tr.addView(tvFour);

        TextView tvFive = new TextView(getActivity());
        tvFive.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tvFive.setText(balance.contains("-")?balance.replace("-",""):balance);
        tvFive.setTextColor(getResources().getColor(R.color.white));
        tvFive.setTypeface(boldTypeface);
        tvFive.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


        tr.addView(tvFive);

        tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }
}
