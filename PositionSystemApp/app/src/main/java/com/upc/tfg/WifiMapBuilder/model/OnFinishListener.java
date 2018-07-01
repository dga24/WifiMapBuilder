package com.upc.tfg.WifiMapBuilder.model;

import android.util.Pair;

import java.util.List;

public interface OnFinishListener {
    void onFinsh(List<Pair<ITask, Integer>> tasksThatFailed);
}
