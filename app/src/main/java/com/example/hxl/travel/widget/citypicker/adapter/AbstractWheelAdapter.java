package com.example.hxl.travel.widget.citypicker.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxl on 2017/1/13 at haiChou.
 * Abstract Wheel adapter.
 */
public abstract class AbstractWheelAdapter implements WheelViewAdapter {
    /**
     * DataSetObserver表示了一个数据集对象的观察者
     * 当数据集有变化时，会调用DataSetObserver的onChanged()方法；
     * 当数据集失效时，会调用DataSetObserver的onINvalidated()方法。
     */
    private List<DataSetObserver> datasetObservers;

    @Override
    public View getEmptyItem(View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (datasetObservers == null) {
            datasetObservers = new LinkedList<DataSetObserver>();
        }
        datasetObservers.add(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (datasetObservers != null) {
            datasetObservers.remove(observer);
        }
    }

    /**
     * Notifies observers about data changing
     */
    protected void notifyDataChangedEvent() {
        if (datasetObservers != null) {
            for (DataSetObserver observer : datasetObservers) {
                observer.onChanged();
            }
        }
    }

    /**
     * Notifies observers about invalidating data
     */
    protected void notifyDataInvalidatedEvent() {
        if (datasetObservers != null) {
            for (DataSetObserver observer : datasetObservers) {
                observer.onInvalidated();
            }
        }
    }
}
