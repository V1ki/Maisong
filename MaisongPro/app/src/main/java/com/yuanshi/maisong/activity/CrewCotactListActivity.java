package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.adapter.SortAdapter;
import com.yuanshi.maisong.bean.ContactMember;
import com.yuanshi.maisong.utils.CharacterParser;
import com.yuanshi.maisong.utils.PinyinComparator;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.ClearEditText;
import com.yuanshi.maisong.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dengbocheng on 2017/10/30.
 * 剧组通讯录列表界面
 */

public class CrewCotactListActivity extends BaseActivity {
    private ListView sortListView;
    private SideBar sideBar;
    /**
     * 显示字母的TextView
     */
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<ContactMember> SourceDateList = new ArrayList<>();

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private String crewId;
    @Override
    protected int getContentViewId() {
        return R.layout.crew_contact_list_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        crewId = getIntent().getStringExtra("crewId");
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }
            }
        });
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getApplication(), ((ContactMember)adapter.getItem(position)).getUsername(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CrewCotactListActivity.this, FriendDetailActivity.class);
                intent.putExtra("phone",((ContactMember)adapter.getItem(position)).getPhone());
                intent.putExtra("department",((ContactMember)adapter.getItem(position)).getDepartment());
                intent.putExtra("position",((ContactMember)adapter.getItem(position)).getPosition());
                startActivity(intent);
            }
        });

        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        iHttpPresenter.usercrew(crewId);
        initData();
    }
    private void initData() {
        //实例化汉字转拼音类
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);
    }

    public void initHttpData(List<ContactMember> list){
        SourceDateList = filledData(list);
        adapter.updateListView(SourceDateList);
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "usercrew":
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                List<ContactMember> list = Utils.jsonToList(json,ContactMember[].class);
                if(list!= null &&list.size() > 0){
                    initHttpData(list);
                }
                break;
        }
    }

    //    /**
//     * 为ListView填充数据
//     * @param date
//     * @return
//     */
//    private List<ContactMember> filledData(String [] date){
//        List<ContactMember> mSortList = new ArrayList<ContactMember>();
//        for(int i=0; i<date.length; i++){
//            ContactMember sortModel = new ContactMember();
//            sortModel.setUsername(date[i]);
//            //汉字转换成拼音
//            String pinyin = characterParser.getSelling(date[i]);
//            String sortString = pinyin.substring(0, 1).toUpperCase();
//
//            // 正则表达式，判断首字母是否是英文字母
//            if(sortString.matches("[A-Z]")){
//                sortModel.setSortLetters(sortString.toUpperCase());
//            }else{
//                sortModel.setSortLetters("#");
//            }
//
//            mSortList.add(sortModel);
//        }
//        return mSortList;
//    }

    /**
     * 为ListView填充数据
     * @param oldData
     * @return
     */
    private List<ContactMember> filledData( List<ContactMember> oldData){
        List<ContactMember> mSortList = new ArrayList<ContactMember>();
        for(ContactMember contactMember: oldData){
//            ContactMember sortModel = new ContactMember();
//            sortModel.setUsername(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(contactMember.getUsername());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                contactMember.setSortLetters(sortString.toUpperCase());
            }else{
                contactMember.setSortLetters("#");
            }

            mSortList.add(contactMember);
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactMember> filterDateList = new ArrayList<ContactMember>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (ContactMember sortModel : SourceDateList) {
                String name = sortModel.getUsername();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

}