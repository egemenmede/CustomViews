package com.egemenmede.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

public class MainActivity extends AppCompatActivity {

    TcknView tcknView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tcknView = findViewById(R.id.tcknView);
        tcknView.checkTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // beforeTextChanged
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txtInput = charSequence.toString();

                if (tcknView.getFirstValueZeroStatus(txtInput)
                        && tcknView.isVisibleErrorMessege()){
                        //DEFAULT: tcknView.setError(tcknView.getDefaultErrorMessege());
                        tcknView.setError(getString(R.string.tckn_rule3_messege));
                }else{
                    if (tcknView.getNumberOfChrs(txtInput) != 0) {
                        tcknView.setError(String.format(getString(R.string.tckn_number_of_chrs), tcknView.getNumberOfChrs(txtInput)));
                    }
                    if (tcknView.getNumberOfChrs(txtInput) == tcknView.getChrCount() || tcknView.getNumberOfChrs(txtInput) == 0) {
                        tcknView.setErrorEnabled(false);
                    }
                    if (txtInput.length() >= tcknView.getChkCount()
                            && tcknView.getRuleFourStatus(txtInput).equals(false)
                            && tcknView.isVisibleErrorMessege()){
                        //DEFAULT: tcknView.setError(tcknView.getDefaultErrorMessege());
                        tcknView.setError(getString(R.string.tckn_rule4_messege));
                    }
                    if (txtInput.length() >= tcknView.getChrCount()
                            && tcknView.getRuleFiveStatus(txtInput).equals(false)
                            && tcknView.isVisibleErrorMessege()){
                        //DEFAULT: tcknView.setError(tcknView.getDefaultErrorMessege());
                        tcknView.setError(getString(R.string.tckn_rule5_messege));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // afterTextChanged
            }
        });
    }
}
