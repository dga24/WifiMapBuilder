package com.example.garci.positionsystemapp.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.example.garci.positionsystemapp.ProgressBarDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MyAsyncTask extends AsyncTask<Void,Integer,List<Pair<ITask, Integer>> > {

    private ArrayList<ITask> mTasks = new ArrayList<>();
    private ArrayList<OnFinishListener> mFinishListeners = new ArrayList<>();
    private ProgressDialog dialog;  // cambiar por dialogfragmens
    private Context context;
    ProgressBarDialogFragment progressBarDialogFragment = new ProgressBarDialogFragment();
    ProgressBar progressBar;

    public MyAsyncTask(Context context) {
        this.context = context;
        progressBarDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(),"");
        progressBar = progressBarDialogFragment.getProgressBar();
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
        progressBar.setProgress(0);
        progressBar.setMax(computeTotalWeight());
        progressBar.setVisibility(View.VISIBLE);

    }

    protected List<Pair<ITask, Integer>> doInBackground(Void... voidParameter) {
        if(mTasks.size()==0) return null;

        List<Pair<ITask, Integer>> result = new ArrayList<>();

        for(ITask task: mTasks) {
            dialog.setMessage(task.description());
            if (task.run() != ITask.EXIT_SUCCESS) {
                result.add(new Pair<>(task, 1));
            }

            publishProgress(task.weight());
        }

        return result;
    }

    protected void onProgressUpdate(Integer weight){
        dialog.setProgress(weight+dialog.getProgress());
    }

    protected void onPostExecute(List<Pair<ITask, Integer>> tasksThatFailed) {
        // Check if everything was fine.

        for (OnFinishListener listener: mFinishListeners) {
            listener.onFinsh(tasksThatFailed);
        }

        dialog.dismiss();
    }

}



