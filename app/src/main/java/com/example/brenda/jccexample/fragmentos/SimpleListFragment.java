package com.example.brenda.jccexample.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brenda.jccexample.Listeners.SwipeListViewTouchListener;
import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.SimpleListShowActivity;
import com.example.brenda.jccexample.adapters.RowListAdapter;
import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.database.DeleteActions;
import com.example.brenda.jccexample.dialogos.ActualizacionDeModismo;
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
        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(listView,new SwipeListViewTouchListener.OnSwipeCallback() {
            @Override
            public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
                //Aqui ponemos lo que hara el programa cuando deslizamos un item ha la izquierda
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
            public void onSwipeRight(ListView listView, int [] reverseSortedPositions) {
                //Aqui ponemos lo que hara el programa cuando deslizamos un item ha la derecha
                if(rla != null) {
                    Modismo modismo = (Modismo) rla.getItem(reverseSortedPositions[0]);
                    rla.remove(modismo);
                    DeleteActions.deleteModismo(context, modismo);
                    ProveedorSnackBar.muestraBarraDeBocados(listView, "Hecho");
                }
                else
                    ProveedorSnackBar.muestraBarraDeBocados(listView, "Error");
            }
        },true, false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SignificadoListFragment slf = new SignificadoListFragment();
                Bundle args = new Bundle();
                args.putInt("idModismo", ((Modismo)rla.getItem(i)).getIdModismo());
                slf.setArguments(args);
                ((SimpleListShowActivity) context).changeFragmentWithBackstack(slf);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long l) {
                ActualizacionDeModismo adm = new ActualizacionDeModismo();
                adm.setAm(new ActualizacionDeModismo.ActualizacionModismo() {
                    @Override
                    public void listActions(ActualizacionDeModismo actualizacionDeModismo) {
                        Modismo modismo = (Modismo)rla.getItem(position);
                        modismo.setExpresion(actualizacionDeModismo.getExpresion().getText().toString());
                        ((TextView) view.findViewById(R.id.modismo_row_view_expresion)).setText(actualizacionDeModismo.getExpresion().getText().toString());
                        ((TextView) view.findViewById(R.id.modismo_row_view_significado)).setText(actualizacionDeModismo.getSignificado().getText().toString());
                        rla.notifyDataSetChanged();
                    }
                });
                Bundle args = new Bundle();
                args.putString("titulo", ((TextView) view.findViewById(R.id.modismo_row_view_expresion)).getText().toString());
                args.putInt("idModismo", ((Modismo)rla.getItem(position)).getIdModismo());
                args.putString("expresion", ((Modismo)rla.getItem(position)).getExpresion());
                args.putString("ejemplo", AccionesLectura.obtenerEjemplo(context, ((Modismo)rla.getItem(position))).getEjemplo());
                args.putString("significado", AccionesLectura.obtenerSignificado(context, ((Modismo)rla.getItem(position))).getSignificado());
                args.putString("similar", AccionesLectura.obtenerSimilar(context, ((Modismo)rla.getItem(position))).getSimilar());
                adm.setArguments(args);
                adm.show(((AppCompatActivity) context).getSupportFragmentManager(), "Modificar Modismo");
                return false;
            }
        });
        //Escuchadores del listView
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putStringArray("modismos", modismos);
    }
}
