package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.HomeMenu;

/**
 * Created by rsbulanon on 6/26/16.
 */
public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.ViewHolder> {

    private ArrayList<HomeMenu> homeMenus;
    private Context context;
    private BaseActivity activity;
    private int[] colorShadeArray;
    private OnSelectHomeMenuListener onSelectHomeMenuListener;

    public HomeMenuAdapter(final Context context,final ArrayList<HomeMenu> homeMenus) {
        this.context = context;
        this.homeMenus = homeMenus;
        this.activity = (BaseActivity) context;
        this.colorShadeArray = context.getResources().getIntArray(R.array.shade_array);
    }

    @Override
    public int getItemCount() {
        return homeMenus.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_menu, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llMenu) LinearLayout llMenu;
        @BindView(R.id.ivMenuIcon) ImageView ivMenuIcon;
        @BindView(R.id.tvMenuTitle) TextView tvMenuTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final HomeMenu menu = homeMenus.get(i);
        holder.llMenu.setBackgroundColor(colorShadeArray[i]);
        holder.ivMenuIcon.setImageDrawable(menu.getMenuImage());
        holder.tvMenuTitle.setText(menu.getMenuTitle());
        holder.llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectHomeMenuListener != null) {
                    onSelectHomeMenuListener.onSelectedMenu(i);
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnSelectHomeMenuListener {
        void onSelectedMenu(final int position);
    }

    public void setOnSelectHomeMenuListener(OnSelectHomeMenuListener onSelectHomeMenuListener) {
        this.onSelectHomeMenuListener = onSelectHomeMenuListener;
    }
}
