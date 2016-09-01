package sanmateo.avinnovz.com.sanmateoprofile.models.others;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by ctmanalo on 9/1/16.
 */
public class ImageUrlParent implements ParentObject {

    private List<Object> mChildren;
    private String title;

    public ImageUrlParent(List<Object> mChildren, String title) {
        this.mChildren = mChildren;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mChildren;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        this.mChildren.clear();
        this.mChildren.addAll(list);
    }
}
