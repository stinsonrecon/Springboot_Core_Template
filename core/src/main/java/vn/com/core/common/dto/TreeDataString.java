package vn.com.core.common.dto;

import vn.com.core.common.utils.ValidationUtil;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class TreeDataString<T extends TreeDataString, K> {

    @Transient
    protected Long level;

    @Transient
    private boolean hasChild;


    @Transient
    private List<T> children;

    //Wrap Object data la du lieu dac trung cho nghiep vu
    @Transient
    private K data;

    public K getData() {
        return data;
    }

    public void setData(K data) {
        this.data = data;
    }


    public abstract Long getLevel();

    public abstract String getNodeCode();

    public abstract String getParentNodeCode();

    public abstract boolean isRoot();

    public boolean isHasChild() {
        if (!ValidationUtil.isNullOrEmpty(children)) {

            return true;
        }
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public List<T> getChildren() {
//		if (isHasChild()){
//			children = new ArrayList<TreeData>();
//		}
        return children;
    }

    public void addChild(T child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public void setLevel(Long level) {
        this.level = level;
    }
}
