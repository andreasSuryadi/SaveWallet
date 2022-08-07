package com.example.tabunganku.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tabunganku.R;
import com.example.tabunganku.adapter.WalletDetailAdapter;
import com.example.tabunganku.api.ApiEndPoint;
import com.example.tabunganku.api.ApiService;
import com.example.tabunganku.databinding.FragmentHomeBinding;
import com.example.tabunganku.model.user.UserModel;
import com.example.tabunganku.model.wallet.WalletDetailModel;
import com.example.tabunganku.model.wallet.WalletModel;
import com.example.tabunganku.response.UserResponse;
import com.example.tabunganku.response.WalletResponse;
import com.example.tabunganku.session.SessionManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 3 Agustus 2022
 *
 */

public class HomeFragment extends Fragment {
    private WalletDetailAdapter walletDetailAdapter;
    private FragmentHomeBinding binding;
    private List<WalletDetailModel> walletDetailModel;
    public SessionManager sessionManager;
    private static final int successCode=200;

    @BindView(R.id.recycler_view_home)
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView totalWalletText = binding.walletBalance;
        TextView dateText = binding.walletDate;
        TextView nameText = binding.profileName;

        getData(totalWalletText, dateText, nameText);
        
        return root;
    }

    private void getData(TextView totalWallet, TextView date, TextView name) {
        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        sessionManager = new SessionManager(getContext());
        name.setText(sessionManager.fetchName());

        String token = "Bearer " + sessionManager.fetchAuthToken();
        Date startDate = new Date();
        Date endDate = new Date();

        Call<WalletResponse> call = api.wallet(token, startDate, endDate);
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                int statusCode = response.code();

                if (statusCode == successCode) {
                    WalletModel walletModel = response.body().getWallet();
                    Integer totalInteger = Integer.parseInt(walletModel.getTotalWallet());
                    String str1 = "Rp ";
                    String total = str1.concat((String.format("%,d", totalInteger)).replace(',', '.'));
                    totalWallet.setText(total);
                    date.setText(walletModel.getCreatedAt());
                    walletDetailModel = response.body().getWalletDetails();

                    recyclerView = binding.recyclerViewHome;

                    walletDetailAdapter = new WalletDetailAdapter(getActivity(), walletDetailModel);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(walletDetailAdapter);
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}