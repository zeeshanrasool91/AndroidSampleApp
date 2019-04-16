package com.example.myapplication.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.android.databinding.library.baseAdapters.BR;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemMostPopularBinding;
import com.example.myapplication.model.MoviesListResponse;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MoviesListResponse> dataModelList;
    private List<MoviesListResponse> FilteredDataList;
    private Context context;
    private ItemClickListener onItemClickListener;

    public void setDeviceWidth(int deviceWidth) {
        this.deviceWidth = deviceWidth;
    }

    private int deviceWidth=-1;

    public void setDeviceHeight(int deviceHeight) {
        this.deviceHeight = deviceHeight;
    }

    private int deviceHeight=-1;


    public RecyclerViewAdapter(Context ctx) {
        context = ctx;
        this.dataModelList = new ArrayList<>();
    }

    public RecyclerViewAdapter(List<MoviesListResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        ItemMostPopularBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_most_popular, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MoviesListResponse dataModel = dataModelList.get(holder.getAdapterPosition());
        holder.bind(dataModel);
        /*if(!dataModelList.get(position).getAdult()) {
            MoviesListResponse dataModel = dataModelList.get(position);
            holder.bind(dataModel);
        }*/
        if(deviceWidth != -1) {
            holder.itemRowBinding.mainLayout.getLayoutParams().width = deviceWidth;
        }if(deviceHeight != -1) {
            holder.itemRowBinding.mainLayout.getLayoutParams().height = deviceHeight;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(dataModel,holder.getAdapterPosition());
                }
            }
        });
    }

    public void setItems(List<MoviesListResponse> dataModelList){
        this.dataModelList=dataModelList;
        notifyDataSetChanged();
    }
    public void setFilteredItems(List<MoviesListResponse> dataModelList){
        this.dataModelList.clear();
        notifyDataSetChanged();
        for(MoviesListResponse data : dataModelList) {
           if(! data.getAdult()){
               this.dataModelList.add(data);
           }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ItemMostPopularBinding itemRowBinding;

        public ViewHolder(ItemMostPopularBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.responseModel, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public interface ItemClickListener {
        void onItemClick(MoviesListResponse moviesListResponse, int position);
    }


}