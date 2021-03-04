package com.example.productive2;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;





public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<NoteData> notes;
    ArrayAdapter adapter;
    private Context mContext;
    ImageView imageView;
    Spinner spinner;



    //constructor for adapter to pass the data.  Display context and List notes in the RV.
    Adapter(Context context, List<NoteData> notes) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;

    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //use custom view to inflate our list.
        //pass new view
        View view = inflater.inflate(R.layout.custom_list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    // bind textview to the data from the database.
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        //create string for title, date, and time.
        String title = notes.get(position).getTitle();
        String date = notes.get(position).getDate();
        String time = notes.get(position).getTime();

        //use Viewholder to bind data to textview.
        holder.nTitle.setText(title);
        holder.nDate.setText(date);
        holder.nTime.setText(time);

    }

    @Override
    public int getItemCount() {

        return notes.size();
    }




    //ViewHolder class to start binding our data.
    //retrieve data from notes list and set the data to our title, date and time using ViewHolder.
    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
        TextView nTitle, nDate, nTime;
        Spinner spinner;
        List<SpinnerValues> values;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nTitle = itemView.findViewById(R.id.nTitle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);
            imageView = itemView.findViewById(R.id.imageView);
            spinner = (Spinner) itemView.findViewById(R.id.spinner1);




            List<SpinnerValues> values = new ArrayList<>();
            SpinnerValues value1 = new SpinnerValues("SELECT");
            values.add(value1);
            SpinnerValues value2 = new SpinnerValues("LOW");
            values.add(value2);
            SpinnerValues value3 = new SpinnerValues("MEDIUM");
            values.add(value3);
            SpinnerValues value4 = new SpinnerValues("HIGH");
            values.add(value4);

            ArrayAdapter<SpinnerValues> adapter = new ArrayAdapter<SpinnerValues>(mContext,
                    android.R.layout.simple_spinner_item, values);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);





//               ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
//                    R.array.Importance, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner.setAdapter(adapter);
//            spinner.setOnItemSelectedListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Details.class);
                    intent.putExtra("ID", notes.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(intent);
                }
            });
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String spinnerValues = parent.getSelectedItem().toString();
//            String item = parent.getItemAtPosition(position).toString();
//            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            if(spinnerValues.equals("SELECT")){
                Toast.makeText(mContext, "SELECT", Toast.LENGTH_SHORT).show();
            }else if(spinnerValues.equals("LOW")){
                imageView.setImageResource(R.drawable.green);
            }else if(spinnerValues.equals("MEDIUM")){
                imageView.setImageResource(R.drawable.yellow);
            }else if(spinnerValues.equals("HIGH")){
                imageView.setImageResource(R.drawable.red);
            }else{
                imageView.setImageResource(R.drawable.light_off);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    }
}
