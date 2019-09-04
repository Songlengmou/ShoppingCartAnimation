package com.example.shoppingcartanimation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;


/**
 * @author admin
 */
public class FoodAdapter extends BaseAdapter {
    private List<FoodModel> models;
    private Context context;
    private FoodActionCallback callback;

    public FoodAdapter(Context context, List<FoodModel> models, FoodActionCallback callback) {
        this.context = context;
        this.models = models;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final FoodModel model = models.get(position);
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.item_good_name.setText(model.getName());
        viewHolder.item_good_price.setText("￥" + priceResult(model.getPrice()));
        viewHolder.item_gooddescription.setText(model.getDescription());
        viewHolder.item_good_num.setText("" + model.getNum());

        viewHolder.item_good_add.setOnClickListener(v -> {
            if (callback == null) {
                return;
            }
            callback.addAction(v, position);
        });
        viewHolder.item_good_reduce.setOnClickListener(v -> {
            if (callback == null || 0 == model.getNum()) {
                return;
            }
            callback.reduceGood(position);
        });
        return convertView;
    }


    public static class ViewHolder {
        TextView item_good_name, item_good_price, item_gooddescription, item_good_num;
        ImageView item_good_add, item_good_reduce;


        public ViewHolder(View convertView) {
            item_good_name = convertView.findViewById(R.id.item_good_name);
            item_good_price = convertView.findViewById(R.id.item_good_price);
            item_gooddescription = convertView.findViewById(R.id.item_gooddescription);
            item_good_num = convertView.findViewById(R.id.item_good_num);
            item_good_add = convertView.findViewById(R.id.item_good_add);
            item_good_reduce = convertView.findViewById(R.id.item_good_reduce);
        }
    }

    public interface FoodActionCallback {
        void addAction(View view, int position);

        void reduceGood(int position);
    }

    /**
     * 单位精度计算(价格)
     *
     * @param price
     * @return
     */
    public String priceResult(double price) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(new BigDecimal(price));
    }
}
