package org.example.Exception;

import org.example.Model.Recompensa;
import org.example.Model.Relatorio;

public class RelatorioNotFoundException extends Exception {
    public RelatorioNotFoundException(String message) {
        super(message);
    }
}
