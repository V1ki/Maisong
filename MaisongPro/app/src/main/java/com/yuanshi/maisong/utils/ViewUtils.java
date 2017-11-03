package com.yuanshi.maisong.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanshi.maisong.R;
import com.yuanshi.maisong.view.WheelView;

import java.util.List;

/**
 * Created by admin on 2017/7/4.
 */

public class ViewUtils {
    /**
     * 展示PopupWindow
     * @param context
     * @param list
     * @param view
     */
    public static void showPopupwindow(Context context, List<String> list , final TextView view){
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.bottom_dialog_layout, null);
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.BottomDialog);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);
        WheelView wheelView = (WheelView) contentView.findViewById(R.id.wheelView);
        wheelView.setItems(list);
        wheelView.setSeletion(list.indexOf(view.getText().toString()));
        wheelView.setOnItemClickListener(new WheelView.OnItemClickListner() {
            @Override
            public void onItemClick(String item) {
                if(popupWindow.isShowing()){
                    if(!TextUtils.isEmpty(item)){
                        view.setText(item);
                        if(listener!= null){
                            listener.onTextChange(item);
                        }
                        popupWindow.dismiss();
                    }
                }
            }
        });
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                view.setText(item);
                if(listener!= null){
                    listener.onTextChange(item);
                }
            }
        });

        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int w = width;
        int h = height * 2/5;
        popupWindow.setWidth(w);
        popupWindow.setHeight(h);
//        popupWindow.showAsDropDown(view);
        popupWindow.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private static OnTextChangedListener listener;
    public interface OnTextChangedListener{
        void onTextChange(String item);
    }
    public static void setOnTextChangedListener(OnTextChangedListener mListener){
        listener = mListener;
    }
}
