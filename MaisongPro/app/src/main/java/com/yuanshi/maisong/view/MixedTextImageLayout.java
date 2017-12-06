package com.yuanshi.maisong.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MixedTextImageLayout extends LinearLayout {

    private int startPos = 0;
    private Context context;
    private final String articleRegex =  "((<img>(.*?)</img>)|(\\{poi\\}(.*?)\\{/poi\\}))";
    private final String imageRegex = "<img>(.*?)</img>";

    public MixedTextImageLayout(Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
    }

    public MixedTextImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
    }

    public MixedTextImageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setOrientation(VERTICAL);
    }

    /**
     * 设置图文混排控件要显示的内容
     * @param content 要显示的内容
     */
    public void setContent(String content) {
        // 格式化字符串(替换特殊符号)
        String text = null;
        setGravity(Gravity.CENTER_HORIZONTAL);
        Pattern pattern = Pattern.compile(imageRegex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            text = content.substring(startPos, matcher.start());
            if (!TextUtils.isEmpty(text)) {
                appendTextView(clearNewlineChar(text));
            }
            appendImageView(content.substring(matcher.start() + 5, matcher.end() - 6));
            startPos = matcher.end();
        }
        text = content.substring(startPos);
        if (!TextUtils.isEmpty(text)) {
            appendTextView(clearNewlineChar(text));
        }
    }

    /**
     * 动态添加文本内容
     * @param content
     */
    private void appendTextView(String content) {
        if (!TextUtils.isEmpty(content)) {
            TextView textView = new TextView(context);
            textView.setTextIsSelectable(true);
            textView.setText(content);
            textView.setGravity(Gravity.LEFT);
            textView.getPaint().setTextSize(42);
            textView.setLineSpacing(0, 1.4f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = dpToPx(12);
            params.leftMargin = dpToPx(10);
            params.rightMargin = dpToPx(10);
            textView.setLayoutParams(params);
            addView(textView);
        }
    }

    /**
     * 动态添加图片
     * @param imageUrl
     */
    private void appendImageView(String imageUrl) {
        ImageView imageView = new ImageView(context);
        final int screenWidth = getDeviceScreenWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, (int) (screenWidth * 2.0 / 3));
        params.bottomMargin = dpToPx(12);
        imageView.setLayoutParams(params);
//        Glide.with(context).load(imageUrl).into(imageView);
        Glide.with(context).
                load(imageUrl).
                placeholder(R.mipmap.ic_launcher).
                error(R.mipmap.delete_icon).
                fitCenter().
                into(imageView);
//        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        addView(imageView);
    }

    /**
     * 清除多余的字符
     * @param str
     * @return
     */
    private String clearNeedlessChars(String str) {
        str = str.replaceAll("&amp;","&");
        str = str.replaceAll("&quot;","\"");  //"
        str = str.replaceAll("&nbsp;&nbsp;","\t");// 替换跳格
        str = str.replaceAll("&nbsp;"," ");// 替换空格
        str = str.replaceAll("&lt;","<");
        str = str.replaceAll("&gt;",">");
        str = str.replaceAll("\r","");
        str = str.replaceAll("\n","");
        str = str.replaceAll("\t","");
        return str;
    }

    /**
     * 清除多余的尾部换行符 注意：replaceFirst不会替换字符串本身的内容
     * @param content
     * @return
     */
    private String clearNewlineChar(String content) {
        int startPos = 0;
        int endPos = content.length() - 1;

        // 清除文字首部多余的换行符
        while (startPos <= endPos) {
            if (content.charAt(startPos) == '\n' || content.charAt(startPos) == '\r') {
                startPos++;
                // 当所有内容都是换行符的情况
                if (startPos > endPos) {
                    content = "";
                    endPos -= startPos;
                    break;
                }
            } else {
                // 获取清除后的字符串，并重新设置尾部位置
                content = content.substring(startPos);
                endPos -= startPos;
                break;
            }
        }
        // 清除文字尾部多余的换行符
        while (endPos > 0) {
            if (content.charAt(endPos) == '\n' || content.charAt(endPos) == '\r') {
                endPos--;
            } else {
                content = content.substring(0, endPos+1);
                break;
            }
        }

        return content;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public int getDeviceScreenWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        return w > h ? h : w;
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    public int dpToPx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * ((float) dp)+0.5);
    }
}