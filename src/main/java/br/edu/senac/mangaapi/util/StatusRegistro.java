package br.edu.senac.mangaapi.util;

public final class StatusRegistro {

    private StatusRegistro() {
    }

    public static final int APAGADO = -1;
    public static final int INATIVO = 0;
    public static final int ATIVO = 1;

    public static boolean isValido(Integer status) {
        return status != null && (status == APAGADO || status == INATIVO || status == ATIVO);
    }

    public static int valorOuAtivo(Integer status) {
        return status == null ? ATIVO : status;
    }
}
