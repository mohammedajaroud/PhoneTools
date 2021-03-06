package com.geniusgithub.calllog;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class CallLogAdapter extends RecyclerView.Adapter<ViewHolder>{

    private static final CommonLog log = LogFactory.createLog();
    
	private Context mContext;
    private Cursor mCursor;
    
    private CalllogAdapterHelper mCalllogAdapterHelper;
    
    
	public CallLogAdapter(Context context){
		super();
		mContext = context;
		mCalllogAdapterHelper = new CalllogAdapterHelper(mContext);
	}
	
	
   public void changeCursor(Cursor cursor) {
        if (cursor == mCursor) {
            return;
        }

        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;

        if (cursor != null) {
            notifyDataSetChanged();
        }
    }
   
   
   @Override
   public int getItemCount() {
	   int itemCount = 0;
       if (mCursor != null) {
    	   itemCount = mCursor.getCount();
       }
    //   log.i("getItemCount = " + itemCount);
       return itemCount;
   }
	
    public Object getItem(int position) {
        if (mCursor == null) {
            return null;
        }
        mCursor.moveToPosition(position);
        return mCursor;
    }
    
    
	
	private final int NORMAL_CALLLOG_TYPE = 0;
	
    @Override
    public int getItemViewType(int position) {
    	return NORMAL_CALLLOG_TYPE;
    }

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
	//	log.i("onBindViewHolder position = " + position);
        switch (getItemViewType(position)) {
            case NORMAL_CALLLOG_TYPE:
                bindCallLogListViewHolder(viewHolder, position);
                break;
            default:
                bindCallLogListViewHolder(viewHolder, position);
                break;
        }

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

		switch (viewType) {
		case NORMAL_CALLLOG_TYPE:
			return createCallLogEntryViewHolder(viewGroup);

		default:
			break;
		}

		return createCallLogEntryViewHolder(viewGroup);
	}
	
	
	
    private ViewHolder createCallLogEntryViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
     //   View view = inflater.inflate(R.layout.call_log_list_item, parent, false);
        View view = new CallLogSearchListItemView(mContext);
        CallLogListItemViewHolder viewHolder = new CallLogListItemViewHolder(view);

        return viewHolder;
    }
    
    private void bindCallLogListViewHolder(ViewHolder viewHolder, int position) {
        Cursor cursor = (Cursor) getItem(position);
        if (cursor == null) {
            return;
        }
        
        CallLogListItemViewHolder callLogListItemViewHolder = (CallLogListItemViewHolder) viewHolder;

        CallLogSearchListItemView itemView = callLogListItemViewHolder.mItemView;
        mCalllogAdapterHelper.bindView(itemView, mContext, cursor);
    }
    
    


}
