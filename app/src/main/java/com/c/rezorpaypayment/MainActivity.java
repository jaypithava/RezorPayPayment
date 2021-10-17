package com.c.rezorpaypayment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    Button payBtn;
    TextView payText;

    //Documentation Link:-https://razorpay.com/docs/payment-gateway/android-integration/standard/
    //Sdk Link:-https://mvnrepository.com/artifact/com.razorpay/checkout/1.6.12

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Checkout.preload(getApplicationContext());

        payBtn=findViewById(R.id.payBtn);
        payText=findViewById(R.id.payText);

        payBtn.setOnClickListener(view -> {
            startPayment();
        });

    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_RIfD2Xuv5R5y97");
        checkout.setImage(R.drawable.logo);

        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Tony Bhai");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//500*100
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","9106104993");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("jay", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        payText.setText("Successful Payment Id:"+s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        payText.setText("Failed and cause is:"+s);
    }
}