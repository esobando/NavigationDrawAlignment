package com.example.admin.navigationdrawalignment;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class FourthFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    private static final String LOAN_AMOUNT = "LOAN_AMOUNT";
    private static final String CUSTOM_INTEREST_RATE = "CUSTOM_INTEREST_RATE";
    private static final String TAG = "Main";

    private double currentLoanAmount ; // loan amount entered by the user
    private EditText loanEditText; // accepts user input for loan amount

    private double currentCustomRate; // interest rate % set with the SeekBar
    private TextView customRateTextView; // custom rate

    private EditText tenYearEditText; // 10 yr monthly
    private EditText fifteenYearEditText; // 15 yr monthly
    private EditText thirtyYearEditText;  // 30 yr monthly

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View myView= inflater.inflate(R.layout.fourth_layout,container,false);

        // check if app just started or is being restored from memory
        if ( savedInstanceState == null ) // the
        // just started running
        {
            currentLoanAmount = 0.0; // initialize the loan amount to zero
            currentCustomRate = 5.00; // initialize the custom rate to 5.00%
        } // end if
        else // app is being restored from memory, not executed from scratch
        {
            // initialize the loan amount to saved amount
            currentLoanAmount = savedInstanceState.getDouble(LOAN_AMOUNT);

            // initialize the custom rate to saved interest rate
            currentCustomRate =
                    savedInstanceState.getDouble(CUSTOM_INTEREST_RATE);
        } // end else

        // get the TextView displaying the custom interest rate
        customRateTextView = myView.findViewById(R.id.customRateTextView);

        // get the loanEditText
        loanEditText = (EditText) myView.findViewById(R.id.loanEditText);
        // loanEditTextWatcher handles loanEditText's onTextChanged event
        loanEditText.addTextChangedListener(loanEditTextWatcher);

        // get the SeekBar used to set the custom interest rate
        SeekBar customSeekBar = (SeekBar) myView.findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

        // get references to the 10yr, 15yr and 30yr EditTexts
        tenYearEditText = (EditText) myView.findViewById(R.id.tenYearEditText);
        fifteenYearEditText = (EditText) myView.findViewById(R.id.fifteenYearEditText);
        thirtyYearEditText = (EditText) myView.findViewById(R.id.thirtyYearEditText);





        return myView;


    }




    // calculate monthly payment
    private double formula(double loan, double rate, int term)
    {
        double c = rate/100/12.;
        double n = term *12 ;
        return loan * (c * Math.pow(1 + c, n )) / ( Math.pow(1 + c,n)-1);
    }

    // updates 10, 15 and 30 yr EditTexts
    private void updateMonthlyPayment()
    {
        // calculate monthly payment
        double tenYearMonthly =
                formula(currentLoanAmount,currentCustomRate, 10 );
        double fifteenYearMonthly =
                formula(currentLoanAmount,currentCustomRate, 20 );
        double thirtyYearMonthly =
                formula(currentLoanAmount,currentCustomRate, 30 );

        // 10, 15 and 30 yr monthly payment EditTexts
        tenYearEditText.setText("$" + String.format("%.0f", tenYearMonthly));
        fifteenYearEditText.setText("$" + String.format("%.0f", fifteenYearMonthly));
        thirtyYearEditText.setText("$" + String.format("%.0f", thirtyYearMonthly));
    }

    // updates the custom rate and monthly payment EditTexts
    private void updateCustomRate()
    {
        // set customRateTextView's text to match the position of the SeekBar
        customRateTextView.setText(String.format("%.02f", currentCustomRate) + "%");
        updateMonthlyPayment(); // update the 10, 15 and 30 yr EditTexts

    }

    // save values of loanEditText and customSeekBar
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putDouble(LOAN_AMOUNT, currentLoanAmount);
        outState.putDouble(CUSTOM_INTEREST_RATE, currentCustomRate);
    }

    // called when the user changes the position of SeekBar
    private SeekBar.OnSeekBarChangeListener customSeekBarListener =
            new SeekBar.OnSeekBarChangeListener()
            {
                // update currentCustomRate, then call updateCustomRate
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    // get currentCustomRate from the position of the SeekBar's thumb
                    currentCustomRate = seekBar.getProgress() / 100.0;
                    // update EditTexts for custom rate and monthly
                    updateCustomRate();
                }

                public void onStartTrackingTouch(SeekBar seekBar)
                {}

                public void onStopTrackingTouch(SeekBar seekBar)
                {}
            };

    // event-handling object that responds to loanEditText's events
    private TextWatcher loanEditTextWatcher = new TextWatcher()
    {
        // called when the user enters a number

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            // convert loanEditText's text to a double
            try
            {
                currentLoanAmount = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e)
            {
                currentLoanAmount = 0.0; // default if an exception occurs
            }

            // update the Monthly Payment
            updateMonthlyPayment(); // update the 10, 15 and 30 yr EditTexts
        }

        public void afterTextChanged(Editable s)
        {}

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after)
        {}
    };



}
