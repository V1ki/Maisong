package com.yuanshi.maisong.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.NoticeDetailBean;
import com.yuanshi.maisong.view.MixedTextImageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/11/2.
 */
public class ShowTextImageActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.mixed_text_image_layout)
    MixedTextImageLayout mixedTextImageLayout;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.autherName)
    TextView autherName;
    @BindView(R.id.create_date)
    TextView createDate;
    @BindView(R.id.subline)
    TextView subline;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    private String id;
    private String title;
    private String requestType;

    @Override
    protected int getContentViewId() {
        return R.layout.show_text_image_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        requestType = getIntent().getStringExtra("requestType");
        if(TextUtils.isEmpty(id)){
            Toast.makeText(getApplicationContext(),  R.string.crew_id_null, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        titleText.setText(title);
        iHttpPresenter.details(requestType,id);
//        String content = "" +
//                "<img>http://img4.imgtn.bdimg.com/it/u=2321120463,723813631&fm=27&gp=0.jpg</img>" +
//                "    秋风送爽，清菊飘香，微露的晨曦里，有一抹霞染的云在旭日上轻舞。秋日的早晨，空气格外的清新，深深地吸上一口，有雨后草的清幽和花的馨香。几只麻雀在枝头欢快的鸣叫着，树叶上的露珠儿纷纷滑落……\n" +
//                "　　\n" +
//                "　　远山笼罩在一层轻似薄纱的白雾中，微风吹来，园子里花枝摇曳。一切都是那么宁静而安详。在安详中迎接美好的一天。我多想人生就像这秋晨一样，透着清新，溢着花香，沐着阳光……\n" +
//                "　　\n<img>http://img4.imgtn.bdimg.com/it/u=2321120463,723813631&fm=27&gp=0.jpg</img>" +
//                "　　盛开的花儿在晨曦里清新，在午夜里迷人。无论是哪一种花，都是大自然馈赠给我们的礼物。就像人生的际遇，或早或迟都是一种美好。生活中有风雨也有晴空，有得到也会有失去，心若简单，活着就简单。不管遇到多大的困难，都要微笑面对，相信，幸福就在不远处。\n" +
//                "　　\n<img>http://img3.imgtn.bdimg.com/it/u=1354592590,1762022981&fm=11&gp=0.jpg</img>" +
//                "　　生命需要沉淀才更加安稳，生活需要梳理才能看清脉络。静静的品读心音，倾听，时光留下的絮语……真诚的感谢那些曾经帮助过我们的人，感恩生活的惠赠，原谅那些伤害与错过。收藏源自心灵的体会和感悟，宠辱不惊，你会发现，生命因爱而博大，因芬芳而美丽，因给予而厚重。\n" +
//                "　　\n" +
//                "　　人生就是一种修行，在修行的过程中，我们要不断反思，取长补短，循序渐进。懂得知足，知福，知恩。春有百花秋望月，夏有凉风冬听雪。花开花落都是情，月缺月圆都是景。只要我们心存希望，以一颗慈悲之心善待一切，我们心灵的天空就会是晴朗的，我们的生活就会充满阳光。\n" +
//                "　　\n" +
//                "　　岁月如歌，声起声落。我们从懵懂无知，到成熟从容，慢慢沉淀出一颗纯净的心灵；时光如织，风雨中前行，阳光中奔跑，渐渐历练成一个坚强的灵魂。书上说，每一个不曾起舞的日子，都是对生命的辜负。每一处花香，每一个明媚的日子，都是生活给予我们最好的礼物。\n" +
//                "　　\n<img>https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510212221&di=4d5c7398270d061d0c3f430cc76724cd&imgtype=jpg&er=1&src=http%3A%2F%2Fimg16.3lian.com%2Fgif2016%2Fq19%2F52%2F108.jpg</img>" +
//                "　　在最美的年华里，拥有最好的人生。即便繁华落幕，也不留遗憾，你不用关心失去多少，只要知道自己还拥有什么就好。不管生活曾经给过你多少，放下即是心安，失去便是收获。\n" +
//                "　　\n" +
//                "　　其实，这个世上随处都有可见的美，只是我们少了发现美的心。春的明媚和冬的洁白，夏的绚丽和秋的静美，都是这个世界赋予我们的色彩。随着对生活愈来愈深的理解与体验，深深的懂得，拥有一份善良平常的心态，以及一份真实满足的心境，便是人生最美。\n" +
//                "　　\n" +
//                "　　清晨的花朵，因着昨夜的雨，谢了晚妆，枝上的叶子，因着多情的风，戚戚然去了远方。一夜之间，树上的叶子变了颜色，红色的秋叶，在阳光下显得格外迷人，把张扬的个性表现得淋漓尽致。小溪，依旧潺潺流淌着，那些不知名的野草，生命力极强，依然吮吸着它那生命之水，努力蓬勃着，生长着……";
//        mixedTextImageLayout.setContent(content);


    }

    private void initData(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        NoticeDetailBean notice = gson.fromJson(json,NoticeDetailBean.class);
        if(!TextUtils.isEmpty(notice.getTitle())){
            textTitle.setText(notice.getTitle());
        }
        if(!TextUtils.isEmpty(notice.getAuthor())){
            autherName.setText("@"+notice.getAuthor());
        }
        if(!TextUtils.isEmpty(notice.getAddtime())){
            createDate.setText(notice.getAddtime());
        }
        buildContent(notice);
    }

    private void buildContent(NoticeDetailBean notice){
        StringBuilder content = new StringBuilder();

        if(!TextUtils.isEmpty(notice.getContent()) && notice.getPics() != null && notice.getPics().length > 0){
            String[] contents = notice.getContent().split("\n");
            if(contents.length <= notice.getPics().length){
                for (int i = 0; i < contents.length; i ++){
                    content.append("<img>").append(notice.getPics()[i]).append("</img>");
                    content.append(contents[i]);
                }
                for(int i = contents.length; i < notice.getPics().length; i ++ ){
                    content.append(contents[i]);
                }
            }else{
                for (int i = 0; i < notice.getPics().length; i ++){
                    content.append("<img>").append(notice.getPics()[i]).append("</img>");
                    content.append(contents[i]);
                }
                for(int i = notice.getPics().length; i < contents.length; i ++ ){
                    content.append(contents[i]);
                }
            }
        }else if(notice.getPics() == null|| notice.getPics().length == 0){
            content.append(notice.getContent());
        }

        mixedTextImageLayout.setContent(content.toString());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        },200);
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case Constant.HTTP_REQUEST_REMIND+":details":
            case Constant.HTTP_REQUEST_SCRIPTPAGE+":details":
                initData(obj);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_icon)
    public void onViewClicked() {
        finish();
    }
}
