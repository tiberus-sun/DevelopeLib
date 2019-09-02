package app.szyl.com.developetools.model;

import java.util.List;

/**
 * 区域下拉
 */
public class DistrictPlace {

    private String code;
    private String name;
    public List<DistrictPlace> child;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictPlace> getChild() {
        return child;
    }

    public void setChild(List<DistrictPlace> child) {
        this.child = child;
    }
}
