package com.example.shopping.homescreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    ArrayList<Product> product;
    IClickIntemProductListener iClickIntemProductListener;
//    Context context;
//    public ProductAdapter(Context context, ArrayList<Product> product){
    public ProductAdapter(ArrayList<Product> product, IClickIntemProductListener listener){
        this.product = product;
        iClickIntemProductListener = listener;
//        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()); //from(context);
        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Product product_ = product.get(position);

        holder.textName.setText(product_.gethatname());
        holder.textprice.setText("đ" + String.format("%,d", product_.getprice()) + " VNĐ");

//        new DownloadImageTask(holder.image).execute(product_.getimage());
        Picasso.get().load(product_.getimage()).resize(100, 150).into(holder.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(this, "chức năng đang hoàn thiện", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(context, UpdateOrDelete.class);
//                Intent intent = new Intent(context, ProductDetail.class);
//                intent.putExtra("product", product_);
//                context.startActivity(intent);
                iClickIntemProductListener.onClickItemProduct(product_);

            }
        });


    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textName, textprice;
        CardView card;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textName = itemView.findViewById(R.id.textName);
            textprice = itemView.findViewById(R.id.textprice);
            card = itemView.findViewById(R.id.each_item_layout);

        }
    }

/*
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
*/
}
