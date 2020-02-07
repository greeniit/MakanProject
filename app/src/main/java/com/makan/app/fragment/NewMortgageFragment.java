package com.makan.app.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.makan.R;
import com.makan.app.professional.ProfessionalsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMortgageFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sb_totalprice)
    SeekBar sbTotalprice;
    @BindView(R.id.et_totalPrice)
    EditText etTotalPrice;
    @BindView(R.id.sb_loanPeriod)
    SeekBar sbLoanPeriod;
    @BindView(R.id.et_loanPeriod)
    EditText etLoanPeriod;
    @BindView(R.id.sb_downPayment)
    SeekBar sbDownPayment;
    @BindView(R.id.tv_downPaymentPercentage)
    TextView tvDownPaymentPercentage;
    @BindView(R.id.et_downPaymentAmount)
    EditText etDownPaymentAmount;
    @BindView(R.id.sb_rateOfInterest)
    SeekBar sbRateOfInterest;
    @BindView(R.id.et_rateOfInterest)
    EditText etRateOfInterest;
    @BindView(R.id.tv_totalPaybleAmount)
    TextView tvTotalPaybleAmount;
    @BindView(R.id.tv_monthlyPayment)
    TextView tvMonthlyPayment;
    @BindView(R.id.pb_breakDown)
    ProgressBar pbBreakDown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_mortgage, container, false);
        ButterKnife.bind(this, rootView);
        etTotalPrice.setText(getArguments().getFloat("PRICE")+" "+getResources().getString(R.string.omr));
