package com.usa.reto.Repository;

import com.usa.reto.Model.Client;
import com.usa.reto.Model.Reservation;
import com.usa.reto.Model.reports.ClientsCounter;
import com.usa.reto.Repository.Crud.RepositoryCrudReservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryReservation {

    @Autowired
    private RepositoryCrudReservation repository;

    public List<Reservation> getAll() {
        return (List<Reservation>) repository.findAll();
    }

    public Optional<Reservation> getReservation(int id) {
        return repository.findById(id);
    }

    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    public void delete(Reservation reservation) {
        repository.delete(reservation);
    }

    public List<Reservation> ReservationStatusRepository(String status) {
        return repository.findAllByStatus(status);

    }

    public List<Reservation> ReservationTimeRepository(Date dateOne, Date dateTwo) {
        return repository.findAllByStartDateAfterAndStartDateBefore(dateOne, dateTwo);
    }

    public List<ClientsCounter> getClientsRepository() {
        List<ClientsCounter> res = new ArrayList<>();
        List<Object[]> report = repository.countTotalReservationsByClient();
        for (int i = 0; i < report.size(); i++) {
            res.add(new ClientsCounter((Long) report.get(i)[1], (Client) report.get(i)[0]));
        }
        return res;

    }
}