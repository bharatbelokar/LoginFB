package bharat.com.fblogin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shrikant on 26-01-2018.
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.MyViewHolder> {

    private List<LikeResponse> likeResponses;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,data;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
        linearLayout=(LinearLayout)view.findViewById(R.id.linearLayoutid);
            date = (TextView) view.findViewById(R.id.date);
            data = (TextView) view.findViewById(R.id.data);
        }
    }


    public LikeAdapter(List<LikeResponse> likeResponses,Context context) {
        this.likeResponses = likeResponses;
        this.context=context;
    }

    @Override
    public LikeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LikeResponse likeResponse = likeResponses.get(position);
        holder.data.setText(likeResponse.getData());
        holder.date.setText(likeResponse.getDate());

    }

    @Override
    public int getItemCount() {
        return likeResponses.size();
    }
}
