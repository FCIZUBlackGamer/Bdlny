package akhaled.shahen.com.exchangeme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fci on 04/03/18.
 */

public class Profile_history_Adapter extends RecyclerView.Adapter<Profile_history_Adapter.Hholder> {
    List<History_profile_item> history_strings;
    Context context;

    public Profile_history_Adapter(List<History_profile_item> history_strings, Context context) {
        this.history_strings = history_strings;
        this.context = context;
    }

    @Override
    public Profile_history_Adapter.Hholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_history_item,parent,false);
        return new Hholder(v);
    }

    @Override
    public void onBindViewHolder(final Profile_history_Adapter.Hholder holder, int position) {
        final History_profile_item s = history_strings.get( position );
        holder.name.setText(s.getName());
        holder.name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Result_Activity.class);
                intent.putExtra("capital",s.getCapital());
                intent.putExtra("price",s.getPrice());
                intent.putExtra("From","Profile");
                intent.putExtra("product_name",s.getName());
                intent.putExtra("product_img",s.getImg());
                intent.putExtra("type",s.getType());
                intent.putExtra("description",s.getDiscription());
                intent.putExtra("phone",s.getPhone());
                intent.putExtra("date",s.getDate());
                context.startActivity( intent );

            }
        } );
    }

    @Override
    public int getItemCount() {
        return history_strings.size();
    }

    public class Hholder extends RecyclerView.ViewHolder {
        TextView name;

        public Hholder(View itemView) {
            super( itemView );
            name = (TextView)itemView.findViewById( R.id.name_history );

        }
    }
}
