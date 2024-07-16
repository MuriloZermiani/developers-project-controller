package com.example.controleprojetodev.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class UtilsGUI {

    public static void aviso(Context contexto, int idTexto){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(com.example.controleprojetodev.R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(idTexto);

       /* builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/

        builder.setNeutralButton(contexto.getString(com.example.controleprojetodev.R.string.ok), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void confirmaAcao(Context contexto,
                                    String mensagem,
                                    DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(com.example.controleprojetodev.R.string.confirmacao);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setPositiveButton("Sim", listener);
        builder.setNegativeButton("Não", listener);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
