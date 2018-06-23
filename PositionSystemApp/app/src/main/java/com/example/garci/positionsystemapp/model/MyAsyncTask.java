package com.example.garci.positionsystemapp.model;



import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.example.garci.positionsystemapp.ProgressBarDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MyAsyncTask extends AsyncTask<Void,Integer,List<Pair<ITask, Integer>> > {

    private ArrayList<ITask> mTasks = new ArrayList<>();
    private ArrayList<OnFinishListener> mFinishListeners = new ArrayList<>();
    private Context context;

    private ProgressDialog progressDialog;

    public MyAsyncTask(Context context) {
        this.context = context;
    }

    int computeTotalWeight() {
        int result = 0;

        for (ITask task: mTasks)
            result += task.weight();

        return result;
    }

    void addTask(ITask task){
        mTasks.add(task);
    }

    void addOnFinishListener(OnFinishListener onFinishListener) {
        mFinishListeners.add(onFinishListener);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //poner dialog a 0 y show
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgress(0);
        progressDialog.setMax(computeTotalWeight());
        progressDialog.show();

    }

    protected List<Pair<ITask, Integer>> doInBackground(Void... voidParameter) {
        if(mTasks.size()==0) return null;

        List<Pair<ITask, Integer>> result = new ArrayList<>();

        for(ITask task: mTasks) {
            //progressBar.setContentDescription(task.description());
            progressDialog.setMessage(task.description());
            if (task.run() != ITask.EXIT_SUCCESS) {
                result.add(new Pair<>(task, 1));
            }
            publishProgress(task.weight());
        }

        return result;
    }

    protected void onProgressUpdate(Integer weight){
        progressDialog.setProgress(weight+progressDialog.getProgress());
    }

    protected void onPostExecute(List<Pair<ITask, Integer>> tasksThatFailed) {
        // Check if everything was fine.

        for (OnFinishListener listener: mFinishListeners) {
            listener.onFinsh(tasksThatFailed);
        }

        progressDialog.dismiss();
    }

}



