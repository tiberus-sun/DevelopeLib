package com.szyl.szyllibrary.view.poplist.ListWindow;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JCY on 2017/6/29.
 */
public class BuildList {
    public static final String CHECK = "checkbox";
    public static final String NAME = "name";
//    生成一个List<HashMap<String, Object>> 里面是checkbox 为巡检界面用  主要选择巡检的点还是巡检的线
    public static ArrayList<HashMap<String, Object>> createList (String list){
        String[] split = list.split(",");
        ArrayList<HashMap<String, Object>> listxj_checkbox = new ArrayList<>();
        for(int i=0;i<split.length;i++){
            HashMap<String, Object> dxmap = new HashMap<>();

                dxmap.put(NAME,split[i]);


            listxj_checkbox.add(dxmap);
        }

        return listxj_checkbox;
    }




}
//public enum FacilityType
//{
//       0 蓄水池,
//        1泵房,
//        2消防队,
//       3 消火栓,
//        4无线电基站,
//       5 瞭望台,
//        6监控中心,
//      7  监控点,
//      8  集中焚烧炉,
//      9  护林房,
//       10 防火指挥中心,
//       11 仓库,
//      12  消防管道,
//      13  隔离网,
//      14  防火通道,
//       15 防火隔离带,
//      16  天然水源,
//      17  瞭望哨,
//      18  防火通道哨卡
//        }
