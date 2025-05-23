package com.economic.app.economic_app.util;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Utilidades para manipulação de datas no contexto da aplicação.
 */
public class DataUtils {
    
    /**
     * Ajusta um dia para o último dia do mês, caso seja maior que o número de dias do mês.
     * Por exemplo: dia 30 em fevereiro será ajustado para 28 ou 29 dependendo do ano.
     * 
     * @param dia O dia a ser ajustado
     * @param mes O mês (1-12)
     * @param ano O ano
     * @return O dia ajustado ao limite do mês
     */
    public static int ajustarDiaParaMes(int dia, int mes, int ano) {
        YearMonth yearMonth = YearMonth.of(ano, mes);
        int ultimoDiaDoMes = yearMonth.lengthOfMonth();
        
        return Math.min(dia, ultimoDiaDoMes);
    }
    
    /**
     * Obtém a data de fechamento ou vencimento do cartão para um determinado mês e ano.
     * Se o dia informado não existir no mês (ex: 30 de fevereiro), 
     * utiliza o último dia do mês.
     * 
     * @param diaReferencia O dia de referência (fechamento ou vencimento)
     * @param mes O mês (1-12)
     * @param ano O ano
     * @return A data de fechamento ou vencimento ajustada
     */
    public static LocalDate obterDataFaturaCartao(int diaReferencia, int mes, int ano) {
        int diaAjustado = ajustarDiaParaMes(diaReferencia, mes, ano);
        return LocalDate.of(ano, mes, diaAjustado);
    }
    
    /**
     * Obtém a data de fechamento ou vencimento do cartão para o mês atual ou próximo.
     * 
     * @param diaReferencia O dia de referência (fechamento ou vencimento)
     * @param dataAtual A data atual
     * @param usarProximoMes Se deve usar o próximo mês quando a data atual já passou do dia de referência
     * @return A data de fechamento ou vencimento
     */
    public static LocalDate obterDataParaCartao(int diaReferencia, LocalDate dataAtual, boolean usarProximoMes) {
        int mes = dataAtual.getMonthValue();
        int ano = dataAtual.getYear();
        
        // Se a data atual já passou do dia de referência e usarProximoMes é true,
        // usar o próximo mês
        if (usarProximoMes && dataAtual.getDayOfMonth() > diaReferencia) {
            if (mes == 12) {
                mes = 1;
                ano++;
            } else {
                mes++;
            }
        }
        
        return obterDataFaturaCartao(diaReferencia, mes, ano);
    }
} 