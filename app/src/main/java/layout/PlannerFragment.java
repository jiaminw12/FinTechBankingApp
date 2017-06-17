package layout;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.ChartAdapter;
import Model.Charts;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class PlannerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    List<PieEntry> entries;
    ChartAdapter chartAdapter;
    private PieChart pieChart;
    private RecyclerView recyclerView;
    LinearLayoutManager llm;

    public PlannerFragment() {
        // Required empty public constructor
    }

    public static PlannerFragment newInstance() {
        PlannerFragment fragment = new PlannerFragment();
        return fragment;
    }

    public static PlannerFragment newInstance(String param) {
        PlannerFragment fragment = new PlannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        mUserId = param;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planner, container,
                false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvLegend);
        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        entries = new ArrayList<>();
        CategoryTask();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void CategoryTask() {

        JSONParser jsonParser = new JSONParser();
        WebServiceAddress webServiceAddress = new WebServiceAddress();
        final String TAG_SUCCESS = "success";
        int success = 0;
        List<Charts> charts = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", mUserId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllTransactionsByUserIdAnalysis"), params);


        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray jsonArray = json.getJSONArray("category");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        charts.add(new Charts(obj.getString("name"), obj
                                .getString("total")));
                        entries.add(new PieEntry(Float.parseFloat(obj
                                .getString("total")),
                                obj.getString("name")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        PieDataSet set = new PieDataSet(entries, "");

        set.setSliceSpace(1f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());

        set.setColors(colors);

        PieData data = new PieData(set);
        //data.setValueFormatter(new PercentFormatter());

        // Remove description
        pieChart.setDrawSliceText(false);
        data.setDrawValues(false);

        pieChart.setData(data);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        pieChart.getLegend().setEnabled(false);

        pieChart.invalidate(); // refresh

        Legend legend = pieChart.getLegend();
        int colorcodes[] = legend.getColors();

        chartAdapter = new ChartAdapter(charts, colorcodes);
        llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(chartAdapter);
    }

}
