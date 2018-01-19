package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.brenda.jccexample.Listeners.SwipeListViewTouchListener;
import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.SimpleListShowActivity;
import com.example.brenda.jccexample.adapters.RowListAdapter;
import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.DeleteActions;
import com.example.brenda.jccexample.dialogos.ProveedorSnackBar;
import com.example.brenda.jccexample.dialogos.ProveedorToast;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.Similar;
import com.example.brenda.jccexample.proveedores.ProveedorSolicitudes;

/**
 * Created by jcapiz on 9/10/17.
 */

public class SimpleListFragment extends Fragment {

    private Context context;
    private String[] modismos;
    private RowListAdapter rla;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.simple_list_view, parent, false);
        ListView listView = (ListView) rootView.findViewById(R.id.simple_list_view_list);
        Bundle args;
        modismos =
                (savedInstanceState == null ? ((args = getArguments()) != null ? args.getStringArray("modismos") : null) : savedInstanceState.getStringArray("modismos"));
        if(modismos != null) {
            listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, modismos));
            Log.e("SLF", "We got " + modismos.length + " to show");
        }else
            listView.setAdapter(rla = new RowListAdapter(context, new RowListAdapter.RowListListener() {
                @Override
                public void clickAction(Fragment fragment) {
                    ((SimpleListShowActivity) context).changeFragmentWithBackstack(fragment);
                }
            }));
        SwipeListViewTouchListener swipe;
        listView.setOnTouchListener(swipe = new SwipeListViewTouchListener(listView, new SwipeListViewTouchListener.OnSwipeCallback() {
            @Override
            public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
                if(rla != null) {
                    Modismo modismo = (Modismo) rla.getItem(reverseSortedPositions[0]);
                    rla.remove(modismo);
                    DeleteActions.deleteModismo(context, modismo);
                    ProveedorSnackBar.muestraBarraDeBocados(listView, "Hecho");
                }
                else
                    ProveedorSnackBar.muestraBarraDeBocados(listView, "Error");
            }

            @Override
            public void onSwipeRight(ListView listView, int[] reverseSortedPositions) {
                if(rla != null) {
                    Modismo modismo = (Modismo) rla.getItem(reverseSortedPositions[0]);
                    rla.remove(modismo);
                    DeleteActions.deleteModismo(context, modismo);
                    ProveedorSnackBar.muestraBarraDeBocados(listView, "Hecho");
                }
                else
                    ProveedorSnackBar.muestraBarraDeBocados(listView, "Error");
            }
        }, true, true));
        listView.setOnScrollListener(swipe.makeScrollListener());
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putStringArray("modismos", modismos);
    }
}
