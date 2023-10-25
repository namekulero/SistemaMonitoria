package Clases.Estructuras.queue;

import Clases.Cita;
import java.time.LocalDateTime;

public class ColaCitas extends Cola<Cita> {
    @Override
    public boolean sort() {
        if (!isEmpty()) {
            ColaCitas cola1 = new ColaCitas();
            ColaCitas cola2 = new ColaCitas();
            int tamano = size();

            for (int i = 0; i < tamano; i++) {
                Cita citaActual = extract();
                while ((!cola1.isEmpty()) && (diferenciaEpoch(citaActual.getDateTime()) > diferenciaEpoch(cola1.peek().getDateTime()))) {
                    cola2.insert(cola1.extract());
                }
                cola1.insert(citaActual);
                while (!cola2.isEmpty()) {
                    cola1.insert(cola2.extract());
                }
            } 
            while (!cola1.isEmpty()) {
                insert(cola1.extract());
            }
            return true;
        }
        return false;
    }

    private long diferenciaEpoch(String dateTime) {
        LocalDateTime fechaActual = LocalDateTime.now();
        int year, month, day, hour, minute, second;
        year = Integer.valueOf(dateTime.substring(0, 3));
        month = Integer.valueOf(dateTime.substring(5, 6));
        day = Integer.valueOf(dateTime.substring(8, 9));
        hour = Integer.valueOf(dateTime.substring(11, 12));
        minute = Integer.valueOf(dateTime.substring(14, 15));
        second = Integer.valueOf(dateTime.substring(17, 18));
        LocalDateTime fechaCita = LocalDateTime.of(year, month, day, hour, minute, second);
        return fechaCita.toEpochSecond(null) - fechaActual.toEpochSecond(null);
    }
}
