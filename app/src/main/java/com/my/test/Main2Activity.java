package com.my.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    OptionsPickerView pvOptions;
    List<String> options1Item_list = new ArrayList<String>();

    List<List<String>> options2Item_list = new ArrayList<List<String>>();
    List<String> options2Item;

    List<List<List<String>>> options3Item_list = new ArrayList<List<List<String>>>();
    List<List<String>> options3Item_listq;
    List<String> options3Item;
    Context mContext;
    int oneItem,twoItem,threeItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mContext=this;
    }
    public void a(View view){
        oneItem=1;
        twoItem=2;
        threeItem=3;
        // 选项选择器

        for (int i = 1; i <= 10; i++) {
            options1Item_list.add("省份" + i);
        }
        for (int i = 1; i <= 10; i++) {
            options2Item = new ArrayList<String>();
            for (int j = 1; j <= 10; j++) {
                options2Item.add("城市" + i + j);
            }
            options2Item_list.add(options2Item);
        }
        for (int k = 0; k <= 10; k++) {
            options3Item_listq = new ArrayList<List<String>>();
            for (int i = 1; i <= 10; i++) {
                options3Item = new ArrayList<String>();
                for (int j = 1; j <= 10 - i; j++) {
                    options3Item.add("区域" + k + j);
                }
                options3Item_listq.add(options3Item);
            }
            options3Item_list.add(options3Item_listq);
        }
        pvOptions = new  OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
            }
        }).build();
        // 三级联动效果
        pvOptions.setPicker(options1Item_list, options2Item_list, options3Item_list);
        pvOptions.setSelectOptions(oneItem, twoItem, threeItem);

        pvOptions.show();
    }
}
