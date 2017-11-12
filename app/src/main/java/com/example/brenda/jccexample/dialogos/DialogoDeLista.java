package com.example.brenda.jccexample.dialogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by jcapiz on 16/03/16.
 */
public class DialogoDeLista extends DialogFragment {

    private int stringArrayRes;
    private String titulo;
    private String[] elementos;
    private AccionDialogoDeLista accion;

    public void setStringArrayRes(int stringArrayRes) { this.stringArrayRes = stringArrayRes; }

    public void setElementos(String[] elementos) { this.elementos = elementos; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAccion(AccionDialogoDeLista accion) {
        this.accion = accion;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder = builder.setTitle(titulo);
        if(elementos != null)
            builder = builder.setItems(elementos, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // of the selected item
                    accion.objetoSeleccionado(elementos[which]);
                }
            });
        else
            builder = builder.setItems(stringArrayRes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // of the selected item
                    accion.objetoSeleccionado(getActivity().getResources().getStringArray(stringArrayRes)[which]);
                }
            });
        return builder.create();
    }

    public interface AccionDialogoDeLista{
        void objetoSeleccionado(String texto);
    }
}
