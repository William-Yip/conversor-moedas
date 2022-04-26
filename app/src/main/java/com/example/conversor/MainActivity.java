package com.example.conversor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RadioGroup from;
    String selectedFrom;
    String selectedTo;
    RadioGroup to;
    TextView result;
    EditText input;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        input = findViewById(R.id.input);

        from = findViewById(R.id.radio1);
        from.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton button = group.findViewById(checkedId);
            selectedFrom = button.getText().toString();
        });

        to = findViewById(R.id.radio2);
        to.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton button = group.findViewById(checkedId);
            selectedTo = button.getText().toString();
        });

        btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> convertValues());

    }

    public void convertValues() {
        String param = String.format("%s-%s", selectedFrom, selectedTo);

        Call<String> call = new RetroFitConfig().getService().convertValues(param);
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String highStr = json.getJSONObject(param.replace("-", "")).getString("high");
                    Double high = Double.valueOf(highStr);
                    Double res = Double.valueOf(input.getText().toString()) * high;

                    System.out.println("res "+ res);
                    result.setText(res.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println();
            }

        });

    }

}