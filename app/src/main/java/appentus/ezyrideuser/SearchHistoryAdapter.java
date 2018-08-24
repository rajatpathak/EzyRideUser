package appentus.ezyrideuser;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import appentus.ezyrideuser.Model.FavPlaceModel;
import appentus.ezyrideuser.apiConnect.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHistoryAdapter  extends   RecyclerView.Adapter<SearchHistoryAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<FavPlaceModel.Result>  productList;
    private ApiInterface apiService;

    SearchHistoryAdapter searchHistoryAdapter;
    //getting the context and product list with constructor
    public SearchHistoryAdapter(Context mCtx, List<FavPlaceModel.Result> productList,ApiInterface apiService) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.apiService= apiService;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.place_history_card, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        //getting the product of the specified position
        final FavPlaceModel.Result  product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getLocation());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favId=productList.get(position).getFavId();
                apiRemoveFav(favId);


                //remove item from list
               productList.remove(position);
               notifyItemRemoved(position);
               notifyItemRangeChanged(position,productList.size());
            }
        });


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }




    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView ivDelete;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.place1);
            ivDelete= itemView.findViewById(R.id.ivDelete);

        }
    }




    private void apiRemoveFav(final String favId) {

        ConstantData.progressDialog.show();
        Call<FavPlaceModel> call = apiService.removeFavLoc(favId);
        call.enqueue(new Callback<FavPlaceModel>() {
            @Override
            public void onResponse(Call<FavPlaceModel>call, Response<FavPlaceModel> response) {

                if (response.isSuccessful()) {
                    FavPlaceModel favPlaceModel = response.body();

                    if (favPlaceModel.getStatus().equals(ConstantData.API_STATUS)) {
                        Log.e("deleteFav "+favId,  "");
                        ConstantData.progressDialog.dismiss();

                    }
                    else {
                        Toast.makeText(mCtx,"Please try again",Toast.LENGTH_SHORT).show();
                        ConstantData.progressDialog.dismiss();
                    }

                }
                else
                {
                    ConstantData.progressDialog.dismiss();
                    Toast.makeText(mCtx,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavPlaceModel>call, Throwable t) {
                // Log error here since request failed
                ConstantData.progressDialog.dismiss();
                Toast.makeText(mCtx,"Network issue",Toast.LENGTH_SHORT).show();
            }
        });
    }
}