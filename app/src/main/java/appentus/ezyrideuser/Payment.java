package appentus.ezyrideuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Payment extends AppCompatActivity {
    @BindView(R.id.ivback)
    ImageView ivBack;
    @BindView(R.id.add100)
    LinearLayout add100;
    @BindView(R.id.add200)
    LinearLayout add200;
    @BindView(R.id.add300)
    LinearLayout add300;
    @BindView(R.id.etAmount)
    EditText etAmount;
    @BindView(R.id.proceed)
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        add100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etAmount.setText("100");
            }
        });
        add200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              etAmount.setText("200");
            }
        });
        add300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etAmount.setText("300");
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

