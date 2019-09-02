package app.szyl.com.developetools.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.model.DistrictPlace;
import app.szyl.com.developetools.model.SearchModel;
import app.szyl.com.developetools.util.ToolUtils;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class MyFilterDropMenuAdapter implements MenuAdapter,View.OnClickListener {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;

    private TextView date_start;
    private TextView date_end;

    //组合搜索条件
    private SearchModel searchModel;

    public MyFilterDropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;

        searchModel=new SearchModel("","","","","","","","","");
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        /*if (position == 3) {
            return 0;
        }*/
        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);
        ArrayList list=null;
        switch (position) {

            case 0:
                String json = ToolUtils.readText(mContext, "district.json");
                ArrayList<DistrictPlace> data = new Gson().fromJson(json, new TypeToken<ArrayList<DistrictPlace>>() {}.getType());
                view = createDoubleListView(0,data);
                break;
            case 1: //时间选择
                view = createDateView(parentContainer);
                break;
            case 2:
                String[] fense= new String[]{"全部" ,"轻工" ,"煤矿" ,"石油天然气" ,"建筑施工" ,"化工" ,
                        "烟花爆竹" ,"冶金", "机械", "贸易", "铁路交通","民航飞行","渔业船舶","农业机械","其他"} ;

                list = new ArrayList<>();
                list.addAll(Arrays.asList(fense));
                view = createSingleGridView(2,list);
                break;
            case 3:
                String[] abc= new String[]{"全部" ," 农、林、牧、渔业" ,"电力、热力、燃气及水生产和供应业" ,"交通运输、仓储和邮政业",
                        "信息传输、软件和信息技术服务业","信息传输、软件和信息技术服务业","公共管理、社会保障和社会组织",
                        "科学研究和技术服务业","其他行业"} ;
                list = new ArrayList<>();
                list.addAll(Arrays.asList(abc));
                view = createSingleListView(3,list);
                break;
            default:
                String[] fense1= new String[]{"全部" ,"轻工" ,"煤矿" ,"石油天然气" ,"建筑施工" ,"化工" ,
                        "烟花爆竹" ,"冶金", "机械", "贸易", "铁路交通","民航飞行","渔业船舶","农业机械","其他"} ;

                list = new ArrayList<>();
                list.addAll(Arrays.asList(fense1));
                view = createSingleGridView(2,list);
                break;

        }

        return view;
    }



    //时间选择数据
    private View createDateView(FrameLayout parent) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.drop_menu_date, parent, false);

        date_start=(TextView)convertView.findViewById(R.id.date_start);
        date_end=(TextView)convertView.findViewById(R.id.date_end);
        TextView tv_reset=(TextView)convertView.findViewById(R.id.tv_reset);
        TextView tv_confirm=(TextView)convertView.findViewById(R.id.tv_confirm);

        date_start.setOnClickListener(this);
        date_end.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        return convertView;
    }


    //单项选择数据
    private View createSingleListView(final int Tposition, List<String> list) {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);

                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {

                        //singleParam=item;
                        if(item.equals("全部")){
                            searchModel.setField("");
                        }else{
                            searchModel.setField(item);
                        }

                        onFilterDone(Tposition,item);
                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
    }

    //两项选择数据
    private View createDoubleListView(final int Tposition, List<DistrictPlace> data) {
        DoubleListView<DistrictPlace, DistrictPlace> comTypeDoubleListView = new DoubleListView<DistrictPlace, DistrictPlace>(mContext)
                .leftAdapter(new SimpleTextAdapter<DistrictPlace>(null, mContext) {
                    @Override
                    public String provideText(DistrictPlace districtPlace) {
                        return districtPlace.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<DistrictPlace>(null, mContext) {
                    @Override
                    public String provideText(DistrictPlace districtPlace) {
                        return districtPlace.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<DistrictPlace, DistrictPlace>() {
                    @Override
                    public List<DistrictPlace> provideRightList(DistrictPlace item, int position) {
                        List<DistrictPlace> child = item.getChild();
                        if (CommonUtil.isEmpty(child)) {

                            String district=item.getName();
                            if(district.equals("全部")){
                                searchModel.setDistrict("");
                            }else{
                                searchModel.setDistrict(district);
                            }
                            onFilterDone(Tposition,district);
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<DistrictPlace, DistrictPlace>() {
                    @Override
                    public void onRightItemClick(DistrictPlace item, DistrictPlace rightItem) {

                        String street=rightItem.getName();
                        if(street.equals("全部")){
                            searchModel.setStreet("");
                        }else{
                            searchModel.setStreet(street);
                        }
                        searchModel.setDistrict(item.getName());
                        /*doubleLeftParam1=item.getName();
                        doubleRightParam1=rightItem.getName();*/

                        onFilterDone(Tposition,street);
                    }
                });


        //初始化选中.
        comTypeDoubleListView.setLeftList(data, 0);
        comTypeDoubleListView.setRightList(data.get(0).getChild(), 0);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }

    //单项选择数据GridView
    private View createSingleGridView(final int Tposition, List<String> list) {
        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {

                        switch (Tposition){
                            case 2:  //分类
                                if(item.equals("全部")){
                                    searchModel.setType("");
                                }else{
                                    searchModel.setType(item);
                                }
                                break;
                        }

                        onFilterDone(Tposition,item);
                    }
                });

        singleGridView.setList(list, -1);

        return singleGridView;
    }




    private void onFilterDone(int position, String positionTitle) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(position, positionTitle, "");
        }
    }

    //获取筛选条件方法
    public SearchModel getFilterData(){

        return  searchModel;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.date_start:
                ToolUtils.popTimePicker(date_start,mContext);
                break;
            case R.id.date_end:
                ToolUtils.popTimePicker(date_end,mContext);
                break;
            case R.id.tv_reset:
                date_start.setText("");
                date_end.setText("");
                break;
            case R.id.tv_confirm:

                String dateStart=date_start.getText().toString();
                String dateEnd=date_end.getText().toString();
                //时间校验
                if(ToolUtils.isNullOrEmpty(dateStart)||ToolUtils.isNullOrEmpty(dateEnd)){
                    searchModel.setTimeStart(dateStart);
                    searchModel.setTimeEnd(dateEnd);

                    onFilterDone(1,"");
                }else{
                   if(ToolUtils.parseTime(dateStart)>=ToolUtils.parseTime(dateEnd)){
                       Toast.makeText(mContext,"结束时间不能小于起始时间",Toast.LENGTH_SHORT).show();
                   }else{
                       searchModel.setTimeStart(dateStart);
                       searchModel.setTimeEnd(dateEnd);

                       onFilterDone(1,"");
                   }
                }

                break;
        }

    }
}
