package com.ashish.android.rxui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding3.widget.RxCompoundButton;
import com.jakewharton.rxbinding3.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    EditText emailEditTextView, phoneEditTextView;
    RadioButton emailRadioButton, phoneRadioButton;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailEditTextView = findViewById(R.id.emailEdittext);
        phoneEditTextView = findViewById(R.id.phoneEditText);
        emailRadioButton = findViewById(R.id.emailRadioButton);
        phoneRadioButton = findViewById(R.id.phoneRadioButton);
        continueButton = findViewById(R.id.continueButton);

        Observable<CharSequence> emailObservable = RxTextView.textChanges(emailEditTextView);
        Observable<CharSequence> phoneObservable = RxTextView.textChanges(phoneEditTextView);

        Observable<Boolean> emailRadioObservable = RxCompoundButton.checkedChanges(emailRadioButton);
        Observable<Boolean> phoneRadioObservable = RxCompoundButton.checkedChanges(phoneRadioButton);

        emailRadioObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean != null && aBoolean) {
                        emailEditTextView.setVisibility(View.VISIBLE);
                        phoneEditTextView.setVisibility(View.GONE);
                        phoneRadioButton.setChecked(false);
                    }
                });

        phoneRadioObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean != null && aBoolean) {
                        phoneEditTextView.setVisibility(View.VISIBLE);
                        emailEditTextView.setVisibility(View.GONE);
                        emailRadioButton.setChecked(false);
                    }
                });

        Observable<Boolean> isEmailValid = emailObservable.map(email -> email != null && email != "" && email.toString().contains("@"));

        Observable<Boolean> isPhoneValid = phoneObservable.map(phone -> phone != null && phone != "" && phone.toString().length() == 10);

        Observable<Boolean> isFormValid = ValidationUtils.or(isEmailValid, isPhoneValid);

        isFormValid.observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean != null && aBoolean) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                });
    }

}
