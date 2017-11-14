package com.example.brenda.jccexample.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.activities.EfectoDeEnfoque;
import com.example.brenda.jccexample.activities.MuestraMensajeDesdeHilo;
import com.example.brenda.jccexample.activities.ProveedorDeRecursos;
import com.example.brenda.jccexample.activities.Verificador;
import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.pojo.Ejemplo;
import com.example.brenda.jccexample.pojo.Modismo;
import com.example.brenda.jccexample.pojo.Significado;
import com.example.brenda.jccexample.pojo.Similar;

/**
 * Created by jcapiz on 26/03/16.
 */
public class RegistroDeModismo extends DialogFragment {

    private EditText expresion;
    private EditText ejemplo;
    private EditText significado;
    private EditText similar;
    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        context = getContext();
        View rootView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.formato_registro_de_modismo, null, false);
        expresion = (EditText) rootView.findViewById(R.id.formato_registro_de_modismo_expresion);
        expresion.setOnFocusChangeListener(new EfectoDeEnfoque(getActivity(),rootView.findViewById(R.id.formato_registro_de_modismo_piso_expresion)));
        ejemplo = (EditText) rootView.findViewById(R.id.formato_registro_de_modismo_ejemplo);
        ejemplo.setOnFocusChangeListener(new EfectoDeEnfoque(getActivity(), rootView.findViewById(R.id.formato_registro_de_modismo_piso_ejemplo)));
        significado = (EditText) rootView.findViewById(R.id.formato_registro_de_modismo_significado);
        significado.setOnFocusChangeListener(new EfectoDeEnfoque(getActivity(), rootView.findViewById(R.id.formato_registro_de_modismo_piso_significado)));
        similar = (EditText) rootView.findViewById(R.id.formato_registro_de_modismo_similar);
        similar.setOnFocusChangeListener(new EfectoDeEnfoque(getActivity(), rootView.findViewById(R.id.formato_registro_de_modismo_piso_similar)));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle(getArguments().getString("titulo"))
                .setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        guardaEnBaseDeDatos();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setView(rootView)
                .create();
    }

    private void guardaEnBaseDeDatos() {
        boolean[] validacion = new boolean[4];
        validacion[0] = Verificador.marcaCampo(getContext(), expresion, Verificador.TEXTO);
        validacion[1] = Verificador.marcaCampo(getContext(), ejemplo, Verificador.TEXTO);
        validacion[2] = Verificador.marcaCampo(getContext(), significado, Verificador.TEXTO);
        validacion[3] = Verificador.marcaCampo(getContext(), similar, Verificador.TEXTO);
        if(validacion[0]) {
            Modismo modismo = new Modismo(AccionesLectura.obtenerUltimoIdModismo(getActivity()) + 1);
            modismo.setExpresion(expresion.getText().toString());
            modismo.setPais(ProveedorDeRecursos.obtenerRecursoInt(getActivity(), ProveedorDeRecursos.PAIS_ACTUAL));
            AccionesEscritura.escribeModismo(getActivity(), modismo);
            if(validacion[1]) {
                Ejemplo ej = new Ejemplo(AccionesLectura.obtenerUltimoIdEjemplo(getActivity()) + 1);
                ej.setEjemplo(ejemplo.getText().toString());
                ej.setIdModismo(modismo.getIdModismo());
                AccionesEscritura.escribeEjemplo(getActivity(), ej);
            }
            if(validacion[2]){
                Significado sig = new Significado(AccionesLectura.obtenerUltimoIdSignificado(getActivity()) +1);
                sig.setSignificado(significado.getText().toString());
                sig.setIdModismo(modismo.getIdModismo());
                AccionesEscritura.escribeSignificado(getActivity(), sig);
            }
            if(validacion[3]){
                Similar sim = new Similar(AccionesLectura.obtenerUltimoIdSimilar(getActivity()) +1);
                sim.setSimilar(similar.getText().toString());
                sim.setIdModismo(modismo.getIdModismo());
                AccionesEscritura.escribeSimilar(getActivity(), sim);
            }
            MuestraMensajeDesdeHilo.muestraToast((AppCompatActivity)context, "Hecho");
        }else{

            MuestraMensajeDesdeHilo.muestraToast((AppCompatActivity)context, "Modismo no guardado n.n");
        }
    }

    private boolean validarInformacion() {
        boolean[] validacion = new boolean[4];
        validacion[0] = Verificador.marcaCampo(getContext(), expresion, Verificador.TEXTO);
        validacion[1] = Verificador.marcaCampo(getContext(), ejemplo, Verificador.TEXTO);
        validacion[2] = Verificador.marcaCampo(getContext(), significado, Verificador.TEXTO);
        validacion[3] = Verificador.marcaCampo(getContext(), similar, Verificador.TEXTO);
        return validacion[0] && validacion[1] && validacion[2] && validacion[3];
    }
}
