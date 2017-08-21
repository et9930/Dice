package com.souvenir.dice;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    private Button btn_roll;
    private RadioButton radio0To9;
    private TextView txtResult;
    private ListView listView;
    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_roll = (Button) findViewById(R.id.btn_roll);
        radio0To9 = (RadioButton) findViewById(R.id.radio0to9);
        txtResult = (TextView) findViewById(R.id.txtResult);
        listView = (ListView) findViewById(R.id.listView);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        final SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout,
                new String[]{"title", "info"},
                new int[]{R.id.title, R.id.info});
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


        btn_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1 = Integer.parseInt(spinner1.getSelectedItem().toString());
                int number2 = Integer.parseInt(spinner2.getSelectedItem().toString());
                Map<String, Object> map = new HashMap<String, Object>();
                String str_result = "";
                int total = 0;
                for (int i = 0; i < number1; i++) {
                    int single_result = rowDice(number2);
                    total += single_result;
                    str_result += String.valueOf(single_result);
                    if (i != number1 - 1) {
                        str_result += "+";
                    }
                }
                //调整结果TextView字号
                if (str_result.length() > 10) {
                    txtResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 48);
                } else if (str_result.length() > 15) {
                    txtResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);
                } else {
                    txtResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 64);
                }

                txtResult.setText(str_result);

                //历史纪录列表
                map.put("title", str_result);
                map.put("info", String.valueOf(number1) + "D" + String.valueOf(number2) + " " + new Date().toLocaleString() + " 总和 = " + String.valueOf(total));
                list.add(0, map);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //掷骰 返回结果 dice_number骰子面数
    private int rowDice(int dice_number) {
        int Result;
        Random random = new Random();
        Result = random.nextInt(dice_number);
        if (radio0To9.isChecked()) {
            Result -= 1;
        }
        return Result + 1;
    }


    //单击两次返回键退出
    long start = 0L;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        long end = System.currentTimeMillis();
        if (end - start <= 2000) {
            finish();
        } else {
            start = end;
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }
}
