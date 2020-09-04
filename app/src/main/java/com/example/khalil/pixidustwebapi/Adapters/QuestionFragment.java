package com.example.khalil.pixidustwebapi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalil.pixidustwebapi.Activities.JobDetail;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;
import com.example.khalil.pixidustwebapi.R;

import java.io.ByteArrayOutputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalil on 1/29/2018.
 */

public class QuestionFragment extends Fragment {
    static RecyclerView recyclerView;
    public static Integer questionPosition;
    public int fileUploadedQuestionPosition;
    public static String[] QuestionsAnswers;
    public static ArrayList<SpecificJobDetail.JobsQuestions> questionList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.questionsrecyclerview, container, false);
        questionList = (ArrayList<SpecificJobDetail.JobsQuestions>) getArguments().getSerializable("QuestionList");
        QuestionFragment.QuestionContentAdapter adapter = new QuestionFragment.QuestionContentAdapter(recyclerView.getContext(),questionList);
        recyclerView.setAdapter(adapter);
        QuestionsAnswers=new String[questionList.size()];
        // Set padding for Tiles
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemViewCacheSize(questionList.size());
        return recyclerView;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public static ImageView button;
        public static Spinner spinner;
        public static EditText editText;
        public static EditText editTextArea;
        public static CheckBox checkBox1;
        public static CheckBox checkBox2;
        public final TextView textPicturePath;
        public static TextView textViewfile;
        public static TextView textViewtext;
        public static TextView textViewtextarea;
        public static TextView textViewcheckbox;
        public static TextView textViewdropdown;

        public static LinearLayout linearLayoutfile;
        public static LinearLayout linearLayouttext;
        public static LinearLayout linearLayouttextarea;
        public static LinearLayout linearLayoutcheckbox;
        public static LinearLayout linearLayoutspinner;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.question_listview, parent, false));
            ViewHolder.textViewfile = (TextView) itemView.findViewById(R.id.questionfiletext);
            ViewHolder.textViewtext = (TextView) itemView.findViewById(R.id.questiontext);
            ViewHolder.textViewtextarea = (TextView) itemView.findViewById(R.id.questiontextarea);
            ViewHolder.textViewcheckbox = (TextView) itemView.findViewById(R.id.questiontextcheckbox);
            ViewHolder.textViewdropdown = (TextView) itemView.findViewById(R.id.questiontextdropdown);
            ViewHolder.button = (ImageView) itemView.findViewById(R.id.btnfile);
            ViewHolder.spinner = (Spinner) itemView.findViewById(R.id.spinner);
            ViewHolder.editText = (EditText) itemView.findViewById(R.id.txtTextbox);
            ViewHolder.editTextArea = (EditText) itemView.findViewById(R.id.txtTextarea);
            ViewHolder.checkBox1 = (CheckBox) itemView.findViewById(R.id.chkboxyes);
            ViewHolder.checkBox2 = (CheckBox) itemView.findViewById(R.id.chkboxno);
            ViewHolder.linearLayoutfile = (LinearLayout) itemView.findViewById(R.id.showfile);
            ViewHolder.linearLayouttext = (LinearLayout) itemView.findViewById(R.id.showedittext);
            ViewHolder.linearLayouttextarea = (LinearLayout) itemView.findViewById(R.id.showedittextarea);
            ViewHolder.linearLayoutcheckbox = (LinearLayout) itemView.findViewById(R.id.showcheckbox);
            ViewHolder.linearLayoutspinner = (LinearLayout) itemView.findViewById(R.id.showdropdown);
            textPicturePath = (TextView) itemView.findViewById(R.id.txtPicturePath);
        }
    }
    public  class QuestionContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        ArrayList<SpecificJobDetail.JobsQuestions> questionList;
        Context context;
        public QuestionContentAdapter(Context paramContext, ArrayList<SpecificJobDetail.JobsQuestions> paramquestioslist) {
            questionList  = paramquestioslist;
            context=paramContext;
        }
        @Override
        public QuestionFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new QuestionFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final QuestionFragment.ViewHolder holder, final int position) {
               questionPosition=position;
               final SpecificJobDetail.JobsQuestions ObjQuestions = questionList.get(position);
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               switch (questionList.get(position).QuestionType) {
                   case 1:
                       holder.textViewtext.setText(questionList.get(position).QuestionName);
                       holder.linearLayouttext.setVisibility(View.VISIBLE);
                       holder.editText.addTextChangedListener(new TextWatcher() {

                           @Override
                           public void onTextChanged(CharSequence s, int start,
                                                     int before, int count) {
                               //setting data to array, when changed
                               // this is a semplified example in the actual app i save the text
                               // in  a .txt in the external storage

                               QuestionsAnswers[position] = s.toString();
                           }

                           @Override
                           public void beforeTextChanged(CharSequence s, int start,
                                                         int count, int after) {
                               Integer value=after;
                           }

                           @Override
                           public void afterTextChanged(Editable s) {
                               Editable value=s;

                           }
                       });
                       break;
                   case 2:
                       holder.textViewtextarea.setText(questionList.get(position).QuestionName);
                       holder.linearLayouttextarea.setVisibility(View.VISIBLE);
                       holder.editTextArea.addTextChangedListener(new TextWatcher() {

                           @Override
                           public void onTextChanged(CharSequence s, int start,
                                                     int before, int count) {
                               //setting data to array, when changed
                               // this is a semplified example in the actual app i save the text
                               // in  a .txt in the external storage

                               QuestionsAnswers[position] = s.toString();
                           }

                           @Override
                           public void beforeTextChanged(CharSequence s, int start,
                                                         int count, int after) {
                               Integer value=after;
                           }

                           @Override
                           public void afterTextChanged(Editable s) {
                               Editable value=s;

                           }
                       });
                       break;

                   case 3:
                       holder.textViewcheckbox.setText(questionList.get(position).QuestionName);
                       holder.linearLayoutcheckbox.setVisibility(View.VISIBLE);
                       for (SpecificJobDetail.QustionsAnswers object : ObjQuestions.QustionsAnswers) {
                           if (ObjQuestions.QustionsAnswers.indexOf(object) > 0) {
                               ViewHolder.checkBox2.setText(object.Title);
                               ViewHolder.checkBox2.setVisibility(View.VISIBLE);
                           } else {
                               ViewHolder.checkBox1.setText(object.Title);
                           }
                       }
                       ViewHolder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                               if(isChecked) {
                                   QuestionsAnswers[position] = Integer.toString(questionList.get(position).QustionsAnswers.get(0).ID);
                               }
                           }
                       });
                       ViewHolder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                               if(isChecked) {
                                   QuestionsAnswers[position] = Integer.toString(questionList.get(position).QustionsAnswers.get(1).ID);
                               }
                           }
                       });
                       break;
                   case 4:
                       holder.textViewdropdown.setText(questionList.get(position).QuestionName);
                       holder.linearLayoutspinner.setVisibility(View.VISIBLE);
                       ArrayList<String> answers = new ArrayList<String>();//add names in this list
                       answers.add("Select");
                       for (SpecificJobDetail.QustionsAnswers object : ObjQuestions.QustionsAnswers) {
                           answers.add(object.Title);
                       }
                       ArrayAdapter<String> dataAdapter;
                       dataAdapter = new ArrayAdapter<String>(context,
                               android.R.layout.simple_spinner_item, answers);
                       dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> parent, View view,
                                                      int pos, long id) {
                               if(pos!=0) {
                                   QuestionsAnswers[position] = Integer.toString(ObjQuestions.QustionsAnswers.get(pos-1).ID);
                               }
                           }

                           @Override
                           public void onNothingSelected(AdapterView<?> arg0) {
                           }
                       });
                       holder.spinner.setAdapter(dataAdapter);
                       break;
                   case 5:
                       holder.textViewfile.setText(questionList.get(position).QuestionName);
                       holder.linearLayoutfile.setVisibility(View.VISIBLE);
                       holder.button.setOnClickListener(new View.OnClickListener()
                       { @Override public void onClick(View v) {
                           fileUploadedQuestionPosition=position;
                           try{ Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                               startActivityForResult(intent, 1);
                           }
                           catch(Exception e){ e.printStackTrace();
                           } } });
                       break;
                   default:
                       break;
               }
           }
        @Override
        public int getItemCount() {
            return questionList.size();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = recyclerView.getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            QuestionsAnswers[fileUploadedQuestionPosition] = picturePath;
            View h = recyclerView.findViewHolderForAdapterPosition(fileUploadedQuestionPosition).itemView;
            TextView textView = (TextView) h.findViewById(R.id.txtPicturePath);
            textView.setText(picturePath.substring(picturePath.lastIndexOf("/")+1));

            cursor.close();
        }
    }
}
