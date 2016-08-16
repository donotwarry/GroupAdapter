package com.tencent.dennyjdeng.groupadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * Group style adapter like iOS for ListView, it's split by section views.<p/>
 *
 * Notice:section view is not clickable default, but you can overwrite #isEnabled() to change it.
 *
 * Created by dennyjdeng on 2016/8/12.
 */
public abstract class GroupAdapter extends BaseAdapter {

    private final Context context;

    public GroupAdapter(Context context) {
        this.context = context;
    }

    @Override
    final public long getItemId(int position) {
        return 0;
    }

    @Override
    final public Object getItem(int position) {
        return null;
    }

    @Override
    final public int getCount() {
        int sectionCount = getSectionCount();
        int count = sectionCount;
        for (int i = 0; i < sectionCount; i++) {
            count += getCountInSection(i);
        }
        return count;
    }

    /**
     * Total section count, this function need subclass to implement.
     */
    abstract public int getSectionCount();

    /**
     * Item count in section, this function need subclass to implement.
     *
     * @param section which section
     * @return item count
     */
    abstract public int getCountInSection(int section);

    /**
     * Return whether it's a section view or a section item at given original position in list view.
     *
     * @param position original position
     * @return true is section view, other section item.
     */
    public boolean isSectionAtPosition(int position) {
        return getIndexForPosition(position).row == -1;
    }

    /**
     * Convert the given original position in list view to an IndexPath item.
     *
     * @param position original position
     * @return an IndexPath object
     */
    public IndexPath getIndexForPosition(int position) {
        IndexPath index = new IndexPath();
        int sumCount = 0;
        int section;
        for (section = 0; section < getSectionCount(); section++) {
            int rowCount = getCountInSection(section);
            sumCount += (rowCount + 1);
            if (position < sumCount) {
                index.section = section;
                index.row = rowCount - (sumCount - position);
                break;
            }
        }
        return index;
    }

    @Override
    public boolean isEnabled(int position) {
        if (isSectionAtPosition(position)) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    final public View getView(int position, View convertView, ViewGroup parent) {
        IndexPath indexPath = getIndexForPosition(position);
        if (indexPath.row == -1) {
            View view = getViewForSection(convertView, parent, indexPath.section);
            if (!isEnabled(position)) {
                view.setClickable(true);
                view.setFocusable(true);
            }
            return view;
        } else {
            View view = getViewForRow(convertView, parent, indexPath.section, indexPath.row);
            view.setBackgroundColor(getBackgroundColorForRow(indexPath));
            return view;
        }
    }

    /**
     * Get a row view that displays the data at the specified position in the data set.
     *
     * @param convertView Convert view, can be used for reuse item.
     * @param parent The parent that this view will eventually be attached to.
     * @param section The section index.
     * @param row The row index in section.
     * @return A View corresponding to the data at the specified position.
     */
    abstract public View getViewForRow(View convertView, ViewGroup parent, int section, int row);

    /**
     * Set the backgroud color for row item.
     *
     * @param indexPath
     * @return
     */
    public int getBackgroundColorForRow(IndexPath indexPath) {
        return Color.WHITE;
    }

    /**
     * Get a section view at the specified position.
     *
     * @param convertView  Convert view, can be used for reuse item.
     * @param parent The parent that this view will eventually be attached to.
     * @param section The section index.
     * @return A section view corresponding to the data at the specified position.
     */
    public View getViewForSection(View convertView, ViewGroup parent, int section) {
        View view = new View(context);
        view.setBackgroundColor(Color.parseColor("#F4F4F4"));
        view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                getHeightForSectionView(section)));
        return view;
    }

    /**
     * Set height for the default section view, default is 10 dip.
     *
     * @param section which section to set
     * @return
     */
    public int getHeightForSectionView(int section) {
        return dip2px(context, 10);
    }

    /**
     * Convert dip size to px size.
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if(context == null) {
            return (int)dipValue;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int)(dipValue * scale + 0.5F);
        }
    }

    /**
     * List item position data wrapper.
     */
    public static class IndexPath {
        /**
         * section index
         */
        public int section;
        /**
         * row index, start with 0. if the value equal -1, which means the position is a section view.
         */
        public int row;
    }
}
