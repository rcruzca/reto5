package com.usa.reto.Service;

import com.usa.reto.Model.Reservation;
import com.usa.reto.Model.reports.ClientsCounter;
import com.usa.reto.Model.reports.ReservationsStatus;
import com.usa.reto.Repository.RepositoryReservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceReservation {

    @Autowired
    private RepositoryReservation repository;

    public List<Reservation> getAll() {
        return repository.getAll();
    }

    public Optional<Reservation> getReservation(int id) {
        return repository.getReservation(id);
    }

    public Reservation save(Reservation r) {
        if (r.getIdReservation() == null) {
            return repository.save(r);
        } else {
            Optional<Reservation> rAux = repository.getReservation(r.getIdReservation());
            if (rAux.isEmpty()) {
                return repository.save(r);
            } else {
                return r;
            }
        }
    }

    public Reservation update(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            Optional<Reservation> g = repository.getReservation(reservation.getIdReservation());
            if (!g.isEmpty()) {

                if (reservation.getDevolutionDate() != null) {
                    g.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if (reservation.getStartDate() != null) {
                    g.get().setStartDate(reservation.getStartDate());
                }
                if (reservation.getStatus() != null) {
                    g.get().setStatus(reservation.getStatus());
                }

                return repository.save(g.get());
            }
        }
        return reservation;
    }

    public boolean deleteReservation(int id) {
        Boolean d = getReservation(id).map(reservation -> {
            repository.delete(reservation);
            return true;
        }).orElse(false);
        return d;
    }

    public List<ClientsCounter> reportClientsService() {
        return repository.getClientsRepository();
    }

    public ReservationsStatus reportStatusService() {
        List<Reservation> completed = repository.ReservationStatusRepository("completed");
        List<Reservation> cancelled = repository.ReservationStatusRepository("cancelled");

        return new ReservationsStatus(completed.size(), cancelled.size());
    }

    public List<Reservation> reportServiceTime(String dateA, String dateB) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

        Date dateOne = new Date();
        Date dateTwo = new Date();

        try {
            dateOne = parser.parse(dateA);
            dateTwo = parser.parse(dateB);
        } catch (ParseException evt) {
            evt.printStackTrace();
        }
        if (dateOne.before(dateTwo)) {
            return repository.ReservationTimeRepository(dateOne, dateTwo);
        } else {
            return new ArrayList<>();

        }
    }
}