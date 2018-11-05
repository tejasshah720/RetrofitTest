package com.my.retrofittest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.my.retrofittest.R;
import com.my.retrofittest.model.AlbumInfo;

import java.util.List;

/**
 * Created by Tejas Shah on 29/10/18.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    //private List<AlbumInfo> lstAlbumTitle;
    private SortedList<AlbumInfo> lstAlbumInfo;
    private Context mContext;

   /* public AlbumAdapter(SortedList<AlbumInfo> lstAlbumTitle, Context mContext) {
        this.lstAlbumTitle = lstAlbumTitle;
        this.mContext = mContext;
    }*/

    public AlbumAdapter(){
        lstAlbumInfo = new SortedList<AlbumInfo>(AlbumInfo.class, new SortedList.Callback<AlbumInfo>() {
            @Override
            public int compare(AlbumInfo albumInfo1, AlbumInfo albumInfo2) {
                return albumInfo1.getTitle().compareTo(albumInfo2.getTitle());
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(AlbumInfo oldalbumInfo, AlbumInfo newalbumInfo) {
                return oldalbumInfo.getTitle().equals(newalbumInfo.getTitle());
            }

            @Override
            public boolean areItemsTheSame(AlbumInfo albumInfo1, AlbumInfo albumInfo2) {
                return albumInfo1.getTitle().equals(albumInfo2.getTitle());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position,count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    public void addAll(List<AlbumInfo> allAlbumInfo) {
        lstAlbumInfo.beginBatchedUpdates();
        for (int i = 0; i < allAlbumInfo.size(); i++) {
            lstAlbumInfo.add(allAlbumInfo.get(i));
        }
        lstAlbumInfo.endBatchedUpdates();
    }

    public AlbumInfo get(int position){
        return lstAlbumInfo.get(position);
    }

    public void clear() {
        lstAlbumInfo.beginBatchedUpdates();
        while (lstAlbumInfo.size() > 0) {
            lstAlbumInfo.removeItemAt(lstAlbumInfo.size() - 1);
        }
        lstAlbumInfo.endBatchedUpdates();
    }

    @NonNull
    @Override
    public AlbumAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.title_row_layout, viewGroup, false);
        return new AlbumViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumViewHolder albumViewHolder, int position) {
            albumViewHolder.txtAlbumTitle.setText(lstAlbumInfo.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return lstAlbumInfo == null ? 0 : lstAlbumInfo.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{
        private final View mView;
        private TextView txtAlbumTitle;

        private AlbumViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtAlbumTitle = (TextView) itemView.findViewById(R.id.txtAlbumTitle);
        }
    }
}
