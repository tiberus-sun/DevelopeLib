package app.szyl.com.developetools.model;

/**
 * Created by LiWenChao on 2018/8/14.
 */

public class SearchModel {
//    此处为搜索框的内容
    private String editValue; //此处为搜索框的内容
    private String district; //区县
    private String street; //乡镇
    private String timeStart; //时间起始
    private String timeEnd; //时间结束
    private String type;//单选1
    private String field;//单选2
    private String more;//更多
    private String doublemore;//更多2

    public String getEditValue() {
        return editValue;
    }

    public void setEditValue(String editValue) {
        this.editValue = editValue;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getDoublemore() {
        return doublemore;
    }

    public void setDoublemore(String doublemore) {
        this.doublemore = doublemore;
    }

    public SearchModel(String editValue, String district, String street, String timeStart, String timeEnd, String type, String field, String more, String doublemore) {
        this.editValue = editValue;
        this.district = district;
        this.street = street;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.type = type;
        this.field = field;
        this.more = more;
        this.doublemore = doublemore;
    }

    @Override
    public String toString() {
        return "条件：{" +
                "editValue='" + editValue + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", type='" + type + '\'' +
                ", field='" + field + '\'' +
                ", more='" + more + '\'' +
                ", doublemore='" + doublemore + '\'' +
                '}';
    }
}