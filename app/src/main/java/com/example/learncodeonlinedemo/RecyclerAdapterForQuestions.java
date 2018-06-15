package com.example.learncodeonlinedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RecyclerAdapterForQuestions extends RecyclerView.Adapter<RecyclerAdapterForQuestions.ViewHolder> {
    private Context context;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    JSONArray dataArray;
//    OnClickFoldingCell onClickFoldingCell;

    public RecyclerAdapterForQuestions(Context context, JSONArray dataArray) {
        this.context = context;
        this.dataArray = dataArray;
    }

    public void setDataArray(JSONArray dataArray) {
        this.dataArray = dataArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(context);
        return new ViewHolder(vi.inflate(R.layout.layout_question_paper, parent, false));


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject item = null;
        try {
            item = dataArray.getJSONObject(position);
            holder.tvForQuestion.setText(Html.fromHtml("<font color='red'>Q: </font>" + item.getString("question")));
            holder.tvForAnswer.setText(item.getString("Answer"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dataArray.length();
    }


    // View lookup cache
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvForQuestion, tvForAnswer;


        public ViewHolder(View view) {
            super(view);

            tvForQuestion = view.findViewById(R.id.tvForQuestion);
            tvForAnswer = view.findViewById(R.id.tvForAnswer);
        }


    }

}