//        int xTest = ConvertIntoNumeric(getArguments().getString("PRICE"));
        sbTotalprice.setProgress((int) getArguments().getFloat("PRICE"));
        etLoanPeriod.setText("1"+" "+getResources().getString(R.string.years));
        sbLoanPeriod.setProgress(1);
        etRateOfInterest.setText("1 %");
        sbRateOfInterest.setProgress(1);
        setToolBar();
        initData();
        return rootView;
    }

    private void initData() {

        etTotalPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String totalPrice = etTotalPrice.getText().toString().trim();

                    if (totalPrice.contains(getResources().getString(R.string.omr))){
                        totalPrice = totalPrice.substring ( 0, totalPrice.length() - 4 );
                        sbTotalprice.setProgress(Integer.parseInt(totalPrice));
                    }else {
                        if (totalPrice.isEmpty()||totalPrice.equals("")){
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.please), Toast.LENGTH_SHORT).show();

                        }else if (totalPrice.matches("^[1-9]\\d*$")){
                            sbTotalprice.setProgress(Integer.parseInt(totalPrice));
                        }else {
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.pleaseenteravaliddata), Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                return handled;
            }
        });

        etLoanPeriod.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String loanPeriod = etLoanPeriod.getText().toString().trim();

                    if (loanPeriod.contains(getResources().getString(R.string.years))){
                        loanPeriod = loanPeriod.substring ( 0, loanPeriod.length() - 6 );
                        sbLoanPeriod.setProgress(Integer.parseInt(loanPeriod));
                    }else {
                        if (loanPeriod.isEmpty()||loanPeriod.equals("")){
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.please), Toast.LENGTH_SHORT).show();

                        }else if (loanPeriod.matches("^[1-9]\\d*$")){
                            sbLoanPeriod.setProgress(Integer.parseInt(loanPeriod));
                        }else {
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.pleaseenteravaliddata), Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                return handled;
            }
        });

        etDownPaymentAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String totalPrice = etDownPaymentAmount.getText().toString().trim();

                    if (totalPrice.contains(getResources().getString(R.string.omr))){
                        totalPrice = totalPrice.substring ( 0, totalPrice.length() - 4 );
                        sbDownPayment.setProgress(Integer.parseInt(totalPrice));
                    }else {
                        if (totalPrice.isEmpty()||totalPrice.equals("")){
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.please), Toast.LENGTH_SHORT).show();

                        }else if (totalPrice.matches("^[1-9]\\d*$")){
                            sbDownPayment.setProgress(Integer.parseInt(totalPrice));
                        }else {
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.pleaseenteravaliddata), Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                return handled;
            }});

        etRateOfInterest.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String totalPrice = etRateOfInterest.getText().toString().trim();

                    if (totalPrice.contains(" %")){
                        totalPrice = totalPrice.substring ( 0, totalPrice.length() - 2 );
                        sbRateOfInterest.setProgress(Integer.parseInt(totalPrice));
                    }else {
                        if (totalPrice.isEmpty()||totalPrice.equals("")){
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.please), Toast.LENGTH_SHORT).show();

                        }else if (totalPrice.matches("^[1-9]\\d*$")){
                            sbRateOfInterest.setProgress(Integer.parseInt(totalPrice));
                        }else {
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.pleaseenteravaliddata), Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                return handled;
            }});


        sbTotalprice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int ii, boolean b) {
                etTotalPrice.setText(""+ii+" OMR");

                String st1 = String.valueOf(sbTotalprice.getProgress());
                String st2 = String.valueOf(sbRateOfInterest.getProgress());
                String st3 = String.valueOf(sbLoanPeriod.getProgress());
                String st4 = String.valueOf(sbDownPayment.getProgress());

                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);
                float d = Float.parseFloat(st4);

                float Principal = calPric(p,d);

                float Rate = calInt(i);

                float Months = calMonth(y);

                float Dvdnt = calDvdnt( Rate, Months);

                float FD = calFinalDvdnt (Principal, Rate, Dvdnt);

                float D = calDivider(Dvdnt);

                float emi = calEmi(FD, D);

                float TA = calTa (emi, Months);

                float ti = calTotalInt(TA, Principal);

                float downPayment = downPayCal(p,d);


                Log.d("TotalPrice",st1);
                Log.d("rateofinterset",st2);
                Log.d("loanperiod",st3);

                Log.d("Principal",String.valueOf(Principal));
                Log.d("Rate",String.valueOf(Rate));
                Log.d("Months",String.valueOf(Months));
                Log.d("Dvdnt",String.valueOf(Dvdnt));
                Log.d("FD",String.valueOf(FD));
                Log.d("D",String.valueOf(D));
                Log.d("monthly",String.valueOf(emi));//monthly
                Log.d("totalpayble",String.valueOf(TA));//totalpayble
                Log.d("ti",String.valueOf(ti));
                Log.d("down",String.valueOf(downPayment));


                tvTotalPaybleAmount.setText(""+ TA);
                tvMonthlyPayment.setText(""+ emi);
                etDownPaymentAmount.setText(""+downPayment+" OMR");
                pbBreakDown.setProgress((int) FD);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbLoanPeriod.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int ii, boolean b) {
                etLoanPeriod.setText(""+ii+" Years");

                String st1 = String.valueOf(sbTotalprice.getProgress());
                String st2 = String.valueOf(sbRateOfInterest.getProgress());
                String st3 = String.valueOf(sbLoanPeriod.getProgress());
                String st4 = String.valueOf(sbDownPayment.getProgress());

                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);
                float d = Float.parseFloat(st4);

                float Principal = calPric(p, d);

                float Rate = calInt(i);

                float Months = calMonth(y);

                float Dvdnt = calDvdnt( Rate, Months);

                float FD = calFinalDvdnt (Principal, Rate, Dvdnt);

                float D = calDivider(Dvdnt);

                float emi = calEmi(FD, D);

                float TA = calTa (emi, Months);

                float ti = calTotalInt(TA, Principal);

                float downPayment = downPayCal(p,d);


                Log.d("TotalPrice",st1);
                Log.d("rateofinterset",st2);
                Log.d("loanperiod",st3);

                Log.d("Principal",String.valueOf(Principal));
                Log.d("Rate",String.valueOf(Rate));
                Log.d("Months",String.valueOf(Months));
                Log.d("Dvdnt",String.valueOf(Dvdnt));
                Log.d("FD",String.valueOf(FD));
                Log.d("D",String.valueOf(D));
                Log.d("emi",String.valueOf(emi));//monthly
                Log.d("TA",String.valueOf(TA));//totalpayble
                Log.d("ti",String.valueOf(ti));
                Log.d("down",String.valueOf(downPayment));

                tvTotalPaybleAmount.setText(""+ TA);
                tvMonthlyPayment.setText(""+ emi);
                etDownPaymentAmount.setText(""+downPayment+" OMR");
                pbBreakDown.setProgress((int) FD);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbDownPayment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int ii, boolean b) {
                tvDownPaymentPercentage.setText(""+ii+" %");

                String st1 = String.valueOf(sbTotalprice.getProgress());
                String st2 = String.valueOf(sbRateOfInterest.getProgress());
                String st3 = String.valueOf(sbLoanPeriod.getProgress());
                String st4 = String.valueOf(sbDownPayment.getProgress());

                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);
                float d = Float.parseFloat(st4);

                float Principal = calPric(p, d);

                float Rate = calInt(i);

                float Months = calMonth(y);

                float Dvdnt = calDvdnt( Rate, Months);

                float FD = calFinalDvdnt (Principal, Rate, Dvdnt);

                float D = calDivider(Dvdnt);

                float emi = calEmi(FD, D);

                float TA = calTa (emi, Months);

                float ti = calTotalInt(TA, Principal);

                float downPayment = downPayCal(p,d);


                Log.d("TotalPrice",st1);
                Log.d("rateofinterset",st2);
                Log.d("loanperiod",st3);

                Log.d("Principal",String.valueOf(Principal));
                Log.d("Rate",String.valueOf(Rate));
                Log.d("Months",String.valueOf(Months));
                Log.d("Dvdnt",String.valueOf(Dvdnt));
                Log.d("FD",String.valueOf(FD));
                Log.d("D",String.valueOf(D));
                Log.d("emi",String.valueOf(emi));//monthly
                Log.d("TA",String.valueOf(TA));//totalpayble
                Log.d("ti",String.valueOf(ti));
                Log.d("down",String.valueOf(downPayment));

                tvTotalPaybleAmount.setText(""+ TA);
                tvMonthlyPayment.setText(""+ emi);
                etDownPaymentAmount.setText(""+downPayment+" OMR");
                pbBreakDown.setProgress((int) FD);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbRateOfInterest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int ii, boolean b) {
                etRateOfInterest.setText(""+ii+" %");
                String st1 = String.valueOf(sbTotalprice.getProgress());
                String st2 = String.valueOf(sbRateOfInterest.getProgress());
                String st3 = String.valueOf(sbLoanPeriod.getProgress());
                String st4 = String.valueOf(sbDownPayment.getProgress());

                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);
                float d = Float.parseFloat(st4);

                float Principal = calPric(p, d);

                float Rate = calInt(i);

                float Months = calMonth(y);

                float Dvdnt = calDvdnt( Rate, Months);

                float FD = calFinalDvdnt (Principal, Rate, Dvdnt);

                float D = calDivider(Dvdnt);

                float emi = calEmi(FD, D);

                float TA = calTa (emi, Months);

                float ti = calTotalInt(TA, Principal);

                float downPayment = downPayCal(p,d);


                Log.d("TotalPrice",st1);
                Log.d("rateofinterset",st2);
                Log.d("loanperiod",st3);

                Log.d("Principal",String.valueOf(Principal));
                Log.d("Rate",String.valueOf(Rate));
                Log.d("Months",String.valueOf(Months));
                Log.d("Dvdnt",String.valueOf(Dvdnt));
                Log.d("FD",String.valueOf(FD));
                Log.d("D",String.valueOf(D));
                Log.d("emi",String.valueOf(emi));//monthly
                Log.d("TA",String.valueOf(TA));//totalpayble
                Log.d("ti",String.valueOf(ti));
                Log.d("down",String.valueOf(downPayment));

                tvTotalPaybleAmount.setText(""+ TA);
                tvMonthlyPayment.setText(""+ emi);
                etDownPaymentAmount.setText(""+downPayment+" OMR");
                pbBreakDown.setProgress((int) FD);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public float downPayCal(float p, float d) {

        return (float) ((p * d)/100);
    }

    protected void setToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.mortgage));

        }
    }


    public  float calPric(float p, float d) {

        float dp =  (float) ((p * d)/100);

        return (float) (p-dp);

    }

    public  float calInt(float i) {

        return (float) (i/12/100);

    }

    public  float calMonth(float y) {

        return (float) (y * 12);

    }

    public  float calDvdnt(float Rate, float Months) {

        return (float) (Math.pow(1+Rate, Months));

    }

    public  float calFinalDvdnt(float Principal, float Rate, float Dvdnt) {

        return (float) (Principal * Rate * Dvdnt);

    }

    public  float calDivider(float Dvdnt) {

        return (float) (Dvdnt-1);

    }

    public  float calEmi(float FD, Float D) {

        return (float) (FD/D);

    }

    public  float calTa(float emi, Float Months) {

        return (float) (emi*Months);

    }

    public  float calTotalInt(float TA, float Principal) {

        return (float) (TA - Principal);

    }

    private int ConvertIntoNumeric(String xVal)
    {
        try
        {
            return Integer.parseInt(xVal);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }




}
