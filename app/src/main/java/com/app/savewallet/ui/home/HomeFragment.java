package com.app.savewallet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.savewallet.R;
import com.app.savewallet.adapter.WalletDetailAdapter;
import com.app.savewallet.api.ApiEndPoint;
import com.app.savewallet.api.ApiService;
import com.app.savewallet.databinding.FragmentHomeBinding;
import com.app.savewallet.model.wallet.WalletDetailModel;
import com.app.savewallet.model.wallet.WalletModel;
import com.app.savewallet.response.WalletResponse;
import com.app.savewallet.session.SessionManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        TextView totalWalletIncomeText = binding.incomeWallet;
        TextView totalWalletExpenseText = binding.expenseWallet;
        TextView dateText = binding.walletDate;
        TextView nameText = binding.profileName;

        getData(totalWalletText, totalWalletIncomeText, totalWalletExpenseText, dateText, nameText);
        
        return root;
    }

    private void getData(TextView totalWallet, TextView incomeWallet, TextView expenseWallet, TextView date, TextView name) {
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
                    String str1 = "Rp ";

                    Integer totalWalletInteger = Integer.parseInt(walletModel.getTotalWallet());
                    String totalWalletString = str1.concat((String.format("%,d", totalWalletInteger)).replace(',', '.'));
                    totalWallet.setText(totalWalletString);

                    Integer totalWalletIncomeInteger = Integer.parseInt(walletModel.getTotalWalletIncome());
                    String totalWalletIncomeString = str1.concat((String.format("%,d", totalWalletIncomeInteger)).replace(',', '.'));
                    incomeWallet.setText(totalWalletIncomeString);

                    Integer totalWalletExpenseInteger = Integer.parseInt(walletModel.getTotalWalletExpense());
                    String totalWalletExpenseString = str1.concat((String.format("%,d", totalWalletExpenseInteger)).replace(',', '.'));
                    expenseWallet.setText(totalWalletExpenseString);

                    String pattern = "dd MMM yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String dateText = simpleDateFormat.format(new Date());

                    date.setText(dateText);
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